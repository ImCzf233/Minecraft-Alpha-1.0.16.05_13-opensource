package net.minecraft.src;

public class ItemTool extends Item {
	private Block[] blocksEffectiveAgainst;
	private float efficiencyOnProperMaterial = 4.0F;
	private int damageVsEntity;
	protected int toolMaterial;

	public ItemTool(int id, int attackDmg, int strength, Block[] blocks) {
		super(id);
		this.toolMaterial = strength;
		this.blocksEffectiveAgainst = blocks;
		this.maxStackSize = 1;
		this.maxDamage = 32 << strength;
		if(strength == 3) {
			this.maxDamage *= 4;
		}

		this.efficiencyOnProperMaterial = (float)((strength + 1) * 2);
		this.damageVsEntity = attackDmg + strength;
	}

	public float getStrVsBlock(ItemStack var1, Block var2) {
		for(int var3 = 0; var3 < this.blocksEffectiveAgainst.length; ++var3) {
			if(this.blocksEffectiveAgainst[var3] == var2) {
				return this.efficiencyOnProperMaterial;
			}
		}

		return 1.0F;
	}

	public void hitEntity(ItemStack var1, EntityLiving var2) {
		var1.damageItem(2);
	}

	public void onBlockDestroyed(ItemStack var1, int var2, int var3, int var4, int var5) {
		var1.damageItem(1);
	}

	public int getDamageVsEntity(Entity entity) {
		return this.damageVsEntity;
	}

	public boolean isFull3D() {
		return true;
	}
}
