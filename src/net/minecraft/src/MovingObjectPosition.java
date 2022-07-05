package net.minecraft.src;

public class MovingObjectPosition {
	public int typeOfHit;
	public int blockX;
	public int blockY;
	public int blockZ;
	public int sideHit;
	public Vec3D hitVec;
	public Entity entityHit;

	public MovingObjectPosition(int x, int y, int z, int side, Vec3D vector) {
		this.typeOfHit = 0;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.sideHit = side;
		this.hitVec = Vec3D.createVector(vector.xCoord, vector.yCoord, vector.zCoord);
	}

	public MovingObjectPosition(Entity entity) {
		this.typeOfHit = 1;
		this.entityHit = entity;
		this.hitVec = Vec3D.createVector(entity.posX, entity.posY, entity.posZ);
	}
}
