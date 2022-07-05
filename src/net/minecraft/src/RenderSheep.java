package net.minecraft.src;

public class RenderSheep extends RenderLiving<EntitySheep> {
	public RenderSheep(ModelBase var1, ModelBase var2, float var3) {
		super(var1, var3);
		this.setRenderPassModel(var2);
	}

	protected boolean shouldRenderPass(EntitySheep var1, int var2) {
		this.loadTexture("/mob/sheep_fur.png");
		return var2 == 0 && !var1.sheared;
	}
}
