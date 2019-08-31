package k4unl.minecraft.colorchat.lib;

/**
 * @author Koen Beckers (K-4U)
 */
public enum Mode {
	RANDOM(1), SAVED(2), OP(3), SET(4), GROUPS(5);

	int ordinal;

	Mode(int i) {
		ordinal = i;
	}


}
