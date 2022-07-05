package net.minecraft.src;

public class ItemSpade extends ItemTool {
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay};

	public ItemSpade(int id, int strength) {
		super(id, 1, strength, blocksEffectiveAgainst);
	}

	public boolean canHarvestBlock(Block var1) {
		return var1 == Block.snow ? true : var1 == Block.blockSnow;
	}
}
