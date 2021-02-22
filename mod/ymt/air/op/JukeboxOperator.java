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
import mod.ymt.air.Materializer;
import mod.ymt.air.cmn.Coord3D;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Yamato
 *
 */
public class JukeboxOperator extends NormalOperator {
	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.jukebox);
	}
	
	@Override
	protected NBTTagCompound readFromTileEntity(Materializer owner, Block block, int metadata, Coord3D pos) {
		NBTTagCompound tag = super.readFromTileEntity(owner, block, metadata, pos);
		// 読み取ったら初期化してブロック破壊に備える
		TileEntity tile = owner.world.getTileEntity(pos.x, pos.y, pos.z);
		if (tile instanceof TileEntityJukebox) {
			TileEntityJukebox jukebox = (TileEntityJukebox) tile;
			if (jukebox.func_145856_a() != null) {
				jukebox.func_145857_a(null);
				World world = jukebox.getWorldObj();
				if (world != null && world.isRemote == false) {	// 音を止める
					world.playAuxSFX(1005, pos.x, pos.y, pos.z, 0);
					world.playRecord(null, pos.x, pos.y, pos.z);
				}
			}
		}
		return tag;
	}
}
