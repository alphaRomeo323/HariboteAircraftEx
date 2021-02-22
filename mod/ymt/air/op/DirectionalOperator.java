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
import mod.ymt.air.AirCraftCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * @author Yamato
 *
 */
public class DirectionalOperator extends AbstractRotationOperator {
	public DirectionalOperator() {
		super(3, 0, 1, 2, 3);
	}

	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(AirCraftCore.getInstance().getBlockPyxis()); // この Operator で移動
		result.add(Blocks.cocoa);
		result.add(Blocks.fence_gate);
		result.add(Blocks.pumpkin);
		result.add(Blocks.lit_pumpkin);
		result.add(Blocks.end_portal_frame);
	}
}
