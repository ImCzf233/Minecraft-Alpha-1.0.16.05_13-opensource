package net.minecraft.src;

public class MovementInput {
	public float moveStrafe = 0.0F;
	public float moveForward = 0.0F;
	public boolean unused = false;
	public boolean jump = false;

	public void updatePlayerMoveState(EntityPlayer entityPlayer) {
	}

	public void resetKeyState() {
	}

	public void checkKeyForMovementInput(int key, boolean state) {
	}
}
