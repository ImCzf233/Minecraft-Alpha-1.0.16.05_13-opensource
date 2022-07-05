package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderGiant extends RenderLiving {
	private float f;

	public RenderGiant(ModelBase var1, float var2, float var3) {
		super(var1, var2 * var3);
		this.f = var3;
	}

	protected void preRenderCallback(EntityLiving var1, float var2) {
		GL11.glScalef(this.f, this.f, this.f);
	}
}
