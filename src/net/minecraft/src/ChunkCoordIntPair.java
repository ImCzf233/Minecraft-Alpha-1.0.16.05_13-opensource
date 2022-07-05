package net.minecraft.src;

public class ChunkCoordIntPair {
	public int chunkXPos;
	public int chunkZPos;

	public ChunkCoordIntPair(int posX, int posZ) {
		this.chunkXPos = posX;
		this.chunkZPos = posZ;
	}

	public int hashCode() {
		return this.chunkXPos << 8 | this.chunkZPos;
	}

	public boolean equals(Object chunkCoordIntPair) {
		ChunkCoordIntPair var2 = (ChunkCoordIntPair)chunkCoordIntPair;
		return var2.chunkXPos == this.chunkXPos && var2.chunkZPos == this.chunkZPos;
	}
}
