/**
 * Copyright 2013 Yamato
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mod.ymt.air;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import mod.ymt.air.cmn.Coord3D;
import mod.ymt.air.cmn.Utils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author Yamato
 *
 */
public class Materializer {
	private final AirCraftCore core = AirCraftCore.getInstance();

	private final int blocklimit;
	public final World world;
	public final ImitationSpace space;
	private final Set<Block> moveable;

	public Materializer(World world, ImitationSpace space, int blocklimit, Set<Block> moveable) {
		this.world = world;
		this.blocklimit = blocklimit;
		this.space = space;
		this.moveable = moveable;
	}

	public void addCoreBlock(int x, int y, int z, Block block, int metadata) {
		BlockData data = BlockData.valueOf(block, metadata, Coord3D.ZERO, new Coord3D(x, y, z));
		if (data != null) { // ありえないはずだけど
			space.setBlockData(data);
		}
	}

	public NBTTagCompound getImitationTileEntity(Coord3D absPos) {
		return space.getTileEntityData(absPos);
	}

	public boolean putBlocks(int x, int y, int z, int rotate) {
		// y 座標の下から配置していく
		List<BlockData> allBlocks = new LinkedList<BlockData>(space.getAllBlocks());
		Collections.sort(allBlocks, new BlockDataPuttingComparator());

		// 一番上と一番下が、ワールド内に収まっていることを確認する
		if (!allBlocks.isEmpty()) {
			int minRelY = Integer.MAX_VALUE, maxRelY = Integer.MIN_VALUE;
			for (BlockData data: allBlocks) {
				if (data.relPos.y < minRelY)
					minRelY = data.relPos.y;
				if (maxRelY < data.relPos.y)
					maxRelY = data.relPos.y;
			}
			if (y + minRelY < 0) { // 最下部が y < 0
				return false;
			}
			if (world.getHeight() <= y + maxRelY + 1) { // 最上部が MaxHeight <= y
				return false;
			}
		}
		// 配置
		processPutBlocks(allBlocks, x, y, z, rotate);

		return true;
	}

	public boolean removeBlocks(int x, int y, int z) {
		core.logFine("remove start!");

		// 探索
		Coord3D base = new Coord3D(x, y, z);
		List<Coord3D> allPoints = new LinkedList<Coord3D>(traceBlock(base)); // 絶対座標系
		core.logFine("get %s blocks traced", allPoints.size());
		// スキャン
		processScanBlocks(allPoints, base);

		// 消去
		List<BlockData> allBlocks = new ArrayList<BlockData>(space.getAllBlocks());
		Collections.sort(allBlocks, new BlockDataRemovingComparator());
		for (BlockData block: allBlocks) {
			setRealBlock(block.absPos.x, block.absPos.y, block.absPos.z, null, 0);
		}

		// 表面計算
		space.updateServerSurface();
		core.logFine("remove %s blocks", space.countAllBlocks());
		core.logFine("surface %s blocks", space.countSurfaceBlocks());

		return true;
	}

	public void setImitationBlock(BlockData block) {
		space.setBlockData(block);
	}

	public void setImitationTileEntity(NBTTagCompound tag) {
		space.setTileEntityData(tag);
	}

	public boolean setRealBlock(int x, int y, int z, Block block, int metadata) {
		if (Utils.isServerSide(world)) {
			if (block == null) {
				block = Blocks.air;
			}
			world.setBlock(x, y, z, block, metadata, 2);
			return true;
		}
		return false;
	}

	private boolean isSurface(World world, Set<Coord3D> all, Coord3D pos) {
		for (Coord3D np: pos.getNeighbor()) {
			if (!all.contains(np)) {
				return true;
			}
			Block block = world.getBlock(np.x, np.y, np.z);
			if (block == null || block == Blocks.air || !block.isOpaqueCube()) {
				return true;
			}
		}
		return false;
	}

	private void processPutBlocks(List<BlockData> allBlocks, int x, int y, int z, int rotate) {
		for (BlockData data: allBlocks) {
			Operator op = core.getBlockOperator(data.block);
			Coord3D pos = data.relPos.rotate(rotate).move(x, y, z);
			// 配置
			op.putBlocksToWorld(this, data, pos, rotate);
		}
	}

	private void processScanBlocks(List<Coord3D> allPoints, Coord3D base) {
		for (Coord3D pos: allPoints) {
			Block block = world.getBlock(pos.x, pos.y, pos.z);
			if (block != null && block != Blocks.air) {
				// スキャン
				core.getBlockOperator(block).pickBlockFromWorld(this, pos, base);
			}
		}
	}

	protected boolean isMoveable(Block block) {
		if (block == Blocks.bedrock)
			return false;
		return moveable.contains(block);
	}

	protected Set<Coord3D> traceBlock(Coord3D base) {
		int surfaceCount = 1; // コアブロックがすでに1個追加されてるはずなので1スタート
		Set<Coord3D> result = new HashSet<Coord3D>();
		Deque<Coord3D> nextPos = new LinkedList<Coord3D>(Arrays.asList(base.getNeighbor()));
		// 探索
		for (Coord3D pos = nextPos.poll(); pos != null && surfaceCount < blocklimit; pos = nextPos.poll()) {
			surfaceCount += traceBlock(result, pos, nextPos);
		}
		return result;
	}

	protected int traceBlock(Set<Coord3D> allblock, Coord3D pos, Deque<Coord3D> nextPos) {
		if (pos.y < 0) {
			return 0;
		}
		Block block = world.getBlock(pos.x, pos.y, pos.z);
		if (block == null || !isMoveable(block)) {
			return 0;
		}
		if (!allblock.add(pos)) { // 追加
			return 0;
		}
		int ret = 1; // 表面ブロック数
		for (Coord3D np: pos.getNeighbor()) {
			if (allblock.contains(np)) {
				// 既に探索済みならば表面計算
				if (!isSurface(world, allblock, np)) {
					ret--; // 表面でないならば表面ブロック数を -1
				}
			}
			else {
				// 未探索ならば次回追加
				nextPos.addLast(np);
			}
		}
		return ret;
	}
}
