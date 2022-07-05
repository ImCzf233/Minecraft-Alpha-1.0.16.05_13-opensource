package net.minecraft.src;

import java.util.Random;

public class SkyGen extends WorldGenerator {
	private int a;

	public SkyGen(int var1) {
		this.a = var1;
	}

	public boolean generate(World var1, Random var2, int var3, int var4, int var5) {
		for(int var6 = 0; var6 < 4; ++var6) {
			int var7 = var3 + var2.nextInt(8) - var2.nextInt(8);
			int var8 = var4 + var2.nextInt(4) - var2.nextInt(4);
			int var9 = var5 + var2.nextInt(8) - var2.nextInt(8);
			boolean var10 = false;
			if(var8 > 90) {
				var10 = true;
			} else if(var8 > 80) {
				var10 = var2.nextInt(100) > 80;
			}

			if(var10 && var1.getBlockId(var7, var8, var9) == 0) {
				var1.setBlock(var7, var8, var9, this.a);
			}
		}

		return true;
	}
}
