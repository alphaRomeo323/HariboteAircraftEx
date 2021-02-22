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
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

/**
 * @author Yamato
 *
 */
public class EndPortalOperatorRender extends AbstractOperatorRender {
	@Override
	public boolean hasSpecialRender() {
		return false;
	}

	@Override
	public void renderBlock(RenderBlocks render, BlockData data) {
		GL11.glPushMatrix();
		{
			final Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setBrightness(15 << 20 | 15 << 4);
			tessellator.setColorRGBA(0, 0, 32, 255);
			tessellator.addVertex(-0.5, 0.75, -0.5);
			tessellator.addVertex(-0.5, 0.75, +0.5);
			tessellator.addVertex(+0.5, 0.75, +0.5);
			tessellator.addVertex(+0.5, 0.75, -0.5);
			tessellator.draw();
		}
		GL11.glPopMatrix();
	}

	@Override
	public void renderBlockSpecial(RenderManager manager, RenderBlocks render, BlockData data) {
		;
	}
}
