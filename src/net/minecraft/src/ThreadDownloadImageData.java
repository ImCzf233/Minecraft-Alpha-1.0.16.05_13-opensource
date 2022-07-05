package net.minecraft.src;

import java.awt.image.BufferedImage;

public class ThreadDownloadImageData {
	public BufferedImage image;
	public int referenceCount = 1;
	public int textureName = -1;
	public boolean textureSetupComplete = false;

	public ThreadDownloadImageData(String location, ImageBuffer imageBuffer) {
		(new ThreadDownloadImage(this, location, imageBuffer)).start();
	}
}
