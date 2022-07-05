package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet14BlockDig extends Packet {
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int face;
	public int status;

	public Packet14BlockDig() {
	}

	public Packet14BlockDig(int status, int x, int y, int z, int side) {
		this.status = status;
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.face = side;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.status = var1.read();
		this.xPosition = var1.readInt();
		this.yPosition = var1.read();
		this.zPosition = var1.readInt();
		this.face = var1.read();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.write(this.status);
		var1.writeInt(this.xPosition);
		var1.write(this.yPosition);
		var1.writeInt(this.zPosition);
		var1.write(this.face);
	}

	public void processPacket(NetHandler var1) {
		var1.handleBlockDig(this);
	}

	public int getPacketSize() {
		return 11;
	}
}
