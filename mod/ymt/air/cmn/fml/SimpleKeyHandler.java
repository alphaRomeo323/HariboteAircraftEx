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
package mod.ymt.air.cmn.fml;

import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public abstract class SimpleKeyHandler {
	private final KeyBinding[] kbs;
	private final boolean repeat;
	
	public SimpleKeyHandler(KeyBinding... kbs) {
		this(false, kbs);
	}
	
	public SimpleKeyHandler(boolean repeat, KeyBinding... kbs) {
		this.kbs = kbs;
		this.repeat = repeat;
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent tick) {
		process(repeat);
	}
	
	@SubscribeEvent
	public void onKey(KeyInputEvent key) {
		process(!repeat);
	}
	
	private void process(boolean enable) {
		if (enable) {
			int index = 0;
			for (KeyBinding kb: kbs) {
				if (kb.isPressed()) {
					onKeyDown(index, kb);
				}
				index++;
			}
		}
	}
	
	public abstract void onKeyDown(int index, KeyBinding kb);
}
