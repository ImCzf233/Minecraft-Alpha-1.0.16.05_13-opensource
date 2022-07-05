package net.minecraft.src;

public class ItemAxe extends ItemTool {
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.planks, Block.bookshelf, Block.wood, Block.chest};

	public ItemAxe(int id, int var2) {
		super(id, 3, var2, blocksEffectiveAgainst);
	}
}
