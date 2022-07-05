package net.minecraft.src;

public class MobGiant extends EntityMob {
	public int maxHP = -1;

	public MobGiant(World var1, int var2) {
		super(var1);
		if(var2 <= 0) {
			var2 = 1;
		}

		this.texture = "/mob/collosal_a.png";
		this.moveSpeed = 0.5F;
		this.attackStrength = 50;
		this.health *= Math.min(4 * var2, 800);
		this.maxHP = this.health;
		this.yOffset *= 6.0F;
		this.setSize(this.width * 6.0F, this.height * 6.0F);
	}

	protected float getBlockPathWeight(int var1, int var2, int var3) {
		return this.worldObj.getBrightness(var1, var2, var3) - 0.5F;
	}

	protected void fall(float var1) {
	}

	protected String getLivingSound() {
		return "ext.giantambient";
	}

	protected String getHurtSound() {
		return "ext.gianthurt";
	}

	protected String getDeathSound() {
		return "ext.giantdead";
	}

	public void onDeath(Entity var1) {
		super.onDeath(var1);
		if(this.rand.nextInt(50) > 10) {
			this.dropItem(Item.flameberge.shiftedIndex, 1);
		}

	}
}
