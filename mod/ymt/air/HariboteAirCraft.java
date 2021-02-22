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
import java.util.Collections;
import java.util.List;
import mod.ymt.air.cmn.CfgFile;
import mod.ymt.air.op.AnvilOperator;
import mod.ymt.air.op.ButtonOperator;
import mod.ymt.air.op.ChestOperator;
import mod.ymt.air.op.DelicateDirectionalOperator;
import mod.ymt.air.op.DelicateOperator;
import mod.ymt.air.op.DirectionalOperator;
import mod.ymt.air.op.DoorOperator;
import mod.ymt.air.op.EndPortalOperator;
import mod.ymt.air.op.EnderChestOperator;
import mod.ymt.air.op.FluidOperator;
import mod.ymt.air.op.InventoryBlockOperator;
import mod.ymt.air.op.JukeboxOperator;
import mod.ymt.air.op.LadderOperator;
import mod.ymt.air.op.LeverOperator;
import mod.ymt.air.op.NormalOperator;
import mod.ymt.air.op.PistonOperator;
import mod.ymt.air.op.RailOperator;
import mod.ymt.air.op.RailPoweredOperator;
import mod.ymt.air.op.SignPostOperator;
import mod.ymt.air.op.SignWallOperator;
import mod.ymt.air.op.SkullOperator;
import mod.ymt.air.op.StairsOperator;
import mod.ymt.air.op.TorchOperator;
import mod.ymt.air.op.TrapdoorOperator;
import mod.ymt.air.op.VineOperator;
import mod.ymt.air.op.WoodOperator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Yamato
 *
 */
@Mod(modid = "mod.ymt.air.HariboteAirCraft", name = "HariboteAirCraft", version = "172v3 hiten",dependencies="required-after:Forge@[10.12.1.1090,)")
public class HariboteAirCraft {
	@Instance("mod.ymt.air.HariboteAirCraft")
	protected static HariboteAirCraft instance;
	
	private List<String> defaultIgnores = new ArrayList<String>();
	{
		// 芝生、土、水、溶岩、砂、砂利、草、枯れ木、花、バラ、雪、ネザーラック、エンドストーン
		defaultIgnores.add("grass");
		defaultIgnores.add("dirt");
		defaultIgnores.add("flowing_water");
		defaultIgnores.add("water");
		defaultIgnores.add("flowing_lava");
		defaultIgnores.add("lava");
		defaultIgnores.add("sand");
		defaultIgnores.add("gravel");
		defaultIgnores.add("tallgrass");
		defaultIgnores.add("deadbush");
		defaultIgnores.add("yellow_flower");
		defaultIgnores.add("red_flower");
		defaultIgnores.add("snow_layer");
		defaultIgnores.add("netherrack");
		defaultIgnores.add("end_stone");
		// TODO 1.7 で追加された花とか入れる
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Operator 登録
		AirCraftCore core = AirCraftCore.getInstance();
		for (Operator op: getDefaultOperators()) {
			op.register(core);
		}
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// デバッグ表示
		AirCraftCore core = AirCraftCore.getInstance();
		core.logFine("defaultMoveableSet: %s", core.getDefaultMoveableSet());
		core.logFine("targetBlockId: %s", core.targetBlockId);
		core.logFine("appendixBlockId: %s", core.appendixBlockId);
		core.logFine("ignoredBlockId: %s", core.ignoredBlockId);
		core.logFine("MoveableBlockIds: %s", core.getMoveableBlocks());
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// 設定ロードや GameRegistory への登録を行うタイミング
		// 追加Blockや追加Itemはこのタイミングで登録を行う
		CfgFile cfg = new CfgFile("mod_HariboteAirCraft.txt");
		
		AirCraftCore core = AirCraftCore.getInstance();
		core.setBlocklimit(cfg.getInt("blockLimit", 2000, 0, Integer.MAX_VALUE));
		core.setCraftBodySize(cfg.getInt("craftBodySize", -1));
		core.setMoveKeepTime(20 * cfg.getInt("moveKeepTime", 60, 1, Integer.MAX_VALUE)); // 20FPS
		core.targetBlockId.addAll(getBlockList(cfg, "blockTarget", Collections.EMPTY_LIST));
		core.appendixBlockId.addAll(getBlockList(cfg, "blockAppend", Collections.EMPTY_LIST));
		core.ignoredBlockId.addAll(getBlockList(cfg, "blockIgnore", defaultIgnores));
		core.ignoreRenderBlockId.addAll(getBlockList(cfg, "renderIgnore", Collections.EMPTY_LIST));
		core.setCreativeOnly(cfg.getBoolean("creativeOnly", false));
		core.run();
		
		// Client の追加設定
		preInitClient();
		cfg.save();
	}
	
	private static List<Block> getBlockList(CfgFile cfg, String key, List<String> _default) {
		List<Block> result = new ArrayList<Block>();
		for (String name: cfg.getStringList(key, _default)) {
			Block block = Block.getBlockFromName(name); // id でも調べてくれるらしい？
			if (block != null && block != Blocks.air) {
				result.add(block);
			}
		}
		return result;
	}
	
	private void preInitClient() {
		try {
			Class<?> cls = Class.forName("mod.ymt.air.ClientInitializer");
			cls.getMethod("preInit").invoke(null);
		}
		catch (NoClassDefFoundError ex) {
			AirCraftCore.getInstance().logWarning("I'm in Server? ClientInitializer encounted exceptions: %s", ex);
		}
		catch (ClassNotFoundException ex) {
			AirCraftCore.getInstance().logWarning("I'm in Server? ClientInitializer encounted exceptions: %s", ex);
		}
		catch (RuntimeException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	protected Operator[] getDefaultOperators() {
		return new Operator[]{
			new NormalOperator(),
			new DelicateOperator(),
			new DirectionalOperator(),
			new DelicateDirectionalOperator(),
			new DoorOperator(),
			new FluidOperator(),
			new LadderOperator(),
			new RailOperator(),
			new RailPoweredOperator(),
			new StairsOperator(),
			new TorchOperator(),
			new TrapdoorOperator(),
			new VineOperator(),
			new WoodOperator(),
			new ButtonOperator(),
			new LeverOperator(),
			new InventoryBlockOperator(),
			new ChestOperator(),
			new EnderChestOperator(),
			new AnvilOperator(),
			new PistonOperator(),
			new SignPostOperator(),
			new SignWallOperator(),
			new EndPortalOperator(),
			new JukeboxOperator(),
			new SkullOperator(),
		};
	}
}
