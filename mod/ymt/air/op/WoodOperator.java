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
public class WoodOperator extends AbstractRotationOperator {
	public WoodOperator() {
		// 4 → 8 → 4 → 8 → 4
		for (int metadata = 0; metadata < rotation.length; metadata++) {
			int d = metadata & ~3;
			if (d == 4)
				rotation[metadata] = (metadata & 3) | 8;
			if (d == 8)
				rotation[metadata] = (metadata & 3) | 4;
		}
	}

	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.log);
		// 1.7で追加
		result.add(Blocks.log2);
	}
}
