package net.minecraft.src;

public class RenderPig extends RenderLiving<EntityPig> {
	public RenderPig(ModelBase var1, ModelBase var2, float var3) {
		super(var1, var3);
		this.setRenderPassModel(var2);
	}

	protected boolean shouldRenderPass(EntityPig var1, int var2) {
		this.loadTexture("/mob/saddle.png");
		return var2 == 0 && var1.saddled;
	}
}
