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
import net.minecraft.world.World;

/**
 * @author Yamato
 *
 */
public class EnderChestOperator extends AbstractRotationOperator {
	public EnderChestOperator() {
		super(2, 5, 3, 4);
	}

	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.ender_chest);
	}

	@Override
	protected boolean canPlaceBlockAt(World world, int x, int y, int z, Block block, int metadata) {
		return block == null || block.canPlaceBlockAt(world, x, y, z);
	}

	@Override
	protected BlockRender newRender() {
		return new EnderChestOperatorRender();
	}
}
