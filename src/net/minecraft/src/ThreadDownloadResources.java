package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.minecraft.client.Minecraft;

public class ThreadDownloadResources extends Thread {
	public File resourcesFolder;
	private Minecraft mc;
	private boolean closing = false;

	public ThreadDownloadResources(File file, Minecraft minecraft) {
		this.mc = minecraft;
		this.setName("Resource unpack thread");
		this.setDaemon(true);
		this.resourcesFolder = new File(file, "resources/");
		if(!this.resourcesFolder.exists() && !this.resourcesFolder.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + this.resourcesFolder);
		}
	}

	void deleteDirectoryWalkTree(java.nio.file.Path var1) throws IOException {
		SimpleFileVisitor var2 = new SimpleFileVisitor() {
			public FileVisitResult visitFile(java.nio.file.Path var1, BasicFileAttributes var2) throws IOException {
				Files.delete(var1);
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult visitFileFailed(java.nio.file.Path var1, IOException var2) throws IOException {
				Files.delete(var1);
				return FileVisitResult.CONTINUE;
			}

			public FileVisitResult postVisitDirectory(java.nio.file.Path var1, IOException var2) throws IOException {
				if(var2 != null) {
					throw var2;
				} else {
					Files.delete(var1);
					return FileVisitResult.CONTINUE;
				}
			}
		};
		Files.walkFileTree(var1, var2);
	}

	public void UnpackReader(BufferedReader var1) throws IOException {
		String var2;
		while((var2 = var1.readLine()) != null) {
			if(var2 != "") {
				String var3 = var2.replace("worldstatic", "saves").replace("sounddata", "resources");
				InputStream var4 = this.getClass().getResourceAsStream(var2);
				File var5 = new File(System.getProperty("user.dir") + var3);
				var5.mkdirs();
				Files.copy(var4, Paths.get(System.getProperty("user.dir") + var3, new String[0]), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
				var4.close();
			}
		}

		System.out.println("Resources unpacked");
		var1.close();
	}

	public void run() {
		try {
			try {
				this.deleteDirectoryWalkTree(Paths.get(System.getProperty("user.dir") + "/resources/", new String[0]));
				this.resourcesFolder.mkdirs();
			} catch (Exception var5) {
				System.out.println("Error removing existing resources...");
			}

			SimpleDateFormat var1 = new SimpleDateFormat("HH");
			int var2 = Integer.parseInt(var1.format(Calendar.getInstance().getTime()));
			InputStream var3 = this.getClass().getResourceAsStream(var2 <= 22 && var2 >= 5 ? "/soundres.txt" : "/soundres_a.txt");
			BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));
			this.UnpackReader(var4);
			System.out.println(System.getProperty("user.dir") + "/saves/World2/");
			if(!(new File(System.getProperty("user.dir") + "/saves/World2/")).exists()) {
				var4 = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/wstatic_data.txt")));
				this.UnpackReader(var4);
			}

			this.loadResource(this.resourcesFolder, "");
		} catch (Exception var6) {
			var6.printStackTrace();
			this.loadResource(this.resourcesFolder, "");
		}

	}

	public void closeMinecraft() {
		this.loadResource(this.resourcesFolder, "");
	}

	public void b() {
		this.closing = true;
	}

	private void loadResource(File file, String path) {
		File[] var3 = file.listFiles();

		for(byte var4 = 0; var4 < var3.length; ++var4) {
			if(var3[var4].isDirectory()) {
				this.loadResource(var3[var4], path + var3[var4].getName() + "/");
			} else {
				try {
					this.mc.installResource(path + var3[var4].getName(), var3[var4]);
				} catch (Exception var6) {
					System.out.println("Failed to add " + path + var3[var4].getName());
				}
			}
		}

	}

	private void a(URL var1, String var2, long var3, int var5) {
		try {
			int var6 = var2.indexOf("/");
			String var7 = var2.substring(0, var6);
			if(!var7.equals("sound") && !var7.equals("newsound")) {
				if(var5 != 1) {
					return;
				}
			} else if(var5 != 0) {
				return;
			}

			File var8 = new File(this.resourcesFolder, var2);
			if(!var8.exists() || var8.length() != var3) {
				var8.getParentFile().mkdirs();
				String var9 = var2.replaceAll(" ", "%20");
				this.a(new URL(var1, var9), var8, var3);
				if(this.closing) {
					return;
				}
			}

			this.mc.installResource(var2, var8);
		} catch (Exception var10) {
			var10.printStackTrace();
		}

	}

	private void a(URL var1, File var2, long var3) {
	}
}
