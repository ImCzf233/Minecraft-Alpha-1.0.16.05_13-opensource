package net.minecraft.src;

import java.util.Random;

public class BlockDoor extends Block {
	protected BlockDoor(int var1, Material var2) {
		super(var1, var2);
		this.blockIndexInTexture = 97;
		if(var2 == Material.iron) {
			++this.blockIndexInTexture;
		}

		float var3 = 0.5F;
		float var4 = 1.0F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		if(var1 != 0 && var1 != 1) {
			int var3 = this.getState(var2);
			if((var3 == 0 || var3 == 2) ^ var1 <= 3) {
				return this.blockIndexInTexture;
			} else {
				int var4 = var3 / 2 + (var1 & 1 ^ var3);
				var4 += (var2 & 4) / 4;
				int var5 = this.blockIndexInTexture - (var2 & 8) * 2;
				if((var4 & 1) != 0) {
					var5 = -var5;
				}

				return var5;
			}
		} else {
			return this.blockIndexInTexture;
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 7;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		return super.getSelectedBoundingBoxFromPool(var1, var2, var3, var4);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		return super.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
		this.setDoorRotation(this.getState(var1.getBlockMetadata(var2, var3, var4)));
	}

	public void setDoorRotation(int metadata) {
		float var2 = 0.1875F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
		if(metadata == 0) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
		}

		if(metadata == 1) {
			this.setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if(metadata == 2) {
			this.setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
		}

		if(metadata == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
		}

	}

	public void onBlockClicked(World worldObj, int x, int y, int z, EntityPlayer entityPlayer) {
		this.blockActivated(worldObj, x, y, z, entityPlayer);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		if(this.material == Material.iron) {
			return true;
		} else {
			int var6 = var1.getBlockMetadata(var2, var3, var4);
			if((var6 & 8) != 0) {
				if(var1.getBlockId(var2, var3 - 1, var4) == this.blockID) {
					this.blockActivated(var1, var2, var3 - 1, var4, var5);
				}

				return true;
			} else {
				if(var1.getBlockId(var2, var3 + 1, var4) == this.blockID) {
					var1.setBlockMetadataWithNotify(var2, var3 + 1, var4, (var6 ^ 4) + 8);
				}

				var1.setBlockMetadataWithNotify(var2, var3, var4, var6 ^ 4);
				var1.markBlocksDirty(var2, var3 - 1, var4, var2, var3, var4);
				if(Math.random() < 0.5D) {
					var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.door_open", 1.0F, var1.rand.nextFloat() * 0.1F + 0.9F);
				} else {
					var1.playSoundEffect((double)var2 + 0.5D, (double)var3 + 0.5D, (double)var4 + 0.5D, "random.door_close", 1.0F, var1.rand.nextFloat() * 0.1F + 0.9F);
				}

				return true;
			}
		}
	}

	public void onPoweredBlockChange(World worldObj, int x, int y, int z, boolean var5) {
		int var6 = worldObj.getBlockMetadata(x, y, z);
		if((var6 & 8) != 0) {
			if(worldObj.getBlockId(x, y - 1, z) == this.blockID) {
				this.onPoweredBlockChange(worldObj, x, y - 1, z, var5);
			}

		} else {
			boolean var7 = (worldObj.getBlockMetadata(x, y, z) & 4) > 0;
			if(var7 != var5) {
				if(worldObj.getBlockId(x, y + 1, z) == this.blockID) {
					worldObj.setBlockMetadataWithNotify(x, y + 1, z, (var6 ^ 4) + 8);
				}

				worldObj.setBlockMetadataWithNotify(x, y, z, var6 ^ 4);
				worldObj.markBlocksDirty(x, y - 1, z, x, y, z);
				if(Math.random() < 0.5D) {
					worldObj.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.door_open", 1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
				} else {
					worldObj.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "random.door_close", 1.0F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
				}

			}
		}
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		int var6 = var1.getBlockMetadata(var2, var3, var4);
		if((var6 & 8) != 0) {
			if(var1.getBlockId(var2, var3 - 1, var4) != this.blockID) {
				var1.setBlockWithNotify(var2, var3, var4, 0);
			}

			if(var5 > 0 && Block.blocksList[var5].canProvidePower()) {
				this.onNeighborBlockChange(var1, var2, var3 - 1, var4, var5);
			}
		} else {
			boolean var7 = false;
			if(var1.getBlockId(var2, var3 + 1, var4) != this.blockID) {
				var1.setBlockWithNotify(var2, var3, var4, 0);
				var7 = true;
			}

			if(!var1.isBlockNormalCube(var2, var3 - 1, var4)) {
				var1.setBlockWithNotify(var2, var3, var4, 0);
				var7 = true;
				if(var1.getBlockId(var2, var3 + 1, var4) == this.blockID) {
					var1.setBlockWithNotify(var2, var3 + 1, var4, 0);
				}
			}

			if(var7) {
				this.dropBlockAsItem(var1, var2, var3, var4, var6);
			} else if(var5 > 0 && Block.blocksList[var5].canProvidePower()) {
				boolean var8 = var1.isBlockIndirectlyGettingPowered(var2, var3, var4) || var1.isBlockIndirectlyGettingPowered(var2, var3 + 1, var4);
				this.onPoweredBlockChange(var1, var2, var3, var4, var8);
			}
		}

	}

	public int idDropped(int var1, Random var2) {
		return (var1 & 8) != 0 ? 0 : (this.material == Material.iron ? Item.doorSteel.shiftedIndex : Item.doorWood.shiftedIndex);
	}

	public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		return super.collisionRayTrace(var1, var2, var3, var4, var5, var6);
	}

	public int getState(int metadata) {
		return (metadata & 4) == 0 ? metadata - 1 & 3 : metadata & 3;
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return var3 >= 127 ? false : var1.isBlockNormalCube(var2, var3 - 1, var4) && super.canPlaceBlockAt(var1, var2, var3, var4) && super.canPlaceBlockAt(var1, var2, var3 + 1, var4);
	}
}
