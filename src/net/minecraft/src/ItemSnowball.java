package net.minecraft.src;

public class ItemSnowball extends Item {
	public ItemSnowball(int var1) {
		super(var1);
		this.maxStackSize = 16;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		--var1.stackSize;
		var2.playSoundAtEntity(var3, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
		var2.spawnEntityInWorld(new EntitySnowball(var2, var3));
		return var1;
	}
}
