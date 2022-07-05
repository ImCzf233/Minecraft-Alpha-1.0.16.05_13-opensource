package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderBoat extends Render<EntityBoat> {
	protected ModelBase modelBoat;

	public RenderBoat() {
		this.shadowSize = 0.5F;
		this.modelBoat = new ModelBoat();
	}

	public void doRender(EntityBoat var1, double var2, double var4, double var6, float var8, float var9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var2, (float)var4, (float)var6);
		GL11.glRotatef(180.0F - var8, 0.0F, 1.0F, 0.0F);
		float var10 = (float)var1.timeSinceHit - var9;
		float var11 = (float)var1.damageTaken - var9;
		if(var11 < 0.0F) {
			var11 = 0.0F;
		}

		if(var10 > 0.0F) {
			GL11.glRotatef(MathHelper.sin(var10) * var10 * var11 / 10.0F * (float)var1.forwardDirection, 1.0F, 0.0F, 0.0F);
		}

		this.loadTexture("/terrain.png");
		float var12 = 0.75F;
		GL11.glScalef(var12, var12, var12);
		GL11.glScalef(1.0F / var12, 1.0F / var12, 1.0F / var12);
		this.loadTexture("/item/boat.png");
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		this.modelBoat.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}
