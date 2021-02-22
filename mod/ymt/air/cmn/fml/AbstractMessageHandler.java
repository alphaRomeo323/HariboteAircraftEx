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
package mod.ymt.air.cmn.fml;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * @author Yamato
 *
 */
public abstract class AbstractMessageHandler {
	private SimpleNetworkWrapper INSTANCE = null;
	
	public abstract void initPackets(SimpleNetworkWrapper instance);
	
	public void init(String channel) {
		synchronized (this) {
			if (INSTANCE != null)
				throw new IllegalStateException();
			INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channel);
			initPackets(INSTANCE);
		}
	}
	
	public void sendToServer(IMessage message) {
		INSTANCE.sendToServer(message);
	}
	
	public void sendToClient(IMessage message) {
		INSTANCE.sendToAll(message);
	}
	
}
