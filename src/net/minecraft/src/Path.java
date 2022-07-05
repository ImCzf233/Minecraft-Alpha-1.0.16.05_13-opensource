package net.minecraft.src;

public class Path {
	private PathPoint[] pathPoints = new PathPoint[1024];
	private int count = 0;

	public PathPoint addPoint(PathPoint pathPoint) {
		if(pathPoint.index >= 0) {
			throw new IllegalStateException("OW KNOWS!");
		} else {
			if(this.count == this.pathPoints.length) {
				PathPoint[] var2 = new PathPoint[this.count << 1];
				System.arraycopy(this.pathPoints, 0, var2, 0, this.count);
				this.pathPoints = var2;
			}

			this.pathPoints[this.count] = pathPoint;
			pathPoint.index = this.count;
			this.sortBack(this.count++);
			return pathPoint;
		}
	}

	public void clearPath() {
		this.count = 0;
	}

	public PathPoint dequeue() {
		PathPoint var1 = this.pathPoints[0];
		this.pathPoints[0] = this.pathPoints[--this.count];
		this.pathPoints[this.count] = null;
		if(this.count > 0) {
			this.sortForward(0);
		}

		var1.index = -1;
		return var1;
	}

	public void changeDistance(PathPoint pathPoint, float distance) {
		float var3 = pathPoint.distanceToTarget;
		pathPoint.distanceToTarget = distance;
		if(distance < var3) {
			this.sortBack(pathPoint.index);
		} else {
			this.sortForward(pathPoint.index);
		}

	}

	private void sortBack(int index) {
		PathPoint var2 = this.pathPoints[index];

		int var4;
		for(float var3 = var2.distanceToTarget; index > 0; index = var4) {
			var4 = index - 1 >> 1;
			PathPoint var5 = this.pathPoints[var4];
			if(var3 >= var5.distanceToTarget) {
				break;
			}

			this.pathPoints[index] = var5;
			var5.index = index;
		}

		this.pathPoints[index] = var2;
		var2.index = index;
	}

	private void sortForward(int index) {
		PathPoint var2 = this.pathPoints[index];
		float var3 = var2.distanceToTarget;

		while(true) {
			int var4 = 1 + (index << 1);
			int var5 = var4 + 1;
			if(var4 >= this.count) {
				break;
			}

			PathPoint var6 = this.pathPoints[var4];
			float var7 = var6.distanceToTarget;
			PathPoint var8;
			float var9;
			if(var5 >= this.count) {
				var8 = null;
				var9 = Float.POSITIVE_INFINITY;
			} else {
				var8 = this.pathPoints[var5];
				var9 = var8.distanceToTarget;
			}

			if(var7 < var9) {
				if(var7 >= var3) {
					break;
				}

				this.pathPoints[index] = var6;
				var6.index = index;
				index = var4;
			} else {
				if(var9 >= var3) {
					break;
				}

				this.pathPoints[index] = var8;
				var8.index = index;
				index = var5;
			}
		}

		this.pathPoints[index] = var2;
		var2.index = index;
	}

	public boolean isPathEmpty() {
		return this.count == 0;
	}
}
