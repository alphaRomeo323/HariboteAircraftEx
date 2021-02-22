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
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * @author Yamato
 *
 */
public class EnderChestOperatorRender extends AbstractOperatorRender {
	private final ModelChest modelEnderChest = new ModelChest();
	private static final ResourceLocation res_ender = new ResourceLocation("textures/entity/chest/ender.png");

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

		loadTexture(manager, res_ender);

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

			GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

			modelEnderChest.chestLid.rotateAngleX = 0;
			modelEnderChest.renderAll();
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		GL11.glPopMatrix();
	}
}
