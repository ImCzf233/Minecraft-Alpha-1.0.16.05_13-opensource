package net.minecraft.src;

public class BlockWorkbench extends Block {
	protected BlockWorkbench(int id) {
		super(id, Material.wood);
		this.blockIndexInTexture = 59;
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 1 ? this.blockIndexInTexture - 16 : (var1 == 0 ? Block.planks.getBlockTextureFromSide(0) : (var1 != 2 && var1 != 4 ? this.blockIndexInTexture : this.blockIndexInTexture + 1));
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		var5.displayWorkbenchGUI();
		return true;
	}
}
