package net.minecraft.src;

import java.util.Comparator;

public class RenderSorter implements Comparator<WorldRenderer> {
	private EntityPlayer baseEntity;

	public RenderSorter(EntityPlayer var1) {
		this.baseEntity = var1;
	}

	public int compare(WorldRenderer var1, WorldRenderer var2) {
		boolean var3 = var1.isInFrustum;
		boolean var4 = var2.isInFrustum;
		return var3 && !var4 ? 1 : (var4 && !var3 ? -1 : (var1.distanceToEntitySquared(this.baseEntity) < var2.distanceToEntitySquared(this.baseEntity) ? 1 : -1));
	}
}
