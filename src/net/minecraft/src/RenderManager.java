package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class RenderManager {
	private Map entityRenderMap;
	public static RenderManager instance = new RenderManager();
	private FontRenderer fontRenderer;
	public static double renderPosX;
	public static double renderPosY;
	public static double renderPosZ;
	public RenderEngine renderEngine;
	public ItemRenderer itemRenderer;
	public World worldObj;
	public EntityPlayer player;
	public float playerViewY;
	public float playerViewX;
	public GameSettings options;
	public double viewerPosX;
	public double viewerPosY;
	public double viewerPosZ;

	private RenderManager() {
		(this.entityRenderMap = new HashMap()).put(EntitySpider.class, new RenderSpider());
		this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
		this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep(), new ModelSheepFur(), 0.7F));
		this.entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7F));
		this.entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3F));
		this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
		this.entityRenderMap.put(EntitySkeleton.class, new RenderLiving(new ModelSkeleton(), 0.5F));
		this.entityRenderMap.put(EntityZombie.class, new RenderLiving(new ModelZombie(), 0.5F));
		this.entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25F));
		this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
		this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5F, 6.0F));
		this.entityRenderMap.put(EntityLiving.class, new RenderLiving(new ModelBiped(), 0.5F));
		this.entityRenderMap.put(Entity.class, new RenderEntity());
		this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
		this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
		this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball());
		this.entityRenderMap.put(EntityItem.class, new RenderItem());
		this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
		this.entityRenderMap.put(EntityFallingSand.class, new RenderFallingSand());
		this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
		this.entityRenderMap.put(EntityBoat.class, new RenderBoat());
		this.entityRenderMap.put(MobGiant.class, new RenderGiant(new ModelZombie(), 0.5F, 6.0F));
		Iterator var1 = this.entityRenderMap.values().iterator();

		while(var1.hasNext()) {
			((Render)var1.next()).setRenderManager(this);
		}

	}

	public Render getEntityClassRenderObject(Class entityClass) {
		Render var2 = (Render)this.entityRenderMap.get(entityClass);
		if(var2 == null && entityClass != Entity.class) {
			var2 = this.getEntityClassRenderObject(entityClass.getSuperclass());
			this.entityRenderMap.put(entityClass, var2);
		}

		return var2;
	}

	public Render getEntityRenderObject(Entity entity) {
		return this.getEntityClassRenderObject(entity.getClass());
	}

	public void cacheActiveRenderInfo(World world, RenderEngine renderEngine, FontRenderer fontRenderer, EntityPlayer entityPlayer, GameSettings gameSettings, float renderPartialTick) {
		this.worldObj = world;
		this.renderEngine = renderEngine;
		this.options = gameSettings;
		this.player = entityPlayer;
		this.fontRenderer = fontRenderer;
		this.playerViewY = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * renderPartialTick;
		this.playerViewX = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * renderPartialTick;
		this.viewerPosX = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * (double)renderPartialTick;
		this.viewerPosY = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * (double)renderPartialTick;
		this.viewerPosZ = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (double)renderPartialTick;
	}

	public void renderEntity(Entity entity, float renderPartialTick) {
		double var3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)renderPartialTick;
		double var5 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)renderPartialTick;
		double var7 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)renderPartialTick;
		float var9 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * renderPartialTick;
		float var10 = entity.getBrightness(renderPartialTick);
		GL11.glColor3f(var10, var10, var10);
		this.renderEntityWithPosYaw(entity, var3 - renderPosX, var5 - renderPosY, var7 - renderPosZ, var9, renderPartialTick);
	}

	public void renderEntityWithPosYaw(Entity entity, double x, double y, double z, float yaw, float pitch) {
		Render var10 = this.getEntityRenderObject(entity);
		if(var10 != null) {
			var10.doRender(entity, x, y, z, yaw, pitch);
			var10.doRenderShadowAndFire(entity, x, y, z, yaw, pitch);
		}

	}

	public void set(World world) {
		this.worldObj = world;
	}

	public double getDistanceToCamera(double x, double y, double z) {
		double var7 = x - this.viewerPosX;
		double var9 = y - this.viewerPosY;
		double var11 = z - this.viewerPosZ;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
}
