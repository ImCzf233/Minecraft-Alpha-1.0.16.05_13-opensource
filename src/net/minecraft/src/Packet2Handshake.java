package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet2Handshake extends Packet {
	public String username;

	public Packet2Handshake() {
	}

	public Packet2Handshake(String username) {
		this.username = username;
	}

	public void readPacketData(DataInputStream var1) throws IOException {
		this.username = var1.readUTF();
	}

	public void writePacket(DataOutputStream var1) throws IOException {
		var1.writeUTF(this.username);
	}

	public void processPacket(NetHandler var1) {
		var1.handleHandshake(this);
	}

	public int getPacketSize() {
		return 4 + this.username.length() + 4;
	}
}
