package net.minecraft.src;

import java.io.IOException;

public class ChunkProviderIso implements IChunkProvider {
	private Chunk[] chunks = new Chunk[256];
	private World worldObj;
	private IChunkLoader chunkLoader;
	byte[] blocks = new byte['\u8000'];

	public ChunkProviderIso(World worldObj, IChunkLoader chunkLoader) {
		this.worldObj = worldObj;
		this.chunkLoader = chunkLoader;
	}

	public boolean chunkExists(int var1, int var2) {
		int var3 = var1 & 15 | (var2 & 15) * 16;
		return this.chunks[var3] != null && this.chunks[var3].isAtLocation(var1, var2);
	}

	public Chunk provideChunk(int var1, int var2) {
		int var3 = var1 & 15 | (var2 & 15) * 16;

		try {
			if(!this.chunkExists(var1, var2)) {
				Chunk var4 = this.getChunkAt(var1, var2);
				if(var4 == null) {
					var4 = new Chunk(this.worldObj, this.blocks, var1, var2);
					var4.isChunkRendered = true;
					var4.neverSave = true;
				}

				this.chunks[var3] = var4;
			}

			return this.chunks[var3];
		} catch (Exception var5) {
			var5.printStackTrace();
			return null;
		}
	}

	private synchronized Chunk getChunkAt(int chunkX, int chunkZ) {
		try {
			return this.chunkLoader.loadChunk(this.worldObj, chunkX, chunkZ);
		} catch (IOException var4) {
			var4.printStackTrace();
			return null;
		}
	}

	public void populate(IChunkProvider var1, int var2, int var3) {
	}

	public boolean saveChunks(boolean var1, IProgressUpdate var2) {
		return true;
	}

	public boolean unload100OldestChunks() {
		return false;
	}

	public boolean canSave() {
		return false;
	}
}
