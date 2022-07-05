package net.minecraft.src;

import java.util.Random;

public class BlockMultiSided extends Block {
	private int texTop;
	private int texSide;
	private int texBottom;
	private int id;

	protected BlockMultiSided(int var1, int var2, int var3, int var4) {
		super(var1, Material.grass);
		this.blockIndexInTexture = var3;
		this.setTickOnLoad(true);
		this.texTop = var2;
		this.texSide = var3;
		this.texBottom = var4;
		this.id = var1;
	}

	public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return var5 == 1 ? this.texTop : (var5 == 0 ? this.texBottom : this.texSide);
	}

	public void updateTick(World worldObj, int x, int y, int z, Random rand) {
	}

	public int idDropped(int var1, Random var2) {
		return this.id;
	}
}
