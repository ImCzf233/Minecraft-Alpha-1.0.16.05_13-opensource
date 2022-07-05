package net.minecraft.src;

import java.util.Random;

public class BlockOre extends Block {
	public BlockOre(int id, int tex) {
		super(id, tex, Material.rock);
	}

	public int idDropped(int var1, Random var2) {
		return this.blockID == Block.oreCoal.blockID ? Item.coal.shiftedIndex : (this.blockID == Block.oreDiamond.blockID ? Item.diamond.shiftedIndex : this.blockID);
	}

	public int quantityDropped(Random var1) {
		return 1;
	}
}
