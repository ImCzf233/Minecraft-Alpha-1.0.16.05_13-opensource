package net.minecraft.src;

public class ObsidianPick extends ItemTool {
	private static Block[] aW = new Block[]{Block.cobblestone, Block.bedrock, Block.stairDouble, Block.stairSingle, Block.stone, Block.cobblestoneMossy, Block.oreIron, Block.blockSteel, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice};
	private int aX;

	public ObsidianPick(int var1, int var2) {
		super(var1, 2, var2, aW);
		this.aX = var2;
	}

	public boolean canHarvestBlock(Block var1) {
		return var1 == Block.bedrock ? true : (var1 == Block.obsidian ? this.aX == 3 : (var1 != Block.blockDiamond && var1 != Block.oreDiamond ? (var1 != Block.blockGold && var1 != Block.oreGold ? (var1 != Block.blockSteel && var1 != Block.oreIron ? (var1 != Block.oreRedstone && var1 != Block.oreRedstoneGlowing ? var1.material == Material.rock || var1.material == Material.iron : this.aX >= 2) : this.aX >= 1) : this.aX >= 2) : this.aX >= 2));
	}
}
