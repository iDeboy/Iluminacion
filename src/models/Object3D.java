package models;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public abstract class Object3D {

	protected String name;

	protected Float x;
	protected Float y;
	protected Float z;
	protected Float h;

	public Object3D(String name, float x, float y, float z, float h) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.h = h;
	}

	public Object3D(String name, float x, float y, float z) {
		this(name, x, y, z, 1f);
	}

	public String getName() {
		return name;
	}

	public Float getX() {
		return x;
	}

	public Float getY() {
		return y;
	}

	public Float getZ() {
		return z;
	}

	public void addX(float amount) {
		this.x += amount;
	}

	public void addY(float amount) {
		this.y += amount;
	}

	public void addZ(float amount) {
		this.z += amount;
	}

	public void add(float amountX, float amountY, float amountZ) {
		addX(x);
		addY(y);
		addZ(z);
	}

	public void translateX(float x) {
		this.x = x;
	}

	public void translateY(float y) {
		this.y = y;
	}

	public void translateZ(float z) {
		this.z = z;
	}

	public void translate(float x, float y, float z) {
		translateX(x);
		translateY(y);
		translateZ(z);
	}

	public float[] getPosition() {
		return new float[]{x, y, z};
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}
