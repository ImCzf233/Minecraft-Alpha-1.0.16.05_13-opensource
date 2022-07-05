package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneWire extends Block {
	private boolean wiresProvidePower = true;

	public BlockRedstoneWire(int id, int tex) {
		super(id, tex, Material.circuits);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return this.blockIndexInTexture + (var2 > 0 ? 16 : 0);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 5;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return var1.isBlockNormalCube(var2, var3 - 1, var4);
	}

	private void updateAndPropagateCurrentStrength(World worldObj, int x, int y, int z) {
		int var5 = worldObj.getBlockMetadata(x, y, z);
		int var6 = 0;
		this.wiresProvidePower = false;
		boolean var7 = worldObj.isBlockIndirectlyGettingPowered(x, y, z);
		this.wiresProvidePower = true;
		int var8;
		int var9;
		int var10;
		if(var7) {
			var6 = 15;
		} else {
			for(var8 = 0; var8 < 4; ++var8) {
				var9 = x;
				var10 = z;
				if(var8 == 0) {
					var9 = x - 1;
				}

				if(var8 == 1) {
					++var9;
				}

				if(var8 == 2) {
					var10 = z - 1;
				}

				if(var8 == 3) {
					++var10;
				}

				var6 = this.getMaxCurrentStrength(worldObj, var9, y, var10, var6);
				if(worldObj.isBlockNormalCube(var9, y, var10) && !worldObj.isBlockNormalCube(x, y + 1, z)) {
					var6 = this.getMaxCurrentStrength(worldObj, var9, y + 1, var10, var6);
				} else if(!worldObj.isBlockNormalCube(var9, y, var10)) {
					var6 = this.getMaxCurrentStrength(worldObj, var9, y - 1, var10, var6);
				}
			}

			if(var6 > 0) {
				--var6;
			} else {
				var6 = 0;
			}
		}

		if(var5 != var6) {
			worldObj.setBlockMetadataWithNotify(x, y, z, var6);
			worldObj.markBlocksDirty(x, y, z, x, y, z);
			if(var6 > 0) {
				--var6;
			}

			for(var8 = 0; var8 < 4; ++var8) {
				var9 = x;
				var10 = z;
				int var11 = y - 1;
				if(var8 == 0) {
					var9 = x - 1;
				}

				if(var8 == 1) {
					++var9;
				}

				if(var8 == 2) {
					var10 = z - 1;
				}

				if(var8 == 3) {
					++var10;
				}

				if(worldObj.isBlockNormalCube(var9, y, var10)) {
					var11 += 2;
				}

				int var12 = this.getMaxCurrentStrength(worldObj, var9, y, var10, -1);
				if(var12 >= 0 && var12 != var6) {
					this.updateAndPropagateCurrentStrength(worldObj, var9, y, var10);
				}

				var12 = this.getMaxCurrentStrength(worldObj, var9, var11, var10, -1);
				if(var12 >= 0 && var12 != var6) {
					this.updateAndPropagateCurrentStrength(worldObj, var9, var11, var10);
				}
			}

			if(var5 == 0 || var6 == 0) {
				worldObj.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
				worldObj.notifyBlocksOfNeighborChange(x - 1, y, z, this.blockID);
				worldObj.notifyBlocksOfNeighborChange(x + 1, y, z, this.blockID);
				worldObj.notifyBlocksOfNeighborChange(x, y, z - 1, this.blockID);
				worldObj.notifyBlocksOfNeighborChange(x, y, z + 1, this.blockID);
				worldObj.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
				worldObj.notifyBlocksOfNeighborChange(x, y + 1, z, this.blockID);
			}
		}

	}

	private void notifyWireNeighborsOfNeighborChange(World worldObj, int x, int y, int z) {
		if(worldObj.getBlockId(x, y, z) == this.blockID) {
			worldObj.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
			worldObj.notifyBlocksOfNeighborChange(x - 1, y, z, this.blockID);
			worldObj.notifyBlocksOfNeighborChange(x + 1, y, z, this.blockID);
			worldObj.notifyBlocksOfNeighborChange(x, y, z - 1, this.blockID);
			worldObj.notifyBlocksOfNeighborChange(x, y, z + 1, this.blockID);
			worldObj.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
			worldObj.notifyBlocksOfNeighborChange(x, y + 1, z, this.blockID);
		}
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		super.onBlockAdded(var1, var2, var3, var4);
		this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
		var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
		var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
		this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3, var4);
		this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3, var4);
		this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 - 1);
		this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 + 1);
		if(var1.isBlockNormalCube(var2 - 1, var3, var4)) {
			this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 + 1, var4);
		} else {
			this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 - 1, var4);
		}

		if(var1.isBlockNormalCube(var2 + 1, var3, var4)) {
			this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 + 1, var4);
		} else {
			this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 - 1, var4);
		}

		if(var1.isBlockNormalCube(var2, var3, var4 - 1)) {
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 - 1);
		} else {
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 - 1);
		}

		if(var1.isBlockNormalCube(var2, var3, var4 + 1)) {
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 + 1);
		} else {
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 + 1);
		}

	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		super.onBlockRemoval(var1, var2, var3, var4);
		var1.notifyBlocksOfNeighborChange(var2, var3 + 1, var4, this.blockID);
		var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
		this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
		this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3, var4);
		this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3, var4);
		this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 - 1);
		this.notifyWireNeighborsOfNeighborChange(var1, var2, var3, var4 + 1);
		if(var1.isBlockNormalCube(var2 - 1, var3, var4)) {
			this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 + 1, var4);
		} else {
			this.notifyWireNeighborsOfNeighborChange(var1, var2 - 1, var3 - 1, var4);
		}

		if(var1.isBlockNormalCube(var2 + 1, var3, var4)) {
			this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 + 1, var4);
		} else {
			this.notifyWireNeighborsOfNeighborChange(var1, var2 + 1, var3 - 1, var4);
		}

		if(var1.isBlockNormalCube(var2, var3, var4 - 1)) {
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 - 1);
		} else {
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 - 1);
		}

		if(var1.isBlockNormalCube(var2, var3, var4 + 1)) {
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 + 1, var4 + 1);
		} else {
			this.notifyWireNeighborsOfNeighborChange(var1, var2, var3 - 1, var4 + 1);
		}

	}

	private int getMaxCurrentStrength(World worldObj, int x, int y, int z, int var5) {
		if(worldObj.getBlockId(x, y, z) != this.blockID) {
			return var5;
		} else {
			int var6 = worldObj.getBlockMetadata(x, y, z);
			return var6 > var5 ? var6 : var5;
		}
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		boolean var7 = this.canPlaceBlockAt(var1, var2, var3, var4);
		if(!var7) {
			this.dropBlockAsItem(var1, var2, var3, var4, var6);
			var1.setBlockWithNotify(var2, var3, var4, 0);
		} else {
			this.updateAndPropagateCurrentStrength(var1, var2, var3, var4);
		}

		super.onNeighborBlockChange(var1, var2, var3, var4, var5);
	}

	public int idDropped(int var1, Random var2) {
		return Item.redstone.shiftedIndex;
	}

	public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
		return !this.wiresProvidePower ? false : this.isPoweringTo(var1, var2, var3, var4, var5);
	}

	public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(!this.wiresProvidePower) {
			return false;
		} else if(var1.getBlockMetadata(var2, var3, var4) == 0) {
			return false;
		} else if(var5 == 1) {
			return true;
		} else {
			boolean var6 = isPowerProviderOrWire(var1, var2 - 1, var3, var4) || !var1.isBlockNormalCube(var2 - 1, var3, var4) && isPowerProviderOrWire(var1, var2 - 1, var3 - 1, var4);
			boolean var7 = isPowerProviderOrWire(var1, var2 + 1, var3, var4) || !var1.isBlockNormalCube(var2 + 1, var3, var4) && isPowerProviderOrWire(var1, var2 + 1, var3 - 1, var4);
			boolean var8 = isPowerProviderOrWire(var1, var2, var3, var4 - 1) || !var1.isBlockNormalCube(var2, var3, var4 - 1) && isPowerProviderOrWire(var1, var2, var3 - 1, var4 - 1);
			boolean var9 = isPowerProviderOrWire(var1, var2, var3, var4 + 1) || !var1.isBlockNormalCube(var2, var3, var4 + 1) && isPowerProviderOrWire(var1, var2, var3 - 1, var4 + 1);
			if(!var1.isBlockNormalCube(var2, var3 + 1, var4)) {
				if(var1.isBlockNormalCube(var2 - 1, var3, var4) && isPowerProviderOrWire(var1, var2 - 1, var3 + 1, var4)) {
					var6 = true;
				}

				if(var1.isBlockNormalCube(var2 + 1, var3, var4) && isPowerProviderOrWire(var1, var2 + 1, var3 + 1, var4)) {
					var7 = true;
				}

				if(var1.isBlockNormalCube(var2, var3, var4 - 1) && isPowerProviderOrWire(var1, var2, var3 + 1, var4 - 1)) {
					var8 = true;
				}

				if(var1.isBlockNormalCube(var2, var3, var4 + 1) && isPowerProviderOrWire(var1, var2, var3 + 1, var4 + 1)) {
					var9 = true;
				}
			}

			return !var8 && !var7 && !var6 && !var9 && var5 >= 2 && var5 <= 5 ? true : (var5 == 2 && var8 && !var6 && !var7 ? true : (var5 == 3 && var9 && !var6 && !var7 ? true : (var5 == 4 && var6 && !var8 && !var9 ? true : var5 == 5 && var7 && !var8 && !var9)));
		}
	}

	public boolean canProvidePower() {
		return this.wiresProvidePower;
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
		if(var1.getBlockMetadata(var2, var3, var4) > 0) {
			double var6 = (double)var2 + 0.5D + ((double)var5.nextFloat() - 0.5D) * 0.2D;
			double var8 = (double)((float)var3 + 0.0625F);
			double var10 = (double)var4 + 0.5D + ((double)var5.nextFloat() - 0.5D) * 0.2D;
			var1.spawnParticle("reddust", var6, var8, var10, 0.0D, 0.0D, 0.0D);
		}

	}

	public static boolean isPowerProviderOrWire(IBlockAccess blockAccess, int x, int y, int z) {
		int var4 = blockAccess.getBlockId(x, y, z);
		return var4 == Block.redstoneWire.blockID ? true : (var4 == 0 ? false : Block.blocksList[var4].canProvidePower());
	}
}
