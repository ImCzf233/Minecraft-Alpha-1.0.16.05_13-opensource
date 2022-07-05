package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EntityPlayer extends EntityLiving {
	public InventoryPlayer inventory = new InventoryPlayer(this);
	public byte unusedMiningCooldown = 0;
	public int score = 0;
	public float prevCameraYaw;
	public float cameraYaw;
	public boolean isSwinging = false;
	public int swingProgressInt = 0;
	public String username;
	private int damageRemainder = 0;
	public Random rand;

	public EntityPlayer(World var1) {
		super(var1);
		this.yOffset = 1.62F;
		this.setLocationAndAngles((double)var1.spawnX + 0.5D, (double)(var1.spawnY + 1), (double)var1.spawnZ + 0.5D, 0.0F, 0.0F);
		this.health = 20;
		this.entityType = "humanoid";
		this.unusedRotation = 180.0F;
		this.fireResistance = 20;
		this.texture = "/char.png";
		this.rand = new Random();
	}

	public void updateRidden() {
		super.updateRidden();
		this.prevCameraYaw = this.cameraYaw;
		this.cameraYaw = 0.0F;
	}

	public void preparePlayerToSpawn() {
		this.yOffset = 1.62F;
		this.setSize(0.6F, 1.8F);
		super.preparePlayerToSpawn();
		this.health = 20;
		this.deathTime = 0;
	}

	protected void updateEntityActionState() {
		if(this.isSwinging) {
			++this.swingProgressInt;
			if(this.swingProgressInt == 8) {
				this.swingProgressInt = 0;
				this.isSwinging = false;
			}
		} else {
			this.swingProgressInt = 0;
		}

		this.swingProgress = (float)this.swingProgressInt / 8.0F;
	}

	public void onLivingUpdate() {
		if(this.worldObj.difficultySetting == 0 && this.health < 20 && this.ticksExisted % 20 * 4 == 0) {
			this.heal(1);
		}

		this.inventory.decrementAnimations();
		this.prevCameraYaw = this.cameraYaw;
		super.onLivingUpdate();
		float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		float var2 = (float)Math.atan(-this.motionY * (double)0.2F) * 15.0F;
		if(var1 > 0.1F) {
			var1 = 0.1F;
		}

		if(!this.onGround || this.health <= 0) {
			var1 = 0.0F;
		}

		if(this.onGround || this.health <= 0) {
			var2 = 0.0F;
		}

		this.cameraYaw += (var1 - this.cameraYaw) * 0.4F;
		this.cameraPitch += (var2 - this.cameraPitch) * 0.8F;
		if(this.health > 0) {
			List var3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.0D, 0.0D, 1.0D));
			if(var3 != null) {
				for(int var4 = 0; var4 < var3.size(); ++var4) {
					this.collideWithPlayer((Entity)var3.get(var4));
				}
			}
		}

		if(this.posY < -1.0D) {
			this.posZ += (double)(this.rand.nextFloat() * 500.0F + 500.0F);
			int var6 = 80;
			boolean var7 = false;

			while(var6 != 0) {
				int var5 = this.worldObj.getBlockId((int)this.posX, var6, (int)this.posZ);
				if(var5 != 0 && var5 != 116) {
					if(var7) {
						++var6;
						break;
					}

					++var6;
				} else {
					--var6;
					var7 = true;
				}
			}

			this.posY = (double)var6;
			this.setPosition(this.posX, this.posY, this.posZ);
			System.out.println("Sending player to Brazil at: " + this.posX + " " + this.posY + " " + this.posZ);
		}

	}

	private void collideWithPlayer(Entity entity) {
		entity.onCollideWithPlayer(this);
	}

	public int getScore() {
		return this.score;
	}

	public void onDeath(Entity var1) {
		this.setSize(0.2F, 0.2F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionY = (double)0.1F;
		if(this.username.equals("Notch")) {
			this.dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
		}

		this.inventory.dropAllItems();
		if(var1 != null) {
			this.motionX = (double)(-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * (float)Math.PI / 180.0F) * 0.1F);
			this.motionZ = (double)(-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * (float)Math.PI / 180.0F) * 0.1F);
		} else {
			this.motionZ = 0.0D;
			this.motionX = 0.0D;
		}

		this.yOffset = 0.1F;
	}

	public void addToPlayerScore(Entity var1, int var2) {
		this.score += var2;
	}

	public void dropPlayerItem(ItemStack itemStack) {
		this.dropPlayerItemWithRandomChoice(itemStack, false);
	}

	public void dropPlayerItemWithRandomChoice(ItemStack itemStack, boolean isRandom) {
		if(itemStack != null) {
			EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY - (double)0.3F + (double)this.getEyeHeight(), this.posZ, itemStack);
			var3.delayBeforeCanPickup = 40;
			if(isRandom) {
				float var4 = this.rand.nextFloat() * 0.5F;
				float var5 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				var3.motionX = (double)(-MathHelper.sin(var5) * var4);
				var3.motionZ = (double)(MathHelper.cos(var5) * var4);
				var3.motionY = (double)0.2F;
			} else {
				var3.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * 0.3F);
				var3.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * 0.3F);
				var3.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI) * 0.3F + 0.1F);
				float var6 = this.rand.nextFloat() * (float)Math.PI * 2.0F;
				float var7 = 0.02F * this.rand.nextFloat();
				var3.motionX += Math.cos((double)var6) * (double)var7;
				var3.motionY += (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
				var3.motionZ += Math.sin((double)var6) * (double)var7;
			}

			this.joinEntityItemWithWorld(var3);
		}
	}

	protected void joinEntityItemWithWorld(EntityItem entityItem) {
		this.worldObj.spawnEntityInWorld(entityItem);
	}

	public float getCurrentPlayerStrVsBlock(Block block) {
		float var2 = this.inventory.getStrVsBlock(block);
		if(this.isInsideOfMaterial(Material.water)) {
			var2 /= 5.0F;
		}

		if(!this.onGround) {
			var2 /= 5.0F;
		}

		return var2;
	}

	public boolean canHarvestBlock(Block block) {
		return this.inventory.canHarvestBlock(block);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
		this.inventory.readFromNBT(var1.getTagList("Inventory"));
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
		var1.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
	}

	public void displayGUIChest(IInventory inventory) {
	}

	public void displayWorkbenchGUI() {
	}

	public void onItemPickup(Entity entity, int var2) {
	}

	protected float getEyeHeight() {
		return 0.12F;
	}

	public boolean attackEntityFrom(Entity var1, int var2) {
		this.entityAge = 0;
		if(this.health <= 0) {
			return false;
		} else if((float)this.heartsLife > (float)this.heartsHalvesLife / 2.0F) {
			return false;
		} else {
			if(var1 instanceof EntityMob || var1 instanceof EntityArrow) {
				if(this.worldObj.difficultySetting == 0) {
					var2 = 0;
				}

				if(this.worldObj.difficultySetting == 1) {
					var2 = var2 / 3 + 1;
				}

				if(this.worldObj.difficultySetting == 3) {
					var2 = var2 * 3 / 2;
				}
			}

			int var3 = var2 * (25 - this.inventory.getTotalArmorValue()) + this.damageRemainder;
			this.inventory.damageArmor(var2);
			var2 = var3 / 25;
			this.damageRemainder = var3 % 25;
			return var2 != 0 && super.attackEntityFrom(var1, var2);
		}
	}

	public void displayGUIFurnace(TileEntityFurnace furnaceTileEntity) {
	}

	public void displayGUIEditSign(TileEntitySign signTileEntity) {
	}

	public void interactWithEntity(Entity entity) {
	}

	public ItemStack getCurrentEquippedItem() {
		return this.inventory.getCurrentItem();
	}

	public void destroyCurrentEquippedItem() {
		this.inventory.setInventorySlotContents(this.inventory.currentItem, (ItemStack)null);
	}

	public double getYOffset() {
		return (double)(this.yOffset - 0.5F);
	}

	public void swingItem() {
		this.swingProgressInt = -1;
		this.isSwinging = true;
	}
}
