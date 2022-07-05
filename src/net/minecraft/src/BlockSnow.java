package net.minecraft.src;

import java.util.Random;

public class BlockSnow extends Block {
	protected BlockSnow(int id, int tex) {
		super(id, tex, Material.snow);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setTickOnLoad(true);
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

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockId(var2, var3 - 1, var4);
		return var5 != 0 && Block.blocksList[var5].isOpaqueCube() ? var1.getBlockMaterial(var2, var3 - 1, var4).getIsSolid() : false;
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		this.canSnowStay(var1, var2, var3, var4);
	}

	private boolean canSnowStay(World worldObj, int x, int y, int z) {
		if(!this.canPlaceBlockAt(worldObj, x, y, z)) {
			this.dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z));
			worldObj.setBlockWithNotify(x, y, z, 0);
			return false;
		} else {
			return true;
		}
	}

	public void harvestBlock(World worldObj, int x, int y, int z, int metadata) {
		int var6 = Item.snowball.shiftedIndex;
		float var7 = 0.7F;
		double var8 = (double)(worldObj.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
		double var10 = (double)(worldObj.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
		double var12 = (double)(worldObj.rand.nextFloat() * var7) + (double)(1.0F - var7) * 0.5D;
		EntityItem var14 = new EntityItem(worldObj, (double)x + var8, (double)y + var10, (double)z + var12, new ItemStack(var6));
		var14.delayBeforeCanPickup = 10;
		worldObj.spawnEntityInWorld(var14);
		worldObj.setBlockWithNotify(x, y, z, 0);
	}

	public int idDropped(int var1, Random var2) {
		return Item.snowball.shiftedIndex;
	}

	public int quantityDropped(Random var1) {
		return 0;
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		if(worldObj.getSavedLightValue(EnumSkyBlock.Block, x, y, z) > 11) {
			this.dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z));
			worldObj.setBlockWithNotify(x, y, z, 0);
		}

	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		Material var6 = var1.getBlockMaterial(var2, var3, var4);
		return var5 == 1 ? true : (var6 == this.material ? false : super.shouldSideBeRendered(var1, var2, var3, var4, var5));
	}
}
