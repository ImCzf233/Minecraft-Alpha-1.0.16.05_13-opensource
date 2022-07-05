package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneOre extends Block {
	private boolean glowing;

	public BlockRedstoneOre(int id, int tex, boolean glowing) {
		super(id, tex, Material.rock);
		if(glowing) {
			this.setTickOnLoad(true);
		}

		this.glowing = glowing;
	}

	public int tickRate() {
		return 30;
	}

	public void onBlockClicked(World worldObj, int x, int y, int z, EntityPlayer entityPlayer) {
		this.glow(worldObj, x, y, z);
		super.onBlockClicked(worldObj, x, y, z, entityPlayer);
	}

	public void onEntityWalking(World worldObj, int x, int y, int z, Entity entity) {
		this.glow(worldObj, x, y, z);
		super.onEntityWalking(worldObj, x, y, z, entity);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		this.glow(var1, var2, var3, var4);
		return super.blockActivated(var1, var2, var3, var4, var5);
	}

	private void glow(World worldObj, int x, int y, int z) {
		this.sparkle(worldObj, x, y, z);
		if(this.blockID == Block.oreRedstone.blockID) {
			worldObj.setBlockWithNotify(x, y, z, Block.oreRedstoneGlowing.blockID);
		}

	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		if(this.blockID == Block.oreRedstoneGlowing.blockID) {
			worldObj.setBlockWithNotify(x, y, z, Block.oreRedstone.blockID);
		}

	}

	public int idDropped(int var1, Random var2) {
		return Item.redstone.shiftedIndex;
	}

	public int quantityDropped(Random var1) {
		return 4 + var1.nextInt(2);
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
		if(this.glowing) {
			this.sparkle(var1, var2, var3, var4);
		}

	}

	private void sparkle(World worldObj, int x, int y, int z) {
		Random var5 = worldObj.rand;
		double var6 = 0.0625D;

		for(int var8 = 0; var8 < 6; ++var8) {
			double var9 = (double)((float)x + var5.nextFloat());
			double var11 = (double)((float)y + var5.nextFloat());
			double var13 = (double)((float)z + var5.nextFloat());
			if(var8 == 0 && !worldObj.isBlockNormalCube(x, y + 1, z)) {
				var11 = (double)(y + 1) + var6;
			}

			if(var8 == 1 && !worldObj.isBlockNormalCube(x, y - 1, z)) {
				var11 = (double)(y + 0) - var6;
			}

			if(var8 == 2 && !worldObj.isBlockNormalCube(x, y, z + 1)) {
				var13 = (double)(z + 1) + var6;
			}

			if(var8 == 3 && !worldObj.isBlockNormalCube(x, y, z - 1)) {
				var13 = (double)(z + 0) - var6;
			}

			if(var8 == 4 && !worldObj.isBlockNormalCube(x + 1, y, z)) {
				var9 = (double)(x + 1) + var6;
			}

			if(var8 == 5 && !worldObj.isBlockNormalCube(x - 1, y, z)) {
				var9 = (double)(x + 0) - var6;
			}

			if(var9 < (double)x || var9 > (double)(x + 1) || var11 < 0.0D || var11 > (double)(y + 1) || var13 < (double)z || var13 > (double)(z + 1)) {
				worldObj.spawnParticle("reddust", var9, var11, var13, 0.0D, 0.0D, 0.0D);
			}
		}

	}
}
