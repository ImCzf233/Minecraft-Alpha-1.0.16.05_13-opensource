package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Random;
import net.minecraft.client.Minecraft;

public class NetClientHandler extends NetHandler {
	private boolean disconnected = false;
	private NetworkManager netManager;
	public String loginProgress;
	private Minecraft mc;
	private WorldClient worldClient;
	private boolean posUpdated = false;
	Random rand = new Random();

	public NetClientHandler(Minecraft minecraft, String ip, int port) throws IOException {
		this.mc = minecraft;
		Socket var4 = new Socket(InetAddress.getByName(ip), port);
		this.netManager = new NetworkManager(var4, "Client", this);
	}

	public void processReadPackets() {
		if(!this.disconnected) {
			this.netManager.processReadPackets();
		}
	}

	public void handleLogin(Packet1Login packet) {
		this.mc.playerController = new PlayerControllerMP(this.mc, this);
		this.worldClient = new WorldClient(this);
		this.worldClient.multiplayerWorld = true;
		this.mc.changeWorld1(this.worldClient);
		this.mc.displayGuiScreen(new GuiDownloadTerrain(this));
	}

	public void handlePickupSpawn(Packet21PickupSpawn packet) {
		double var2 = (double)packet.xPosition / 32.0D;
		double var4 = (double)packet.yPosition / 32.0D;
		double var6 = (double)packet.zPosition / 32.0D;
		EntityItem var8 = new EntityItem(this.worldClient, var2, var4, var6, new ItemStack(packet.itemID, packet.count));
		var8.motionX = (double)packet.rotation / 128.0D;
		var8.motionY = (double)packet.pitch / 128.0D;
		var8.motionZ = (double)packet.roll / 128.0D;
		var8.serverPosX = packet.xPosition;
		var8.serverPosY = packet.yPosition;
		var8.serverPosZ = packet.zPosition;
		this.worldClient.addEntityToWorld(packet.entityId, var8);
	}

	public void handleVehicleSpawn(Packet23VehicleSpawn packet) {
		double var2 = (double)packet.xPosition / 32.0D;
		double var4 = (double)packet.yPosition / 32.0D;
		double var6 = (double)packet.zPosition / 32.0D;
		Object var8 = null;
		if(packet.type == 10) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 0);
		}

		if(packet.type == 11) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 1);
		}

		if(packet.type == 12) {
			var8 = new EntityMinecart(this.worldClient, var2, var4, var6, 2);
		}

		if(packet.type == 1) {
			var8 = new EntityBoat(this.worldClient, var2, var4, var6);
		}

		if(var8 != null) {
			((Entity)var8).serverPosX = packet.xPosition;
			((Entity)var8).serverPosY = packet.yPosition;
			((Entity)var8).serverPosZ = packet.zPosition;
			((Entity)var8).rotationYaw = 0.0F;
			((Entity)var8).rotationPitch = 0.0F;
			((Entity)var8).entityID = packet.entityId;
			this.worldClient.addEntityToWorld(packet.entityId, (Entity)var8);
		}

	}

	public void handleNamedEntitySpawn(Packet20NamedEntitySpawn packet) {
		double var2 = (double)packet.xPosition / 32.0D;
		double var4 = (double)packet.yPosition / 32.0D;
		double var6 = (double)packet.zPosition / 32.0D;
		float var8 = (float)(packet.rotation * 360) / 256.0F;
		float var9 = (float)(packet.pitch * 360) / 256.0F;
		EntityOtherPlayerMP var10 = new EntityOtherPlayerMP(this.mc.theWorld, packet.name);
		var10.serverPosX = packet.xPosition;
		var10.serverPosY = packet.yPosition;
		var10.serverPosZ = packet.zPosition;
		int var11 = packet.currentItem;
		if(var11 == 0) {
			var10.inventory.mainInventory[var10.inventory.currentItem] = null;
		} else {
			var10.inventory.mainInventory[var10.inventory.currentItem] = new ItemStack(var11);
		}

		var10.setPositionAndRotation(var2, var4, var6, var8, var9);
		this.worldClient.addEntityToWorld(packet.entityId, var10);
	}

	public void handleEntityTeleport(Packet34EntityTeleport packet) {
		Entity var2 = this.worldClient.getEntityByID(packet.entityId);
		if(var2 != null) {
			var2.serverPosX = packet.xPosition;
			var2.serverPosY = packet.yPosition;
			var2.serverPosZ = packet.zPosition;
			double var3 = (double)var2.serverPosX / 32.0D;
			double var5 = (double)var2.serverPosY / 32.0D;
			double var7 = (double)var2.serverPosZ / 32.0D;
			float var9 = (float)(packet.yaw * 360) / 256.0F;
			float var10 = (float)(packet.pitch * 360) / 256.0F;
			var2.setPositionAndRotation(var3, var5, var7, var9, var10, 3);
		}
	}

	public void handleEntity(Packet30Entity var1) {
		Entity var2 = this.worldClient.getEntityByID(var1.entityId);
		if(var2 != null) {
			var2.serverPosX += var1.xPosition;
			var2.serverPosY += var1.yPosition;
			var2.serverPosZ += var1.zPosition;
			double var3 = (double)var2.serverPosX / 32.0D;
			double var5 = (double)var2.serverPosY / 32.0D;
			double var7 = (double)var2.serverPosZ / 32.0D;
			float var9 = var1.rotating ? (float)(var1.yaw * 360) / 256.0F : var2.rotationYaw;
			float var10 = var1.rotating ? (float)(var1.pitch * 360) / 256.0F : var2.rotationPitch;
			var2.setPositionAndRotation(var3, var5, var7, var9, var10, 3);
		}
	}

	public void handleDestroyEntity(Packet29DestroyEntity var1) {
		this.worldClient.removeEntityFromWorld(var1.entityId);
	}

	public void handleFlying(Packet10Flying packet) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		double var3 = var2.posX;
		double var5 = var2.posY;
		double var7 = var2.posZ;
		float var9 = var2.rotationYaw;
		float var10 = var2.rotationPitch;
		if(packet.moving) {
			var3 = packet.xPosition;
			var5 = packet.yPosition;
			var7 = packet.zPosition;
		}

		if(packet.rotating) {
			var9 = packet.yaw;
			var10 = packet.pitch;
		}

		var2.ySize = 0.0F;
		var2.motionX = var2.motionY = var2.motionZ = 0.0D;
		var2.setPositionAndRotation(var3, var5, var7, var9, var10);
		packet.xPosition = var2.posX;
		packet.yPosition = var2.boundingBox.minY;
		packet.zPosition = var2.posZ;
		packet.stance = var2.posY;
		this.netManager.addToSendQueue(packet);
		if(!this.posUpdated) {
			this.mc.thePlayer.prevPosX = this.mc.thePlayer.posX;
			this.mc.thePlayer.prevPosY = this.mc.thePlayer.posY;
			this.mc.thePlayer.prevPosZ = this.mc.thePlayer.posZ;
			this.posUpdated = true;
			this.mc.displayGuiScreen((GuiScreen)null);
		}

	}

	public void handlePreChunk(Packet50PreChunk packet) {
		this.worldClient.doPreChunk(packet.xPosition, packet.yPosition, packet.mode);
	}

	public void handleMultiBlockChange(Packet52MultiBlockChange var1) {
		Chunk var2 = this.worldClient.getChunkFromChunkCoords(var1.xPosition, var1.zPosition);
		int var3 = var1.xPosition * 16;
		int var4 = var1.zPosition * 16;

		for(int var5 = 0; var5 < var1.size; ++var5) {
			short var6 = var1.coordinateArray[var5];
			int var7 = var1.typeArray[var5] & 255;
			byte var8 = var1.metadataArray[var5];
			int var9 = var6 >> 12 & 15;
			int var10 = var6 >> 8 & 15;
			int var11 = var6 & 255;
			var2.setBlockIDWithMetadata(var9, var11, var10, var7, var8);
			this.worldClient.invalidateBlockReceiveRegion(var9 + var3, var11, var10 + var4, var9 + var3, var11, var10 + var4);
			this.worldClient.markBlocksDirty(var9 + var3, var11, var10 + var4, var9 + var3, var11, var10 + var4);
		}

	}

	public void handleMapChunk(Packet51MapChunk packet) {
		this.worldClient.invalidateBlockReceiveRegion(packet.xPosition, packet.yPosition, packet.zPosition, packet.xPosition + packet.xSize - 1, packet.yPosition + packet.ySize - 1, packet.zPosition + packet.zSize - 1);
		this.worldClient.setChunkData(packet.xPosition, packet.yPosition, packet.zPosition, packet.xSize, packet.ySize, packet.zSize, packet.chunkData);
	}

	public void handleBlockChange(Packet53BlockChange packet) {
		this.worldClient.handleBlockChange(packet.xPosition, packet.yPosition, packet.zPosition, packet.type, packet.metadata);
	}

	public void handleKickDisconnect(Packet255KickDisconnect packet) {
		this.netManager.networkShutdown("Got kicked");
		this.disconnected = true;
		this.mc.changeWorld1((World)null);
		this.mc.displayGuiScreen(new GuiConnectFailed("Disconnected by server", packet.reason));
	}

	public void handleErrorMessage(String message) {
		if(!this.disconnected) {
			this.disconnected = true;
			this.mc.changeWorld1((World)null);
			this.mc.displayGuiScreen(new GuiConnectFailed("Connection lost", message));
		}
	}

	public void addToSendQueue(Packet packet) {
		if(!this.disconnected) {
			this.netManager.addToSendQueue(packet);
		}
	}

	public void handleCollect(Packet22Collect packet) {
		EntityItem var2 = (EntityItem)this.worldClient.getEntityByID(packet.collectedEntityId);
		Object var3 = (EntityLiving)this.worldClient.getEntityByID(packet.collectorEntityId);
		if(var3 == null) {
			var3 = this.mc.thePlayer;
		}

		if(var2 != null) {
			this.worldClient.playSoundAtEntity(var2, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			this.mc.effectRenderer.addEffect(new EntityPickupFX(this.mc.theWorld, var2, (Entity)var3, -0.5F));
			this.worldClient.removeEntityFromWorld(packet.collectedEntityId);
		}

	}

	public void handleBlockItemSwitch(Packet16BlockItemSwitch packet) {
		Entity var2 = this.worldClient.getEntityByID(packet.entityId);
		if(var2 != null) {
			EntityPlayer var3 = (EntityPlayer)var2;
			int var4 = packet.id;
			if(var4 == 0) {
				var3.inventory.mainInventory[var3.inventory.currentItem] = null;
			} else {
				var3.inventory.mainInventory[var3.inventory.currentItem] = new ItemStack(var4);
			}

		}
	}

	public void handleChat(Packet3Chat packet) {
		this.mc.ingameGUI.addChatMessage(packet.message);
	}

	public void handleArmAnimation(Packet18ArmAnimation var1) {
		Entity var2 = this.worldClient.getEntityByID(var1.entityId);
		if(var2 != null) {
			EntityPlayer var3 = (EntityPlayer)var2;
			var3.swingItem();
		}
	}

	public void handleAddToInventory(Packet17AddToInventory packet) {
		this.mc.thePlayer.inventory.addItemStackToInventory(new ItemStack(packet.itemID, packet.count, packet.itemDamage));
	}

	public void handleHandshake(Packet2Handshake packet) {
		if(packet.username.equals("-")) {
			this.addToSendQueue(new Packet1Login(this.mc.session.username, "Password", 2));
		} else {
			try {
				URL var2 = new URL("http://www.minecraft.net/game/joinserver.jsp?user=" + this.mc.session.username + "&sessionId=" + this.mc.session.sessionId + "&serverId=" + packet.username);
				BufferedReader var3 = new BufferedReader(new InputStreamReader(var2.openStream()));
				String var4 = var3.readLine();
				var3.close();
				if(var4.equalsIgnoreCase("ok")) {
					this.addToSendQueue(new Packet1Login(this.mc.session.username, "Password", 2));
				} else {
					this.netManager.networkShutdown("Failed to login: " + var4);
				}
			} catch (Exception var5) {
				var5.printStackTrace();
				this.netManager.networkShutdown("Internal client error: " + var5.toString());
			}
		}

	}

	public void disconnect() {
		this.disconnected = true;
		this.netManager.networkShutdown("Closed");
	}

	public void handleMobSpawn(Packet24MobSpawn packet) {
		double var2 = (double)packet.xPosition / 32.0D;
		double var4 = (double)packet.yPosition / 32.0D;
		double var6 = (double)packet.zPosition / 32.0D;
		float var8 = (float)(packet.yaw * 360) / 256.0F;
		float var9 = (float)(packet.pitch * 360) / 256.0F;
		EntityLiving var10 = (EntityLiving)EntityList.createEntityByID(packet.type, this.mc.theWorld);
		var10.serverPosX = packet.xPosition;
		var10.serverPosY = packet.yPosition;
		var10.serverPosZ = packet.zPosition;
		var10.setPositionAndRotation(var2, var4, var6, var8, var9);
		var10.isAIEnabled = true;
		this.worldClient.addEntityToWorld(packet.entityId, var10);
	}

	public void handleUpdateTime(Packet4UpdateTime packet) {
		this.mc.theWorld.setWorldTime(packet.time);
	}

	public void handlePlayerInventory(Packet5PlayerInventory packet) {
		EntityPlayerSP var2 = this.mc.thePlayer;
		if(packet.inventoryType == -1) {
			var2.inventory.mainInventory = packet.inventory;
		}

		if(packet.inventoryType == -2) {
			var2.inventory.craftingInventory = packet.inventory;
		}

		if(packet.inventoryType == -3) {
			var2.inventory.armorInventory = packet.inventory;
		}

	}

	public void handleComplexEntity(Packet59ComplexEntity packet) {
		TileEntity var2 = this.worldClient.getBlockTileEntity(packet.xCoord, packet.yCoord, packet.zCoord);
		if(var2 != null) {
			var2.readFromNBT(packet.tileEntityNBT);
			this.worldClient.markBlocksDirty(packet.xCoord, packet.yCoord, packet.zCoord, packet.xCoord, packet.yCoord, packet.zCoord);
		}

	}

	public void handleSpawnPosition(Packet6SpawnPosition packet) {
		this.worldClient.spawnX = packet.xPosition;
		this.worldClient.spawnY = packet.yPosition;
		this.worldClient.spawnZ = packet.zPosition;
	}
}
