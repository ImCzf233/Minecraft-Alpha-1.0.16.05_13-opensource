package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {
	private static Map packetIdToClassMap = new HashMap();
	private static Map packetClassToIdMap = new HashMap();
	public boolean isChunkDataPacket = false;

	static void addIdClassMapping(int id, Class packetClass) {
		if(packetIdToClassMap.containsKey(Integer.valueOf(id))) {
			throw new IllegalArgumentException("Duplicate packet id:" + id);
		} else if(packetClassToIdMap.containsKey(packetClass)) {
			throw new IllegalArgumentException("Duplicate packet class:" + packetClass);
		} else {
			packetIdToClassMap.put(Integer.valueOf(id), packetClass);
			packetClassToIdMap.put(packetClass, Integer.valueOf(id));
		}
	}

	public static Packet getNewPacket(int id) {
		try {
			Class var1 = (Class)packetIdToClassMap.get(Integer.valueOf(id));
			return var1 == null ? null : (Packet)var1.newInstance();
		} catch (Exception var2) {
			var2.printStackTrace();
			System.out.println("Skipping packet with id " + id);
			return null;
		}
	}

	public final int getPacketId() throws IOException {
		return ((Integer)packetClassToIdMap.get(this.getClass())).intValue();
	}

	public static Packet readPacket(DataInputStream dataInputStream) throws IOException {
		int var1 = dataInputStream.read();
		if(var1 == -1) {
			return null;
		} else {
			Packet var2 = getNewPacket(var1);
			if(var2 == null) {
				throw new IOException("Bad packet id " + var1);
			} else {
				var2.readPacketData(dataInputStream);
				return var2;
			}
		}
	}

	public static void writePacket(Packet packet, DataOutputStream dataOutputStream) throws IOException {
		dataOutputStream.write(packet.getPacketId());
		packet.writePacket(dataOutputStream);
	}

	public abstract void readPacketData(DataInputStream var1) throws IOException;

	public abstract void writePacket(DataOutputStream var1) throws IOException;

	public abstract void processPacket(NetHandler var1);

	public abstract int getPacketSize();

	static {
		addIdClassMapping(0, Packet0KeepAlive.class);
		addIdClassMapping(1, Packet1Login.class);
		addIdClassMapping(2, Packet2Handshake.class);
		addIdClassMapping(3, Packet3Chat.class);
		addIdClassMapping(4, Packet4UpdateTime.class);
		addIdClassMapping(5, Packet5PlayerInventory.class);
		addIdClassMapping(6, Packet6SpawnPosition.class);
		addIdClassMapping(10, Packet10Flying.class);
		addIdClassMapping(11, Packet11PlayerPosition.class);
		addIdClassMapping(12, Packet12PlayerLook.class);
		addIdClassMapping(13, Packet13PlayerLookMove.class);
		addIdClassMapping(14, Packet14BlockDig.class);
		addIdClassMapping(15, Packet15Place.class);
		addIdClassMapping(16, Packet16BlockItemSwitch.class);
		addIdClassMapping(17, Packet17AddToInventory.class);
		addIdClassMapping(18, Packet18ArmAnimation.class);
		addIdClassMapping(20, Packet20NamedEntitySpawn.class);
		addIdClassMapping(21, Packet21PickupSpawn.class);
		addIdClassMapping(22, Packet22Collect.class);
		addIdClassMapping(23, Packet23VehicleSpawn.class);
		addIdClassMapping(24, Packet24MobSpawn.class);
		addIdClassMapping(29, Packet29DestroyEntity.class);
		addIdClassMapping(30, Packet30Entity.class);
		addIdClassMapping(31, Packet31RelEntityMove.class);
		addIdClassMapping(32, Packet32EntityLook.class);
		addIdClassMapping(33, Packet33RelEntityMoveLook.class);
		addIdClassMapping(34, Packet34EntityTeleport.class);
		addIdClassMapping(50, Packet50PreChunk.class);
		addIdClassMapping(51, Packet51MapChunk.class);
		addIdClassMapping(52, Packet52MultiBlockChange.class);
		addIdClassMapping(53, Packet53BlockChange.class);
		addIdClassMapping(59, Packet59ComplexEntity.class);
		addIdClassMapping(255, Packet255KickDisconnect.class);
	}
}
