package net.minecraft.src;

import java.util.Comparator;

public class EntitySorter implements Comparator<WorldRenderer> {
	private Entity comparedEntity;

	public EntitySorter(Entity var1) {
		this.comparedEntity = var1;
	}

	public int compare(WorldRenderer var1, WorldRenderer var2) {
		return var1.distanceToEntitySquared(this.comparedEntity) < var2.distanceToEntitySquared(this.comparedEntity) ? -1 : 1;
	}
}
