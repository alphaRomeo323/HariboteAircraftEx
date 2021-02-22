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
import java.util.Collection;
import java.util.List;
import mod.ymt.air.cmn.Coord3D;
import mod.ymt.air.cmn.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author Yamato
 *
 */
public abstract class EntityImitator extends EntityCraftCore {
	/**
	 * DataWatcher -> BlockID
	 */
	protected final int DWKEY_BLOCK = 12;
	/**
	 * DataWatcher -> x
	 */
	protected final int DWKEY_BLOCK_X = 13;
	/**
	 * DataWatcher -> y
	 */
	protected final int DWKEY_BLOCK_Y = 14;
	/**
	 * DataWatcher -> z
	 */
	protected final int DWKEY_BLOCK_Z = 15;
	
	protected final ImitationSpace space;
	protected State status = State.INIT;
	protected boolean requestSurfaceFromClient = false;
	
	protected int glCallList = -1;
	protected boolean glUpdateList = true;
	protected boolean glDisposed = false;
	
	protected EntityImitator(World world) {
		super(world);
		this.space = new ImitationSpace(world);
		this.renderDistanceWeight = 10.0D; // 10倍くらい遠くでもレンダリングする
		this.ignoreFrustumCheck = true; // このエンティティはカメラ外でもレンダリングする
		core.registerImitator(this);
	}
	
	public void addClientSemiSurfaces(byte[] data) {
		space.addClientNonSurfaceBlocks(deserializeClientBlocks(data));
		glUpdateList = true;
	}
	
	public void addClientSurfaces(byte[] data) {
		space.addClientSurfaceBlocks(deserializeClientBlocks(data));
		glUpdateList = true;
	}
	
	public void addClientTileData(NBTTagCompound tag) {
		if (tag != null) {
			space.setTileEntityData(tag);
		}
	}
	
	public void dispose() {
		synchronized (this) {
			if (isDead == false) {
				isDead = true; // setDead では余計な処理がされる可能性がある
			}
			super.dispose();
			if (!glDisposed) {
				if (0 < glCallList) {
					GLAllocation.deleteDisplayLists(glCallList);
				}
				glCallList = -1;
				glUpdateList = false;
				glDisposed = true;
			}
			space.clear();
		}
		core.cleanupImitator();
	}
	
	public ImitationSpace getImitationSpace() {
		return space;
	}
	
	public State getStatus() {
		return status;
	}
	
	public List<BlockData> getSurfaces() {
		return space.getSurfaceBlocks();
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate(); // TODO ディメンジョン移動できるようにする
		cleanSubEntity();
		if (Utils.isServerSide(worldObj)) {
			switch (getStatus()) {
				case INIT: {
					removeBlock();
					break;
				}
				case RUNNING: {
					onEntityPositionUpdate();
					break;
				}
				case PUTBLOCK: {
					putBlock();
					break;
				}
				default: {
					setDead();
				}
			}
			if (requestSurfaceFromClient) {
				requestSurfaceFromClient = false;
				processSendSurfaceToClient(); // クライアントに表面データを送信
			}
		}
		else {
			switch (getStatus()) {
				case INIT:
					// サーバに表面データをリクエスト
					core.net.sendRequestSurfacesToServer(getEntityId());
					status = State.RUNNING;
					break;
				default:
					onEntityPositionUpdate();
					break;
			}
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		// とりあえず全部読み込んじゃう
		byte status = tag.getByte("ImitatorStatus");
		int block = tag.getInteger("ThisBlock");
		int this_x = tag.getInteger("ThisBlockX");
		int this_y = tag.getInteger("ThisBlockY");
		int this_z = tag.getInteger("ThisBlockZ");
		byte[] blocksData = tag.getByteArray("Blocks");
		NBTTagList tileData = tag.getTagList("TileEntities", 10); // 10 = NBTTagCompound
		
		// エンティティ情報を反映
		setStatus(State.fromCode(status));
		
		// このブロック情報を反映
		setThisBlock(block, this_x, this_y, this_z);
		// 保持しているブロック情報を反映
		space.setServerAllBlocks(newSerializer().deserialize(new Coord3D(this_x, this_y, this_z), blocksData));
		
		// 保持している TileEntity 情報を反映
		for (int i = 0; i < tileData.tagCount(); i++) {
			NBTBase base = tileData.getCompoundTagAt(i);
			if (base instanceof NBTTagCompound)
				space.setTileEntityData((NBTTagCompound) base);
			else
				core.logFine("EntityImitator#writeEntityToNBT unknown tag %s", base);
		}
	}
	
	public void requestSurfaceFromClient() {
		requestSurfaceFromClient = true;
	}
	
	@Override
	public void setDead() {
		try {
			super.setDead();
		}
		finally {
			dispose();
		}
	}
	
	@Override
	public void terminate() {
		if (getStatus() == State.RUNNING) {
			// adjustPositionAndRotation をクライアント側でも実施するように改良したほうがいいかなぁ
			adjustPositionAndRotation();
			setStatus(State.PUTBLOCK); // adjust 後には強制ブロック配置
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
		// とりあえず書き込みデータを用意する
		byte status = getStatus().getCode();
		int block = getThisBlockData();
		Coord3D base = getThisBlockCoord();
		int this_x = base.x;
		int this_y = base.y;
		int this_z = base.z;
		byte[] blockData = newSerializer().serialize(space.getAllBlocks());
		NBTTagList tileData = new NBTTagList();
		
		// 保持している TileEntity 情報を書き込み
		for (NBTTagCompound t: space.getAllTileEntities()) {
			tileData.appendTag(t.copy());
		}
		
		// セット
		tag.setByte("ImitatorStatus", status);
		tag.setInteger("ThisBlock", block);
		tag.setInteger("ThisBlockX", this_x);
		tag.setInteger("ThisBlockY", this_y);
		tag.setInteger("ThisBlockZ", this_z);
		tag.setByteArray("Blocks", blockData);
		tag.setTag("TileEntities", tileData);
	}
	
	protected Collection<BlockData> deserializeClientBlocks(byte[] data) {
		List<BlockData> blocks = newSerializer().deserialize(getThisBlockCoord(), data);
		return toSafeClientBlocks(blocks);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(DWKEY_BLOCK, 0);
		dataWatcher.addObject(DWKEY_BLOCK_X, 0);
		dataWatcher.addObject(DWKEY_BLOCK_Y, 0);
		dataWatcher.addObject(DWKEY_BLOCK_Z, 0);
	}
	
	@Override
	protected void finalize() throws Throwable {
		try {
			super.finalize();
		}
		finally {
			dispose();
		}
	}
	
	protected int getThisBlockData() {
		return dataWatcher.getWatchableObjectInt(DWKEY_BLOCK);
	}
	
	protected Coord3D getThisBlockCoord() {
		int x = dataWatcher.getWatchableObjectInt(DWKEY_BLOCK_X);
		int y = dataWatcher.getWatchableObjectInt(DWKEY_BLOCK_Y);
		int z = dataWatcher.getWatchableObjectInt(DWKEY_BLOCK_Z);
		return new Coord3D(x, y, z);
	}
	
	protected Block getThisBlock() {
		return BlockData.unpackBlock(getThisBlockData());
	}
	
	protected int getThisBlockMetadata() {
		return BlockData.unpackMetadata(getThisBlockData());
	}
	
	protected void initCraftBody(int x, int y, int z) {
		String ownerName = getOwnerName();
		int size = core.getCraftBodySize();
		for (BlockData data: space.getSurfaceBlocks()) {
			if (data.relPos.isZero()) { // EntityImitator 自身が CraftBody になるため EntityCraftBody は生成しない
				continue;
			}
			if (size < 0 || data.relPos.nearFrom(size, Coord3D.ZERO)) {
				int ax = x + data.relPos.x;
				int ay = y + data.relPos.y;
				int az = z + data.relPos.z;
				AxisAlignedBB aabb = data.block.getCollisionBoundingBoxFromPool(worldObj, ax, ay, az);
				if (aabb != null) {
					EntityAirCraft ent = new EntityCraftBody(worldObj, ownerName);
					// TODO ここで Invisible の衝突範囲を設定しておく
					ent.setPosition(ax + 0.5, ay, az + 0.5);
					worldObj.spawnEntityInWorld(ent);
				}
			}
		}
	}
	
	protected Serializer newSerializer() {
		return new Serializer();
	}
	
	protected void processSendSurfaceToClient() {
		// 準表面ブロック送信
		processSendSurfaceToClient(false); // さきにこっちを送信しておく
		// 表面ブロック送信
		processSendSurfaceToClient(true);
		// タイルデータ送信
		processSendTileDataToclient();
	}
	
	protected void processSendSurfaceToClient(boolean isSurface) {
		final Serializer serializer = newSerializer();
		List<BlockData> blocks = isSurface ? space.getSurfaceBlocks() : space.getSemiSurfaceBlocks();
		byte[] data = serializer.serialize(blocks);
		if (isSurface)
			core.net.sendSurfaceToClient(getEntityId(), data);
		else
			core.net.sendSemiSurfaceToClient(getEntityId(), data);
	}
	
	protected void processSendTileDataToclient() {
		for (NBTTagCompound tag: space.getAllTileEntities()) {
			core.net.sendTileDataToClient(getEntityId(), tag);
		}
	}
	
	protected void putBlock() { // Side: Server
		if (Utils.isClientSide(worldObj)) {
			return;
		}
		int x = MathHelper.floor_double(posX);
		int y = (int) Math.round(posY);
		int z = MathHelper.floor_double(posZ);
		int d = getDirection(this);
		Materializer materializer = core.newMaterializer(worldObj, space);
		if (materializer.putBlocks(x, y, z, d)) {
			Utils.showMessage(worldObj, "HariboteAirCraft: Off");
			setStatus(State.END); // END へ
		}
		else {
			Utils.showMessage(worldObj, "HariboteAirCraft: Can't put block on here");
			setStatus(State.RUNNING); // RUNNING へ戻す
		}
	}
	
	protected void removeBlock() { // Side: Server
		if (Utils.isClientSide(worldObj)) {
			return;
		}
		int x = MathHelper.floor_double(posX);
		int y = (int) Math.round(posY);
		int z = MathHelper.floor_double(posZ);
		Materializer materializer = core.newMaterializer(worldObj, space);
		materializer.addCoreBlock(x, y, z, getThisBlock(), getThisBlockMetadata());
		if (materializer.removeBlocks(x, y, z)) {
			// クラフト本体の作成
			initCraftBody(x, y, z);
			// メッセージ表示
			int all = space.countAllBlocks();
			int surface = space.countSurfaceBlocks();
			Utils.showMessage(worldObj, "HariboteAirCraft: Total " + all + " blocks, Surface " + surface + " blocks");
			// Running
			setStatus(State.RUNNING);
		}
	}
	
	protected void setStatus(State state) {
		this.status = state;
	}
	
	protected void setThisBlock(Block block, int metadata, Coord3D point) {
		setThisBlock(BlockData.pack(Block.getIdFromBlock(block), metadata), point.x, point.y, point.z);
	}
	
	protected void setThisBlock(int block, int x, int y, int z) {
		dataWatcher.updateObject(DWKEY_BLOCK, block);
		dataWatcher.updateObject(DWKEY_BLOCK_X, x);
		dataWatcher.updateObject(DWKEY_BLOCK_Y, y);
		dataWatcher.updateObject(DWKEY_BLOCK_Z, z);
	}
	
	protected List<BlockData> toSafeClientBlocks(List<BlockData> blocks) {
		List<BlockData> result = new ArrayList<BlockData>(blocks.size());
		for (BlockData block: blocks) {
			block = core.toSafeClientBlock(block);
			if (block != null) {
				result.add(block);
			}
		}
		return result;
	}
	
	public enum State {
		INIT((byte) 0), RUNNING((byte) 1), PUTBLOCK((byte) 2), END((byte) 3);
		private final byte code;
		
		private State(byte code) {
			this.code = code;
		}
		
		public byte getCode() {
			return code;
		}
		
		public static State fromCode(byte code) {
			for (State stt: values()) {
				if (stt.getCode() == code) {
					return stt;
				}
			}
			return INIT;
		}
	}
}
