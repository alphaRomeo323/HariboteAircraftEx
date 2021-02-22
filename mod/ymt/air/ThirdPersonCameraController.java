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

import java.util.HashSet;
import java.util.Set;
import mod.ymt.air.cmn.Reflection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;

public class ThirdPersonCameraController {
	private static final ThirdPersonCameraController instance = new ThirdPersonCameraController();
	private int cnt = 2;

	private static final String[] fieldNames = {
		"thirdPersonDistance", "field_78490_B", "A"
	};

	private ThirdPersonCameraController() {
		;
	}

	public void turn() {
		cnt++;
		if (8 < cnt) {
			cnt = 2;
		}
		try {
			setThirdPersonDistance(Minecraft.getMinecraft().entityRenderer, cnt * cnt, new HashSet<EntityRenderer>());
		}
		catch (Exception e) {
			AirCraftCore.getInstance().logFine(e, "ThirdPersonCameraController#turn");
		}
	}

	public static ThirdPersonCameraController getInstance() {
		return instance;
	}

	private static void setThirdPersonDistance(EntityRenderer obj, float distance, Set<EntityRenderer> visited) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException {
		if (visited.contains(obj)) {
			return;
		}
		else {
			visited.add(obj);
			// entityRenderer に入ってる EntityRenderer を直接書き換えてみる
			Reflection.setFieldValue(EntityRenderer.class, obj, distance, Float.TYPE, fieldNames);
			// entityRenderer が入れ子になっていたら、それらも書き換えてみる
			for (EntityRenderer obj2: Reflection.getFieldValues(obj.getClass(), obj, EntityRenderer.class)) {
				setThirdPersonDistance(obj2, distance, visited);
			}
		}
	}
}
