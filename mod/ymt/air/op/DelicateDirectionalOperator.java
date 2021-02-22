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
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * @author Yamato
 *
 */
public class DelicateDirectionalOperator extends DirectionalOperator {
	@Override
	public int getPriority(BlockData data) {
		return PRIORITY_DELECATE;
	}

	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.bed);
		result.add(Blocks.unpowered_repeater);
		result.add(Blocks.powered_repeater);
		result.add(Blocks.tripwire_hook);
		result.add(Blocks.unpowered_comparator);
		result.add(Blocks.powered_comparator);
	}
	
	@Override
	protected boolean isNeedScheduleUpdate(Block block) {
		return block == Blocks.tripwire_hook;
	}
}
