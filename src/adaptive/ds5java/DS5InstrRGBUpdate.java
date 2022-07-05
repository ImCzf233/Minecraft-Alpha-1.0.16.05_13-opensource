package adaptive.ds5java;

public class DS5InstrRGBUpdate implements DS5Instruction {
	public int r;
	public int g;
	public int b;
	public int controllerIndex;

	public DS5InstrRGBUpdate(int var1, int var2, int var3, int var4) {
		this.r = var2;
		this.g = var3;
		this.b = var4;
		this.controllerIndex = var1;
	}

	public String GetJSON() {
		return "{\"type\":2,\"parameters\":[" + this.controllerIndex + "," + this.r + "," + this.g + "," + this.b + "]}";
	}
}
