package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet22Collect extends Packet {
	public int collectedEntityId;
	public int collectorEntityId;

	public void readPacketData(DataInputStream var1) throws IOException {
		this.collectedEntityId = var1.readInt();
		this.collectorEntityId = var1.readInt();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeInt(this.collectedEntityId);
		var1.writeInt(this.collectorEntityId);
	}

	public void processPacket(NetHandler var1) {
		var1.handleCollect(this);
	}

	public int getPacketSize() {
		return 8;
	}
}
