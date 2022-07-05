package net.minecraft.src;

import java.util.Random;

public class BlockFlower extends Block {
	protected BlockFlower(int id, int tex) {
		super(id, Material.plants);
		this.blockIndexInTexture = tex;
		this.setTickOnLoad(true);
		float var3 = 0.2F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 3.0F, 0.5F + var3);
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
	}

	protected boolean canThisPlantGrowOnThisBlockID(int blockID) {
		return blockID == Block.grass.blockID || blockID == Block.dirt.blockID || blockID == Block.tilledField.blockID;
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		super.onNeighborBlockChange(var1, var2, var3, var4, var5);
		this.checkFlowerChange(var1, var2, var3, var4);
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		this.checkFlowerChange(worldObj, x, y, z);
	}

	protected final void checkFlowerChange(World worldObj, int x, int y, int z) {
		if(!this.canBlockStay(worldObj, x, y, z)) {
			this.dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z));
			worldObj.setBlockWithNotify(x, y, z, 0);
		}

	}

	public boolean canBlockStay(World var1, int var2, int var3, int var4) {
		return (var1.getBlockLightValue(var2, var3, var4) >= 8 || var1.canBlockSeeTheSky(var2, var3, var4)) && this.canThisPlantGrowOnThisBlockID(var1.getBlockId(var2, var3 - 1, var4));
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
		return 1;
	}
}
