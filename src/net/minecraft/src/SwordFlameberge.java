package net.minecraft.src;

public class SwordFlameberge extends ItemSword {
	int[] v2s = new int[]{3, 0, 3, 1, 2, 2, 1, 3, 0, 3, -1, 3, -2, 2, -3, 1, -3, 0, -3, -1, -2, -2, -1, -3, 0, -3, 1, -3, 2, -2, 3, -1};

	public SwordFlameberge(int var1) {
		super(var1, 2);
	}

	public boolean BlockIDFirable(int var1) {
		return var1 == 0 || var1 == 78;
	}

	public void SetBlockFire(World var1, int var2, int var3, int var4, int var5) {
		if(var5 != 6) {
			int var6 = var1.getBlockId(var2, var3, var4);
			var1.getBlockId(var2, var3 + 1, var4);
			int var8 = var1.getBlockId(var2, var3 - 1, var4);
			if(this.BlockIDFirable(var6)) {
				if(!this.BlockIDFirable(var8)) {
					var1.setBlockWithNotify(var2, var3, var4, Block.fire.blockID);
				} else {
					this.SetBlockFire(var1, var2, var3 - 1, var4, var5 + 1);
				}
			} else {
				this.SetBlockFire(var1, var2, var3 + 1, var4, var5 + 1);
			}

		}
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		int var4 = (int)InputHandler.mc.thePlayer.posX;
		int var5 = (int)InputHandler.mc.thePlayer.posY;
		int var6 = (int)InputHandler.mc.thePlayer.posZ;

		for(int var7 = 0; var7 != this.v2s.length / 2; ++var7) {
			this.SetBlockFire(var2, var4 + this.v2s[var7 * 2], var5, var6 + this.v2s[var7 * 2 + 1], 0);
		}

		return var1;
	}
}
