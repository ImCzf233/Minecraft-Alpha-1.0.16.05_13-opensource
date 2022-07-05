package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderMinecart extends Render<EntityMinecart> {
	protected ModelBase modelMinecart;

	public RenderMinecart() {
		this.shadowSize = 0.5F;
		this.modelMinecart = new ModelMinecart();
	}

	public void doRender(EntityMinecart var1, double var2, double var4, double var6, float var8, float var9) {
		GL11.glPushMatrix();
		double var10 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var9;
		double var12 = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var9;
		double var14 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var9;
		double var16 = (double)0.3F;
		Vec3D var18 = var1.getPos(var10, var12, var14);
		float var19 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9;
		if(var18 != null) {
			Vec3D var20 = var1.getPosOffset(var10, var12, var14, var16);
			Vec3D var21 = var1.getPosOffset(var10, var12, var14, -var16);
			if(var20 == null) {
				var20 = var18;
			}

			if(var21 == null) {
				var21 = var18;
			}

			var2 += var18.xCoord - var10;
			var4 += (var20.yCoord + var21.yCoord) / 2.0D - var12;
			var6 += var18.zCoord - var14;
			Vec3D var22 = var21.addVector(-var20.xCoord, -var20.yCoord, -var20.zCoord);
			if(var22.lengthVector() != 0.0D) {
				var22 = var22.normalize();
				var8 = (float)(Math.atan2(var22.zCoord, var22.xCoord) * 180.0D / Math.PI);
				var19 = (float)(Math.atan(var22.yCoord) * 73.0D);
			}
		}

		GL11.glTranslatef((float)var2, (float)var4, (float)var6);
		GL11.glRotatef(180.0F - var8, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-var19, 0.0F, 0.0F, 1.0F);
		float var23 = (float)var1.timeSinceHit - var9;
		float var24 = (float)var1.damageTaken - var9;
		if(var24 < 0.0F) {
			var24 = 0.0F;
		}

		if(var23 > 0.0F) {
			GL11.glRotatef(MathHelper.sin(var23) * var23 * var24 / 10.0F * (float)var1.forwardDirection, 1.0F, 0.0F, 0.0F);
		}

		if(var1.minecartType != 0) {
			this.loadTexture("/terrain.png");
			float var25 = 0.75F;
			GL11.glScalef(var25, var25, var25);
			GL11.glTranslatef(0.0F, 0.3125F, 0.0F);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			if(var1.minecartType == 1) {
				(new RenderBlocks()).renderBlockOnInventory(Block.chest);
			} else if(var1.minecartType == 2) {
				(new RenderBlocks()).renderBlockOnInventory(Block.stoneOvenIdle);
			}

			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.3125F, 0.0F);
			GL11.glScalef(1.0F / var25, 1.0F / var25, 1.0F / var25);
		}

		this.loadTexture("/item/cart.png");
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		this.modelMinecart.render(0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}
}
