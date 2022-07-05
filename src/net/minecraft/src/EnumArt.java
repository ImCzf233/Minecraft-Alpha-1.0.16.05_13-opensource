package net.minecraft.src;

public enum EnumArt {
	Kebab("Kebab", 16, 16, 0, 0),
	Aztec("Aztec", 16, 16, 16, 0),
	Alban("Alban", 16, 16, 32, 0),
	Aztec2("Aztec2", 16, 16, 48, 0),
	Bomb("Bomb", 16, 16, 64, 0),
	Plant("Plant", 16, 16, 80, 0),
	Wasteland("Wasteland", 16, 16, 96, 0),
	Pool("Pool", 32, 16, 0, 32),
	Courbet("Courbet", 32, 16, 32, 32),
	Sea("Sea", 32, 16, 64, 32),
	Sunset("Sunset", 32, 16, 96, 32),
	Wanderer("Wanderer", 16, 32, 0, 64),
	Match("Match", 32, 32, 0, 128),
	Bust("Bust", 32, 32, 32, 128),
	Stage("Stage", 32, 32, 64, 128),
	Void("Void", 32, 32, 96, 128),
	SkullAndRoses("SkullAndRoses", 32, 32, 128, 128),
	Fighters("Fighters", 64, 32, 0, 96),
	Pointer("Pointer", 64, 64, 0, 192);

	public final String title;
	public final int sizeX;
	public final int sizeY;
	public final int offsetX;
	public final int offsetY;

	private EnumArt(String title, int sizeX, int sizeY, int offsetX, int offsetY) {
		this.title = title;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
}
