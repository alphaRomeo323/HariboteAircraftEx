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

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Yamato
 *
 */
public abstract class AbstractMessage<T extends AbstractMessage> implements IMessage, IMessageHandler<T, IMessage> {
	@Override
	public final IMessage onMessage(T packet, MessageContext ctx) {
		if (ctx.side == Side.SERVER)
			packet.onHandleServer();
		else
			packet.onHandleClient();
		return null;
	}
	
	protected void onHandleServer() {
		throw new UnsupportedOperationException();
	}
	
	protected void onHandleClient() {
		throw new UnsupportedOperationException();
	}
}
