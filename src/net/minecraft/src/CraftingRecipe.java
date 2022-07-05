package net.minecraft.src;

public class CraftingRecipe {
	private int width;
	private int height;
	private int[] ingredientMap;
	private ItemStack resultStack;
	public final int resultId;

	public CraftingRecipe(int width, int height, int[] ingredientMap, ItemStack resultStack) {
		this.resultId = resultStack.itemID;
		this.width = width;
		this.height = height;
		this.ingredientMap = ingredientMap;
		this.resultStack = resultStack;
	}

	public boolean matches(int[] var1) {
		for(int var2 = 0; var2 <= 3 - this.width; ++var2) {
			for(int var3 = 0; var3 <= 3 - this.height; ++var3) {
				if(this.checkMatch(var1, var2, var3, true)) {
					return true;
				}

				if(this.checkMatch(var1, var2, var3, false)) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean checkMatch(int[] var1, int var2, int var3, boolean var4) {
		for(int var5 = 0; var5 < 3; ++var5) {
			for(int var6 = 0; var6 < 3; ++var6) {
				int var7 = var5 - var2;
				int var8 = var6 - var3;
				int var9 = -1;
				if(var7 >= 0 && var8 >= 0 && var7 < this.width && var8 < this.height) {
					if(var4) {
						var9 = this.ingredientMap[this.width - var7 - 1 + var8 * this.width];
					} else {
						var9 = this.ingredientMap[var7 + var8 * this.width];
					}
				}

				if(var1[var5 + var6 * 3] != var9) {
					return false;
				}
			}
		}

		return true;
	}

	public ItemStack getCraftingResult(int[] var1) {
		return new ItemStack(this.resultStack.itemID, this.resultStack.stackSize);
	}

	public int getRecipeSize() {
		return this.width * this.height;
	}
}
