package net.minecraft.src;

public class ColorUtil {
	public static float[] BlendColor(float var0, float var1, float var2, float var3) {
		float var4 = 0.58431375F;
		float var5 = 0.0F;
		float var6 = 1.0F;
		float[] var7 = new float[]{var0 * var4 + (1.0F - var0) * var1, var0 * var5 + (1.0F - var0) * var2, var0 * var6 + (1.0F - var0) * var3};
		return var7;
	}

	public static float[] BlendColorA(float var0, float var1, float var2, float var3, float var4, float var5, float var6) {
		float[] var7 = new float[]{var0 * var4 + (1.0F - var0) * var1, var0 * var5 + (1.0F - var0) * var2, var0 * var6 + (1.0F - var0) * var3};
		return var7;
	}
}
