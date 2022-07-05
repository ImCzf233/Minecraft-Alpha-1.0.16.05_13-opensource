package net.minecraft.src;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;

public class MinecraftAppletImpl extends Minecraft {
	final MinecraftApplet mainFrame;

	public MinecraftAppletImpl(MinecraftApplet frame, Component component, Canvas canvas, MinecraftApplet mcApplet, int width, int height, boolean fullscreen) {
		super(component, canvas, mcApplet, width, height, fullscreen);
		this.mainFrame = frame;
	}

	public void displayUnexpectedThrowable(UnexpectedThrowable var1) {
		this.mainFrame.removeAll();
		this.mainFrame.setLayout(new BorderLayout());
		this.mainFrame.add(new PanelCrashReport(var1), "Center");
		this.mainFrame.validate();
	}
}
