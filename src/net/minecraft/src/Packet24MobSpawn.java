package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet24MobSpawn extends Packet {
	public int entityId;
	public byte type;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte yaw;
	public byte pitch;

	public Packet24MobSpawn() {
	}

	public Packet24MobSpawn(EntityLiving entityLiving) {
		this.entityId = entityLiving.entityID;
		this.type = (byte)EntityList.getEntityID(entityLiving);
		this.xPosition = MathHelper.floor_double(entityLiving.posX * 32.0D);
		this.yPosition = MathHelper.floor_double(entityLiving.posY * 32.0D);
		this.zPosition = MathHelper.floor_double(entityLiving.posZ * 32.0D);
		this.yaw = (byte)((int)(entityLiving.rotationYaw * 256.0F / 360.0F));
		this.pitch = (byte)((int)(entityLiving.rotationPitch * 256.0F / 360.0F));
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.entityId = var1.readInt();
		this.type = var1.readByte();
		this.xPosition = var1.readInt();
		this.yPosition = var1.readInt();
		this.zPosition = var1.readInt();
		this.yaw = var1.readByte();
		this.pitch = var1.readByte();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeInt(this.entityId);
		var1.writeByte(this.type);
		var1.writeInt(this.xPosition);
		var1.writeInt(this.yPosition);
		var1.writeInt(this.zPosition);
		var1.writeByte(this.yaw);
		var1.writeByte(this.pitch);
	}

	public void processPacket(NetHandler var1) {
		var1.handleMobSpawn(this);
	}

	public int getPacketSize() {
		return 19;
	}
}
