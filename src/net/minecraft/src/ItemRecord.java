package net.minecraft.src;

public class ItemRecord extends Item {
	private String recordName;

	protected ItemRecord(int id, String record) {
		super(id);
		this.recordName = record;
		this.maxStackSize = 1;
	}

	public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, int var4, int var5, int var6, int var7) {
		if(var3.getBlockId(var4, var5, var6) == Block.jukebox.blockID && var3.getBlockMetadata(var4, var5, var6) == 0) {
			var3.setBlockMetadataWithNotify(var4, var5, var6, this.shiftedIndex - Item.record13.shiftedIndex + 1);
			var3.playRecord(this.recordName, var4, var5, var6);
			--var1.stackSize;
			return true;
		} else {
			return false;
		}
	}
}
