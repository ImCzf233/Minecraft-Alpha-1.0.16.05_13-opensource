package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class TileEntity {
	private static Map nameToClassMap = new HashMap();
	private static Map classToNameMap = new HashMap();
	public World worldObj;
	public int xCoord;
	public int yCoord;
	public int zCoord;

	private static void addMapping(Class tileClass, String name) {
		if(classToNameMap.containsKey(name)) {
			throw new IllegalArgumentException("Duplicate id: " + name);
		} else {
			nameToClassMap.put(name, tileClass);
			classToNameMap.put(tileClass, name);
		}
	}

	public void readFromNBT(NBTTagCompound compoundTag) {
		this.xCoord = compoundTag.getInteger("x");
		this.yCoord = compoundTag.getInteger("y");
		this.zCoord = compoundTag.getInteger("z");
	}

	public void writeToNBT(NBTTagCompound compoundTag) {
		String var2 = (String)classToNameMap.get(this.getClass());
		if(var2 == null) {
			throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
		} else {
			compoundTag.setString("id", var2);
			compoundTag.setInteger("x", this.xCoord);
			compoundTag.setInteger("y", this.yCoord);
			compoundTag.setInteger("z", this.zCoord);
		}
	}

	public void updateEntity() {
	}

	public static TileEntity createAndLoadEntity(NBTTagCompound compoundTag) {
		TileEntity var1 = null;

		try {
			Class var2 = (Class)nameToClassMap.get(compoundTag.getString("id"));
			if(var2 != null) {
				var1 = (TileEntity)var2.newInstance();
			}
		} catch (Exception var3) {
			var3.printStackTrace();
		}

		if(var1 != null) {
			var1.readFromNBT(compoundTag);
		} else {
			System.out.println("Skipping TileEntity with id " + compoundTag.getString("id"));
		}

		return var1;
	}

	public int getBlockMetadata() {
		return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
	}

	public void onInventoryChanged() {
		this.worldObj.updateTileEntityChunkAndDoNothing(this.xCoord, this.yCoord, this.zCoord, this);
	}

	public double getDistanceFrom(double x, double y, double z) {
		double var7 = (double)this.xCoord + 0.5D - x;
		double var9 = (double)this.yCoord + 0.5D - y;
		double var11 = (double)this.zCoord + 0.5D - z;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	public Block getBlockType() {
		return Block.blocksList[this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord)];
	}

	static {
		addMapping(TileEntityFurnace.class, "Furnace");
		addMapping(TileEntityChest.class, "Chest");
		addMapping(TileEntitySign.class, "Sign");
		addMapping(TileEntityMobSpawner.class, "MobSpawner");
	}
}
