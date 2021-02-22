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

import mod.ymt.air.cmn.fml.AbstractMessageHandler;
import mod.ymt.air.net.PacketKeyHandling;
import mod.ymt.air.net.PacketRequestSurface;
import mod.ymt.air.net.PacketSurfaceBlock;
import mod.ymt.air.net.PacketTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Session;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Yamato
 *
 */
public class AirCraftNetHandler extends AbstractMessageHandler {
	protected final AirCraftCore core;
	
	public AirCraftNetHandler(AirCraftCore core) {
		this.core = core;
	}
	
	@Override
	public void initPackets(SimpleNetworkWrapper instance) {
		int id = 0;
		instance.registerMessage(PacketKeyHandling.class, PacketKeyHandling.class, id++, Side.CLIENT);
		instance.registerMessage(PacketKeyHandling.class, PacketKeyHandling.class, id++, Side.SERVER);
		instance.registerMessage(PacketRequestSurface.class, PacketRequestSurface.class, id++, Side.SERVER);
		instance.registerMessage(PacketSurfaceBlock.class, PacketSurfaceBlock.class, id++, Side.CLIENT);
		instance.registerMessage(PacketTileEntity.class, PacketTileEntity.class, id++, Side.CLIENT);
	}
	
	public void sendKeyToServer(byte key) {
		sendToServer(new PacketKeyHandling(clientUserName(), key));
	}
	
	public void sendMoveStopToServerAndClients(String playerName) {
		core.logFine("sendMoveStopToClients %s", playerName);
		new PacketKeyHandling(clientUserName(), AirCraftMoveHandler.PROC_STOP).onHandleServer();
	}
	
	public void sendRequestSurfacesToServer(int entityId) {
		core.logFine("sendRequestSurfacesToServer: sender = %s", entityId);
		sendToServer(new PacketRequestSurface(entityId));
	}
	
	public void sendSemiSurfaceToClient(int entityId, byte[] data) {
		core.logFine("sendSemiSurfaceToClient: sender = %s", entityId);
		sendToClient(new PacketSurfaceBlock(true, entityId, data)); // TODO ディメンション指定
	}
	
	public void sendSurfaceToClient(int entityId, byte[] data) {
		core.logFine("sendSurfaceToClient: sender = %s", entityId);
		sendToClient(new PacketSurfaceBlock(false, entityId, data)); // TODO ディメンション指定
	}
	
	public void sendTileDataToClient(int entityId, NBTTagCompound data) {
		core.logFine("sendTileDataToClient: sender = %s", entityId);
		sendToClient(new PacketTileEntity(entityId, data)); // TODO ディメンション指定
	}
	
	private static String clientUserName() {
		Minecraft mc = FMLClientHandler.instance().getClient();
		if (mc != null) {
			Session session = mc.getSession();
			if (session != null && session.getUsername() != null) {
				return session.getUsername();
			}
		}
		return "";
	}
}
