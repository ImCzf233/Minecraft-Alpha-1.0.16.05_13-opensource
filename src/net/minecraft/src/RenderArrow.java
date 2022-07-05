package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderArrow extends Render<EntityArrow> {
	public void doRender(EntityArrow var1, double var2, double var4, double var6, float var8, float var9) {
		this.loadTexture("/item/arrows.png");
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var2, (float)var4, (float)var6);
		GL11.glRotatef(var1.prevRotationYaw + (var1.rotationYaw - var1.prevRotationYaw) * var9 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9, 0.0F, 0.0F, 1.0F);
		Tessellator var10 = Tessellator.instance;
		byte var11 = 0;
		float var12 = 0.0F;
		float var13 = 0.5F;
		float var14 = (float)(0 + var11 * 10) / 32.0F;
		float var15 = (float)(5 + var11 * 10) / 32.0F;
		float var16 = 0.0F;
		float var17 = 0.15625F;
		float var18 = (float)(5 + var11 * 10) / 32.0F;
		float var19 = (float)(10 + var11 * 10) / 32.0F;
		float var20 = 0.05625F;
		GL11.glEnable('\u803a');
		float var21 = (float)var1.arrowShake - var9;
		if(var21 > 0.0F) {
			float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
			GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
		}

		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(var20, var20, var20);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(var20, 0.0F, 0.0F);
		var10.startDrawingQuads();
		var10.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)var16, (double)var18);
		var10.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)var17, (double)var18);
		var10.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)var17, (double)var19);
		var10.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)var16, (double)var19);
		var10.draw();
		GL11.glNormal3f(-var20, 0.0F, 0.0F);
		var10.startDrawingQuads();
		var10.addVertexWithUV(-7.0D, 2.0D, -2.0D, (double)var16, (double)var18);
		var10.addVertexWithUV(-7.0D, 2.0D, 2.0D, (double)var17, (double)var18);
		var10.addVertexWithUV(-7.0D, -2.0D, 2.0D, (double)var17, (double)var19);
		var10.addVertexWithUV(-7.0D, -2.0D, -2.0D, (double)var16, (double)var19);
		var10.draw();

		for(int var23 = 0; var23 < 4; ++var23) {
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, var20);
			var10.startDrawingQuads();
			var10.addVertexWithUV(-8.0D, -2.0D, 0.0D, (double)var12, (double)var14);
			var10.addVertexWithUV(8.0D, -2.0D, 0.0D, (double)var13, (double)var14);
			var10.addVertexWithUV(8.0D, 2.0D, 0.0D, (double)var13, (double)var15);
			var10.addVertexWithUV(-8.0D, 2.0D, 0.0D, (double)var12, (double)var15);
			var10.draw();
		}

		GL11.glDisable('\u803a');
		GL11.glPopMatrix();
	}
}
