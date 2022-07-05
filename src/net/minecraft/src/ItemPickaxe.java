package net.minecraft.src;

public class ItemPickaxe extends ItemTool {
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.cobblestone, Block.stairDouble, Block.stairSingle, Block.stone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice};
	private int harvestLevel;

	public ItemPickaxe(int id, int harvestLevel) {
		super(id, 2, harvestLevel, blocksEffectiveAgainst);
		this.harvestLevel = harvestLevel;
	}

	public boolean canHarvestBlock(Block var1) {
		return var1 == Block.obsidian ? this.harvestLevel == 3 : (var1 != Block.blockDiamond && var1 != Block.oreDiamond ? (var1 != Block.blockGold && var1 != Block.oreGold ? (var1 != Block.blockSteel && var1 != Block.oreIron ? (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing ? (var1.material == Material.rock ? true : var1.material == Material.iron) : this.harvestLevel >= 2) : this.harvestLevel >= 1) : this.harvestLevel >= 2) : this.harvestLevel >= 2);
	}
}
