package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Chunk {
	public static boolean isLit;
	public byte[] blocks;
	public boolean isChunkLoaded;
	public World worldObj;
	public NibbleArray data;
	public NibbleArray skylightMap;
	public NibbleArray blocklightMap;
	public byte[] heightMap;
	public int height;
	public final int xPosition;
	public final int zPosition;
	public Map chunkTileEntityMap;
	public List[] entities;
	public boolean isTerrainPopulated;
	public boolean isModified;
	public boolean neverSave;
	public boolean isChunkRendered;
	public boolean hasEntities;
	public long lastSaveTime;

	public Chunk(World worldObj, int xPosition, int zPosition) {
		this.chunkTileEntityMap = new HashMap();
		this.entities = new List[8];
		this.isTerrainPopulated = false;
		this.isModified = false;
		this.isChunkRendered = false;
		this.hasEntities = false;
		this.lastSaveTime = 0L;
		this.worldObj = worldObj;
		this.xPosition = xPosition;
		this.zPosition = zPosition;
		this.heightMap = new byte[256];

		for(int var4 = 0; var4 < this.entities.length; ++var4) {
			this.entities[var4] = new ArrayList();
		}

	}

	public Chunk(World worldObj, byte[] blocks, int xPosition, int zPosition) {
		this(worldObj, xPosition, zPosition);
		this.blocks = blocks;
		this.data = new NibbleArray(blocks.length);
		this.skylightMap = new NibbleArray(blocks.length);
		this.blocklightMap = new NibbleArray(blocks.length);
	}

	public boolean isAtLocation(int xPosition, int zPosition) {
		return xPosition == this.xPosition && zPosition == this.zPosition;
	}

	public int getHeightValue(int blockX, int blockZ) {
		return this.heightMap[blockZ << 4 | blockX] & 255;
	}

	public void doNothing() {
	}

	public void generateHeightMap() {
		int var1 = 127;

		for(int var2 = 0; var2 < 16; ++var2) {
			for(int var3 = 0; var3 < 16; ++var3) {
				int var4 = 127;

				for(int var5 = var2 << 11 | var3 << 7; var4 > 0 && Block.lightOpacity[this.blocks[var5 + var4 - 1]] == 0; --var4) {
				}

				this.heightMap[var3 << 4 | var2] = (byte)var4;
				if(var4 < var1) {
					var1 = var4;
				}
			}
		}

		this.height = var1;
		this.isModified = true;
	}

	public void generateSkylightMap() {
		int var1 = 127;

		int var2;
		int var3;
		for(var2 = 0; var2 < 16; ++var2) {
			for(var3 = 0; var3 < 16; ++var3) {
				this.heightMap[var3 << 4 | var2] = -128;
				this.relightBlock(var2, 127, var3);
				if((this.heightMap[var3 << 4 | var2] & 255) < var1) {
					var1 = this.heightMap[var3 << 4 | var2] & 255;
				}
			}
		}

		this.height = var1;

		for(var2 = 0; var2 < 16; ++var2) {
			for(var3 = 0; var3 < 16; ++var3) {
				this.updateSkylight_do(var2, var3);
			}
		}

		this.isModified = true;
	}

	private void updateSkylight_do(int x, int z) {
		int var3 = this.getHeightValue(x, z);
		int var4 = this.xPosition * 16 + x;
		int var5 = this.zPosition * 16 + z;
		this.checkSkylightNeighborHeight(var4 - 1, var5, var3);
		this.checkSkylightNeighborHeight(var4 + 1, var5, var3);
		this.checkSkylightNeighborHeight(var4, var5 - 1, var3);
		this.checkSkylightNeighborHeight(var4, var5 + 1, var3);
	}

	private void checkSkylightNeighborHeight(int blockX, int blockZ, int blockY) {
		int var4 = this.worldObj.getHeightValue(blockX, blockZ);
		if(var4 > blockY) {
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, blockX, blockY, blockZ, blockX, var4, blockZ);
		} else if(var4 < blockY) {
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, blockX, var4, blockZ, blockX, blockY, blockZ);
		}

		this.isModified = true;
	}

	private void relightBlock(int x, int y, int z) {
		int var4 = this.heightMap[z << 4 | x] & 255;
		int var5 = var4;
		if(y > var4) {
			var5 = y;
		}

		for(int var6 = x << 11 | z << 7; var5 > 0 && Block.lightOpacity[this.blocks[var6 + var5 - 1]] == 0; --var5) {
		}

		if(var5 != var4) {
			this.worldObj.markBlocksDirtyVertical(x, z, var5, var4);
			this.heightMap[z << 4 | x] = (byte)var5;
			int var7;
			int var8;
			int var9;
			if(var5 < this.height) {
				this.height = var5;
			} else {
				var7 = 127;

				for(var8 = 0; var8 < 16; ++var8) {
					for(var9 = 0; var9 < 16; ++var9) {
						if((this.heightMap[var9 << 4 | var8] & 255) < var7) {
							var7 = this.heightMap[var9 << 4 | var8] & 255;
						}
					}
				}

				this.height = var7;
			}

			var7 = this.xPosition * 16 + x;
			var8 = this.zPosition * 16 + z;
			if(var5 < var4) {
				for(var9 = var5; var9 < var4; ++var9) {
					this.skylightMap.set(x, var9, z, 15);
				}
			} else {
				this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var7, var4, var8, var7, var5, var8);

				for(var9 = var4; var9 < var5; ++var9) {
					this.skylightMap.set(x, var9, z, 0);
				}
			}

			var9 = 15;

			int var10;
			for(var10 = var5; var5 > 0 && var9 > 0; this.skylightMap.set(x, var5, z, var9)) {
				--var5;
				int var11 = Block.lightOpacity[this.getBlockID(x, var5, z)];
				if(var11 == 0) {
					var11 = 1;
				}

				var9 -= var11;
				if(var9 < 0) {
					var9 = 0;
				}
			}

			while(var5 > 0 && Block.lightOpacity[this.getBlockID(x, var5 - 1, z)] == 0) {
				--var5;
			}

			if(var5 != var10) {
				this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var7 - 1, var5, var8 - 1, var7 + 1, var10, var8 + 1);
			}

			this.isModified = true;
		}
	}

	public int getBlockID(int x, int y, int z) {
		return this.blocks[x << 11 | z << 7 | y];
	}

	public boolean setBlockIDWithMetadata(int x, int y, int z, int id, int metadata) {
		byte var6 = (byte)id;
		int var7 = this.heightMap[z << 4 | x] & 255;
		int var8 = this.blocks[x << 11 | z << 7 | y] & 255;
		if(var8 == id && this.data.get(x, y, z) == metadata) {
			return false;
		} else {
			int var9 = this.xPosition * 16 + x;
			int var10 = this.zPosition * 16 + z;
			this.blocks[x << 11 | z << 7 | y] = var6;
			if(var8 != 0 && !this.worldObj.multiplayerWorld) {
				Block.blocksList[var8].onBlockRemoval(this.worldObj, var9, y, var10);
			}

			this.data.set(x, y, z, metadata);
			if(Block.lightOpacity[var6] != 0) {
				if(y >= var7) {
					this.relightBlock(x, y + 1, z);
				}
			} else if(y == var7 - 1) {
				this.relightBlock(x, y, z);
			}

			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var9, y, var10, var9, y, var10);
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Block, var9, y, var10, var9, y, var10);
			this.updateSkylight_do(x, z);
			if(id != 0) {
				Block.blocksList[id].onBlockAdded(this.worldObj, var9, y, var10);
			}

			this.isModified = true;
			return true;
		}
	}

	public boolean setBlockID(int x, int y, int z, int id) {
		byte var5 = (byte)id;
		int var6 = this.heightMap[z << 4 | x] & 255;
		int var7 = this.blocks[x << 11 | z << 7 | y] & 255;
		if(var7 == id) {
			return false;
		} else {
			int var8 = this.xPosition * 16 + x;
			int var9 = this.zPosition * 16 + z;
			this.blocks[x << 11 | z << 7 | y] = var5;
			if(var7 != 0) {
				Block.blocksList[var7].onBlockRemoval(this.worldObj, var8, y, var9);
			}

			this.data.set(x, y, z, 0);
			if(Block.lightOpacity[var5] != 0) {
				if(y >= var6) {
					this.relightBlock(x, y + 1, z);
				}
			} else if(y == var6 - 1) {
				this.relightBlock(x, y, z);
			}

			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Sky, var8, y, var9, var8, y, var9);
			this.worldObj.scheduleLightingUpdate(EnumSkyBlock.Block, var8, y, var9, var8, y, var9);
			this.updateSkylight_do(x, z);
			if(id != 0 && !this.worldObj.multiplayerWorld) {
				Block.blocksList[id].onBlockAdded(this.worldObj, var8, y, var9);
			}

			this.isModified = true;
			return true;
		}
	}

	public int getBlockMetadata(int x, int y, int z) {
		return this.data.get(x, y, z);
	}

	public void setBlockMetadata(int x, int y, int z, int metadata) {
		this.isModified = true;
		this.data.set(x, y, z, metadata);
	}

	public int getSavedLightValue(EnumSkyBlock skyBlock, int x, int y, int z) {
		return skyBlock == EnumSkyBlock.Sky ? this.skylightMap.get(x, y, z) : (skyBlock == EnumSkyBlock.Block ? this.blocklightMap.get(x, y, z) : 0);
	}

	public void setLightValue(EnumSkyBlock skyBlock, int x, int y, int z, int lightValue) {
		this.isModified = true;
		if(skyBlock == EnumSkyBlock.Sky) {
			this.skylightMap.set(x, y, z, lightValue);
		} else {
			if(skyBlock != EnumSkyBlock.Block) {
				return;
			}

			this.blocklightMap.set(x, y, z, lightValue);
		}

	}

	public int getBlockLightValue(int x, int y, int z, int skyLightSubtracted) {
		int var5 = this.skylightMap.get(x, y, z);
		if(var5 > 0) {
			isLit = true;
		}

		var5 -= skyLightSubtracted;
		int var6 = this.blocklightMap.get(x, y, z);
		if(var6 > var5) {
			var5 = var6;
		}

		return var5;
	}

	public void addEntity(Entity entity) {
		if(!this.isChunkRendered) {
			this.hasEntities = true;
			int var2 = MathHelper.floor_double(entity.posX / 16.0D);
			int var3 = MathHelper.floor_double(entity.posZ / 16.0D);
			if(var2 != this.xPosition || var3 != this.zPosition) {
				System.out.println("Wrong location! " + entity);
			}

			int var4 = MathHelper.floor_double(entity.posY / 16.0D);
			if(var4 < 0) {
				var4 = 0;
			}

			if(var4 >= this.entities.length) {
				var4 = this.entities.length - 1;
			}

			entity.addedToChunk = true;
			entity.chunkCoordX = this.xPosition;
			entity.chunkCoordY = var4;
			entity.chunkCoordZ = this.zPosition;
			this.entities[var4].add(entity);
		}
	}

	public void removeEntity(Entity entity) {
		this.removeEntityAtIndex(entity, entity.chunkCoordY);
	}

	public void removeEntityAtIndex(Entity entity, int index) {
		if(index < 0) {
			index = 0;
		}

		if(index >= this.entities.length) {
			index = this.entities.length - 1;
		}

		this.entities[index].remove(entity);
	}

	public boolean canBlockSeeTheSky(int x, int y, int z) {
		return y >= (this.heightMap[z << 4 | x] & 255);
	}

	public TileEntity getChunkBlockTileEntity(int x, int y, int z) {
		ChunkPosition var4 = new ChunkPosition(x, y, z);
		TileEntity var5 = (TileEntity)this.chunkTileEntityMap.get(var4);
		if(var5 == null) {
			int var6 = this.getBlockID(x, y, z);
			if(!Block.isBlockContainer[var6]) {
				return null;
			}

			BlockContainer var7 = (BlockContainer)Block.blocksList[var6];
			var7.onBlockAdded(this.worldObj, this.xPosition * 16 + x, y, this.zPosition * 16 + z);
			var5 = (TileEntity)this.chunkTileEntityMap.get(var4);
		}

		return var5;
	}

	public void addTileEntity(TileEntity tileEntity) {
		int var2 = tileEntity.xCoord - this.xPosition * 16;
		int var3 = tileEntity.yCoord;
		int var4 = tileEntity.zCoord - this.zPosition * 16;
		this.setChunkBlockTileEntity(var2, var3, var4, tileEntity);
	}

	public void setChunkBlockTileEntity(int x, int y, int z, TileEntity tileEntity) {
		ChunkPosition var5 = new ChunkPosition(x, y, z);
		tileEntity.worldObj = this.worldObj;
		tileEntity.xCoord = this.xPosition * 16 + x;
		tileEntity.yCoord = y;
		tileEntity.zCoord = this.zPosition * 16 + z;
		if(this.getBlockID(x, y, z) != 0 && Block.blocksList[this.getBlockID(x, y, z)] instanceof BlockContainer) {
			if(this.isChunkLoaded) {
				if(this.chunkTileEntityMap.get(var5) != null) {
					this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.get(var5));
				}

				this.worldObj.loadedTileEntityList.add(tileEntity);
			}

			this.chunkTileEntityMap.put(var5, tileEntity);
		} else {
			System.out.println("Attempted to place a tile entity where there was no entity tile!");
		}
	}

	public void removeChunkBlockTileEntity(int x, int y, int z) {
		ChunkPosition var4 = new ChunkPosition(x, y, z);
		if(this.isChunkLoaded) {
			this.worldObj.loadedTileEntityList.remove(this.chunkTileEntityMap.remove(var4));
		}

	}

	public void onChunkLoad() {
		this.isChunkLoaded = true;
		this.worldObj.loadedTileEntityList.addAll(this.chunkTileEntityMap.values());

		for(int var1 = 0; var1 < this.entities.length; ++var1) {
			this.worldObj.addLoadedEntities(this.entities[var1]);
		}

	}

	public void onChunkUnload() {
		this.isChunkLoaded = false;
		this.worldObj.loadedTileEntityList.removeAll(this.chunkTileEntityMap.values());

		for(int var1 = 0; var1 < this.entities.length; ++var1) {
			this.worldObj.unloadEntities(this.entities[var1]);
		}

	}

	public void setChunkModified() {
		this.isModified = true;
	}

	public void getEntitiesWithinAABBForEntity(Entity entity, AxisAlignedBB aabb, List list) {
		int var4 = MathHelper.floor_double((aabb.minY - 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((aabb.maxY + 2.0D) / 16.0D);
		if(var4 < 0) {
			var4 = 0;
		}

		if(var5 >= this.entities.length) {
			var5 = this.entities.length - 1;
		}

		for(int var6 = var4; var6 <= var5; ++var6) {
			List var7 = this.entities[var6];

			for(int var8 = 0; var8 < var7.size(); ++var8) {
				Entity var9 = (Entity)var7.get(var8);
				if(var9 != entity && var9.boundingBox.intersectsWith(aabb)) {
					list.add(var9);
				}
			}
		}

	}

	public void getEntitiesOfTypeWithinAAAB(Class entityClass, AxisAlignedBB aabb, List list) {
		int var4 = MathHelper.floor_double((aabb.minY - 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((aabb.maxY + 2.0D) / 16.0D);
		if(var4 < 0) {
			var4 = 0;
		}

		if(var5 >= this.entities.length) {
			var5 = this.entities.length - 1;
		}

		for(int var6 = var4; var6 <= var5; ++var6) {
			List var7 = this.entities[var6];

			for(int var8 = 0; var8 < var7.size(); ++var8) {
				Entity var9 = (Entity)var7.get(var8);
				if(entityClass.isAssignableFrom(var9.getClass()) && var9.boundingBox.intersectsWith(aabb)) {
					list.add(var9);
				}
			}
		}

	}

	public boolean needsSaving(boolean var1) {
		return this.neverSave ? false : (this.hasEntities && this.worldObj.worldTime != this.lastSaveTime ? true : this.isModified);
	}

	public int setChunkData(byte[] blocks, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int size) {
		int var9;
		int var10;
		int var11;
		int var12;
		for(var9 = minX; var9 < maxX; ++var9) {
			for(var10 = minZ; var10 < maxZ; ++var10) {
				var11 = var9 << 11 | var10 << 7 | minY;
				var12 = maxY - minY;
				System.arraycopy(blocks, size, this.blocks, var11, var12);
				size += var12;
			}
		}

		this.generateHeightMap();

		for(var9 = minX; var9 < maxX; ++var9) {
			for(var10 = minZ; var10 < maxZ; ++var10) {
				var11 = (var9 << 11 | var10 << 7 | minY) >> 1;
				var12 = (maxY - minY) / 2;
				System.arraycopy(blocks, size, this.data.data, var11, var12);
				size += var12;
			}
		}

		for(var9 = minX; var9 < maxX; ++var9) {
			for(var10 = minZ; var10 < maxZ; ++var10) {
				var11 = (var9 << 11 | var10 << 7 | minY) >> 1;
				var12 = (maxY - minY) / 2;
				System.arraycopy(blocks, size, this.blocklightMap.data, var11, var12);
				size += var12;
			}
		}

		for(var9 = minX; var9 < maxX; ++var9) {
			for(var10 = minZ; var10 < maxZ; ++var10) {
				var11 = (var9 << 11 | var10 << 7 | minY) >> 1;
				var12 = (maxY - minY) / 2;
				System.arraycopy(blocks, size, this.skylightMap.data, var11, var12);
				size += var12;
			}
		}

		return size;
	}

	public Random getRandomWithSeed(long var1) {
		return new Random(this.worldObj.randomSeed + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871L + (long)(this.zPosition * 389711) ^ var1);
	}
}
