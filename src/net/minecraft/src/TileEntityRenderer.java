package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class TileEntityRenderer {
	private Map specialRendererMap = new HashMap();
	public static TileEntityRenderer instance = new TileEntityRenderer();
	private FontRenderer fontRenderer;
	public static double staticPlayerX;
	public static double staticPlayerY;
	public static double staticPlayerZ;
	public RenderEngine renderEngine;
	public World worldObj;
	public EntityPlayer entityPlayer;
	public float playerYaw;
	public float playerPitch;
	public double playerX;
	public double playerY;
	public double playerZ;

	private TileEntityRenderer() {
		this.specialRendererMap.put(TileEntitySign.class, new TileEntitySignRenderer());
		this.specialRendererMap.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
		Iterator var1 = this.specialRendererMap.values().iterator();

		while(var1.hasNext()) {
			TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)var1.next();
			var2.setTileEntityRenderer(this);
		}

	}

	public TileEntitySpecialRenderer getSpecialRendererForClass(Class tileClass) {
		TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)this.specialRendererMap.get(tileClass);
		if(var2 == null && tileClass != TileEntity.class) {
			var2 = this.getSpecialRendererForClass(tileClass.getSuperclass());
			this.specialRendererMap.put(tileClass, var2);
		}

		return var2;
	}

	public boolean hasSpecialRenderer(TileEntity tileEntity) {
		return this.getSpecialRendererForEntity(tileEntity) != null;
	}

	public TileEntitySpecialRenderer getSpecialRendererForEntity(TileEntity tileEntity) {
		return this.getSpecialRendererForClass(tileEntity.getClass());
	}

	public void cacheActiveRenderInfo(World world, RenderEngine renderEngine, FontRenderer fontRenderer, EntityPlayer entityPlayer, float renderPartialTick) {
		this.worldObj = world;
		this.renderEngine = renderEngine;
		this.entityPlayer = entityPlayer;
		this.fontRenderer = fontRenderer;
		this.playerYaw = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * renderPartialTick;
		this.playerPitch = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * renderPartialTick;
		this.playerX = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * (double)renderPartialTick;
		this.playerY = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * (double)renderPartialTick;
		this.playerZ = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (double)renderPartialTick;
	}

	public void renderTileEntity(TileEntity tileEntity, float renderPartialTick) {
		if(tileEntity.getDistanceFrom(this.playerX, this.playerY, this.playerZ) < 4096.0D) {
			float var3 = this.worldObj.getBrightness(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			GL11.glColor3f(var3, var3, var3);
			this.renderTileEntityAt(tileEntity, (double)tileEntity.xCoord - staticPlayerX, (double)tileEntity.yCoord - staticPlayerY, (double)tileEntity.zCoord - staticPlayerZ, renderPartialTick);
		}

	}

	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float renderPartialTick) {
		TileEntitySpecialRenderer var9 = this.getSpecialRendererForEntity(tileEntity);
		if(var9 != null) {
			var9.renderTileEntityAt(tileEntity, x, y, z, renderPartialTick);
		}

	}

	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
}
