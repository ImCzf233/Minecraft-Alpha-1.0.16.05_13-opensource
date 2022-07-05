package net.minecraft.src;

public class ItemSword extends Item {
	private int weaponDamage;

	public ItemSword(int id, int strength) {
		super(id);
		this.maxStackSize = 1;
		this.maxDamage = 32 << strength;
		if(strength == 3) {
			this.maxDamage *= 4;
		}

		this.weaponDamage = 4 + strength * 2;
	}

	public float getStrVsBlock(ItemStack var1, Block var2) {
		return 1.5F;
	}

	public void hitEntity(ItemStack var1, EntityLiving var2) {
		var1.damageItem(1);
	}

	public void onBlockDestroyed(ItemStack var1, int var2, int var3, int var4, int var5) {
		var1.damageItem(2);
	}

	public int getDamageVsEntity(Entity entity) {
		return this.weaponDamage;
	}

	public boolean isFull3D() {
		return true;
	}
}
