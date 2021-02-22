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
package mod.ymt.air;

import java.util.List;
import mod.ymt.air.cmn.Coord3D;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author Yamato
 *
 */
public class BlockPyxis extends Block {
	private IIcon[] icons = new IIcon[6];

	public BlockPyxis() {
		super(Material.rock);
		setHardness(0.5F);
		setStepSound(soundTypeStone);
		setBlockBounds(false); // ハーフブロックサイズ
		setLightLevel(1.0F); // 光源になるよ
		setLightOpacity(255);
		setCreativeTab(CreativeTabs.tabTransport); // 乗り物タブ
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List result, Entity ent) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, aabb, result, ent);
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		switch (side) {
			case 0: // 底面
				return icons[0];
			case 1: // 上面
				return icons[2 + getDirection(metadata)];
			default: // 側面
				return icons[1];
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		AirCraftCore core = AirCraftCore.getInstance();
		if (core.tryInteractServer(world)) {
			if (core.isCreativeOnly() && player.capabilities.isCreativeMode == false) {
				return true; // creative only なのにクリエイティブじゃなかったら帰る
			}
			int metadata = world.getBlockMetadata(x, y, z);
			// ブロック削除
			world.setBlockToAir(x, y, z);
			// エンティティ生成
			world.spawnEntityInWorld(newPyxis(world, player, metadata, new Coord3D(x, y, z)));
		}
		return true;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		switch (side) {
			case 0: // 底面
				return metadata | 8;
			case 1: // 上面
				return metadata;
			default: // 側面
				return hitY <= 0.5 ? metadata : metadata | 8;
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int metadata = world.getBlockMetadata(x, y, z);
		metadata = (metadata & ~3) | (getDirection(player) & 3);
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
	}

	@Override
	public void registerBlockIcons(IIconRegister ir) {
		icons = new IIcon[]{
			ir.registerIcon("mod_ymt_air:py_bottom"),
			ir.registerIcon("mod_ymt_air:py_side"),
			ir.registerIcon("mod_ymt_air:py_top0"),
			ir.registerIcon("mod_ymt_air:py_top1"),
			ir.registerIcon("mod_ymt_air:py_top2"),
			ir.registerIcon("mod_ymt_air:py_top3"),
		};
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		setBlockBounds(isUpper(world.getBlockMetadata(x, y, z)));
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(false);
	}

	protected Entity newPyxis(World world, EntityPlayer player, int metadata, Coord3D basePos) {
		Entity ent = new EntityPyxis(player.getCommandSenderName(), world, this, metadata, basePos);
		ent.setPosition(basePos.x + 0.5, basePos.y, basePos.z + 0.5);
		return ent;
	}

	protected void setBlockBounds(boolean upper) {
		if (upper)
			this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		else
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	public static int getDirection(int metadata) {
		return metadata & 3;
	}

	public static boolean isUpper(int metadata) {
		return (metadata & 8) != 0;
	}

	private static int getDirection(Entity ent) {
		return MathHelper.floor_double((ent.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	}
}
