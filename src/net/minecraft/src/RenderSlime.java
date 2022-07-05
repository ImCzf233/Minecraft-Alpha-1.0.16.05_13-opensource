package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderSlime extends RenderLiving<EntitySlime> {
	private ModelBase modelSlime;

	public RenderSlime(ModelBase var1, ModelBase var2, float var3) {
		super(var1, var3);
		this.modelSlime = var2;
	}

	protected boolean shouldRenderPass(EntitySlime var1, int var2) {
		if(var2 == 0) {
			this.setRenderPassModel(this.modelSlime);
			GL11.glEnable(GL11.GL_NORMALIZE);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			return true;
		} else {
			if(var2 == 1) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}

			return false;
		}
	}

	protected void preRenderCallback(EntitySlime var1, float var2) {
		float var3 = (var1.prevSquishFactor + (var1.squishFactor - var1.prevSquishFactor) * var2) / ((float)var1.size * 0.5F + 1.0F);
		float var4 = 1.0F / (var3 + 1.0F);
		float var5 = (float)var1.size;
		GL11.glScalef(var4 * var5, 1.0F / var4 * var5, var4 * var5);
	}
}
