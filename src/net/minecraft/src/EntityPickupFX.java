package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class EntityPickupFX extends EntityFX {
	private Entity entityToPickUp;
	private Entity entityPickingUp;
	private int age = 0;
	private int maxAge = 0;
	private float yOffs;

	public EntityPickupFX(World worldObj, Entity entityToPickUp, Entity entityPickingUp, float yOffset) {
		super(worldObj, entityToPickUp.posX, entityToPickUp.posY, entityToPickUp.posZ, entityToPickUp.motionX, entityToPickUp.motionY, entityToPickUp.motionZ);
		this.entityToPickUp = entityToPickUp;
		this.entityPickingUp = entityPickingUp;
		this.maxAge = 3;
		this.yOffs = yOffset;
	}

	public void renderParticle(Tessellator var1, float var2, float var3, float var4, float var5, float var6, float var7) {
		float var8 = ((float)this.age + var2) / (float)this.maxAge;
		var8 *= var8;
		double var9 = this.entityToPickUp.posX;
		double var11 = this.entityToPickUp.posY;
		double var13 = this.entityToPickUp.posZ;
		double var15 = this.entityPickingUp.lastTickPosX + (this.entityPickingUp.posX - this.entityPickingUp.lastTickPosX) * (double)var2;
		double var17 = this.entityPickingUp.lastTickPosY + (this.entityPickingUp.posY - this.entityPickingUp.lastTickPosY) * (double)var2 + (double)this.yOffs;
		double var19 = this.entityPickingUp.lastTickPosZ + (this.entityPickingUp.posZ - this.entityPickingUp.lastTickPosZ) * (double)var2;
		double var21 = var9 + (var15 - var9) * (double)var8;
		double var23 = var11 + (var17 - var11) * (double)var8;
		double var25 = var13 + (var19 - var13) * (double)var8;
		int var27 = MathHelper.floor_double(var21);
		int var28 = MathHelper.floor_double(var23 + (double)(this.yOffset / 2.0F));
		int var29 = MathHelper.floor_double(var25);
		float var30 = this.worldObj.getBrightness(var27, var28, var29);
		var21 -= interpPosX;
		var23 -= interpPosY;
		var25 -= interpPosZ;
		GL11.glColor4f(var30, var30, var30, 1.0F);
		RenderManager.instance.renderEntityWithPosYaw(this.entityToPickUp, (double)((float)var21), (double)((float)var23), (double)((float)var25), this.entityToPickUp.rotationYaw, var2);
	}

	public void onUpdate() {
		++this.age;
		if(this.age == this.maxAge) {
			this.setEntityDead();
		}

	}

	public int getFXLayer() {
		return 3;
	}
}
