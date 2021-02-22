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

import mod.ymt.air.cmn.fml.SimpleKeyHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientInitializer {
	private static final boolean DEBUG_RENDER_IMITATOR = false;
	private static final boolean DEBUG_RENDER_INVISIBLE = false;
	private static final boolean DEBUG_RENDER_MAT = false;
	
	private ClientInitializer() {
		;
	}
	
	public static void preInit() {
		initKeyBindings();
		initRenders();
	}
	
	protected static void initKeyBindings() {
		KeyBinding[] keys = new KeyBinding[]{ // MoveManager の並びと合わせる
			new KeyBinding("key.HAC_Stop", Keyboard.KEY_NUMPAD5, "key.categories.movement"),
			new KeyBinding("key.HAC_Forward", Keyboard.KEY_NUMPAD1, "key.categories.movement"),
			new KeyBinding("key.HAC_Backward", Keyboard.KEY_NUMPAD3, "key.categories.movement"),
			new KeyBinding("key.HAC_TurnRight", Keyboard.KEY_NUMPAD9, "key.categories.movement"),
			new KeyBinding("key.HAC_TurnLeft", Keyboard.KEY_NUMPAD7, "key.categories.movement"),
			new KeyBinding("key.HAC_Up", Keyboard.KEY_NUMPAD8, "key.categories.movement"),
			new KeyBinding("key.HAC_Down", Keyboard.KEY_NUMPAD2, "key.categories.movement"),
			new KeyBinding("key.HAC_Right", Keyboard.KEY_NUMPAD6, "key.categories.movement"),
			new KeyBinding("key.HAC_Left", Keyboard.KEY_NUMPAD4, "key.categories.movement"),
			new KeyBinding("key.HAC_Terminate", Keyboard.KEY_DIVIDE, "key.categories.movement"),
		};
		for (KeyBinding kb: keys) {
			ClientRegistry.registerKeyBinding(kb);
		}
		FMLCommonHandler.instance().bus().register(new AirCraftKeyHandler(keys));
		
		KeyBinding cameraKey = new KeyBinding("key.HAC_Camera", Keyboard.KEY_MULTIPLY, "key.categories.movement");
		ClientRegistry.registerKeyBinding(cameraKey);
		FMLCommonHandler.instance().bus().register(new CameraKeyHandler(cameraKey));
	}
	
	protected static void initRenders() {
		// Imitator
		if (DEBUG_RENDER_IMITATOR)
			RenderingRegistry.registerEntityRenderingHandler(EntityImitator.class, new RenderDebugEntity(Blocks.diamond_block));
		else
			RenderingRegistry.registerEntityRenderingHandler(EntityImitator.class, new RenderPyxis());
		// Invisible
		if (DEBUG_RENDER_INVISIBLE)
			RenderingRegistry.registerEntityRenderingHandler(EntityCraftBody.class, new RenderDebugEntity(Blocks.glass));
		else
			RenderingRegistry.registerEntityRenderingHandler(EntityCraftBody.class, new RenderNothing());
		// Mat
		if (DEBUG_RENDER_MAT)
			RenderingRegistry.registerEntityRenderingHandler(EntityMobMat.class, new RenderDebugEntity(Blocks.wool));
		else
			RenderingRegistry.registerEntityRenderingHandler(EntityMobMat.class, new RenderNothing());
	}
	
	public static class AirCraftKeyHandler extends SimpleKeyHandler {
		public AirCraftKeyHandler(KeyBinding[] kb) {
			super(kb);
		}
		
		@Override
		public void onKeyDown(int index, KeyBinding kb) {
			AirCraftCore.getInstance().net.sendKeyToServer((byte) index);
		}
	}
	
	public static class CameraKeyHandler extends SimpleKeyHandler {
		public CameraKeyHandler(KeyBinding kb) {
			super(kb);
		}
		
		@Override
		public void onKeyDown(int index, KeyBinding kb) {
			ThirdPersonCameraController.getInstance().turn();
		}
	}
}
