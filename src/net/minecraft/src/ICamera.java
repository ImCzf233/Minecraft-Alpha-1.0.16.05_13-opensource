package net.minecraft.src;

public interface ICamera {
	boolean isBoundingBoxInFrustum(AxisAlignedBB var1);

	void setPosition(double var1, double var3, double var5);
}
