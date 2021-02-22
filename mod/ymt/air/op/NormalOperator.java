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
package mod.ymt.air.op;

import java.util.Set;
import mod.ymt.air.Materializer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * @author Yamato
 *
 */
public class NormalOperator extends AbstractOperator {
	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.stone);
		result.add(Blocks.grass);
		result.add(Blocks.dirt);
		result.add(Blocks.cobblestone);
		result.add(Blocks.planks); // 木材
		result.add(Blocks.sand);
		result.add(Blocks.gravel);
		result.add(Blocks.gold_ore);
		result.add(Blocks.iron_ore);
		result.add(Blocks.coal_ore);
		result.add(Blocks.leaves);
		result.add(Blocks.sponge);
		result.add(Blocks.glass);
		result.add(Blocks.lapis_ore);
		result.add(Blocks.lapis_block);
		result.add(Blocks.sandstone);
		result.add(Blocks.web);
		result.add(Blocks.wool);
		result.add(Blocks.gold_block);
		result.add(Blocks.iron_block);
		result.add(Blocks.double_stone_slab); // 石のハーフブロック二段重ね
		result.add(Blocks.stone_slab); // 石のハーフブロック
		result.add(Blocks.brick_block); // レンガ
		result.add(Blocks.tnt);
		result.add(Blocks.bookshelf);
		result.add(Blocks.mossy_cobblestone); // 苔石
		result.add(Blocks.obsidian); // 黒曜石動かすと、ネザーポータルが破綻するよね
		result.add(Blocks.diamond_ore);
		result.add(Blocks.diamond_block);
		result.add(Blocks.farmland); // 農地
		result.add(Blocks.redstone_ore);
		result.add(Blocks.lit_redstone_ore);
		result.add(Blocks.ice);
		result.add(Blocks.snow);
		result.add(Blocks.clay);
		result.add(Blocks.fence);
		result.add(Blocks.netherrack); // ネザーラック
		result.add(Blocks.soul_sand); // ソウルサンド
		result.add(Blocks.glowstone);
		result.add(Blocks.monster_egg); // シルバーフィッシュ入りブロック
		result.add(Blocks.stonebrick); // 石レンガ
		result.add(Blocks.red_mushroom_block); // 赤いキノコブロック
		result.add(Blocks.brown_mushroom_block); // 茶色のキノコブロック
		result.add(Blocks.iron_bars); // 鉄フェンス
		result.add(Blocks.glass_pane); // 板ガラス
		result.add(Blocks.melon_block); // すいか
		result.add(Blocks.mycelium); // 菌糸ブロック
		result.add(Blocks.nether_brick); // ネザーレンガ
		result.add(Blocks.nether_brick_fence); // ネザーレンガフェンス
		result.add(Blocks.end_stone); // エンドストーン
		result.add(Blocks.redstone_lamp); // レッドストーンランプ(消灯)
		result.add(Blocks.lit_redstone_lamp); // レッドストーンランプ(点灯)
		result.add(Blocks.double_wooden_slab); // 木のハーフブロック二段重ね
		result.add(Blocks.wooden_slab); // 木のハーフブロック
		result.add(Blocks.emerald_ore);
		result.add(Blocks.emerald_block);
		result.add(Blocks.cobblestone_wall); // 丸石フェンス
		result.add(Blocks.crafting_table); // 作業台は NormalOperator で移動可能
		result.add(Blocks.cauldron); // 大釜
		result.add(Blocks.redstone_block); // レッドストーンブロック
		result.add(Blocks.quartz_ore); // ネザー水晶鉱石
		result.add(Blocks.quartz_block); // ネザー水晶ブロック
		// AbstractOperator が TileEntity に対応したので、BlockContainer も少しだけ動かせる
		result.add(Blocks.noteblock); // ノートブロック
		result.add(Blocks.daylight_detector); // 太陽光センサー
		result.add(Blocks.beacon); // ビーコン
		result.add(Blocks.enchanting_table); // エンチャ台
		result.add(Blocks.command_block); // コマンドブロック
		result.add(Blocks.mob_spawner); // スポーンブロック
		// 1.6.1 で追加されたブロック
		result.add(Blocks.stained_hardened_clay); // 色付き粘土
		result.add(Blocks.hay_block); // 麦わらブロック
		result.add(Blocks.carpet); // カーペット
		result.add(Blocks.hardened_clay); // 焼き粘土
		result.add(Blocks.coal_block); // 石炭ブロック
		// 1.7で追加
		result.add(Blocks.stained_glass);
		result.add(Blocks.stained_glass_pane);
		result.add(Blocks.leaves2);
		result.add(Blocks.packed_ice);
		result.add(Blocks.double_plant);
	}
	
	@Override
	protected boolean setRealBlockWithRotation(Materializer owner, Block block, int metadata, int x, int y, int z, int rotate) {
		return setRealBlock(owner, block, metadata, x, y, z);
	}
}
