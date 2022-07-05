package net.minecraft.src;

import java.awt.Component;
import java.nio.IntBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

public class MouseHelper {
	private Component windowComponent;
	private Cursor cursor;
	public int deltaX;
	public int deltaY;
	private int mouseInt = 10;
	private long lastUpdate = -1L;

	public MouseHelper(Component component) {
		this.windowComponent = component;
		IntBuffer var2 = GLAllocation.createDirectIntBuffer(1);
		var2.put(0);
		var2.flip();
		IntBuffer var3 = GLAllocation.createDirectIntBuffer(1024);

		try {
			this.cursor = new Cursor(32, 32, 16, 16, 1, var3, var2);
		} catch (LWJGLException var5) {
			var5.printStackTrace();
		}

	}

	public void grabMouseCursor() {
		Mouse.setGrabbed(true);
		this.deltaX = 0;
		this.deltaY = 0;
	}

	public void ungrabMouseCursor() {
		Mouse.setCursorPosition(this.windowComponent.getWidth() / 2, this.windowComponent.getHeight() / 2);
		Mouse.setGrabbed(false);
	}

	public void mouseXYChange() {
		if(this.lastUpdate == -1L) {
			this.lastUpdate = System.currentTimeMillis();
		}

		float var1 = (float)(System.currentTimeMillis() - this.lastUpdate) / 1000.0F;
		this.lastUpdate = System.currentTimeMillis();
		if(InputHandler.gamepads != null) {
			for(int var2 = 0; var2 != InputHandler.gamepads.length; ++var2) {
				if(InputHandler.gamepads[var2] != null && (InputHandler.gamepads[var2].getRXAxisValue() != 0.0F || InputHandler.gamepads[var2].getRYAxisValue() != 0.0F) && (InputHandler.gamepads[var2].getRXAxisValue() != -1.0F || InputHandler.gamepads[var2].getRYAxisValue() != -1.0F)) {
					this.deltaX = (int)(InputHandler.gamepads[var2].getRXAxisValue() * 500.0F * (float)InputHandler.lookSens * var1);
					if(this.deltaX > -1 && this.deltaX < 1) {
						this.deltaX = 0;
					}

					this.deltaY = (int)(-InputHandler.gamepads[var2].getRYAxisValue() * 250.0F * (float)InputHandler.lookSens * var1);
					if(this.deltaY > -1 && this.deltaY < 1) {
						this.deltaY = 0;
					}

					if(this.deltaX != 0 || this.deltaY != 0) {
						return;
					}
				}
			}
		}

		this.deltaX = Mouse.getDX();
		this.deltaY = Mouse.getDY();
	}
}
