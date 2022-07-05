package net.minecraft.src;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

class ThreadDownloadImage extends Thread {
	private ThreadDownloadImageData paramby2;
	private String paramString2;
	private ImageBuffer paramgr2;

	ThreadDownloadImage(ThreadDownloadImageData downloadImageData, String location, ImageBuffer imageBuffer) {
		this.paramby2 = downloadImageData;
		this.paramString2 = location;
		this.paramgr2 = imageBuffer;
	}

	public void run() {
		if(this.paramString2.startsWith("file:///")) {
			try {
				if(this.paramgr2 == null) {
					this.paramby2.image = ImageIO.read(new File(this.paramString2.substring(8)));
				} else {
					this.paramby2.image = this.paramgr2.parseUserSkin(ImageIO.read(new File(this.paramString2.substring(8))));
				}

				System.out.println("Loaded from local file");
			} catch (Exception var7) {
				var7.printStackTrace();
			}

		} else {
			HttpURLConnection var1 = null;

			try {
				URL var2 = new URL(this.paramString2);
				var1 = (HttpURLConnection)var2.openConnection();
				var1.setDoInput(true);
				var1.setDoOutput(false);
				var1.connect();
				if(var1.getResponseCode() != 404) {
					if(this.paramgr2 == null) {
						this.paramby2.image = ImageIO.read(var1.getInputStream());
					} else {
						this.paramby2.image = this.paramgr2.parseUserSkin(ImageIO.read(var1.getInputStream()));
					}

					return;
				}
			} catch (Exception var8) {
				var8.printStackTrace();
				return;
			} finally {
				var1.disconnect();
			}

		}
	}
}
