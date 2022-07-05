package net.minecraft.src;

public class BlockBreakable extends Block {
	private boolean localFlag;

	protected BlockBreakable(int id, int tex, Material material, boolean localFlag) {
		super(id, tex, material);
		this.localFlag = localFlag;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		int var6 = var1.getBlockId(var2, var3, var4);
		return !this.localFlag && var6 == this.blockID ? false : super.shouldSideBeRendered(var1, var2, var3, var4, var5);
	}
}
