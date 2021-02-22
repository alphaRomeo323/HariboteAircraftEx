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
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;

/**
 * @author Yamato
 *
 */
public class PacketTileEntity extends AbstractMessage<PacketTileEntity> {
	private static final NBTTagCompound NULL = new NBTTagCompound();
	
	private int entityId;
	private NBTTagCompound tag;
	
	public PacketTileEntity() {
		this(0, NULL);
	}
	
	public PacketTileEntity(int entityId, NBTTagCompound tag) {
		this.entityId = entityId;
		this.tag = tag;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(entityId);
		ByteBufUtils.writeTag(buffer, tag);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		entityId = buffer.readInt();
		tag = ByteBufUtils.readTag(buffer);
	}
	
	@Override
	public void onHandleClient() {
		AirCraftCore.getInstance().processAppendTileEntityData(entityId, tag);
	}
}
