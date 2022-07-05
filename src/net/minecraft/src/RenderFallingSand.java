package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderFallingSand extends Render<EntityFallingSand> {
	private RenderBlocks sandRenderBlocks = new RenderBlocks();

	public RenderFallingSand() {
		this.shadowSize = 0.5F;
	}

	public void doRender(EntityFallingSand var1, double var2, double var4, double var6, float var8, float var9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var2, (float)var4, (float)var6);
		this.loadTexture("/terrain.png");
		Block var10 = Block.blocksList[var1.blockID];
		World var11 = var1.getWorld();
		GL11.glDisable(GL11.GL_LIGHTING);
		this.sandRenderBlocks.renderBlockFallingSand(var10, var11, MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ));
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}
