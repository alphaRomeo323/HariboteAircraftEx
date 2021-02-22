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
import mod.ymt.air.BlockData;
import mod.ymt.air.Materializer;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * @author Yamato
 *
 */
public class DelicateOperator extends AbstractOperator {
	@Override
	public int getPriority(BlockData data) {
		return data.block == Blocks.redstone_wire ? PRIORITY_REDSTONEWIRE : PRIORITY_DELECATE;
	}

	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.sapling); // 苗木
		result.add(Blocks.tallgrass);
		result.add(Blocks.deadbush);
		result.add(Blocks.yellow_flower);
		result.add(Blocks.red_flower);
		result.add(Blocks.brown_mushroom);
		result.add(Blocks.red_mushroom);
		result.add(Blocks.fire);
		result.add(Blocks.redstone_wire);
		result.add(Blocks.wheat); // 小麦
		result.add(Blocks.stone_pressure_plate); // 石の感圧板
		result.add(Blocks.wooden_pressure_plate); // 木の感圧板
		result.add(Blocks.snow_layer);
		result.add(Blocks.cactus);
		result.add(Blocks.reeds); // サトウキビ
		result.add(Blocks.portal); // ポータル移動可能
		result.add(Blocks.cake);
		result.add(Blocks.melon_stem); // スイカの苗
		result.add(Blocks.pumpkin_stem); // かぼちゃの苗
		result.add(Blocks.waterlily); // 蓮の葉
		result.add(Blocks.nether_wart); // ネザーいぼ
		result.add(Blocks.dragon_egg);
		result.add(Blocks.carrots);
		result.add(Blocks.potatoes);
		result.add(Blocks.tripwire); // トリップワイヤー
		result.add(Blocks.flower_pot); // 植木鉢
		result.add(Blocks.heavy_weighted_pressure_plate); // 金の感圧板
		result.add(Blocks.light_weighted_pressure_plate); // 鉄の感圧板
	}

	@Override
	protected boolean isNeedScheduleUpdate(Block block) {
		// 感圧板シリーズ
		return block == Blocks.stone_pressure_plate || block == Blocks.wooden_pressure_plate || block == Blocks.heavy_weighted_pressure_plate
				|| block == Blocks.light_weighted_pressure_plate;
	}

	@Override
	protected boolean setRealBlockWithRotation(Materializer owner, Block block, int metadata, int x, int y, int z, int rotate) {
		return setRealBlock(owner, block, metadata, x, y, z);
	}
}
