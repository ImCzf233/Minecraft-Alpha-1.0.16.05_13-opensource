package net.minecraft.src;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class RenderPainting extends Render {
	private Random rand = new Random();

	public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
		this.a_lt((EntityPainting)var1, var2, var4, var6, var8, var9);
	}

	public void a_lt(EntityPainting var1, double var2, double var4, double var6, float var8, float var9) {
		this.rand.setSeed(187L);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var2, (float)var4, (float)var6);
		GL11.glRotatef(var8, 0.0F, 1.0F, 0.0F);
		GL11.glEnable('\u803a');
		SimpleDateFormat var10 = new SimpleDateFormat("HH");
		int var11 = Integer.parseInt(var10.format(Calendar.getInstance().getTime()));
		this.loadTexture(var11 <= 22 && var11 >= 5 ? "/art/kz.png" : "/art/zz.png");
		EnumArt var12 = var1.art;
		GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
		this.setSizes(var1, var12.sizeX, var12.sizeY, var12.offsetX, var12.offsetY);
		GL11.glDisable('\u803a');
		GL11.glPopMatrix();
	}

	private void setSizes(EntityPainting entityPainting, int var2, int var3, int var4, int var5) {
		float var6 = (float)(-var2) / 2.0F;
		float var7 = (float)(-var3) / 2.0F;

		for(int var10 = 0; var10 < var2 / 16; ++var10) {
			for(int var11 = 0; var11 < var3 / 16; ++var11) {
				float var12 = var6 + (float)((var10 + 1) * 16);
				float var13 = var6 + (float)(var10 * 16);
				float var14 = var7 + (float)((var11 + 1) * 16);
				float var15 = var7 + (float)(var11 * 16);
				this.getOffset(entityPainting, (var12 + var13) / 2.0F, (var14 + var15) / 2.0F);
				float var16 = (float)(var4 + var2 - var10 * 16) / 256.0F;
				float var17 = (float)(var4 + var2 - (var10 + 1) * 16) / 256.0F;
				float var18 = (float)(var5 + var3 - var11 * 16) / 256.0F;
				float var19 = (float)(var5 + var3 - (var11 + 1) * 16) / 256.0F;
				Tessellator var32 = Tessellator.instance;
				var32.startDrawingQuads();
				var32.setNormal(0.0F, 0.0F, -1.0F);
				var32.addVertexWithUV((double)var12, (double)var15, -0.5D, (double)var17, (double)var18);
				var32.addVertexWithUV((double)var13, (double)var15, -0.5D, (double)var16, (double)var18);
				var32.addVertexWithUV((double)var13, (double)var14, -0.5D, (double)var16, (double)var19);
				var32.addVertexWithUV((double)var12, (double)var14, -0.5D, (double)var17, (double)var19);
				var32.setNormal(0.0F, 0.0F, 1.0F);
				var32.addVertexWithUV((double)var12, (double)var14, 0.5D, 0.75D, 0.0D);
				var32.addVertexWithUV((double)var13, (double)var14, 0.5D, 0.8125D, 0.0D);
				var32.addVertexWithUV((double)var13, (double)var15, 0.5D, 0.8125D, 0.0625D);
				var32.addVertexWithUV((double)var12, (double)var15, 0.5D, 0.75D, 0.0625D);
				var32.setNormal(0.0F, -1.0F, 0.0F);
				var32.addVertexWithUV((double)var12, (double)var14, -0.5D, 0.75D, 0.001953125D);
				var32.addVertexWithUV((double)var13, (double)var14, -0.5D, 0.8125D, 0.001953125D);
				var32.addVertexWithUV((double)var13, (double)var14, 0.5D, 0.8125D, 0.001953125D);
				var32.addVertexWithUV((double)var12, (double)var14, 0.5D, 0.75D, 0.001953125D);
				var32.setNormal(0.0F, 1.0F, 0.0F);
				var32.addVertexWithUV((double)var12, (double)var15, 0.5D, 0.75D, 0.001953125D);
				var32.addVertexWithUV((double)var13, (double)var15, 0.5D, 0.8125D, 0.001953125D);
				var32.addVertexWithUV((double)var13, (double)var15, -0.5D, 0.8125D, 0.001953125D);
				var32.addVertexWithUV((double)var12, (double)var15, -0.5D, 0.75D, 0.001953125D);
				var32.setNormal(-1.0F, 0.0F, 0.0F);
				var32.addVertexWithUV((double)var12, (double)var14, 0.5D, 0.751953125D, 0.0D);
				var32.addVertexWithUV((double)var12, (double)var15, 0.5D, 0.751953125D, 0.0625D);
				var32.addVertexWithUV((double)var12, (double)var15, -0.5D, 0.751953125D, 0.0625D);
				var32.addVertexWithUV((double)var12, (double)var14, -0.5D, 0.751953125D, 0.0D);
				var32.setNormal(1.0F, 0.0F, 0.0F);
				var32.addVertexWithUV((double)var13, (double)var14, -0.5D, 0.751953125D, 0.0D);
				var32.addVertexWithUV((double)var13, (double)var15, -0.5D, 0.751953125D, 0.0625D);
				var32.addVertexWithUV((double)var13, (double)var15, 0.5D, 0.751953125D, 0.0625D);
				var32.addVertexWithUV((double)var13, (double)var14, 0.5D, 0.751953125D, 0.0D);
				var32.draw();
			}
		}

	}

	private void getOffset(EntityPainting entityPainting, float var2, float var3) {
		int var4 = MathHelper.floor_double(entityPainting.posX);
		int var5 = MathHelper.floor_double(entityPainting.posY + (double)(var3 / 16.0F));
		int var6 = MathHelper.floor_double(entityPainting.posZ);
		if(entityPainting.direction == 0) {
			var4 = MathHelper.floor_double(entityPainting.posX + (double)(var2 / 16.0F));
		}

		if(entityPainting.direction == 1) {
			var6 = MathHelper.floor_double(entityPainting.posZ - (double)(var2 / 16.0F));
		}

		if(entityPainting.direction == 2) {
			var4 = MathHelper.floor_double(entityPainting.posX - (double)(var2 / 16.0F));
		}

		if(entityPainting.direction == 3) {
			var6 = MathHelper.floor_double(entityPainting.posZ + (double)(var2 / 16.0F));
		}

		float var7 = this.renderManager.worldObj.getBrightness(var4, var5, var6);
		GL11.glColor3f(var7, var7, var7);
	}
}
