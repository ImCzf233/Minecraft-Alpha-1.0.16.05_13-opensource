package net.minecraft.src;

public class BlockHidable extends BlockGlass {
	public boolean render = true;
	public int id;
	public int tRes;

	public BlockHidable(int var1, int var2, Material var3, boolean var4) {
		super(var1, var2, var3, var4);
		this.id = var1;
		this.tRes = var2;
	}

	public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return this.render ? this.tRes : 160;
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		ItemStack var6 = var5.inventory.getCurrentItem();
		if(var6 == null) {
			this.render = !this.render;
			var1.setBlockWithNotify(var2, var3, var4, this.id);
			return true;
		} else {
			return false;
		}
	}
}
