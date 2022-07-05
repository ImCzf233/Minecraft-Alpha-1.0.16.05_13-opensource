package net.minecraft.src;

public class RenderChicken extends RenderLiving<EntityChicken> {
	public RenderChicken(ModelBase var1, float var2) {
		super(var1, var2);
	}

	public void doRender(EntityChicken var1, double var2, double var4, double var6, float var8, float var9) {
		super.doRender(var1, var2, var4, var6, var8, var9);
	}

	protected float handleRotationFloat(EntityChicken var1, float var2) {
		float var3 = var1.prevWingRotation + (var1.wingRotation - var1.prevWingRotation) * var2;
		float var4 = var1.prevDestPos + (var1.destPos - var1.prevDestPos) * var2;
		return (MathHelper.sin(var3) + 1.0F) * var4;
	}
}
