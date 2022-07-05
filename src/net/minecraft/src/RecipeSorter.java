package net.minecraft.src;

import java.util.Comparator;

class RecipeSorter implements Comparator<CraftingRecipe> {
	final CraftingManager craftingManager;

	RecipeSorter(CraftingManager craftingManager) {
		this.craftingManager = craftingManager;
	}

	public int compare(CraftingRecipe var1, CraftingRecipe var2) {
		return var2.getRecipeSize() < var1.getRecipeSize() ? -1 : (var2.getRecipeSize() > var1.getRecipeSize() ? 1 : 0);
	}
}
