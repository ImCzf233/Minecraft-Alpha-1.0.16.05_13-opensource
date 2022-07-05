package net.minecraft.src;

public class ClippingHelper {
	public float[][] frustum = new float[16][16];
	public float[] projectionMatrix = new float[16];
	public float[] modelviewMatrix = new float[16];
	public float[] clippingMatrix = new float[16];

	public boolean isBoxInFrustum(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		for(int var13 = 0; var13 < 6; ++var13) {
			if((double)this.frustum[var13][0] * minX + (double)this.frustum[var13][1] * minY + (double)this.frustum[var13][2] * minZ + (double)this.frustum[var13][3] <= 0.0D && (double)this.frustum[var13][0] * maxX + (double)this.frustum[var13][1] * minY + (double)this.frustum[var13][2] * minZ + (double)this.frustum[var13][3] <= 0.0D && (double)this.frustum[var13][0] * minX + (double)this.frustum[var13][1] * maxY + (double)this.frustum[var13][2] * minZ + (double)this.frustum[var13][3] <= 0.0D && (double)this.frustum[var13][0] * maxX + (double)this.frustum[var13][1] * maxY + (double)this.frustum[var13][2] * minZ + (double)this.frustum[var13][3] <= 0.0D && (double)this.frustum[var13][0] * minX + (double)this.frustum[var13][1] * minY + (double)this.frustum[var13][2] * maxZ + (double)this.frustum[var13][3] <= 0.0D && (double)this.frustum[var13][0] * maxX + (double)this.frustum[var13][1] * minY + (double)this.frustum[var13][2] * maxZ + (double)this.frustum[var13][3] <= 0.0D && (double)this.frustum[var13][0] * minX + (double)this.frustum[var13][1] * maxY + (double)this.frustum[var13][2] * maxZ + (double)this.frustum[var13][3] <= 0.0D && (double)this.frustum[var13][0] * maxX + (double)this.frustum[var13][1] * maxY + (double)this.frustum[var13][2] * maxZ + (double)this.frustum[var13][3] <= 0.0D) {
				return false;
			}
		}

		return true;
	}
}
