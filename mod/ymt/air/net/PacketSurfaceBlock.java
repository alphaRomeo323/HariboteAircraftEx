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
public class PacketSurfaceBlock extends AbstractMessage<PacketSurfaceBlock> {
	private boolean semisurface = false;
	private int entityId;
	private byte[] data;
	
	public PacketSurfaceBlock() {
		this(false, 0, new byte[0]);
	}
	
	public PacketSurfaceBlock(boolean semisurface, int entityId, byte[] data) {
		this.semisurface = semisurface;
		this.entityId = entityId;
		this.data = data;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeBoolean(semisurface);
		buffer.writeInt(entityId);
		buffer.writeInt(data.length);
		buffer.writeBytes(data);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		semisurface = buffer.readBoolean();
		entityId = buffer.readInt();
		int length = buffer.readInt();
		buffer.readBytes(data = new byte[length]);
	}
	
	@Override
	public void onHandleClient() {
		if (semisurface)
			AirCraftCore.getInstance().processAppendSemiSurface(entityId, data);
		else
			AirCraftCore.getInstance().processAppendSurface(entityId, data);
	}
}
