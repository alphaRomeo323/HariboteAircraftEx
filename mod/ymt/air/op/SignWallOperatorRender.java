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
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author Yamato
 *
 */
public class SignWallOperatorRender extends AbstractOperatorRender {
	private final ModelSign modelSign = new ModelSign();
	private static final ResourceLocation res_sign = new ResourceLocation("textures/entity/sign.png");

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
		GL11.glPushMatrix();
		{
			float scale = 0.6666667F;
			{
				float angle = 0.0F;
				switch (data.metadata) {
					case 2:
						angle = 180.0F;
						break;
					case 4:
						angle = 90.0F;
						break;
					case 5:
						angle = -90.0F;
						break;
				}
				GL11.glTranslatef(0, 0.75F * scale, 0);
				GL11.glRotatef(-angle, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
				this.modelSign.signStick.showModel = false;
			}
			loadTexture(manager, res_sign);
			GL11.glPushMatrix();
			{
				GL11.glScalef(scale, -scale, -scale);
				this.modelSign.renderSign();
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
}
