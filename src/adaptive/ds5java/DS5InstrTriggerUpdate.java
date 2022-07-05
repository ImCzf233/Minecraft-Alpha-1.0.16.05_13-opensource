package adaptive.ds5java;

public class DS5InstrTriggerUpdate implements DS5Instruction {
	public int controllerIndex;
	public DS5InstrTriggerUpdate.TriggerIndex triggerIndex;
	public DS5InstrTriggerUpdate.TriggerMode triggerMode;
	public int[] triggerModeVal;

	public DS5InstrTriggerUpdate(int var1, DS5InstrTriggerUpdate.TriggerIndex var2, DS5InstrTriggerUpdate.TriggerMode var3, int... var4) {
		this.controllerIndex = var1;
		this.triggerIndex = var2;
		this.triggerMode = var3;
		this.triggerModeVal = var4;
	}

	public String GetJSON() {
		String var1 = "{\"type\":1,\"parameters\":[" + this.controllerIndex + "," + this.triggerIndex.index + "," + this.triggerMode.index + ",";

		for(int var2 = 0; var2 != this.triggerModeVal.length; ++var2) {
			var1 = var1 + this.triggerModeVal[var2] + (var2 != this.triggerModeVal.length - 1 ? "," : "");
		}

		var1 = var1 + "]}";
		return var1;
	}

	public static enum CustomTriggerValueMode {
		OFF(0),
		Rigid(1),
		RigidA(2),
		RigidB(3),
		RigidAB(4),
		Pulse(5),
		PulseA(6),
		PulseB(7),
		PulseAB(8),
		VibrateResistance(9),
		VibrateResistanceA(10),
		VibrateResistanceB(11),
		VibrateResistanceAB(12),
		VibratePulse(13),
		VibratePulseA(14),
		VibratePulsB(15),
		VibratePulseAB(16);

		int index;

		private CustomTriggerValueMode(int var3) {
			this.index = var3;
		}
	}

	public static enum TriggerMode {
		Normal(0),
		GameCube(1),
		VerySoft(2),
		Soft(3),
		Hard(4),
		VeryHard(5),
		Hardest(6),
		Rigid(7),
		VibrateTrigger(8),
		Choppy(9),
		Medium(10),
		VibrateTriggerPulse(11),
		CustomTriggerValue(12),
		Resistance(13),
		Bow(14),
		Galloping(15),
		SemiAutomaticGun(16),
		AutomaticGun(17),
		Machine(18);

		int index;

		private TriggerMode(int var3) {
			this.index = var3;
		}
	}

	public static enum TriggerIndex {
		Invalid(0),
		Left(1),
		Right(2);

		int index;

		private TriggerIndex(int var3) {
			this.index = var3;
		}
	}
}
