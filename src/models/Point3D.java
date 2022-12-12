package models;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class Point3D extends Object3D {

	public Point3D(String name, float x, float y, float z, float h) {
		super(name, x, y, z, h);
	}

	public Point3D(String name, float x, float y, float z) {
		super(name, x, y, z);
	}

	@Override
	public String toString() {
		return name + super.toString();
	}

}
