package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet13PlayerLookMove extends Packet10Flying {
	public Packet13PlayerLookMove() {
		this.rotating = true;
		this.moving = true;
	}

	public Packet13PlayerLookMove(double x, double minY, double y, double z, float yaw, float pitch, boolean onGround) {
		this.xPosition = x;
		this.yPosition = minY;
		this.stance = y;
		this.zPosition = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
		this.rotating = true;
		this.moving = true;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.xPosition = var1.readDouble();
		this.yPosition = var1.readDouble();
		this.stance = var1.readDouble();
		this.zPosition = var1.readDouble();
		this.yaw = var1.readFloat();
		this.pitch = var1.readFloat();
		super.readPacketData(var1);
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeDouble(this.xPosition);
		var1.writeDouble(this.yPosition);
		var1.writeDouble(this.stance);
		var1.writeDouble(this.zPosition);
		var1.writeFloat(this.yaw);
		var1.writeFloat(this.pitch);
		super.writePacket(var1);
	}

	public int getPacketSize() {
		return 41;
	}
}
