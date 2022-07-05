package net.minecraft.src;

import java.util.Random;

public class BlockSapling extends BlockFlower {
	protected BlockSapling(int var1, int var2) {
		super(var1, var2);
		float var3 = 0.4F;
		this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		super.updateTick(worldObj, x, y, z, rand);
		if(worldObj.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(5) == 0) {
			int var6 = worldObj.getBlockMetadata(x, y, z);
			if(var6 < 15) {
				worldObj.setBlockMetadataWithNotify(x, y, z, var6 + 1);
			} else {
				worldObj.setBlock(x, y, z, 0);
				Object var7 = new WorldGenTrees();
				if(rand.nextInt(10) == 0) {
					var7 = new WorldGenBigTree();
				}

				if(!((WorldGenerator)var7).generate(worldObj, rand, x, y, z)) {
					worldObj.setBlock(x, y, z, this.blockID);
				}
			}
		}

	}
}
