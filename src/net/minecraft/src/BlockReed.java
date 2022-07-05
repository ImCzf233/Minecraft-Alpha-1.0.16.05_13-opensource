package net.minecraft.src;

import java.util.Random;

public class BlockReed extends Block {
	protected BlockReed(int id, int tex) {
		super(id, Material.plants);
		this.blockIndexInTexture = tex;
		float var3 = 0.375F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 1.0F, 0.5F + var3);
		this.setTickOnLoad(true);
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		if(worldObj.getBlockId(x, y + 1, z) == 0) {
			int var6;
			for(var6 = 1; worldObj.getBlockId(x, y - var6, z) == this.blockID; ++var6) {
			}

			if(var6 < 3) {
				int var7 = worldObj.getBlockMetadata(x, y, z);
				if(var7 == 15) {
					worldObj.setBlockWithNotify(x, y + 1, z, this.blockID);
					worldObj.setBlockMetadataWithNotify(x, y, z, 0);
				} else {
					worldObj.setBlockMetadataWithNotify(x, y, z, var7 + 1);
				}
			}
		}

	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockId(var2, var3 - 1, var4);
		return var5 == this.blockID ? true : (var5 != Block.grass.blockID && var5 != Block.dirt.blockID ? false : (var1.getBlockMaterial(var2 - 1, var3 - 1, var4) == Material.water ? true : (var1.getBlockMaterial(var2 + 1, var3 - 1, var4) == Material.water ? true : (var1.getBlockMaterial(var2, var3 - 1, var4 - 1) == Material.water ? true : var1.getBlockMaterial(var2, var3 - 1, var4 + 1) == Material.water))));
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		this.checkBlockCoordValid(var1, var2, var3, var4);
	}

	protected final void checkBlockCoordValid(World worldObj, int x, int y, int z) {
		if(!this.canBlockStay(worldObj, x, y, z)) {
			this.dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z));
			worldObj.setBlockWithNotify(x, y, z, 0);
		}

	}

	public boolean canBlockStay(World var1, int var2, int var3, int var4) {
		return this.canPlaceBlockAt(var1, var2, var3, var4);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return null;
	}

	public int idDropped(int var1, Random var2) {
		return Item.reed.shiftedIndex;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 1;
	}
}
