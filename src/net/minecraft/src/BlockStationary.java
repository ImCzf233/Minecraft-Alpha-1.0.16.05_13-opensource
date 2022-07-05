package net.minecraft.src;

import java.util.Random;

public class BlockStationary extends BlockFluid {
	protected BlockStationary(int var1, Material var2) {
		super(var1, var2);
		this.setTickOnLoad(false);
		if(var2 == Material.lava) {
			this.setTickOnLoad(true);
		}

	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
		super.onNeighborBlockChange(var1, var2, var3, var4, var5);
		if(var1.getBlockId(var2, var3, var4) == this.blockID) {
			this.setNotStationary(var1, var2, var3, var4);
		}

	}

	private void setNotStationary(World worldObj, int x, int y, int z) {
		int var5 = worldObj.getBlockMetadata(x, y, z);
		worldObj.editingBlocks = true;
		worldObj.setBlockAndMetadata(x, y, z, this.blockID - 1, var5);
		worldObj.markBlocksDirty(x, y, z, x, y, z);
		worldObj.scheduleBlockUpdate(x, y, z, this.blockID - 1);
		worldObj.editingBlocks = false;
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
		if(this.material == Material.lava) {
			int var6 = rand.nextInt(3);

			for(int var7 = 0; var7 < var6; ++var7) {
				x += rand.nextInt(3) - 1;
				++y;
				z += rand.nextInt(3) - 1;
				int var8 = worldObj.getBlockId(x, y, z);
				if(var8 == 0) {
					if(this.isFlammable(worldObj, x - 1, y, z) || this.isFlammable(worldObj, x + 1, y, z) || this.isFlammable(worldObj, x, y, z - 1) || this.isFlammable(worldObj, x, y, z + 1) || this.isFlammable(worldObj, x, y - 1, z) || this.isFlammable(worldObj, x, y + 1, z)) {
						worldObj.setBlockWithNotify(x, y, z, Block.fire.blockID);
						return;
					}
				} else if(Block.blocksList[var8].material.getIsSolid()) {
					return;
				}
			}
		}

	}

	private boolean isFlammable(World worldObj, int x, int y, int z) {
		return worldObj.getBlockMaterial(x, y, z).getCanBurn();
	}
}
