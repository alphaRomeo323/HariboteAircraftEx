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
package mod.ymt.air.net;

import io.netty.buffer.ByteBuf;
import mod.ymt.air.AirCraftCore;
import mod.ymt.air.cmn.fml.AbstractMessage;

/**
 * @author Yamato
 *
 */
public class PacketRequestSurface extends AbstractMessage<PacketRequestSurface> {
	public int entityId;
	
	public PacketRequestSurface() {
		this(0);
	}
	
	public PacketRequestSurface(int entityId) {
		this.entityId = entityId;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(entityId);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		entityId = buffer.readInt();
	}
	
	@Override
	public void onHandleServer() {
		AirCraftCore.getInstance().processRequestSurfaces(entityId);
	}
}
