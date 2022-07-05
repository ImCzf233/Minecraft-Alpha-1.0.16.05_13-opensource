package net.minecraft.src;

import java.util.Random;

public class BlockGlowingFlower extends BlockGlowing {
	int renderMode = 1;

	protected BlockGlowingFlower(int var1, int var2, int var3) {
		super(var1, var2, var3);
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
		return true;
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		ItemStack var6 = var5.inventory.getCurrentItem();
		if(var6 == null) {
			return false;
		} else {
			if(var6.itemID == 116) {
				var1.setBlockWithNotify(var2, var3, var4, 117);
				var5.inventory.consumeInventoryItem(116);
				var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "ext.infuse", 1.0F, 1.0F);
			} else if(var6.itemID == 266) {
				var1.setBlockWithNotify(var2, var3, var4, 118);
				var5.inventory.consumeInventoryItem(266);
				var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "ext.infuse", 1.0F, 0.7F);
			} else if(var6.itemID == 355) {
				var1.setBlockWithNotify(var2, var3, var4, 119);
				var5.inventory.consumeInventoryItem(355);
				var1.playSoundEffect((double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F), (double)((float)var4 + 0.5F), "ext.infuse", 1.0F, 0.3F);
			}

			return true;
		}
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
	}
}
