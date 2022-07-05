package net.minecraft.src;

import java.util.Random;

public class BlockSand extends Block {
	public static boolean fallInstantly = false;

	public BlockSand(int id, int tex) {
		super(id, tex, Material.sand);
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		var1.scheduleBlockUpdate(var2, var3, var4, this.blockID);
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		var1.scheduleBlockUpdate(var2, var3, var4, this.blockID);
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		this.tryToFall(worldObj, x, y, z);
	}

	private void tryToFall(World worldObj, int x, int y, int z) {
		if(canFallBelow(worldObj, x, y - 1, z) && y >= 0) {
			EntityFallingSand var8 = new EntityFallingSand(worldObj, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, this.blockID);
			if(fallInstantly) {
				while(!var8.isDead) {
					var8.onUpdate();
				}
			} else {
				worldObj.spawnEntityInWorld(var8);
			}
		}

	}

	public int tickRate() {
		return 3;
	}

	public static boolean canFallBelow(World worldObj, int x, int y, int z) {
		int var4 = worldObj.getBlockId(x, y, z);
		if(var4 == 0) {
			return true;
		} else if(var4 == Block.fire.blockID) {
			return true;
		} else {
			Material var5 = Block.blocksList[var4].material;
			return var5 == Material.water ? true : var5 == Material.lava;
		}
	}
}
