package net.minecraft.src;

public final class ItemStack {
	public int stackSize;
	public int animationsToGo;
	public int itemID;
	public int itemDmg;

	public ItemStack(Block block) {
		this((Block)block, 1);
	}

	public ItemStack(Block block, int stackSize) {
		this(block.blockID, stackSize);
	}

	public ItemStack(Item item) {
		this((Item)item, 1);
	}

	public ItemStack(Item item, int stackSize) {
		this(item.shiftedIndex, stackSize);
	}

	public ItemStack(int itemID) {
		this(itemID, 1);
	}

	public ItemStack(int itemID, int stackSize) {
		this.stackSize = 0;
		this.itemID = itemID;
		this.stackSize = stackSize;
	}

	public ItemStack(int itemID, int stackSize, int itemDmg) {
		this.stackSize = 0;
		this.itemID = itemID;
		this.stackSize = stackSize;
		this.itemDmg = itemDmg;
	}

	public ItemStack(NBTTagCompound nbtCompound) {
		this.stackSize = 0;
		this.readFromNBT(nbtCompound);
	}

	public ItemStack splitStack(int stackSize) {
		this.stackSize -= stackSize;
		return new ItemStack(this.itemID, stackSize, this.itemDmg);
	}

	public Item getItem() {
		return Item.itemsList[this.itemID];
	}

	public int getIconIndex() {
		return this.getItem().getIconIndex(this);
	}

	public boolean useItem(EntityPlayer entityPlayer, World worldObj, int x, int y, int z, int side) {
		return this.getItem().onItemUse(this, entityPlayer, worldObj, x, y, z, side);
	}

	public float getStrVsBlock(Block block) {
		return this.getItem().getStrVsBlock(this, block);
	}

	public ItemStack useItemRightClick(World worldObj, EntityPlayer entityPlayer) {
		return this.getItem().onItemRightClick(this, worldObj, entityPlayer);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbtCompound) {
		nbtCompound.setShort("id", (short)this.itemID);
		nbtCompound.setByte("Count", (byte)this.stackSize);
		nbtCompound.setShort("Damage", (short)this.itemDmg);
		return nbtCompound;
	}

	public void readFromNBT(NBTTagCompound nbtCompound) {
		this.itemID = nbtCompound.getShort("id");
		this.stackSize = nbtCompound.getByte("Count");
		this.itemDmg = nbtCompound.getShort("Damage");
	}

	public int getMaxStackSize() {
		return this.getItem().getItemStackLimit();
	}

	public int getMaxDamage() {
		return Item.itemsList[this.itemID].getMaxDamage();
	}

	public void damageItem(int damage) {
		this.itemDmg += damage;
		if(this.itemDmg > this.getMaxDamage()) {
			--this.stackSize;
			if(this.stackSize < 0) {
				this.stackSize = 0;
			}

			this.itemDmg = 0;
		}

	}

	public void hitEntity(EntityLiving entityLiving) {
		Item.itemsList[this.itemID].hitEntity(this, entityLiving);
	}

	public void onDestroyBlock(int id, int x, int y, int z) {
		Item.itemsList[this.itemID].onBlockDestroyed(this, id, x, y, z);
	}

	public int getDamageVsEntity(Entity entity) {
		return Item.itemsList[this.itemID].getDamageVsEntity(entity);
	}

	public boolean canHarvestBlock(Block block) {
		return Item.itemsList[this.itemID].canHarvestBlock(block);
	}

	public void onItemDestroyedByUse(EntityPlayer entityPlayer) {
	}

	public void useItemOnEntity(EntityLiving entityLiving) {
		Item.itemsList[this.itemID].saddleEntity(this, entityLiving);
	}

	public ItemStack copy() {
		return new ItemStack(this.itemID, this.stackSize, this.itemDmg);
	}
}
