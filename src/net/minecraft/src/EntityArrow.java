package net.minecraft.src;

import java.util.List;

public class EntityArrow extends Entity {
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	private boolean inData = false;
	public int arrowShake = 0;
	private EntityLiving shootingEntity;
	private int ticksInGround;
	private int ticksInAir = 0;

	public EntityArrow(World var1) {
		super(var1);
		this.setSize(0.5F, 0.5F);
	}

	public EntityArrow(World world, EntityLiving entityLiving) {
		super(world);
		this.shootingEntity = entityLiving;
		this.setSize(0.5F, 0.5F);
		this.setLocationAndAngles(entityLiving.posX, entityLiving.posY, entityLiving.posZ, entityLiving.rotationYaw, entityLiving.rotationPitch);
		this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.posY -= (double)0.1F;
		this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.yOffset = 0.0F;
		this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
		this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
		this.setArrowHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	public void setArrowHeading(double moveX, double moveY, double moveZ, float arrowSpeed, float arrowSpread) {
		float var9 = MathHelper.sqrt_double(moveX * moveX + moveY * moveY + moveZ * moveZ);
		moveX /= (double)var9;
		moveY /= (double)var9;
		moveZ /= (double)var9;
		moveX += this.rand.nextGaussian() * (double)0.0075F * (double)arrowSpread;
		moveY += this.rand.nextGaussian() * (double)0.0075F * (double)arrowSpread;
		moveZ += this.rand.nextGaussian() * (double)0.0075F * (double)arrowSpread;
		moveX *= (double)arrowSpeed;
		moveY *= (double)arrowSpeed;
		moveZ *= (double)arrowSpeed;
		this.motionX = moveX;
		this.motionY = moveY;
		this.motionZ = moveZ;
		float var10 = MathHelper.sqrt_double(moveX * moveX + moveZ * moveZ);
		this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(moveX, moveZ) * 180.0D / (double)(float)Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(moveY, (double)var10) * 180.0D / (double)(float)Math.PI);
		this.ticksInGround = 0;
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.arrowShake > 0) {
			--this.arrowShake;
		}

		if(this.inData) {
			int var1 = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
			if(var1 == this.inTile) {
				++this.ticksInGround;
				if(this.ticksInGround == 1200) {
					this.setEntityDead();
				}

				return;
			}

			this.inData = false;
			this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
			this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
			this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
			this.ticksInGround = 0;
			this.ticksInAir = 0;
		} else {
			++this.ticksInAir;
		}

		Vec3D var15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
		Vec3D var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		MovingObjectPosition var3 = this.worldObj.rayTraceBlocks(var15, var2);
		var15 = Vec3D.createVector(this.posX, this.posY, this.posZ);
		var2 = Vec3D.createVector(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		if(var3 != null) {
			var2 = Vec3D.createVector(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
		}

		Entity var4 = null;
		List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
		double var6 = 0.0D;

		float var10;
		for(int var8 = 0; var8 < var5.size(); ++var8) {
			Entity var9 = (Entity)var5.get(var8);
			if(var9.canBeCollidedWith() && (var9 != this.shootingEntity || this.ticksInAir >= 5)) {
				var10 = 0.3F;
				AxisAlignedBB var11 = var9.boundingBox.expand((double)var10, (double)var10, (double)var10);
				MovingObjectPosition var12 = var11.calculateIntercept(var15, var2);
				if(var12 != null) {
					double var13 = var15.distanceTo(var12.hitVec);
					if(var13 < var6 || var6 == 0.0D) {
						var4 = var9;
						var6 = var13;
					}
				}
			}
		}

		if(var4 != null) {
			var3 = new MovingObjectPosition(var4);
		}

		float var16;
		if(var3 != null) {
			if(var3.entityHit != null) {
				if(var3.entityHit.attackEntityFrom(this.shootingEntity, 4)) {
					this.worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.setEntityDead();
				} else {
					this.motionX *= -0.10000000149011612D;
					this.motionY *= -0.10000000149011612D;
					this.motionZ *= -0.10000000149011612D;
					this.rotationYaw += 180.0F;
					this.prevRotationYaw += 180.0F;
					this.ticksInAir = 0;
				}
			} else {
				this.xTile = var3.blockX;
				this.yTile = var3.blockY;
				this.zTile = var3.blockZ;
				this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
				this.motionX = (double)((float)(var3.hitVec.xCoord - this.posX));
				this.motionY = (double)((float)(var3.hitVec.yCoord - this.posY));
				this.motionZ = (double)((float)(var3.hitVec.zCoord - this.posZ));
				var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
				this.posX -= this.motionX / (double)var16 * (double)0.05F;
				this.posY -= this.motionY / (double)var16 * (double)0.05F;
				this.posZ -= this.motionZ / (double)var16 * (double)0.05F;
				this.worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				this.inData = true;
				this.arrowShake = 7;
			}
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		var16 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / (double)(float)Math.PI);

		for(this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var16) * 180.0D / (double)(float)Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
		}

		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
		float var17 = 0.99F;
		var10 = 0.03F;
		if(this.handleWaterMovement()) {
			for(int var18 = 0; var18 < 4; ++var18) {
				float var19 = 0.25F;
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)var19, this.posY - this.motionY * (double)var19, this.posZ - this.motionZ * (double)var19, this.motionX, this.motionY, this.motionZ);
			}

			var17 = 0.8F;
		}

		this.motionX *= (double)var17;
		this.motionY *= (double)var17;
		this.motionZ *= (double)var17;
		this.motionY -= (double)var10;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		var1.setShort("xTile", (short)this.xTile);
		var1.setShort("yTile", (short)this.yTile);
		var1.setShort("zTile", (short)this.zTile);
		var1.setByte("inTile", (byte)this.inTile);
		var1.setByte("shake", (byte)this.arrowShake);
		var1.setByte("inGround", (byte)(this.inData ? 1 : 0));
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		this.xTile = var1.getShort("xTile");
		this.yTile = var1.getShort("yTile");
		this.zTile = var1.getShort("zTile");
		this.inTile = var1.getByte("inTile") & 255;
		this.arrowShake = var1.getByte("shake") & 255;
		this.inData = var1.getByte("inGround") == 1;
	}

	public void onCollideWithPlayer(EntityPlayer var1) {
		if(this.inData && this.shootingEntity == var1 && this.arrowShake <= 0 && var1.inventory.addItemStackToInventory(new ItemStack(Item.arrow.shiftedIndex, 1))) {
			this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			var1.onItemPickup(this, 1);
			this.setEntityDead();
		}

	}

	public float getShadowSize() {
		return 0.0F;
	}
}
