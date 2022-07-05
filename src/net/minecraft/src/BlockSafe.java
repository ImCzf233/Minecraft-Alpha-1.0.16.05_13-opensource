package net.minecraft.src;

public class BlockSafe extends BlockMultiSided {
	protected BlockSafe(int var1, int var2, int var3, int var4) {
		super(var1, var2, var3, var4);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		ItemStack var6 = var5.inventory.getCurrentItem();
		if(var6 == null) {
			InputHandler.mc.displayGuiScreen(new ScreenKeyInput(InputHandler.mc, 1));
		}

		return true;
	}
}
