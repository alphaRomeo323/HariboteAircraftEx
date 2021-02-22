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
import cpw.mods.fml.common.network.ByteBufUtils;

/**
 * @author Yamato
 *
 */
public class PacketKeyHandling extends AbstractMessage<PacketKeyHandling> {
	private String playerName;
	private byte key;
	
	public PacketKeyHandling() {
		this("", (byte) 0);
	}
	
	public PacketKeyHandling(String playerName, byte key) {
		this.playerName = playerName;
		this.key = key;
	}
	
	@Override
	public void toBytes(ByteBuf buffer) {
		ByteBufUtils.writeUTF8String(buffer, playerName);
		buffer.writeByte(key);
	}
	
	@Override
	public void fromBytes(ByteBuf buffer) {
		playerName = ByteBufUtils.readUTF8String(buffer);
		key = buffer.readByte();
	}
	
	@Override
	public void onHandleServer() {
		AirCraftCore.getInstance().net.sendToClient(this); // サーバに届いたパケットを全クライアントへ転送
		AirCraftCore.getInstance().processMoveServer(key, playerName);
	}
	
	@Override
	public void onHandleClient() {
		AirCraftCore.getInstance().processMoveClient(key, playerName);
	}
}
