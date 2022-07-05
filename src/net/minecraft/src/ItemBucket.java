package net.minecraft.src;

public class ItemBucket extends Item {
	private int isFull;

	public ItemBucket(int id, int liquid) {
		super(id);
		this.maxStackSize = 1;
		this.maxDamage = 64;
		this.isFull = liquid;
	}

	public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
		float var4 = 1.0F;
		float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * var4;
		float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * var4;
		double var7 = var3.prevPosX + (var3.posX - var3.prevPosX) * (double)var4;
		double var9 = var3.prevPosY + (var3.posY - var3.prevPosY) * (double)var4;
		double var11 = var3.prevPosZ + (var3.posZ - var3.prevPosZ) * (double)var4;
		Vec3D var13 = Vec3D.createVector(var7, var9, var11);
		float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
		float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
		float var16 = -MathHelper.cos(-var5 * 0.017453292F);
		float var17 = MathHelper.sin(-var5 * 0.017453292F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		double var21 = 5.0D;
		Vec3D var23 = var13.addVector((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
		MovingObjectPosition var24 = var2.rayTraceBlocks_do(var13, var23, this.isFull == 0);
		if(var24 == null) {
			return var1;
		} else {
			if(var24.typeOfHit == 0) {
				int var25 = var24.blockX;
				int var26 = var24.blockY;
				int var27 = var24.blockZ;
				if(this.isFull == 0) {
					if(var2.getBlockMaterial(var25, var26, var27) == Material.water && var2.getBlockMetadata(var25, var26, var27) == 0) {
						var2.setBlockWithNotify(var25, var26, var27, 0);
						return new ItemStack(Item.bucketWater);
					}

					if(var2.getBlockMaterial(var25, var26, var27) == Material.lava && var2.getBlockMetadata(var25, var26, var27) == 0) {
						var2.setBlockWithNotify(var25, var26, var27, 0);
						return new ItemStack(Item.bucketLava);
					}
				} else {
					if(this.isFull < 0) {
						return new ItemStack(Item.bucketEmpty);
					}

					if(var24.sideHit == 0) {
						--var26;
					}

					if(var24.sideHit == 1) {
						++var26;
					}

					if(var24.sideHit == 2) {
						--var27;
					}

					if(var24.sideHit == 3) {
						++var27;
					}

					if(var24.sideHit == 4) {
						--var25;
					}

					if(var24.sideHit == 5) {
						++var25;
					}

					if(var2.getBlockId(var25, var26, var27) == 0 || !var2.getBlockMaterial(var25, var26, var27).isSolid()) {
						var2.setBlockAndMetadataWithNotify(var25, var26, var27, this.isFull, 0);
						return new ItemStack(Item.bucketEmpty);
					}
				}
			} else if(this.isFull == 0 && var24.entityHit instanceof EntityCow) {
				return new ItemStack(Item.bucketMilk);
			}

			return var1;
		}
	}
}
