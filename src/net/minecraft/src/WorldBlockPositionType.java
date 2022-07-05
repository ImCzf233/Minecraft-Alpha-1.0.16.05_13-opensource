package net.minecraft.src;

class WorldBlockPositionType {
	int posX;
	int posY;
	int posZ;
	int acceptCountdown;
	int blockID;
	int metadata;
	final WorldClient worldClient;

	public WorldBlockPositionType(WorldClient world, int x, int y, int z, int id, int metadata) {
		this.worldClient = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.acceptCountdown = 80;
		this.blockID = id;
		this.metadata = metadata;
	}
}
