package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class Entity {
	private static int nextEntityID = 0;
	public int entityID = nextEntityID++;
	public double renderDistanceWeight = 1.0D;
	public boolean preventEntitySpawning = false;
	public Entity riddenByEntity;
	public Entity ridingEntity;
	protected World worldObj;
	public double prevPosX;
	public double prevPosY;
	public double prevPosZ;
	public double posX;
	public double posY;
	public double posZ;
	public double motionX;
	public double motionY;
	public double motionZ;
	public float rotationYaw;
	public float rotationPitch;
	public float prevRotationYaw;
	public float prevRotationPitch;
	public final AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
	public boolean onGround = false;
	public boolean isCollidedHorizontally;
	public boolean isCollidedVertically;
	public boolean isCollided = false;
	public boolean surfaceCollision = true;
	public boolean isDead = false;
	public float yOffset = 0.0F;
	public float width = 0.6F;
	public float height = 1.8F;
	public float prevDistanceWalkedModified = 0.0F;
	public float distanceWalkedModified = 0.0F;
	protected boolean canTriggerWalking = true;
	protected float fallDistance = 0.0F;
	private int nextStepDistance = 1;
	public double lastTickPosX;
	public double lastTickPosY;
	public double lastTickPosZ;
	public float ySize = 0.0F;
	public float stepHeight = 0.0F;
	public boolean noClip = false;
	public float entityCollisionReduction = 0.0F;
	public boolean unusedBool = false;
	protected Random rand = new Random();
	public int ticksExisted = 0;
	public int fireResistance = 1;
	public int fire = 0;
	protected int maxAir = 300;
	protected boolean inWater = false;
	public int heartsLife = 0;
	public int air = 300;
	private boolean firstUpdate = true;
	public String skinUrl;
	private double entityRiderPitchDelta;
	private double entityRiderYawDelta;
	public boolean addedToChunk = false;
	public int chunkCoordX;
	public int chunkCoordY;
	public int chunkCoordZ;
	public int serverPosX;
	public int serverPosY;
	public int serverPosZ;

	public Entity(World world) {
		this.worldObj = world;
		this.setPosition(0.0D, 0.0D, 0.0D);
	}

	public boolean equals(Object entity) {
		return entity instanceof Entity ? ((Entity)entity).entityID == this.entityID : false;
	}

	public int hashCode() {
		return this.entityID;
	}

	protected void preparePlayerToSpawn() {
		if(this.worldObj != null) {
			while(this.posY > 0.0D) {
				this.setPosition(this.posX, this.posY, this.posZ);
				if(this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0) {
					break;
				}

				++this.posY;
			}

			this.motionX = this.motionY = this.motionZ = 0.0D;
			this.rotationPitch = 0.0F;
		}
	}

	public void setEntityDead() {
		this.isDead = true;
	}

	protected void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	protected void setRotation(float rotationYaw, float rotationPitch) {
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
	}

	public void setPosition(double posX, double posY, double posZ) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		float var7 = this.width / 2.0F;
		float var8 = this.height;
		this.boundingBox.setBounds(posX - (double)var7, posY - (double)this.yOffset + (double)this.ySize, posZ - (double)var7, posX + (double)var7, posY - (double)this.yOffset + (double)this.ySize + (double)var8, posZ + (double)var7);
	}

	public void setAngles(float yaw, float pitch) {
		float var3 = this.rotationPitch;
		float var4 = this.rotationYaw;
		this.rotationYaw = (float)((double)this.rotationYaw + (double)yaw * 0.15D);
		this.rotationPitch = (float)((double)this.rotationPitch - (double)pitch * 0.15D);
		if(this.rotationPitch < -90.0F) {
			this.rotationPitch = -90.0F;
		}

		if(this.rotationPitch > 90.0F) {
			this.rotationPitch = 90.0F;
		}

		this.prevRotationPitch += this.rotationPitch - var3;
		this.prevRotationYaw += this.rotationYaw - var4;
	}

	public void onUpdate() {
		this.onEntityUpdate();
	}

	public void onEntityUpdate() {
		if(this.ridingEntity != null && this.ridingEntity.isDead) {
			this.ridingEntity = null;
		}

		++this.ticksExisted;
		this.prevDistanceWalkedModified = this.distanceWalkedModified;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;
		if(this.handleWaterMovement()) {
			if(!this.inWater && !this.firstUpdate) {
				float var1 = MathHelper.sqrt_double(this.motionX * this.motionX * (double)0.2F + this.motionY * this.motionY + this.motionZ * this.motionZ * (double)0.2F) * 0.2F;
				if(var1 > 1.0F) {
					var1 = 1.0F;
				}

				this.worldObj.playSoundAtEntity(this, "random.splash", var1, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
				float var2 = (float)MathHelper.floor_double(this.boundingBox.minY);

				int var3;
				float var4;
				float var5;
				for(var3 = 0; (float)var3 < 1.0F + this.width * 20.0F; ++var3) {
					var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
					var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
					this.worldObj.spawnParticle("bubble", this.posX + (double)var4, (double)(var2 + 1.0F), this.posZ + (double)var5, this.motionX, this.motionY - (double)(this.rand.nextFloat() * 0.2F), this.motionZ);
				}

				for(var3 = 0; (float)var3 < 1.0F + this.width * 20.0F; ++var3) {
					var4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
					var5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
					this.worldObj.spawnParticle("splash", this.posX + (double)var4, (double)(var2 + 1.0F), this.posZ + (double)var5, this.motionX, this.motionY, this.motionZ);
				}
			}

			this.fallDistance = 0.0F;
			this.inWater = true;
			this.fire = 0;
		} else {
			this.inWater = false;
		}

		if(this.fire > 0) {
			if(this.fire % 20 == 0) {
				this.attackEntityFrom((Entity)null, 1);
			}

			--this.fire;
		}

		if(this.handleLavaMovement()) {
			this.attackEntityFrom((Entity)null, 10);
			this.fire = 600;
		}

		if(this.posY < -64.0D) {
			this.kill();
		}

		this.firstUpdate = false;
	}

	protected void kill() {
		this.setEntityDead();
	}

	public boolean isOffsetPositionInLiquid(double offsetX, double offsetY, double offsetZ) {
		AxisAlignedBB var7 = this.boundingBox.getOffsetBoundingBox(offsetX, offsetY, offsetZ);
		List var8 = this.worldObj.getCollidingBoundingBoxes(this, var7);
		return var8.size() > 0 ? false : !this.worldObj.getIsAnyLiquid(var7);
	}

	public void moveEntity(double moveX, double moveY, double moveZ) {
		if(this.noClip) {
			this.boundingBox.offset(moveX, moveY, moveZ);
			this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
			this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
			this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
		} else {
			double var7 = this.posX;
			double var9 = this.posZ;
			double var11 = moveX;
			double var13 = moveY;
			double var15 = moveZ;
			AxisAlignedBB var17 = this.boundingBox.copy();
			List var18 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(moveX, moveY, moveZ));

			for(int var19 = 0; var19 < var18.size(); ++var19) {
				moveY = ((AxisAlignedBB)var18.get(var19)).calculateYOffset(this.boundingBox, moveY);
			}

			this.boundingBox.offset(0.0D, moveY, 0.0D);
			if(!this.surfaceCollision && var13 != moveY) {
				moveZ = 0.0D;
				moveY = 0.0D;
				moveX = 0.0D;
			}

			boolean var34 = this.onGround || var13 != moveY && var13 < 0.0D;

			int var20;
			for(var20 = 0; var20 < var18.size(); ++var20) {
				moveX = ((AxisAlignedBB)var18.get(var20)).calculateXOffset(this.boundingBox, moveX);
			}

			this.boundingBox.offset(moveX, 0.0D, 0.0D);
			if(!this.surfaceCollision && var11 != moveX) {
				moveZ = 0.0D;
				moveY = 0.0D;
				moveX = 0.0D;
			}

			for(var20 = 0; var20 < var18.size(); ++var20) {
				moveZ = ((AxisAlignedBB)var18.get(var20)).calculateZOffset(this.boundingBox, moveZ);
			}

			this.boundingBox.offset(0.0D, 0.0D, moveZ);
			if(!this.surfaceCollision && var15 != moveZ) {
				moveZ = 0.0D;
				moveY = 0.0D;
				moveX = 0.0D;
			}

			double var22;
			int var27;
			double var35;
			if(this.stepHeight > 0.0F && var34 && this.ySize < 0.05F && (var11 != moveX || var15 != moveZ)) {
				var35 = moveX;
				var22 = moveY;
				double var24 = moveZ;
				moveX = var11;
				moveY = (double)this.stepHeight;
				moveZ = var15;
				AxisAlignedBB var26 = this.boundingBox.copy();
				this.boundingBox.setBB(var17);
				var18 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(var11, moveY, var15));

				for(var27 = 0; var27 < var18.size(); ++var27) {
					moveY = ((AxisAlignedBB)var18.get(var27)).calculateYOffset(this.boundingBox, moveY);
				}

				this.boundingBox.offset(0.0D, moveY, 0.0D);
				if(!this.surfaceCollision && var13 != moveY) {
					moveZ = 0.0D;
					moveY = 0.0D;
					moveX = 0.0D;
				}

				for(var27 = 0; var27 < var18.size(); ++var27) {
					moveX = ((AxisAlignedBB)var18.get(var27)).calculateXOffset(this.boundingBox, moveX);
				}

				this.boundingBox.offset(moveX, 0.0D, 0.0D);
				if(!this.surfaceCollision && var11 != moveX) {
					moveZ = 0.0D;
					moveY = 0.0D;
					moveX = 0.0D;
				}

				for(var27 = 0; var27 < var18.size(); ++var27) {
					moveZ = ((AxisAlignedBB)var18.get(var27)).calculateZOffset(this.boundingBox, moveZ);
				}

				this.boundingBox.offset(0.0D, 0.0D, moveZ);
				if(!this.surfaceCollision && var15 != moveZ) {
					moveZ = 0.0D;
					moveY = 0.0D;
					moveX = 0.0D;
				}

				if(var35 * var35 + var24 * var24 >= moveX * moveX + moveZ * moveZ) {
					moveX = var35;
					moveY = var22;
					moveZ = var24;
					this.boundingBox.setBB(var26);
				} else {
					this.ySize = (float)((double)this.ySize + 0.5D);
				}
			}

			this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
			this.posY = this.boundingBox.minY + (double)this.yOffset - (double)this.ySize;
			this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
			this.isCollidedHorizontally = var11 != moveX || var15 != moveZ;
			this.isCollidedVertically = var13 != moveY;
			this.onGround = var13 != moveY && var13 < 0.0D;
			this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
			if(this.onGround) {
				if(this.fallDistance > 0.0F) {
					this.fall(this.fallDistance);
					this.fallDistance = 0.0F;
				}
			} else if(moveY < 0.0D) {
				this.fallDistance = (float)((double)this.fallDistance - moveY);
			}

			if(var11 != moveX) {
				this.motionX = 0.0D;
			}

			if(var13 != moveY) {
				this.motionY = 0.0D;
			}

			if(var15 != moveZ) {
				this.motionZ = 0.0D;
			}

			var35 = this.posX - var7;
			var22 = this.posZ - var9;
			this.distanceWalkedModified = (float)((double)this.distanceWalkedModified + (double)MathHelper.sqrt_double(var35 * var35 + var22 * var22) * 0.6D);
			int var25;
			int var36;
			int var38;
			if(this.canTriggerWalking) {
				var36 = MathHelper.floor_double(this.posX);
				var25 = MathHelper.floor_double(this.posY - (double)0.2F - (double)this.yOffset);
				var38 = MathHelper.floor_double(this.posZ);
				var27 = this.worldObj.getBlockId(var36, var25, var38);
				if(this.distanceWalkedModified > (float)this.nextStepDistance && var27 > 0) {
					++this.nextStepDistance;
					StepSound var28 = Block.blocksList[var27].stepSound;
					if(this.worldObj.getBlockId(var36, var25 + 1, var38) == Block.snow.blockID) {
						var28 = Block.snow.stepSound;
						this.worldObj.playSoundAtEntity(this, var28.getStepSound(), var28.getVolume() * 0.15F, var28.getPitch());
					} else if(!Block.blocksList[var27].material.getIsLiquid()) {
						this.worldObj.playSoundAtEntity(this, var28.getStepSound(), var28.getVolume() * 0.15F, var28.getPitch());
					}

					Block.blocksList[var27].onEntityWalking(this.worldObj, var36, var25, var38, this);
				}
			}

			var36 = MathHelper.floor_double(this.boundingBox.minX);
			var25 = MathHelper.floor_double(this.boundingBox.minY);
			var38 = MathHelper.floor_double(this.boundingBox.minZ);
			var27 = MathHelper.floor_double(this.boundingBox.maxX);
			int var39 = MathHelper.floor_double(this.boundingBox.maxY);
			int var29 = MathHelper.floor_double(this.boundingBox.maxZ);

			for(int var30 = var36; var30 <= var27; ++var30) {
				for(int var31 = var25; var31 <= var39; ++var31) {
					for(int var32 = var38; var32 <= var29; ++var32) {
						int var33 = this.worldObj.getBlockId(var30, var31, var32);
						if(var33 > 0) {
							Block.blocksList[var33].onEntityCollidedWithBlock(this.worldObj, var30, var31, var32, this);
						}
					}
				}
			}

			this.ySize *= 0.4F;
			boolean var37 = this.handleWaterMovement();
			if(this.worldObj.isBoundingBoxBurning(this.boundingBox)) {
				this.dealFireDamage(1);
				if(!var37) {
					++this.fire;
					if(this.fire == 0) {
						this.fire = 300;
					}
				}
			} else if(this.fire <= 0) {
				this.fire = -this.fireResistance;
			}

			if(var37 && this.fire > 0) {
				this.worldObj.playSoundAtEntity(this, "random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
				this.fire = -this.fireResistance;
			}

		}
	}

	public AxisAlignedBB getBoundingBox() {
		return null;
	}

	protected void dealFireDamage(int damage) {
		this.attackEntityFrom((Entity)null, damage);
	}

	protected void fall(float distance) {
	}

	public boolean handleWaterMovement() {
		return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D), Material.water, this);
	}

	public boolean isInsideOfMaterial(Material material) {
		double var2 = this.posY + (double)this.getEyeHeight();
		int var4 = MathHelper.floor_double(this.posX);
		int var5 = MathHelper.floor_float((float)MathHelper.floor_double(var2));
		int var6 = MathHelper.floor_double(this.posZ);
		int var7 = this.worldObj.getBlockId(var4, var5, var6);
		if(var7 != 0 && Block.blocksList[var7].material == material) {
			float var8 = BlockFluid.getFluidHeightPercent(this.worldObj.getBlockMetadata(var4, var5, var6)) - 0.11111111F;
			float var9 = (float)(var5 + 1) - var8;
			return var2 < (double)var9;
		} else {
			return false;
		}
	}

	protected float getEyeHeight() {
		return 0.0F;
	}

	public boolean handleLavaMovement() {
		return this.worldObj.isMaterialInBB(this.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D), Material.lava);
	}

	public void moveFlying(float moveX, float moveZ, float moveY) {
		float var4 = MathHelper.sqrt_float(moveX * moveX + moveZ * moveZ);
		if(var4 >= 0.01F) {
			if(var4 < 1.0F) {
				var4 = 1.0F;
			}

			var4 = moveY / var4;
			moveX *= var4;
			moveZ *= var4;
			float var5 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
			float var6 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
			this.motionX += (double)(moveX * var6 - moveZ * var5);
			this.motionZ += (double)(moveZ * var6 + moveX * var5);
		}
	}

	public float getBrightness(float renderPartialTick) {
		int var2 = MathHelper.floor_double(this.posX);
		double var3 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
		int var5 = MathHelper.floor_double(this.posY - (double)this.yOffset + var3);
		int var6 = MathHelper.floor_double(this.posZ);
		return this.worldObj.getBrightness(var2, var5, var6);
	}

	public void setWorld(World world) {
		this.worldObj = world;
	}

	public void setPositionAndRotation(double posX, double posY, double posZ, float rotationYaw, float rotationPitch) {
		this.prevPosX = this.posX = posX;
		this.prevPosY = this.posY = posY;
		this.prevPosZ = this.posZ = posZ;
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
		this.ySize = 0.0F;
		double var9 = (double)(this.prevRotationYaw - rotationYaw);
		if(var9 < -180.0D) {
			this.prevRotationYaw += 360.0F;
		}

		if(var9 >= 180.0D) {
			this.prevRotationYaw -= 360.0F;
		}

		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public void setLocationAndAngles(double posX, double posY, double posZ, float rotationYaw, float rotationPitch) {
		this.prevPosX = this.posX = posX;
		this.prevPosY = this.posY = posY + (double)this.yOffset;
		this.prevPosZ = this.posZ = posZ;
		this.rotationYaw = rotationYaw;
		this.rotationPitch = rotationPitch;
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	public float getDistanceToEntity(Entity entity) {
		float var2 = (float)(this.posX - entity.posX);
		float var3 = (float)(this.posY - entity.posY);
		float var4 = (float)(this.posZ - entity.posZ);
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}

	public double getDistanceSq(double x, double y, double z) {
		double var7 = this.posX - x;
		double var9 = this.posY - y;
		double var11 = this.posZ - z;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	public double getDistance(double x, double y, double z) {
		double var7 = this.posX - x;
		double var9 = this.posY - y;
		double var11 = this.posZ - z;
		return (double)MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
	}

	public double getDistanceSqToEntity(Entity entity) {
		double var2 = this.posX - entity.posX;
		double var4 = this.posY - entity.posY;
		double var6 = this.posZ - entity.posZ;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}

	public void onCollideWithPlayer(EntityPlayer entityPlayer) {
	}

	public void applyEntityCollision(Entity entity) {
		if(entity.riddenByEntity != this && entity.ridingEntity != this) {
			double var2 = entity.posX - this.posX;
			double var4 = entity.posZ - this.posZ;
			double var6 = MathHelper.abs_max(var2, var4);
			if(var6 >= (double)0.01F) {
				var6 = (double)MathHelper.sqrt_double(var6);
				var2 /= var6;
				var4 /= var6;
				double var8 = 1.0D / var6;
				if(var8 > 1.0D) {
					var8 = 1.0D;
				}

				var2 *= var8;
				var4 *= var8;
				var2 *= (double)0.05F;
				var4 *= (double)0.05F;
				var2 *= (double)(1.0F - this.entityCollisionReduction);
				var4 *= (double)(1.0F - this.entityCollisionReduction);
				this.addVelocity(-var2, 0.0D, -var4);
				entity.addVelocity(var2, 0.0D, var4);
			}

		}
	}

	public void addVelocity(double motionX, double motionY, double motionZ) {
		this.motionX += motionX;
		this.motionY += motionY;
		this.motionZ += motionZ;
	}

	public boolean attackEntityFrom(Entity entity, int damage) {
		return false;
	}

	public boolean canBeCollidedWith() {
		return false;
	}

	public boolean canBePushed() {
		return false;
	}

	public void addToPlayerScore(Entity entity, int score) {
	}

	public boolean isInRangeToRenderVec3D(Vec3D vector) {
		double var2 = this.posX - vector.xCoord;
		double var4 = this.posY - vector.yCoord;
		double var6 = this.posZ - vector.zCoord;
		double var8 = var2 * var2 + var4 * var4 + var6 * var6;
		return this.isInRangeToRenderDist(var8);
	}

	public boolean isInRangeToRenderDist(double distance) {
		double var3 = this.boundingBox.getAverageEdgeLength();
		var3 *= 64.0D * this.renderDistanceWeight;
		return distance < var3 * var3;
	}

	public String getTexture() {
		return null;
	}

	public boolean addEntityID(NBTTagCompound nbtCompound) {
		String var2 = this.getEntityString();
		if(!this.isDead && var2 != null) {
			nbtCompound.setString("id", var2);
			this.writeToNBT(nbtCompound);
			return true;
		} else {
			return false;
		}
	}

	public void writeToNBT(NBTTagCompound nbtCompound) {
		nbtCompound.setTag("Pos", this.newDoubleNBTList(new double[]{this.posX, this.posY, this.posZ}));
		nbtCompound.setTag("Motion", this.newDoubleNBTList(new double[]{this.motionX, this.motionY, this.motionZ}));
		nbtCompound.setTag("Rotation", this.newFloatNBTList(new float[]{this.rotationYaw, this.rotationPitch}));
		nbtCompound.setFloat("FallDistance", this.fallDistance);
		nbtCompound.setShort("Fire", (short)this.fire);
		nbtCompound.setShort("Air", (short)this.air);
		nbtCompound.setBoolean("OnGround", this.onGround);
		this.writeEntityToNBT(nbtCompound);
	}

	public void readFromNBT(NBTTagCompound nbtCompound) {
		NBTTagList var2 = nbtCompound.getTagList("Pos");
		NBTTagList var3 = nbtCompound.getTagList("Motion");
		NBTTagList var4 = nbtCompound.getTagList("Rotation");
		this.setPosition(0.0D, 0.0D, 0.0D);
		this.motionX = ((NBTTagDouble)var3.tagAt(0)).doubleValue;
		this.motionY = ((NBTTagDouble)var3.tagAt(1)).doubleValue;
		this.motionZ = ((NBTTagDouble)var3.tagAt(2)).doubleValue;
		this.prevPosX = this.lastTickPosX = this.posX = ((NBTTagDouble)var2.tagAt(0)).doubleValue;
		this.prevPosY = this.lastTickPosY = this.posY = ((NBTTagDouble)var2.tagAt(1)).doubleValue;
		this.prevPosZ = this.lastTickPosZ = this.posZ = ((NBTTagDouble)var2.tagAt(2)).doubleValue;
		this.prevRotationYaw = this.rotationYaw = ((NBTTagFloat)var4.tagAt(0)).floatValue;
		this.prevRotationPitch = this.rotationPitch = ((NBTTagFloat)var4.tagAt(1)).floatValue;
		this.fallDistance = nbtCompound.getFloat("FallDistance");
		this.fire = nbtCompound.getShort("Fire");
		this.air = nbtCompound.getShort("Air");
		this.onGround = nbtCompound.getBoolean("OnGround");
		this.setPosition(this.posX, this.posY, this.posZ);
		this.readEntityFromNBT(nbtCompound);
	}

	protected final String getEntityString() {
		return EntityList.getEntityString(this);
	}

	protected abstract void readEntityFromNBT(NBTTagCompound var1);

	protected abstract void writeEntityToNBT(NBTTagCompound var1);

	protected NBTTagList newDoubleNBTList(double... doubleArray) {
		NBTTagList var2 = new NBTTagList();
		double[] var3 = doubleArray;
		int var4 = doubleArray.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			double var6 = var3[var5];
			var2.setTag(new NBTTagDouble(var6));
		}

		return var2;
	}

	protected NBTTagList newFloatNBTList(float... floatArray) {
		NBTTagList var2 = new NBTTagList();
		float[] var3 = floatArray;
		int var4 = floatArray.length;

		for(int var5 = 0; var5 < var4; ++var5) {
			float var6 = var3[var5];
			var2.setTag(new NBTTagFloat(var6));
		}

		return var2;
	}

	public float getShadowSize() {
		return this.height / 2.0F;
	}

	public EntityItem dropItem(int var1, int var2) {
		return this.entityDropItem(var1, var2, 0.0F);
	}

	public EntityItem entityDropItem(int var1, int var2, float var3) {
		EntityItem var4 = new EntityItem(this.worldObj, this.posX, this.posY + (double)var3, this.posZ, new ItemStack(var1, var2));
		var4.delayBeforeCanPickup = 10;
		this.worldObj.spawnEntityInWorld(var4);
		return var4;
	}

	public boolean isEntityAlive() {
		return !this.isDead;
	}

	public boolean isEntityInsideOpaqueBlock() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.posY + (double)this.getEyeHeight());
		int var3 = MathHelper.floor_double(this.posZ);
		return this.worldObj.isBlockNormalCube(var1, var2, var3);
	}

	public boolean interact(EntityPlayer entityPlayer) {
		return false;
	}

	public AxisAlignedBB getCollisionBox(Entity entity) {
		return null;
	}

	public void updateRidden() {
		if(this.ridingEntity.isDead) {
			this.ridingEntity = null;
		} else {
			this.motionX = 0.0D;
			this.motionY = 0.0D;
			this.motionZ = 0.0D;
			this.onUpdate();
			this.ridingEntity.updateRiderPosition();
			this.entityRiderYawDelta += (double)(this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);

			for(this.entityRiderPitchDelta += (double)(this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch); this.entityRiderYawDelta >= 180.0D; this.entityRiderYawDelta -= 360.0D) {
			}

			while(this.entityRiderYawDelta < -180.0D) {
				this.entityRiderYawDelta += 360.0D;
			}

			while(this.entityRiderPitchDelta >= 180.0D) {
				this.entityRiderPitchDelta -= 360.0D;
			}

			while(this.entityRiderPitchDelta < -180.0D) {
				this.entityRiderPitchDelta += 360.0D;
			}

			double var1 = this.entityRiderYawDelta * 0.5D;
			double var3 = this.entityRiderPitchDelta * 0.5D;
			float var5 = 10.0F;
			if(var1 > (double)var5) {
				var1 = (double)var5;
			}

			if(var1 < (double)(-var5)) {
				var1 = (double)(-var5);
			}

			if(var3 > (double)var5) {
				var3 = (double)var5;
			}

			if(var3 < (double)(-var5)) {
				var3 = (double)(-var5);
			}

			this.entityRiderYawDelta -= var1;
			this.entityRiderPitchDelta -= var3;
			this.rotationYaw = (float)((double)this.rotationYaw + var1);
			this.rotationPitch = (float)((double)this.rotationPitch + var3);
		}
	}

	protected void updateRiderPosition() {
		this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
	}

	public double getYOffset() {
		return (double)this.yOffset;
	}

	public double getMountedYOffset() {
		return (double)this.height * 0.75D;
	}

	public void mountEntity(Entity entity) {
		this.entityRiderPitchDelta = 0.0D;
		this.entityRiderYawDelta = 0.0D;
		if(this.ridingEntity == entity) {
			this.ridingEntity.riddenByEntity = null;
			this.ridingEntity = null;
			this.setLocationAndAngles(entity.posX, entity.boundingBox.minY + (double)entity.height, entity.posZ, this.rotationYaw, this.rotationPitch);
		} else {
			if(this.ridingEntity != null) {
				this.ridingEntity.riddenByEntity = null;
			}

			if(entity.riddenByEntity != null) {
				entity.riddenByEntity.ridingEntity = null;
			}

			this.ridingEntity = entity;
			entity.riddenByEntity = this;
		}
	}

	public void setPositionAndRotation(double x, double y, double z, float rotationYaw, float rotationPitch, int newPosRotationIncrements) {
		this.setPosition(x, y, z);
		this.setRotation(rotationYaw, rotationPitch);
	}
}
