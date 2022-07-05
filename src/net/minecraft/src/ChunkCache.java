package net.minecraft.src;

public class ChunkCache implements IBlockAccess {
	private int chunkX;
	private int chunkZ;
	private Chunk[][] chunkArray;
	private World worldObj;

	public ChunkCache(World worldObj, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		this.worldObj = worldObj;
		this.chunkX = minX >> 4;
		this.chunkZ = minZ >> 4;
		int var8 = maxX >> 4;
		int var9 = maxZ >> 4;
		this.chunkArray = new Chunk[var8 - this.chunkX + 1][var9 - this.chunkZ + 1];

		for(int var10 = this.chunkX; var10 <= var8; ++var10) {
			for(int var11 = this.chunkZ; var11 <= var9; ++var11) {
				this.chunkArray[var10 - this.chunkX][var11 - this.chunkZ] = worldObj.getChunkFromChunkCoords(var10, var11);
			}
		}

	}

	public int getBlockId(int x, int y, int z) {
		if(y < 0) {
			return 0;
		} else if(y >= 128) {
			return 0;
		} else {
			int var4 = (x >> 4) - this.chunkX;
			int var5 = (z >> 4) - this.chunkZ;
			return this.chunkArray[var4][var5].getBlockID(x & 15, y, z & 15);
		}
	}

	public TileEntity getBlockTileEntity(int var1, int var2, int var3) {
		int var4 = (var1 >> 4) - this.chunkX;
		int var5 = (var3 >> 4) - this.chunkZ;
		return this.chunkArray[var4][var5].getChunkBlockTileEntity(var1 & 15, var2, var3 & 15);
	}

	public float getBrightness(int x, int y, int z) {
		return World.lightBrightnessTable[this.getLightValue(x, y, z)];
	}

	public int getLightValue(int x, int y, int z) {
		return this.getLightValueExt(x, y, z, true);
	}

	public int getLightValueExt(int x, int y, int z, boolean var4) {
		if(x >= -32000000 && z >= -32000000 && x < 32000000 && z <= 32000000) {
			int var5;
			int var6;
			if(var4) {
				var5 = this.getBlockId(x, y, z);
				if(var5 == Block.stairSingle.blockID || var5 == Block.tilledField.blockID) {
					var6 = this.getLightValueExt(x, y + 1, z, false);
					int var7 = this.getLightValueExt(x + 1, y, z, false);
					int var8 = this.getLightValueExt(x - 1, y, z, false);
					int var9 = this.getLightValueExt(x, y, z + 1, false);
					int var10 = this.getLightValueExt(x, y, z - 1, false);
					if(var7 > var6) {
						var6 = var7;
					}

					if(var8 > var6) {
						var6 = var8;
					}

					if(var9 > var6) {
						var6 = var9;
					}

					if(var10 > var6) {
						var6 = var10;
					}

					return var6;
				}
			}

			if(y < 0) {
				return 0;
			} else if(y >= 128) {
				var5 = 15 - this.worldObj.skylightSubtracted;
				if(var5 < 0) {
					var5 = 0;
				}

				return var5;
			} else {
				var5 = (x >> 4) - this.chunkX;
				var6 = (z >> 4) - this.chunkZ;
				return this.chunkArray[var5][var6].getBlockLightValue(x & 15, y, z & 15, this.worldObj.skylightSubtracted);
			}
		} else {
			return 15;
		}
	}

	public int getBlockMetadata(int x, int y, int z) {
		if(y < 0) {
			return 0;
		} else if(y >= 128) {
			return 0;
		} else {
			int var4 = (x >> 4) - this.chunkX;
			int var5 = (z >> 4) - this.chunkZ;
			return this.chunkArray[var4][var5].getBlockMetadata(x & 15, y, z & 15);
		}
	}

	public Material getBlockMaterial(int var1, int var2, int var3) {
		int var4 = this.getBlockId(var1, var2, var3);
		return var4 == 0 ? Material.air : Block.blocksList[var4].material;
	}

	public boolean isBlockNormalCube(int var1, int var2, int var3) {
		Block var4 = Block.blocksList[this.getBlockId(var1, var2, var3)];
		return var4 == null ? false : var4.isOpaqueCube();
	}
}
