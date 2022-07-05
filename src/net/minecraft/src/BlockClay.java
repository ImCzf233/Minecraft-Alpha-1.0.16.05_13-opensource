package net.minecraft.src;

import java.util.Random;

public class BlockClay extends Block {
	public BlockClay(int id, int tex) {
		super(id, tex, Material.clay);
	}

	public int idDropped(int var1, Random var2) {
		return Item.clay.shiftedIndex;
	}

	public int quantityDropped(Random var1) {
		return 4;
	}
}
