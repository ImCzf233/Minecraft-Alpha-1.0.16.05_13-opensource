package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

public class ChunkLoader implements IChunkLoader {
	private File saveDir;
	private boolean createIfNecessary;

	public ChunkLoader(File saveDir, boolean createIfNecessary) {
		this.saveDir = saveDir;
		this.createIfNecessary = createIfNecessary;
	}

	private File chunkFileForXZ(int chunkX, int chunkZ) {
		String var3 = "c." + Integer.toString(chunkX, 36) + "." + Integer.toString(chunkZ, 36) + ".dat";
		String var4 = Integer.toString(chunkX & 63, 36);
		String var5 = Integer.toString(chunkZ & 63, 36);
		File var6 = new File(this.saveDir, var4);
		if(!var6.exists()) {
			if(!this.createIfNecessary) {
				return null;
			}

			var6.mkdir();
		}

		var6 = new File(var6, var5);
		if(!var6.exists()) {
			if(!this.createIfNecessary) {
				return null;
			}

			var6.mkdir();
		}

		var6 = new File(var6, var3);
		return !var6.exists() && !this.createIfNecessary ? null : var6;
	}

	public Chunk loadChunk(World worldObj, int chunkX, int chunkZ) {
		File var4 = this.chunkFileForXZ(chunkX, chunkZ);
		if(var4 != null && var4.exists()) {
			try {
				FileInputStream var5 = new FileInputStream(var4);
				NBTTagCompound var6 = CompressedStreamTools.readCompressed(var5);
				if(!var6.hasKey("Level")) {
					System.out.println("Chunk file at " + chunkX + "," + chunkZ + " is missing level data, skipping");
					return null;
				}

				if(!var6.getCompoundTag("Level").hasKey("Blocks")) {
					System.out.println("Chunk file at " + chunkX + "," + chunkZ + " is missing block data, skipping");
					return null;
				}

				Chunk var7 = loadChunkIntoWorldFromCompound(worldObj, var6.getCompoundTag("Level"));
				if(!var7.isAtLocation(chunkX, chunkZ)) {
					System.out.println("Chunk file at " + chunkX + "," + chunkZ + " is in the wrong location; relocating. (Expected " + chunkX + ", " + chunkZ + ", got " + var7.xPosition + ", " + var7.zPosition + ")");
					var6.setInteger("xPos", chunkX);
					var6.setInteger("zPos", chunkZ);
					var7 = loadChunkIntoWorldFromCompound(worldObj, var6.getCompoundTag("Level"));
				}

				return var7;
			} catch (Exception var8) {
				var8.printStackTrace();
			}
		}

		return null;
	}

	public void saveChunk(World var1, Chunk var2) {
		var1.checkSessionLock();
		File var3 = this.chunkFileForXZ(var2.xPosition, var2.zPosition);
		if(var3.exists()) {
			var1.sizeOnDisk -= var3.length();
		}

		try {
			File var4 = new File(this.saveDir, "tmp_chunk.dat");
			FileOutputStream var5 = new FileOutputStream(var4);
			NBTTagCompound var6 = new NBTTagCompound();
			NBTTagCompound var7 = new NBTTagCompound();
			var6.setTag("Level", var7);
			this.storeChunkInCompound(var2, var1, var7);
			CompressedStreamTools.writeCompressed(var6, var5);
			var5.close();
			if(var3.exists()) {
				var3.delete();
			}

			var4.renameTo(var3);
			var1.sizeOnDisk += var3.length();
		} catch (Exception var8) {
			var8.printStackTrace();
		}

	}

	public void storeChunkInCompound(Chunk chunk, World worldObj, NBTTagCompound nbtCompound) {
		worldObj.checkSessionLock();
		nbtCompound.setInteger("xPos", chunk.xPosition);
		nbtCompound.setInteger("zPos", chunk.zPosition);
		nbtCompound.setLong("LastUpdate", worldObj.worldTime);
		nbtCompound.setByteArray("Blocks", chunk.blocks);
		nbtCompound.setByteArray("Data", chunk.data.data);
		nbtCompound.setByteArray("SkyLight", chunk.skylightMap.data);
		nbtCompound.setByteArray("BlockLight", chunk.blocklightMap.data);
		nbtCompound.setByteArray("HeightMap", chunk.heightMap);
		nbtCompound.setBoolean("TerrainPopulated", chunk.isTerrainPopulated);
		chunk.hasEntities = false;
		NBTTagList var4 = new NBTTagList();

		Iterator var6;
		NBTTagCompound var8;
		for(int var5 = 0; var5 < chunk.entities.length; ++var5) {
			var6 = chunk.entities[var5].iterator();

			while(var6.hasNext()) {
				Entity var7 = (Entity)var6.next();
				chunk.hasEntities = true;
				var8 = new NBTTagCompound();
				if(var7.addEntityID(var8)) {
					var4.setTag(var8);
				}
			}
		}

		nbtCompound.setTag("Entities", var4);
		NBTTagList var9 = new NBTTagList();
		var6 = chunk.chunkTileEntityMap.values().iterator();

		while(var6.hasNext()) {
			TileEntity var10 = (TileEntity)var6.next();
			var8 = new NBTTagCompound();
			var10.writeToNBT(var8);
			var9.setTag(var8);
		}

		nbtCompound.setTag("TileEntities", var9);
	}

	public static Chunk loadChunkIntoWorldFromCompound(World worldObj, NBTTagCompound nbtCompound) {
		int var2 = nbtCompound.getInteger("xPos");
		int var3 = nbtCompound.getInteger("zPos");
		Chunk var4 = new Chunk(worldObj, var2, var3);
		var4.blocks = nbtCompound.getByteArray("Blocks");
		var4.data = new NibbleArray(nbtCompound.getByteArray("Data"));
		var4.skylightMap = new NibbleArray(nbtCompound.getByteArray("SkyLight"));
		var4.blocklightMap = new NibbleArray(nbtCompound.getByteArray("BlockLight"));
		var4.heightMap = nbtCompound.getByteArray("HeightMap");
		var4.isTerrainPopulated = nbtCompound.getBoolean("TerrainPopulated");
		if(!var4.data.isValid()) {
			var4.data = new NibbleArray(var4.blocks.length);
		}

		if(var4.heightMap == null || !var4.skylightMap.isValid()) {
			var4.heightMap = new byte[256];
			var4.skylightMap = new NibbleArray(var4.blocks.length);
			var4.generateSkylightMap();
		}

		if(!var4.blocklightMap.isValid()) {
			var4.blocklightMap = new NibbleArray(var4.blocks.length);
			var4.doNothing();
		}

		NBTTagList var5 = nbtCompound.getTagList("Entities");
		if(var5 != null) {
			for(int var6 = 0; var6 < var5.tagCount(); ++var6) {
				NBTTagCompound var7 = (NBTTagCompound)var5.tagAt(var6);
				Entity var8 = EntityList.createEntityFromNBT(var7, worldObj);
				var4.hasEntities = true;
				if(var8 != null) {
					var4.addEntity(var8);
				}
			}
		}

		NBTTagList var10 = nbtCompound.getTagList("TileEntities");
		if(var10 != null) {
			for(int var11 = 0; var11 < var10.tagCount(); ++var11) {
				NBTTagCompound var12 = (NBTTagCompound)var10.tagAt(var11);
				TileEntity var9 = TileEntity.createAndLoadEntity(var12);
				if(var9 != null) {
					var4.addTileEntity(var9);
				}
			}
		}

		return var4;
	}

	public void chunkTick() {
	}

	public void saveExtraData() {
	}

	public void saveExtraChunkData(World worldObj, Chunk chunk) {
	}
}
