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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import mod.ymt.air.cmn.NekonoteCore;
import mod.ymt.air.cmn.Utils;
import mod.ymt.air.cmn.WeakEntityCollection;
import mod.ymt.air.op.NullOperator;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * @author Yamato
 *
 */
public class AirCraftCore extends NekonoteCore {
	private static final AirCraftCore instance = new AirCraftCore();
	
	private int blocklimit = 2000;
	private int craftBodySize = -1;
	private int moveKeepTime = 20 * 60;
	private boolean creativeOnly = false;
	
	private static final int entityIdPyxis = 1;
	private static final int entityIdInvisible = 2;
	private static final int entityIdMobMat = 3;
	
	private Block blockPyxis = null;
	
	public final AirCraftNetHandler net = new AirCraftNetHandler(this);
	private final Map<Block, Operator> blockops = new HashMap<Block, Operator>();
	private final Collection<EntityImitator> imitatorServer = new WeakEntityCollection<EntityImitator>();
	private final Collection<EntityImitator> imitatorClient = new WeakEntityCollection<EntityImitator>();
	
	public final Set<Block> targetBlockId = new HashSet<Block>();
	public final Set<Block> appendixBlockId = new HashSet<Block>();
	public final Set<Block> ignoredBlockId = new HashSet<Block>();
	public final Set<Block> ignoreRenderBlockId = new HashSet<Block>();
	
	private static final Set<Block> vanillaBlockContainers = new HashSet<Block>();
	static {
		// バニラで ITileEntityProvider なものを登録
		vanillaBlockContainers.add(Blocks.dispenser);
		vanillaBlockContainers.add(Blocks.noteblock);
		vanillaBlockContainers.add(Blocks.piston_extension);
		vanillaBlockContainers.add(Blocks.mob_spawner);
		vanillaBlockContainers.add(Blocks.chest);
		vanillaBlockContainers.add(Blocks.furnace);
		vanillaBlockContainers.add(Blocks.lit_furnace);
		vanillaBlockContainers.add(Blocks.standing_sign);
		vanillaBlockContainers.add(Blocks.wall_sign);
		vanillaBlockContainers.add(Blocks.jukebox);
		vanillaBlockContainers.add(Blocks.unpowered_repeater);
		vanillaBlockContainers.add(Blocks.powered_repeater);
		// vanillaBlockContainers.add(Blocks.lockedChest);
		vanillaBlockContainers.add(Blocks.enchanting_table);
		vanillaBlockContainers.add(Blocks.brewing_stand);
		vanillaBlockContainers.add(Blocks.end_portal);
		vanillaBlockContainers.add(Blocks.ender_chest);
		vanillaBlockContainers.add(Blocks.command_block);
		vanillaBlockContainers.add(Blocks.beacon);
		vanillaBlockContainers.add(Blocks.skull);
		vanillaBlockContainers.add(Blocks.anvil);
		vanillaBlockContainers.add(Blocks.trapped_chest);
		vanillaBlockContainers.add(Blocks.unpowered_comparator);
		vanillaBlockContainers.add(Blocks.powered_comparator);
		vanillaBlockContainers.add(Blocks.daylight_detector);
		vanillaBlockContainers.add(Blocks.hopper);
		vanillaBlockContainers.add(Blocks.dropper);
	}
	
	private AirCraftCore() {
		;
	}
	
	public Operator getBlockOperator(Block block) {
		Operator result = blockops.get(block);
		return result != null ? result : NullOperator.INSTANCE;
	}
	
	public Block getBlockPyxis() {
		return blockPyxis;
	}
	
	public int getCraftBodySize() {
		return craftBodySize;
	}
	
	public Set<Block> getDefaultMoveableSet() {
		return blockops.keySet();
	}
	
	public Set<Block> getMoveableBlocks() {
		Set<Block> result = new HashSet<Block>();
		
		// targetBlockId
		if (targetBlockId.isEmpty())
			result.addAll(getDefaultMoveableSet());
		else
			result.addAll(targetBlockId);
		
		// appendixBlockId
		result.addAll(appendixBlockId);
		// ignoredBlockId
		result.removeAll(ignoredBlockId);
		
		// その他
		result.remove(0); // 空気ブロックは移動禁止
		result.remove(Blocks.bedrock); // 岩盤は移動禁止
		
		// 完成
		return result;
	}
	
	public int getMoveKeepTime() {
		return moveKeepTime;
	}
	
	public boolean isCreativeOnly() {
		return creativeOnly;
	}
	
	public Materializer newMaterializer(World world, ImitationSpace space) {
		Set<Block> moveable = Utils.isServerSide(world) ? getMoveableBlocks() : new HashSet<Block>();
		return new Materializer(world, space, blocklimit, moveable);
	}
	
	public AirCraftMoveHandler newMoveHandler(World worldObj, EntityCraftCore craftCore, String playerName) {
		return new AirCraftMoveHandler(craftCore, playerName, getMoveKeepTime());
	}
	
	public void processAppendSemiSurface(int entId, byte[] data) {
		synchronized (imitatorClient) {
			for (EntityImitator cli: imitatorClient) {
				if (cli != null && cli.getEntityId() == entId) {
					logFine("receive appendSemiSurface: sender = %s, size = %s", entId, data.length);
					cli.addClientSemiSurfaces(data);
				}
			}
		}
	}
	
	public void processAppendSurface(int entId, byte[] data) {
		synchronized (imitatorClient) {
			for (EntityImitator cli: imitatorClient) {
				if (cli != null && cli.getEntityId() == entId) {
					logFine("receive appendSurface: sender = %s, size = %s", entId, data.length);
					cli.addClientSurfaces(data);
				}
			}
		}
	}
	
	public void processAppendTileEntityData(int entId, NBTTagCompound data) {
		synchronized (imitatorClient) {
			for (EntityImitator cli: imitatorClient) {
				if (cli != null && cli.getEntityId() == entId) {
					logFine("receive appendTileData: sender = %s", entId);
					cli.addClientTileData(data);
				}
			}
		}
	}
	
	public void processMoveClient(byte type, String name) {
		processMove(imitatorClient, type, name);
	}
	
	public void processMoveServer(byte type, String name) {
		processMove(imitatorServer, type, name);
	}
	
	public void processRequestSurfaces(int entId) {
		synchronized (imitatorServer) {
			for (EntityImitator svr: imitatorServer) {
				if (svr != null && svr.getEntityId() == entId) {
					logFine("receive requestSurface: sender = %s", entId);
					svr.requestSurfaceFromClient();
				}
			}
		}
	}
	
	public void registerImitator(EntityImitator imitator) {
		if (Utils.isServerSide(imitator.worldObj)) {
			synchronized (imitatorServer) {
				imitatorServer.add(imitator);
			}
		}
		else {
			synchronized (imitatorClient) {
				imitatorClient.add(imitator);
			}
		}
	}
	
	public void cleanupImitator() {
		synchronized(imitatorServer) {
			for(@SuppressWarnings("unused") EntityImitator ent: imitatorServer) {
				// WeakEntityCollection の iterator にクリンナップ処理をさせる
			}
		}
		synchronized(imitatorClient) {
			for(@SuppressWarnings("unused") EntityImitator ent: imitatorClient) {
				// WeakEntityCollection の iterator にクリンナップ処理をさせる
			}
		}
	}
	
	public void setBlocklimit(int blocklimit) {
		this.blocklimit = blocklimit;
	}
	
	public void setBlockOperator(Block block, Operator operator) {
		if (block != Blocks.air) {
			Operator oldOperator = blockops.get(block);
			if (oldOperator != null) {
				logWarning("BlockOperator[%s] overwrite %s -> %s", block, oldOperator, operator);
			}
			blockops.put(block, operator);
		}
	}
	
	public void setCraftBodySize(int craftBodySize) {
		this.craftBodySize = craftBodySize;
	}
	
	public void setCreativeOnly(boolean creativeOnly) {
		this.creativeOnly = creativeOnly;
	}
	
	public void setMoveKeepTime(int moveKeepTime) {
		this.moveKeepTime = moveKeepTime;
	}
	
	public BlockData toSafeClientBlock(BlockData data) {
		if (!ignoreRenderBlockId.contains(data.block)) {
			// ignoreRenderBlockId に入っていない、かつ
			if (data.block instanceof ITileEntityProvider == false) {
				// BlockContainer でなければたぶん安全
				return data;
			}
			if (vanillaBlockContainers.contains(data.block)) {
				// バニラの BlockContainer ならたぶん安全
				return data;
			}
		}
		// 安全でないものは安全なものに変換
		logFine("toSafeClientBlock [%s - %s]", data.block, data.metadata);
		if (data.block.isOpaqueCube())
			return new BlockData(Blocks.wool, data.metadata, data.relPos, data.absPos);
		else
			return null;
	}
	
	@Override
	protected void init() {
		// ブロック登録
		blockPyxis = new BlockPyxis().setBlockName("Pyxis");
		GameRegistry.registerBlock(blockPyxis, "blockPyxis");
		GameRegistry.addRecipe(new ItemStack(blockPyxis), new Object[]{
			" C ", "DGD", "OOO", 'C', Items.compass, 'D', Items.diamond, 'G', Blocks.gold_block, 'O', Blocks.obsidian
		});
		
		// エンティティ登録
		EntityRegistry.registerModEntity(EntityPyxis.class, "HAC_Pyxis", entityIdPyxis, HariboteAirCraft.instance, 256, 4, true);
		EntityRegistry.registerModEntity(EntityCraftBody.class, "HAC_CraftBody", entityIdInvisible, HariboteAirCraft.instance, 32, 4, true);
		EntityRegistry.registerModEntity(EntityMobMat.class, "HAC_MobMat", entityIdMobMat, HariboteAirCraft.instance, 32, 4, true);
		
		// チャネル登録
		net.init("mod.ymt.air.HAC");
	}
	
	protected void processMove(Collection<EntityImitator> imitators, byte type, String name) {
		synchronized (imitators) {
			for (EntityCraftCore imitator: imitators) {
				if (imitator != null) {
					imitator.processMove(name, type);
				}
			}
		}
	}
	
	public static AirCraftCore getInstance() {
		if (instance == null)
			throw new RuntimeException("AirCraftCore not initialized");
		return instance;
	}
}
