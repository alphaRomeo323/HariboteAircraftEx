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

import java.util.Random;
import java.util.Set;
import mod.ymt.air.BlockData;
import mod.ymt.air.Materializer;
import mod.ymt.air.cmn.Coord3D;
import mod.ymt.air.cmn.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Yamato
 *
 */
public class InventoryBlockOperator extends AbstractRotationOperator {
	public InventoryBlockOperator() {
		super(7, 2, 5, 3, 4);
	}

	@Override
	public int getPriority(BlockData data) {
		return data.block == Blocks.hopper ? PRIORITY_REDSTONEOUTPUT : PRIORITY_NORMAL;
	}

	@Override
	protected void addMoveableBlocks(Set<Block> result) {
		result.add(Blocks.furnace);
		result.add(Blocks.lit_furnace);
		result.add(Blocks.brewing_stand);
		result.add(Blocks.dispenser);
		result.add(Blocks.dropper);
		result.add(Blocks.hopper);
	}

	protected void dropItemFromNBT(World world, NBTTagCompound tag, double x, double y, double z) {
		if (Utils.isClientSide(world)) {
			return;
		}
		ItemStack item = ItemStack.loadItemStackFromNBT(tag);
		if (item != null) {
			Random rand = world.rand;
			while (0 < item.stackSize) {
				int stackSize = rand.nextInt(21) + 10;
				if (stackSize > item.stackSize) {
					stackSize = item.stackSize;
				}
				item.stackSize -= stackSize;
				x += rand.nextFloat() * 0.8 + 0.1;
				y += rand.nextFloat() * 0.8 + 0.1;
				z += rand.nextFloat() * 0.8 + 0.1;
				EntityItem ent = new EntityItem(world, x, y, z, new ItemStack(item.getItem(), stackSize, item.getItemDamage()));
				float motionRate = 0.05F;
				ent.motionX = ((float) rand.nextGaussian() * motionRate);
				ent.motionY = ((float) rand.nextGaussian() * motionRate + 0.2F);
				ent.motionZ = ((float) rand.nextGaussian() * motionRate);
				if (item.hasTagCompound()) {
					ent.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}
				world.spawnEntityInWorld(ent);
			}
		}
	}

	@Override
	protected void onCancelSetRealBlock(Materializer owner, BlockData data, Coord3D target) {
		super.onCancelSetRealBlock(owner, data, target);
		// 臓物をぶち撒けろ
		NBTTagCompound tag = owner.space.getTileEntityData(data.absPos);
		if (tag != null) {
			NBTTagList list = tag.getTagList("Items", 10);	// 10 = CompoundTag
			for (int i = 0; i < list.tagCount(); i++) {
				dropItemFromNBT(owner.world, (NBTTagCompound) list.getCompoundTagAt(i).copy(), target.x + 0.5, target.y + 0.5, target.z + 0.5);
			}
		}
	}

	@Override
	protected NBTTagCompound readFromTileEntity(Materializer owner, Block block, int metadata, Coord3D pos) {
		NBTTagCompound tag = super.readFromTileEntity(owner, block, metadata, pos);
		// 読み取ったら Inventory 初期化してブロック破壊に備える
		TileEntity tile = owner.world.getTileEntity(pos.x, pos.y, pos.z);
		clearInventory(tile);
		return tag;
	}

	@Override
	protected boolean setRealBlock(Materializer owner, Block block, int metadata, int x, int y, int z) {
		boolean result = super.setRealBlock(owner, block, metadata, x, y, z);
		if (result && block != null) {
			owner.world.setBlockMetadataWithNotify(x, y, z, metadata, 2); // 無視されるので重ねて metadata 設定
		}
		return result;
	}

	public static void clearInventory(IInventory inventory) {
		if (inventory != null) {
			for (int i = inventory.getSizeInventory() - 1; 0 <= i; i--) {
				inventory.setInventorySlotContents(i, null);
			}
		}
	}

	public static void clearInventory(TileEntity tile) {
		if (tile instanceof IInventory) {
			clearInventory((IInventory) tile);
		}
	}
}
