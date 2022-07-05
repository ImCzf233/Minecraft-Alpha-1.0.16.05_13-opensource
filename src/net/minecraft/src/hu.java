package net.minecraft.src;

import java.util.Random;

public class hu extends Block {
	protected hu(int var1, int var2) {
		super(var1, var2, Material.circuits);
		this.setTickOnLoad(true);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public int tickRate() {
		return 20;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return var1.isBlockNormalCube(var2 - 1, var3, var4) ? true : (var1.isBlockNormalCube(var2 + 1, var3, var4) ? true : (var1.isBlockNormalCube(var2, var3, var4 - 1) ? true : var1.isBlockNormalCube(var2, var3, var4 + 1)));
	}

	public void onBlockPlaced(World var1, int var2, int var3, int var4, int var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		int var7 = var6 & 8;
		var6 &= 7;
		if(var5 == 2 && var1.isBlockNormalCube(var2, var3, var4 + 1)) {
			var6 = 4;
		}

		if(var5 == 3 && var1.isBlockNormalCube(var2, var3, var4 - 1)) {
			var6 = 3;
		}

		if(var5 == 4 && var1.isBlockNormalCube(var2 + 1, var3, var4)) {
			var6 = 2;
		}

		if(var5 == 5 && var1.isBlockNormalCube(var2 - 1, var3, var4)) {
			var6 = 1;
		}

		var1.setBlockMetadataWithNotify(var2, var3, var4, var6 + var7);
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		if(var1.isBlockNormalCube(var2 - 1, var3, var4)) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
		} else if(var1.isBlockNormalCube(var2 + 1, var3, var4)) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
		} else if(var1.isBlockNormalCube(var2, var3, var4 - 1)) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
		} else if(var1.isBlockNormalCube(var2, var3, var4 + 1)) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
		}

		this.h(var1, var2, var3, var4);
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		if(this.h(var1, var2, var3, var4)) {
			int var6 = var1.getBlockMetadata(var2, var3, var4) & 7;
			boolean var7 = false;
			if(!var1.isBlockNormalCube(var2 - 1, var3, var4) && var6 == 1) {
				var7 = true;
			}

			if(!var1.isBlockNormalCube(var2 + 1, var3, var4) && var6 == 2) {
				var7 = true;
			}

			if(!var1.isBlockNormalCube(var2, var3, var4 - 1) && var6 == 3) {
				var7 = true;
			}

			if(!var1.isBlockNormalCube(var2, var3, var4 + 1) && var6 == 4) {
				var7 = true;
			}

			if(var7) {
				this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
				var1.setBlockWithNotify(var2, var3, var4, 0);
			}
		}

	}

	private boolean h(World var1, int var2, int var3, int var4) {
		if(!this.canPlaceBlockAt(var1, var2, var3, var4)) {
			this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4));
			var1.setBlockWithNotify(var2, var3, var4, 0);
			return false;
		} else {
			return true;
		}
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4);
		int var6 = var5 & 7;
		boolean var7 = (var5 & 8) > 0;
		float var8 = 0.375F;
		float var9 = 0.625F;
		float var10 = 0.1875F;
		float var11 = 0.125F;
		if(var7) {
			var11 = 0.0625F;
		}

		if(var6 == 1) {
			this.setBlockBounds(0.0F, var8, 0.5F - var10, var11, var9, 0.5F + var10);
		} else if(var6 == 2) {
			this.setBlockBounds(1.0F - var11, var8, 0.5F - var10, 1.0F, var9, 0.5F + var10);
		} else if(var6 == 3) {
			this.setBlockBounds(0.5F - var10, var8, 0.0F, 0.5F + var10, var9, var11);
		} else if(var6 == 4) {
			this.setBlockBounds(0.5F - var10, var8, 1.0F - var11, 0.5F + var10, var9, 1.0F);
		}

	}

	public void onBlockClicked(World worldObj, int x, int y, int z, EntityPlayer entityPlayer) {
		this.blockActivated(worldObj, x, y, z, entityPlayer);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		int var7 = var6 & 7;
		int var8 = 8 - (var6 & 8);
		if(var8 == 0) {
			return true;
		} else {
			var1.setBlockMetadataWithNotify(var2, var3, var4, var7 + var8);
			var1.markBlocksDirty(var2, var3, var4, var2, var3, var4);
			var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.click", 0.3F, 0.6F);
			var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
			if(var7 == 1) {
				var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
			} else if(var7 == 2) {
				var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
			} else if(var7 == 3) {
				var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
			} else if(var7 == 4) {
				var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
			} else {
				var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			}

			var1.scheduleBlockUpdate(var2, var3, var4, this.blockID);
			return true;
		}
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4);
		if((var5 & 8) > 0) {
			var1.notifyBlocksOfNeighborChange(var2, var3, var4, this.blockID);
			int var6 = var5 & 7;
			if(var6 == 1) {
				var1.notifyBlocksOfNeighborChange(var2 - 1, var3, var4, this.blockID);
			} else if(var6 == 2) {
				var1.notifyBlocksOfNeighborChange(var2 + 1, var3, var4, this.blockID);
			} else if(var6 == 3) {
				var1.notifyBlocksOfNeighborChange(var2, var3, var4 - 1, this.blockID);
			} else if(var6 == 4) {
				var1.notifyBlocksOfNeighborChange(var2, var3, var4 + 1, this.blockID);
			} else {
				var1.notifyBlocksOfNeighborChange(var2, var3 - 1, var4, this.blockID);
			}
		}

		super.onBlockRemoval(var1, var2, var3, var4);
	}

	public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return (var1.getBlockMetadata(var2, var3, var4) & 8) > 0;
	}

	public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		if((var6 & 8) == 0) {
			return false;
		} else {
			int var7 = var6 & 7;
			return var7 == 5 && var5 == 1 ? true : (var7 == 4 && var5 == 2 ? true : (var7 == 3 && var5 == 3 ? true : (var7 == 2 && var5 == 4 ? true : var7 == 1 && var5 == 5)));
		}
	}

	public boolean canProvidePower() {
		return true;
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		int var6 = worldObj.getBlockMetadata(x, y, z);
		if((var6 & 8) != 0) {
			worldObj.setBlockMetadataWithNotify(x, y, z, var6 & 7);
			worldObj.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
			int var7 = var6 & 7;
			if(var7 == 1) {
				worldObj.notifyBlocksOfNeighborChange(x - 1, y, z, this.blockID);
			} else if(var7 == 2) {
				worldObj.notifyBlocksOfNeighborChange(x + 1, y, z, this.blockID);
			} else if(var7 == 3) {
				worldObj.notifyBlocksOfNeighborChange(x, y, z - 1, this.blockID);
			} else if(var7 == 4) {
				worldObj.notifyBlocksOfNeighborChange(x, y, z + 1, this.blockID);
			} else {
				worldObj.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
			}

			worldObj.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.click", 0.3F, 0.5F);
			worldObj.markBlocksDirty(x, y, z, x, y, z);
		}
	}

	public void setBlockBoundsForItemRender() {
		float var1 = 0.1875F;
		float var2 = 0.125F;
		float var3 = 0.125F;
		this.setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
	}
}
