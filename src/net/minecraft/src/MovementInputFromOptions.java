package net.minecraft.src;

public class MovementInputFromOptions extends MovementInput {
	private boolean[] movementKeyStates;
	private GameSettings gameSettings;

	public MovementInputFromOptions(GameSettings gameSettings) {
		InputHandler.CaptureGamepad();
		this.movementKeyStates = new boolean[10];
		this.gameSettings = gameSettings;
	}

	public void checkKeyForMovementInput(int key, boolean state) {
		byte var3 = -1;
		if(key == this.gameSettings.keyBindForward.keyCode) {
			var3 = 0;
		}

		if(key == this.gameSettings.keyBindBack.keyCode) {
			var3 = 1;
		}

		if(key == this.gameSettings.keyBindLeft.keyCode) {
			var3 = 2;
		}

		if(key == this.gameSettings.keyBindRight.keyCode) {
			var3 = 3;
		}

		if(key == this.gameSettings.keyBindJump.keyCode) {
			var3 = 4;
		}

		if(var3 >= 0) {
			this.movementKeyStates[var3] = state;
		}

	}

	public void resetKeyState() {
		for(int var1 = 0; var1 < 10; ++var1) {
			this.movementKeyStates[var1] = false;
		}

	}

	public void updatePlayerMoveState(EntityPlayer var1) {
		this.moveStrafe = 0.0F;
		this.moveForward = 0.0F;
		this.jump = this.movementKeyStates[4];
		if(InputHandler.gamepads != null) {
			for(int var2 = 0; var2 != InputHandler.gamepads.length; ++var2) {
				if(InputHandler.gamepads[var2] != null && (InputHandler.gamepads[var2].getXAxisValue() != -1.0F || InputHandler.gamepads[var2].getYAxisValue() != -1.0F)) {
					this.moveStrafe = -InputHandler.gamepads[var2].getXAxisValue();
					if((double)this.moveStrafe > -0.15D && (double)this.moveStrafe < 0.15D) {
						this.moveStrafe = 0.0F;
					}

					this.moveForward = -InputHandler.gamepads[var2].getYAxisValue();
					if((double)this.moveForward > -0.15D && (double)this.moveForward < 0.15D) {
						this.moveForward = 0.0F;
					}

					if(this.moveStrafe != 0.0F || this.moveForward != 0.0F) {
						return;
					}
				}
			}
		}

		if(this.movementKeyStates[0]) {
			++this.moveForward;
		}

		if(this.movementKeyStates[1]) {
			--this.moveForward;
		}

		if(this.movementKeyStates[2]) {
			++this.moveStrafe;
		}

		if(this.movementKeyStates[3]) {
			--this.moveStrafe;
		}

	}
}
