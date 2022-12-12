package models;

import java.awt.Color;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public abstract class ColorConverter {

	public static float getRed(Color color) {
		return (float) color.getRed() / 255f;
	}

	public static float getGreen(Color color) {
		return (float) color.getGreen() / 255f;
	}

	public static float getBlue(Color color) {
		return (float) color.getBlue() / 255f;
	}

	public static float getAlpha(Color color) {
		return (float) color.getAlpha() / 255f;
	}

	public static float[] convertToFME(Color color) {
		return new float[]{
			getRed(color),
			getGreen(color),
			getBlue(color),
			getAlpha(color)
		};
	}

}
