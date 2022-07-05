package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class EntityList {
	private static Map stringToClassMapping = new HashMap();
	private static Map classToStringMapping = new HashMap();
	private static Map IDtoClassMapping = new HashMap();
	private static Map classToIDMapping = new HashMap();

	private static void addMapping(Class entityClass, String entityName, int entityID) {
		stringToClassMapping.put(entityName, entityClass);
		classToStringMapping.put(entityClass, entityName);
		IDtoClassMapping.put(Integer.valueOf(entityID), entityClass);
		classToIDMapping.put(entityClass, Integer.valueOf(entityID));
	}

	public static Entity createEntityByName(String entityName, World worldObj) {
		Entity var2 = null;

		try {
			Class var3 = (Class)stringToClassMapping.get(entityName);
			if(var3 != null) {
				var2 = (Entity)var3.getConstructor(new Class[]{World.class}).newInstance(new Object[]{worldObj});
			}
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		return var2;
	}

	public static Entity createEntityFromNBT(NBTTagCompound nbtCompound, World worldObj) {
		Entity var2 = null;

		try {
			Class var3 = (Class)stringToClassMapping.get(nbtCompound.getString("id"));
			if(var3 != null) {
				var2 = (Entity)var3.getConstructor(new Class[]{World.class}).newInstance(new Object[]{worldObj});
			}
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		if(var2 != null) {
			var2.readFromNBT(nbtCompound);
		} else {
			System.out.println("Skipping Entity with id " + nbtCompound.getString("id"));
		}

		return var2;
	}

	public static Entity createEntityByID(int entityID, World worldObj) {
		Entity var2 = null;

		try {
			Class var3 = (Class)IDtoClassMapping.get(Integer.valueOf(entityID));
			if(var3 != null) {
				var2 = (Entity)var3.getConstructor(new Class[]{World.class}).newInstance(new Object[]{worldObj});
			}
		} catch (Exception var4) {
			var4.printStackTrace();
		}

		if(var2 == null) {
			System.out.println("Skipping Entity with id " + entityID);
		}

		return var2;
	}

	public static int getEntityID(Entity entity) {
		return ((Integer)classToIDMapping.get(entity.getClass())).intValue();
	}

	public static String getEntityString(Entity entity) {
		return (String)classToStringMapping.get(entity.getClass());
	}

	static {
		addMapping(EntityArrow.class, "Arrow", 10);
		addMapping(EntitySnowball.class, "Snowball", 11);
		addMapping(EntityItem.class, "Item", 1);
		addMapping(EntityPainting.class, "Painting", 9);
		addMapping(EntityLiving.class, "Mob", 48);
		addMapping(EntityMob.class, "Monster", 49);
		addMapping(EntityCreeper.class, "Creeper", 50);
		addMapping(EntitySkeleton.class, "Skeleton", 51);
		addMapping(EntitySpider.class, "Spider", 52);
		addMapping(EntityGiantZombie.class, "Giant", 53);
		addMapping(EntityZombie.class, "Zombie", 54);
		addMapping(EntitySlime.class, "Slime", 55);
		addMapping(EntityPig.class, "Pig", 90);
		addMapping(EntitySheep.class, "Sheep", 91);
		addMapping(EntityCow.class, "Cow", 91);
		addMapping(EntityChicken.class, "Chicken", 91);
		addMapping(EntityTNTPrimed.class, "PrimedTnt", 20);
		addMapping(EntityFallingSand.class, "FallingSand", 21);
		addMapping(EntityMinecart.class, "Minecart", 40);
		addMapping(EntityBoat.class, "Boat", 41);
	}
}
