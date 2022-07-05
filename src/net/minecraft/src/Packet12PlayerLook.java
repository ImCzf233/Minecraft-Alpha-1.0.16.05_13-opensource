package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet12PlayerLook extends Packet10Flying {
	public Packet12PlayerLook() {
		this.rotating = true;
	}

	public Packet12PlayerLook(float yaw, float pitch, boolean onGround) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
		this.rotating = true;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.yaw = var1.readFloat();
		this.pitch = var1.readFloat();
		super.readPacketData(var1);
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeFloat(this.yaw);
		var1.writeFloat(this.pitch);
		super.writePacket(var1);
	}

	public int getPacketSize() {
		return 9;
	}
}
