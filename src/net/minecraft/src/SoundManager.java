package net.minecraft.src;

import java.io.File;
import java.util.Random;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
	private SoundSystem sndSystem;
	private SoundPool soundPoolSounds = new SoundPool();
	private SoundPool soundPoolStreaming = new SoundPool();
	private SoundPool soundPoolMusic = new SoundPool();
	private int playedSoundsCount = 0;
	private GameSettings options;
	private boolean loaded = false;
	private Random rand = new Random();
	private int ticksBeforeMusic = this.rand.nextInt(12000);

	public void loadSoundSettings(GameSettings settings) {
		this.soundPoolStreaming.isGetRandomSound = false;
		this.options = settings;
		if(!this.loaded && (settings == null || settings.b || settings.a)) {
			this.tryToSetLibraryAndCodecs();
		}

	}

	private void tryToSetLibraryAndCodecs() {
		try {
			boolean var1 = this.options.b;
			boolean var2 = this.options.a;
			this.options.b = false;
			this.options.a = false;
			this.options.saveOptions();
			SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
			SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
			SoundSystemConfig.setCodec("mus", CodecMus.class);
			SoundSystemConfig.setCodec("wav", CodecWav.class);
			this.sndSystem = new SoundSystem();
			this.options.b = var1;
			this.options.a = var2;
			this.options.saveOptions();
		} catch (Throwable var3) {
			var3.printStackTrace();
			System.err.println("error linking with the LibraryJavaSound plug-in");
		}

		this.loaded = true;
	}

	public void onSoundOptionsChanged() {
		if(!this.loaded && (this.options.b || this.options.a)) {
			this.tryToSetLibraryAndCodecs();
		}

		if(!this.options.a) {
			this.sndSystem.stop("BgMusic");
		}

	}

	public void closeMinecraft() {
		if(this.loaded) {
			this.sndSystem.cleanup();
		}

	}

	public void addSound(String name, File file) {
		this.soundPoolSounds.addSound(name, file);
	}

	public void addStreaming(String name, File file) {
		this.soundPoolStreaming.addSound(name, file);
	}

	public void addMusic(String name, File file) {
		this.soundPoolMusic.addSound(name, file);
	}

	public void playRandomMusicIfReady() {
		if(this.loaded && this.options.a) {
			if(!this.sndSystem.playing("BgMusic") && !this.sndSystem.playing("streaming")) {
				if(this.ticksBeforeMusic > 0) {
					--this.ticksBeforeMusic;
					return;
				}

				SoundPoolEntry var1 = this.soundPoolMusic.getRandomSound();
				if(var1 != null) {
					this.ticksBeforeMusic = this.rand.nextInt(24000) + 24000;
					this.sndSystem.backgroundMusic("BgMusic", var1.soundUrl, var1.soundName, false);
					this.sndSystem.play("BgMusic");
				}
			}

		}
	}

	public void setListener(EntityLiving listener, float partialTick) {
		if(this.loaded && this.options.b) {
			if(listener != null) {
				float var3 = listener.prevRotationYaw + (listener.rotationYaw - listener.prevRotationYaw) * partialTick;
				double var4 = listener.prevPosX + (listener.posX - listener.prevPosX) * (double)partialTick;
				double var6 = listener.prevPosY + (listener.posY - listener.prevPosY) * (double)partialTick;
				double var8 = listener.prevPosZ + (listener.posZ - listener.prevPosZ) * (double)partialTick;
				float var10 = MathHelper.cos(-var3 * 0.017453292F - (float)Math.PI);
				float var11 = MathHelper.sin(-var3 * 0.017453292F - (float)Math.PI);
				float var12 = -var11;
				float var13 = 0.0F;
				float var14 = -var10;
				float var15 = 0.0F;
				float var16 = 1.0F;
				float var17 = 0.0F;
				this.sndSystem.setListenerPosition((float)var4, (float)var6, (float)var8);
				this.sndSystem.setListenerOrientation(var12, var13, var14, var15, var16, var17);
			}
		}
	}

	public void playStreaming(String sound, float posX, float posY, float posZ, float volume, float pitch) {
		if(this.loaded && this.options.b) {
			String var7 = "streaming";
			if(this.sndSystem.playing("streaming")) {
				this.sndSystem.stop("streaming");
			}

			if(sound != null) {
				SoundPoolEntry var8 = this.soundPoolStreaming.getRandomSoundFromSoundPool(sound);
				if(var8 != null && volume > 0.0F) {
					if(this.sndSystem.playing("BgMusic")) {
						this.sndSystem.stop("BgMusic");
					}

					float var9 = 16.0F;
					this.sndSystem.newStreamingSource(true, var7, var8.soundUrl, var8.soundName, false, posX, posY, posZ, 2, var9 * 4.0F);
					this.sndSystem.setVolume(var7, 0.5F);
					this.sndSystem.play(var7);
				}

			}
		}
	}

	public void playSound(String sound, float posX, float posY, float posZ, float volume, float pitch) {
		if(this.loaded && this.options.b) {
			SoundPoolEntry var7 = this.soundPoolSounds.getRandomSoundFromSoundPool(sound);
			if(var7 != null && volume > 0.0F) {
				this.playedSoundsCount = (this.playedSoundsCount + 1) % 256;
				String var8 = "sound_" + this.playedSoundsCount;
				float var9 = 16.0F;
				if(volume > 1.0F) {
					var9 *= volume;
				}

				this.sndSystem.newSource(volume > 1.0F, var8, var7.soundUrl, var7.soundName, false, posX, posY, posZ, 2, var9);
				this.sndSystem.setPitch(var8, pitch);
				if(volume > 1.0F) {
					volume = 1.0F;
				}

				this.sndSystem.setVolume(var8, volume);
				this.sndSystem.play(var8);
			}

		}
	}

	public void playSoundFX(String sound, float volume, float pitch) {
		if(this.loaded && this.options.b) {
			SoundPoolEntry var4 = this.soundPoolSounds.getRandomSoundFromSoundPool(sound);
			if(var4 != null) {
				this.playedSoundsCount = (this.playedSoundsCount + 1) % 256;
				String var5 = "sound_" + this.playedSoundsCount;
				this.sndSystem.newSource(false, var5, var4.soundUrl, var4.soundName, false, 0.0F, 0.0F, 0.0F, 0, 0.0F);
				if(volume > 1.0F) {
					volume = 1.0F;
				}

				volume *= 0.25F;
				this.sndSystem.setPitch(var5, pitch);
				this.sndSystem.setVolume(var5, volume);
				this.sndSystem.play(var5);
			}

		}
	}
}
