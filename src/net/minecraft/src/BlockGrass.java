package net.minecraft.src;

import java.util.Random;

public class BlockGrass extends Block {
	protected BlockGrass(int blockID) {
		super(blockID, Material.grass);
		this.blockIndexInTexture = 3;
		this.setTickOnLoad(true);
	}

	public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		if(var5 == 1) {
			return 0;
		} else if(var5 == 0) {
			return 2;
		} else {
			Material var6 = var1.getBlockMaterial(var2, var3 + 1, var4);
			return var6 != Material.snow && var6 != Material.craftedSnow ? 3 : 68;
		}
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		if(worldObj.getBlockLightValue(x, y + 1, z) < 4 && worldObj.getBlockMaterial(x, y + 1, z).getCanBlockGrass()) {
			if(rand.nextInt(4) != 0) {
				return;
			}

			worldObj.setBlockWithNotify(x, y, z, Block.dirt.blockID);
		} else if(worldObj.getBlockLightValue(x, y + 1, z) >= 9) {
			int var6 = x + rand.nextInt(3) - 1;
			int var7 = y + rand.nextInt(5) - 3;
			int var8 = z + rand.nextInt(3) - 1;
			if(worldObj.getBlockId(var6, var7, var8) == Block.dirt.blockID && worldObj.getBlockLightValue(var6, var7 + 1, var8) >= 4 && !worldObj.getBlockMaterial(var6, var7 + 1, var8).getCanBlockGrass()) {
				worldObj.setBlockWithNotify(var6, var7, var8, Block.grass.blockID);
			}
		}

	}

	public int idDropped(int var1, Random var2) {
		return Block.dirt.idDropped(0, var2);
	}
}
