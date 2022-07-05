package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet21PickupSpawn extends Packet {
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte rotation;
	public byte pitch;
	public byte roll;
	public int itemID;
	public int count;

	public Packet21PickupSpawn() {
	}

	public Packet21PickupSpawn(EntityItem entityItem) {
		this.entityId = entityItem.entityID;
		this.itemID = entityItem.item.itemID;
		this.count = entityItem.item.stackSize;
		this.xPosition = MathHelper.floor_double(entityItem.posX * 32.0D);
		this.yPosition = MathHelper.floor_double(entityItem.posY * 32.0D);
		this.zPosition = MathHelper.floor_double(entityItem.posZ * 32.0D);
		this.rotation = (byte)((int)(entityItem.motionX * 128.0D));
		this.pitch = (byte)((int)(entityItem.motionY * 128.0D));
		this.roll = (byte)((int)(entityItem.motionZ * 128.0D));
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.entityId = var1.readInt();
		this.itemID = var1.readShort();
		this.count = var1.readByte();
		this.xPosition = var1.readInt();
		this.yPosition = var1.readInt();
		this.zPosition = var1.readInt();
		this.rotation = var1.readByte();
		this.pitch = var1.readByte();
		this.roll = var1.readByte();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeInt(this.entityId);
		var1.writeShort(this.itemID);
		var1.writeByte(this.count);
		var1.writeInt(this.xPosition);
		var1.writeInt(this.yPosition);
		var1.writeInt(this.zPosition);
		var1.writeByte(this.rotation);
		var1.writeByte(this.pitch);
		var1.writeByte(this.roll);
	}

	public void processPacket(NetHandler var1) {
		var1.handlePickupSpawn(this);
	}

	public int getPacketSize() {
		return 22;
	}
}
