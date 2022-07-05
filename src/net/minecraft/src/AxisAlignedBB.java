package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AxisAlignedBB {
	private static List boundingBoxes = new ArrayList();
	private static int numBoundingBoxesInUse = 0;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;

	public static AxisAlignedBB getBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public static void clearBoundingBoxPool() {
		numBoundingBoxesInUse = 0;
	}

	public static AxisAlignedBB getBoundingBoxFromPool(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		if(numBoundingBoxesInUse >= boundingBoxes.size()) {
			boundingBoxes.add(getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
		}

		return ((AxisAlignedBB)boundingBoxes.get(numBoundingBoxesInUse++)).setBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}

	private AxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	public AxisAlignedBB setBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		return this;
	}

	public AxisAlignedBB addCoord(double x, double y, double z) {
		double var7 = this.minX;
		double var9 = this.minY;
		double var11 = this.minZ;
		double var13 = this.maxX;
		double var15 = this.maxY;
		double var17 = this.maxZ;
		if(x < 0.0D) {
			var7 += x;
		}

		if(x > 0.0D) {
			var13 += x;
		}

		if(y < 0.0D) {
			var9 += y;
		}

		if(y > 0.0D) {
			var15 += y;
		}

		if(z < 0.0D) {
			var11 += z;
		}

		if(z > 0.0D) {
			var17 += z;
		}

		return getBoundingBoxFromPool(var7, var9, var11, var13, var15, var17);
	}

	public AxisAlignedBB expand(double x, double y, double z) {
		double var7 = this.minX - x;
		double var9 = this.minY - y;
		double var11 = this.minZ - z;
		double var13 = this.maxX + x;
		double var15 = this.maxY + y;
		double var17 = this.maxZ + z;
		return getBoundingBoxFromPool(var7, var9, var11, var13, var15, var17);
	}

	public AxisAlignedBB getOffsetBoundingBox(double offsetX, double offsetY, double offsetZ) {
		return getBoundingBoxFromPool(this.minX + offsetX, this.minY + offsetY, this.minZ + offsetZ, this.maxX + offsetX, this.maxY + offsetY, this.maxZ + offsetZ);
	}

	public double calculateXOffset(AxisAlignedBB aabb, double offsetX) {
		if(aabb.maxY > this.minY && aabb.minY < this.maxY) {
			if(aabb.maxZ > this.minZ && aabb.minZ < this.maxZ) {
				double var4;
				if(offsetX > 0.0D && aabb.maxX <= this.minX) {
					var4 = this.minX - aabb.maxX;
					if(var4 < offsetX) {
						offsetX = var4;
					}
				}

				if(offsetX < 0.0D && aabb.minX >= this.maxX) {
					var4 = this.maxX - aabb.minX;
					if(var4 > offsetX) {
						offsetX = var4;
					}
				}

				return offsetX;
			} else {
				return offsetX;
			}
		} else {
			return offsetX;
		}
	}

	public double calculateYOffset(AxisAlignedBB aabb, double offsetY) {
		if(aabb.maxX > this.minX && aabb.minX < this.maxX) {
			if(aabb.maxZ > this.minZ && aabb.minZ < this.maxZ) {
				double var4;
				if(offsetY > 0.0D && aabb.maxY <= this.minY) {
					var4 = this.minY - aabb.maxY;
					if(var4 < offsetY) {
						offsetY = var4;
					}
				}

				if(offsetY < 0.0D && aabb.minY >= this.maxY) {
					var4 = this.maxY - aabb.minY;
					if(var4 > offsetY) {
						offsetY = var4;
					}
				}

				return offsetY;
			} else {
				return offsetY;
			}
		} else {
			return offsetY;
		}
	}

	public double calculateZOffset(AxisAlignedBB aabb, double offsetZ) {
		if(aabb.maxX > this.minX && aabb.minX < this.maxX) {
			if(aabb.maxY > this.minY && aabb.minY < this.maxY) {
				double var4;
				if(offsetZ > 0.0D && aabb.maxZ <= this.minZ) {
					var4 = this.minZ - aabb.maxZ;
					if(var4 < offsetZ) {
						offsetZ = var4;
					}
				}

				if(offsetZ < 0.0D && aabb.minZ >= this.maxZ) {
					var4 = this.maxZ - aabb.minZ;
					if(var4 > offsetZ) {
						offsetZ = var4;
					}
				}

				return offsetZ;
			} else {
				return offsetZ;
			}
		} else {
			return offsetZ;
		}
	}

	public boolean intersectsWith(AxisAlignedBB aabb) {
		return aabb.maxX > this.minX && aabb.minX < this.maxX ? (aabb.maxY > this.minY && aabb.minY < this.maxY ? aabb.maxZ > this.minZ && aabb.minZ < this.maxZ : false) : false;
	}

	public AxisAlignedBB offset(double offsetX, double offsetY, double offsetZ) {
		this.minX += offsetX;
		this.minY += offsetY;
		this.minZ += offsetZ;
		this.maxX += offsetX;
		this.maxY += offsetY;
		this.maxZ += offsetZ;
		return this;
	}

	public double getAverageEdgeLength() {
		double var1 = this.maxX - this.minX;
		double var3 = this.maxY - this.minY;
		double var5 = this.maxZ - this.minZ;
		return (var1 + var3 + var5) / 3.0D;
	}

	public AxisAlignedBB copy() {
		return getBoundingBoxFromPool(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
	}

	public MovingObjectPosition calculateIntercept(Vec3D vector1, Vec3D vector2) {
		Vec3D var3 = vector1.getIntermediateWithXValue(vector2, this.minX);
		Vec3D var4 = vector1.getIntermediateWithXValue(vector2, this.maxX);
		Vec3D var5 = vector1.getIntermediateWithYValue(vector2, this.minY);
		Vec3D var6 = vector1.getIntermediateWithYValue(vector2, this.maxY);
		Vec3D var7 = vector1.getIntermediateWithZValue(vector2, this.minZ);
		Vec3D var8 = vector1.getIntermediateWithZValue(vector2, this.maxZ);
		if(!this.isVecInYZ(var3)) {
			var3 = null;
		}

		if(!this.isVecInYZ(var4)) {
			var4 = null;
		}

		if(!this.isVecInXZ(var5)) {
			var5 = null;
		}

		if(!this.isVecInXZ(var6)) {
			var6 = null;
		}

		if(!this.isVecInXY(var7)) {
			var7 = null;
		}

		if(!this.isVecInXY(var8)) {
			var8 = null;
		}

		Vec3D var9 = null;
		if(var3 != null && (var9 == null || vector1.squareDistanceTo(var3) < vector1.squareDistanceTo(var9))) {
			var9 = var3;
		}

		if(var4 != null && (var9 == null || vector1.squareDistanceTo(var4) < vector1.squareDistanceTo(var9))) {
			var9 = var4;
		}

		if(var5 != null && (var9 == null || vector1.squareDistanceTo(var5) < vector1.squareDistanceTo(var9))) {
			var9 = var5;
		}

		if(var6 != null && (var9 == null || vector1.squareDistanceTo(var6) < vector1.squareDistanceTo(var9))) {
			var9 = var6;
		}

		if(var7 != null && (var9 == null || vector1.squareDistanceTo(var7) < vector1.squareDistanceTo(var9))) {
			var9 = var7;
		}

		if(var8 != null && (var9 == null || vector1.squareDistanceTo(var8) < vector1.squareDistanceTo(var9))) {
			var9 = var8;
		}

		if(var9 == null) {
			return null;
		} else {
			byte var10 = -1;
			if(var9 == var3) {
				var10 = 4;
			}

			if(var9 == var4) {
				var10 = 5;
			}

			if(var9 == var5) {
				var10 = 0;
			}

			if(var9 == var6) {
				var10 = 1;
			}

			if(var9 == var7) {
				var10 = 2;
			}

			if(var9 == var8) {
				var10 = 3;
			}

			return new MovingObjectPosition(0, 0, 0, var10, var9);
		}
	}

	private boolean isVecInYZ(Vec3D vector) {
		return vector == null ? false : vector.yCoord >= this.minY && vector.yCoord <= this.maxY && vector.zCoord >= this.minZ && vector.zCoord <= this.maxZ;
	}

	private boolean isVecInXZ(Vec3D vector) {
		return vector == null ? false : vector.xCoord >= this.minX && vector.xCoord <= this.maxX && vector.zCoord >= this.minZ && vector.zCoord <= this.maxZ;
	}

	private boolean isVecInXY(Vec3D vector) {
		return vector == null ? false : vector.xCoord >= this.minX && vector.xCoord <= this.maxX && vector.yCoord >= this.minY && vector.yCoord <= this.maxY;
	}

	public void setBB(AxisAlignedBB aabb) {
		this.minX = aabb.minX;
		this.minY = aabb.minY;
		this.minZ = aabb.minZ;
		this.maxX = aabb.maxX;
		this.maxY = aabb.maxY;
		this.maxZ = aabb.maxZ;
	}
}
