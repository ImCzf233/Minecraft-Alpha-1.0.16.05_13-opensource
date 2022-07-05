package net.minecraft.src;

public class BlockOreBlock extends Block {
	public BlockOreBlock(int id, int tex) {
		super(id, Material.iron);
		this.blockIndexInTexture = tex;
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 1 ? this.blockIndexInTexture - 16 : (var1 == 0 ? this.blockIndexInTexture + 16 : this.blockIndexInTexture);
	}
}
