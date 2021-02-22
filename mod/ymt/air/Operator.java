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

import mod.ymt.air.cmn.Coord3D;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderManager;

/**
 * @author Yamato
 *
 */
public interface Operator {
	public static final int PRIORITY_NORMAL = 0;
	public static final int PRIORITY_DELECATE = 1;
	public static final int PRIORITY_REDSTONEWIRE = 2;
	public static final int PRIORITY_REDSTONEOUTPUT = 3;
	public static final int PRIORITY_PISTONBASE = 4;

	public int getPriority(BlockData data);

	public BlockRender getRender();

	public void pickBlockFromWorld(Materializer owner, Coord3D pos, Coord3D base);

	public void putBlocksToWorld(Materializer owner, BlockData data, Coord3D target, int rotate);

	public void register(AirCraftCore core);

	public interface BlockRender {
		public boolean hasSpecialRender();

		public void renderBlock(RenderBlocks render, BlockData data);

		public void renderBlockSpecial(RenderManager manager, RenderBlocks render, BlockData data);
	}
}
