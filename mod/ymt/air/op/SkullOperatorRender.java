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

import mod.ymt.air.BlockData;
import mod.ymt.air.ImitationSpace;
import mod.ymt.air.cmn.Coord3D;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author Yamato
 *
 */
public class SkullOperatorRender extends AbstractOperatorRender {
	private static final ResourceLocation resSkeleton = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation resWither = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
	private static final ResourceLocation resZombie = new ResourceLocation("textures/entity/zombie/zombie.png");
	private static final ResourceLocation resCreeper = new ResourceLocation("textures/entity/creeper/creeper.png");
	
	private ModelSkeletonHead model1 = new ModelSkeletonHead(0, 0, 64, 32);
	private ModelSkeletonHead model2 = new ModelSkeletonHead(0, 0, 64, 64);
	
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
		int tagSkullType = 0;
		int tagRot = 0;
		String tagExtraType = null;
		NBTTagCompound tag = getNBT(render.blockAccess, data.absPos);
		if (tag != null) {
			tagSkullType = tag.getByte("SkullType");
			tagRot = tag.getByte("Rot");
			tagExtraType = tag.getString("ExtraType");
		}
		
		ModelSkeletonHead modelskeletonhead = model1;
		switch (tagSkullType) {
			case 0:
			default:
				loadTexture(manager, resSkeleton);
				break;
			case 1:
				loadTexture(manager, resWither);
				break;
			case 2:
				loadTexture(manager, resZombie);
				modelskeletonhead = model2;
				break;
			case 3:
				ResourceLocation res = AbstractClientPlayer.locationStevePng;
				if (tagExtraType != null && tagExtraType.isEmpty() == false) {
					res = AbstractClientPlayer.getLocationSkull(tagExtraType);
					AbstractClientPlayer.getDownloadImageSkin(res, tagExtraType);
				}
				loadTexture(manager, res);
				break;
			case 4:
				loadTexture(manager, resCreeper);
				break;
		}
		
		GL11.glPushMatrix();
		{
			GL11.glDisable(GL11.GL_CULL_FACE);
			float angle = 0;
			switch (data.metadata & 7) {
				default:
				case 1:
					GL11.glTranslatef(0, 0, 0);
					angle =  (tagRot * 360) / 16.0F;
					break;
				case 2:
					GL11.glTranslatef(0, 0.25F, 0.24F);
					angle = 0;
					break;
				case 3:
					GL11.glTranslatef(0, 0.25F, -0.24F);
					angle = 180;
					break;
				case 4:
					GL11.glTranslatef(0.24F, 0.25F, 0);
					angle = 270;
					break;
				case 5:
					GL11.glTranslatef(-0.24F, 0.25F, 0);
					angle = 90;
			}
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			modelskeletonhead.render(null, 0, 0, 0, angle, 0, 0.0625F);
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
