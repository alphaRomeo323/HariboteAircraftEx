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
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * @author Yamato
 *
 */
public class StairsOperator extends AbstractRotationOperator {
	public StairsOperator() {
		super(3, 0, 2, 1, 3);
	}
	
	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.oak_stairs);
		result.add(Blocks.stone_stairs);
		result.add(Blocks.brick_stairs);
		result.add(Blocks.stone_brick_stairs);
		result.add(Blocks.nether_brick_stairs);
		result.add(Blocks.sandstone_stairs);
		result.add(Blocks.spruce_stairs);
		result.add(Blocks.birch_stairs);
		result.add(Blocks.jungle_stairs);
		result.add(Blocks.quartz_stairs); // ネザー水晶の階段
		// 1.7で追加
		result.add(Blocks.acacia_stairs);
		result.add(Blocks.dark_oak_stairs);
	}
}
