/**
 * Copyright 2014 Yamato
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
import mod.ymt.air.cmn.Coord3D;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;

/**
 * @author Yamato
 *
 */
public class SkullOperator extends AbstractRotationOperator {
	public SkullOperator() {
		super(2, 5, 3, 4);
	}
	
	@Override
	public int getPriority(BlockData data) {
		return PRIORITY_DELECATE;
	}
	
	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.skull);
	}
	
	@Override
	protected void writeToTileEntity(Materializer owner, BlockData data, Coord3D target, int rotate) {
		super.writeToTileEntity(owner, data, target, rotate);
		if (data.metadata == 1) { // ブロック上面に置かれた頭蓋骨
			TileEntity tile = owner.world.getTileEntity(target.x, target.y, target.z);
			if (tile instanceof TileEntitySkull) {
				TileEntitySkull skull = (TileEntitySkull) tile;
				NBTTagCompound tag = new NBTTagCompound();
				skull.writeToNBT(tag);
				skull.func_145903_a((tag.getByte("Rot") + rotate * 4) & 15);	// 回転させる
			}
		}
	}
	
	@Override
	protected BlockRender newRender() {
		return new SkullOperatorRender();
	}
}
