package net.minecraft.src;

public class KeyBinding {
	public String keyDescription;
	public int keyCode;

	public KeyBinding(String name, int key) {
		this.keyDescription = name;
		this.keyCode = key;
	}
}
