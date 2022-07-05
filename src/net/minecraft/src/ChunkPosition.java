package net.minecraft.src;

public class ChunkPosition {
	public final int x;
	public final int y;
	public final int z;

	public ChunkPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean equals(Object chunkPosition) {
		if(!(chunkPosition instanceof ChunkPosition)) {
			return false;
		} else {
			ChunkPosition var2 = (ChunkPosition)chunkPosition;
			return var2.x == this.x && var2.y == this.y && var2.z == this.z;
		}
	}

	public int hashCode() {
		return this.x * 8976890 + this.y * 981131 + this.z;
	}
}
