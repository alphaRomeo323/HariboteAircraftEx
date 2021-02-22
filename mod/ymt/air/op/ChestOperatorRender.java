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

import mod.ymt.air.BlockData;
import mod.ymt.air.ImitationSpace;
import mod.ymt.air.cmn.Coord3D;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author Yamato
 *
 */
public class ChestOperatorRender extends AbstractOperatorRender {
	private final ModelChest modelChest = new ModelChest();
	private final ModelChest modelLargeChest = new ModelLargeChest();

	private static final ResourceLocation res_large_trapped = new ResourceLocation("textures/entity/chest/trapped_double.png");
	private static final ResourceLocation res_large = new ResourceLocation("textures/entity/chest/normal_double.png");
	private static final ResourceLocation res_trapped = new ResourceLocation("textures/entity/chest/trapped.png");
	private static final ResourceLocation res_normal = new ResourceLocation("textures/entity/chest/normal.png");

	@Override
	public boolean hasSpecialRender() {
		return true;
	}

	@Override
	public void renderBlock(RenderBlocks render, BlockData data) {
		;
	}

	@Override
	public void renderBlockSpecial(RenderManager manager, RenderBlocks render, BlockData data) {
		int metadata = data.metadata;

		boolean adjacentChestZNeg = false;
		boolean adjacentChestZPos = false;
		boolean adjacentChestXNeg = false;
		boolean adjacentChestXPos = false;
		NBTTagCompound tag = getNBT(render.blockAccess, data.absPos);
		if (tag != null) {
			adjacentChestZNeg = tag.getBoolean("adjacentChestZNeg");
			adjacentChestZPos = tag.getBoolean("adjacentChestZPos");
			adjacentChestXNeg = tag.getBoolean("adjacentChestXNeg");
			adjacentChestXPos = tag.getBoolean("adjacentChestXPos");
		}
		if (adjacentChestZNeg || adjacentChestXNeg) {
			return;
		}

		ModelChest model;

		if (!adjacentChestXPos && !adjacentChestZPos) {
			model = modelChest;
			if (data.block == Blocks.trapped_chest)
				loadTexture(manager, res_trapped);
			else
				loadTexture(manager, res_normal);
		}
		else {
			model = modelLargeChest;
			if (data.block == Blocks.trapped_chest)
				loadTexture(manager, res_large_trapped);
			else
				loadTexture(manager, res_large);
		}

		GL11.glPushMatrix();
		{
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(-0.5F, 1.0F, 0.5F);
			GL11.glScalef(1.0F, -1.0F, -1.0F);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			short angle = 0;
			if (metadata == 2) {
				angle = 180;
			}
			if (metadata == 3) {
				angle = 0;
			}
			if (metadata == 4) {
				angle = 90;
			}
			if (metadata == 5) {
				angle = -90;
			}

			if (metadata == 2 && adjacentChestXPos) {
				GL11.glTranslatef(1.0F, 0.0F, 0.0F);
			}
			if (metadata == 5 && adjacentChestZPos) {
				GL11.glTranslatef(0.0F, 0.0F, -1.0F);
			}
			GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

			model.chestLid.rotateAngleX = 0;
			model.renderAll();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		GL11.glPopMatrix();
	}

	private static NBTTagCompound getNBT(IBlockAccess blockAccess, Coord3D pos) {
		if (blockAccess instanceof ImitationSpace) {
			ImitationSpace space = (ImitationSpace) blockAccess;
			return space.getTileEntityData(pos);
		}
		return null;
	}
}
