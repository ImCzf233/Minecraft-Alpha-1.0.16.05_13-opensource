package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet20NamedEntitySpawn extends Packet {
	public int entityId;
	public String name;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte rotation;
	public byte pitch;
	public int currentItem;

	public Packet20NamedEntitySpawn() {
	}

	public Packet20NamedEntitySpawn(EntityPlayer entityPlayer) {
		this.entityId = entityPlayer.entityID;
		this.name = entityPlayer.username;
		this.xPosition = MathHelper.floor_double(entityPlayer.posX * 32.0D);
		this.yPosition = MathHelper.floor_double(entityPlayer.posY * 32.0D);
		this.zPosition = MathHelper.floor_double(entityPlayer.posZ * 32.0D);
		this.rotation = (byte)((int)(entityPlayer.rotationYaw * 256.0F / 360.0F));
		this.pitch = (byte)((int)(entityPlayer.rotationPitch * 256.0F / 360.0F));
		ItemStack var2 = entityPlayer.inventory.getCurrentItem();
		this.currentItem = var2 == null ? 0 : var2.itemID;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.entityId = var1.readInt();
		this.name = var1.readUTF();
		this.xPosition = var1.readInt();
		this.yPosition = var1.readInt();
		this.zPosition = var1.readInt();
		this.rotation = var1.readByte();
		this.pitch = var1.readByte();
		this.currentItem = var1.readShort();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeInt(this.entityId);
		var1.writeUTF(this.name);
		var1.writeInt(this.xPosition);
		var1.writeInt(this.yPosition);
		var1.writeInt(this.zPosition);
		var1.writeByte(this.rotation);
		var1.writeByte(this.pitch);
		var1.writeShort(this.currentItem);
	}

	public void processPacket(NetHandler var1) {
		var1.handleNamedEntitySpawn(this);
	}

	public int getPacketSize() {
		return 28;
	}
}
