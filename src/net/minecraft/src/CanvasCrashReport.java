package net.minecraft.src;

import java.awt.Canvas;
import java.awt.Dimension;

class CanvasCrashReport extends Canvas {
	public CanvasCrashReport(int size) {
		this.setPreferredSize(new Dimension(size, size));
		this.setMinimumSize(new Dimension(size, size));
	}
}
