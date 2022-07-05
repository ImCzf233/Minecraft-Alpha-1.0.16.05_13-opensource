package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class Vec3D {
	private static List vectorList = new ArrayList();
	private static int nextVector = 0;
	public double xCoord;
	public double yCoord;
	public double zCoord;

	public static Vec3D createVectorHelper(double x, double y, double z) {
		return new Vec3D(x, y, z);
	}

	public static void initialize() {
		nextVector = 0;
	}

	public static Vec3D createVector(double x, double y, double z) {
		if(nextVector >= vectorList.size()) {
			vectorList.add(createVectorHelper(0.0D, 0.0D, 0.0D));
		}

		return ((Vec3D)vectorList.get(nextVector++)).setComponents(x, y, z);
	}

	private Vec3D(double x, double y, double z) {
		if(x == -0.0D) {
			x = 0.0D;
		}

		if(y == -0.0D) {
			y = 0.0D;
		}

		if(z == -0.0D) {
			z = 0.0D;
		}

		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}

	private Vec3D setComponents(double x, double y, double z) {
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		return this;
	}

	public Vec3D subtract(Vec3D vector) {
		return createVector(vector.xCoord - this.xCoord, vector.yCoord - this.yCoord, vector.zCoord - this.zCoord);
	}

	public Vec3D normalize() {
		double var1 = (double)MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
		return var1 < 1.0E-4D ? createVector(0.0D, 0.0D, 0.0D) : createVector(this.xCoord / var1, this.yCoord / var1, this.zCoord / var1);
	}

	public Vec3D crossProduct(Vec3D vector) {
		return createVector(this.yCoord * vector.zCoord - this.zCoord * vector.yCoord, this.zCoord * vector.xCoord - this.xCoord * vector.zCoord, this.xCoord * vector.yCoord - this.yCoord * vector.xCoord);
	}

	public Vec3D addVector(double x, double y, double z) {
		return createVector(this.xCoord + x, this.yCoord + y, this.zCoord + z);
	}

	public double distanceTo(Vec3D vector) {
		double var2 = vector.xCoord - this.xCoord;
		double var4 = vector.yCoord - this.yCoord;
		double var6 = vector.zCoord - this.zCoord;
		return (double)MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
	}

	public double squareDistanceTo(Vec3D vector) {
		double var2 = vector.xCoord - this.xCoord;
		double var4 = vector.yCoord - this.yCoord;
		double var6 = vector.zCoord - this.zCoord;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}

	public double squareDistanceTo(double x, double y, double z) {
		double var7 = x - this.xCoord;
		double var9 = y - this.yCoord;
		double var11 = z - this.zCoord;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}

	public double lengthVector() {
		return (double)MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
	}

	public Vec3D getIntermediateWithXValue(Vec3D vector, double xValue) {
		double var4 = vector.xCoord - this.xCoord;
		double var6 = vector.yCoord - this.yCoord;
		double var8 = vector.zCoord - this.zCoord;
		if(var4 * var4 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double var10 = (xValue - this.xCoord) / var4;
			return var10 >= 0.0D && var10 <= 1.0D ? createVector(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
		}
	}

	public Vec3D getIntermediateWithYValue(Vec3D vector, double yValue) {
		double var4 = vector.xCoord - this.xCoord;
		double var6 = vector.yCoord - this.yCoord;
		double var8 = vector.zCoord - this.zCoord;
		if(var6 * var6 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double var10 = (yValue - this.yCoord) / var6;
			return var10 >= 0.0D && var10 <= 1.0D ? createVector(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
		}
	}

	public Vec3D getIntermediateWithZValue(Vec3D vector, double zValue) {
		double var4 = vector.xCoord - this.xCoord;
		double var6 = vector.yCoord - this.yCoord;
		double var8 = vector.zCoord - this.zCoord;
		if(var8 * var8 < 1.0000000116860974E-7D) {
			return null;
		} else {
			double var10 = (zValue - this.zCoord) / var8;
			return var10 >= 0.0D && var10 <= 1.0D ? createVector(this.xCoord + var4 * var10, this.yCoord + var6 * var10, this.zCoord + var8 * var10) : null;
		}
	}

	public String toString() {
		return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
	}

	public void rotateAroundX(float x) {
		float var2 = MathHelper.cos(x);
		float var3 = MathHelper.sin(x);
		double var4 = this.xCoord;
		double var6 = this.yCoord * (double)var2 + this.zCoord * (double)var3;
		double var8 = this.zCoord * (double)var2 - this.yCoord * (double)var3;
		this.xCoord = var4;
		this.yCoord = var6;
		this.zCoord = var8;
	}

	public void rotateAroundY(float y) {
		float var2 = MathHelper.cos(y);
		float var3 = MathHelper.sin(y);
		double var4 = this.xCoord * (double)var2 + this.zCoord * (double)var3;
		double var6 = this.yCoord;
		double var8 = this.zCoord * (double)var2 - this.xCoord * (double)var3;
		this.xCoord = var4;
		this.yCoord = var6;
		this.zCoord = var8;
	}
}
