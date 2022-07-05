package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityMobSpawner> {
	private Map entityHashMap = new HashMap();

	public void renderTileEntityAt(TileEntityMobSpawner var1, double var2, double var4, double var6, float var8) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)var2 + 0.5F, (float)var4, (float)var6 + 0.5F);
		Entity var9 = (Entity)this.entityHashMap.get(var1.mobID);
		if(var9 == null) {
			var9 = EntityList.createEntityByName(var1.mobID, (World)null);
			this.entityHashMap.put(var1.mobID, var9);
		}

		if(var9 != null) {
			var9.setWorld(var1.worldObj);
			float var10 = 0.4375F;
			GL11.glTranslatef(0.0F, 0.4F, 0.0F);
			GL11.glRotatef((float)(var1.prevYaw + (var1.yaw - var1.prevYaw) * (double)var8) * 10.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.0F, -0.4F, 0.0F);
			GL11.glScalef(var10, var10, var10);
			var9.setLocationAndAngles(var2, var4, var6, 0.0F, 0.0F);
			RenderManager.instance.renderEntityWithPosYaw(var9, 0.0D, 0.0D, 0.0D, 0.0F, var8);
		}

		GL11.glPopMatrix();
	}
}
