package net.minecraft.src;

public class BlockGlowingFlowerInfused extends BlockGlowing {
	public int power = 0;

	protected BlockGlowingFlowerInfused(int var1, int var2, int var3, int var4) {
		super(var1, var2, var3);
		this.power = var4;
	}

	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityMob) {
			((EntityMob)entity).attackEntityFrom((Entity)null, 2 * this.power);
			((EntityMob)entity).fire = 300;
		}

	}
}
