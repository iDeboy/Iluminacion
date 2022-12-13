package models;

import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public final class GlLight {

	private final int value;
	private boolean used;

	private GlLight(int light) {
		this.value = light;
		this.used = false;
	}

	public static GlLight next() {

		for (var l : values()) {
			if (!l.used) {
				return l;
			}
		}

		return None;
	}

	public static GlLight[] values() {

		return new GlLight[]{
			Light0,
			Light1,
			Light2,
			Light3,
			Light4,
			Light5,
			Light6,
			Light7};
	}

	public static final GlLight fromValue(int light) {

		for (var lgt : values()) {
			if (lgt.value == light) {
				return lgt;
			}
		}

		return None;
	}

	public int getValue() {
		return value;
	}

	public boolean isUsed() {
		return this.used;
	}

	public void enable() {
		if (value == 0) {
			return;
		}
		this.used = true;
	}

	public void disable() {
		if (value == 0) {
			return;
		}
		this.used = false;
	}

	public static final GlLight None = new GlLight(0);
	public static final GlLight Light0 = new GlLight(GL_LIGHT0);
	public static final GlLight Light1 = new GlLight(GL_LIGHT1);
	public static final GlLight Light2 = new GlLight(GL_LIGHT2);
	public static final GlLight Light3 = new GlLight(GL_LIGHT3);
	public static final GlLight Light4 = new GlLight(GL_LIGHT4);
	public static final GlLight Light5 = new GlLight(GL_LIGHT5);
	public static final GlLight Light6 = new GlLight(GL_LIGHT6);
	public static final GlLight Light7 = new GlLight(GL_LIGHT7);

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 83 * hash + this.value;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final GlLight other = (GlLight) obj;
		return this.value == other.value;
	}

	@Override
	public String toString() {
		return "value: " + value + ", used: " + used;
	}

}
