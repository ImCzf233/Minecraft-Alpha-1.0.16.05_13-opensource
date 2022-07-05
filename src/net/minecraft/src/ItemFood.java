package net.minecraft.src;

public class ItemFood extends Item {
	private int healAmount;

	public ItemFood(int id, int healAmount) {
		super(id);
		this.healAmount = healAmount;
		this.maxStackSize = 1;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		--var1.stackSize;
		var3.heal(this.healAmount);
		return var1;
	}
}
