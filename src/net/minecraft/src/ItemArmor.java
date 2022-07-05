package net.minecraft.src;

public class ItemArmor extends Item {
	private static final int[] damageReduceAmountArray = new int[]{3, 8, 6, 3};
	private static final int[] maxDamageArray = new int[]{11, 16, 15, 13};
	public final int armorLevel;
	public final int armorType;
	public final int damageReduceAmount;
	public final int renderIndex;

	public ItemArmor(int id, int armorLevel, int renderIndex, int armorType) {
		super(id);
		this.armorLevel = armorLevel;
		this.armorType = armorType;
		this.renderIndex = renderIndex;
		this.damageReduceAmount = damageReduceAmountArray[armorType];
		this.maxDamage = maxDamageArray[armorType] * 3 << armorLevel;
		this.maxStackSize = 1;
	}
}
