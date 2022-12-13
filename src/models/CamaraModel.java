package models;

import static com.jogamp.opengl.GL.GL_FRONT;
import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_AMBIENT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_DIFFUSE;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_EMISSION;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SHININESS;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SPECULAR;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public final class CamaraModel extends Object3D implements IDrawable {

	private Float fovy;
	private Float aspect;
	private Float zNear;
	private Float zFar;

	private final Point3D pointing;
	private final Point3D looking;

	public CamaraModel(float x, float y, float z, float h,
					float fovy, float aspect, float near, float far,
					float xPoint, float yPoint, float zPoint,
					float xLook, float yLook, float zLook) {

		super("Camara", x, y, z, h);
		this.fovy = fovy;
		this.aspect = aspect;
		this.zNear = near;
		this.zFar = far;
		this.pointing = new Point3D("PointAt", xPoint, yPoint, zPoint);
		this.looking = new Point3D("LookAt", xLook, yLook, zLook);
	}

	public CamaraModel(float x, float y, float z,
					float fovy, float aspect, float near, float far,
					float xPoint, float yPoint, float zPoint,
					float xLook, float yLook, float zLook) {

		this(x, y, z, 1f, fovy, aspect, near, far, xPoint, yPoint, zPoint, xLook, yLook, zLook);
	}

	// Getters
	public Float getFov() {
		return fovy;
	}

	public Float getAspect() {
		return aspect;
	}

	public Float getNear() {
		return zNear;
	}

	public Float getFar() {
		return zFar;
	}

	public Float getPointAtX() {
		return this.pointing.x;
	}

	public Float getPointAtY() {
		return this.pointing.y;
	}

	public Float getPointAtZ() {
		return this.pointing.z;
	}

	public Float getLookAtX() {
		return this.looking.x;
	}

	public Float getLookAtY() {
		return this.looking.y;
	}

	public Float getLookAtZ() {
		return this.looking.z;
	}

	// Setters
	public void setFov(float fovy) {
		this.fovy = fovy;
	}

	public void setAspect(float aspect) {
		this.aspect = aspect;
	}

	public void setNear(float zNear) {
		this.zNear = zNear;
	}

	public void setFar(float zFar) {
		this.zFar = zFar;
	}

	// Adders
	public void addFov(float amount) {
		this.fovy += amount;
	}

	public void addAspect(float amount) {
		this.aspect += amount;
	}

	public void addNear(float amount) {
		this.zNear += amount;
	}

	public void addFar(float amount) {
		this.zFar += amount;
	}

	public void addXPoint(float amount) {
		this.pointing.addX(amount);
	}

	public void addYPoint(float amount) {
		this.pointing.addY(amount);
	}

	public void addZPoint(float amount) {
		this.pointing.addZ(amount);
	}

	public void addPointing(float amountX, float amountY, float amountZ) {
		this.pointing.add(amountX, amountY, amountZ);
	}

	public void addXLook(float amount) {
		this.looking.addX(amount);
	}

	public void addYLook(float amount) {
		this.looking.addY(amount);
	}

	public void addZLook(float amount) {
		this.looking.addZ(amount);
	}

	public void addLooking(float amountX, float amountY, float amountZ) {
		this.looking.add(amountX, amountY, amountZ);
	}

	// Translaters
	public void translateXPointAt(float x) {
		this.pointing.translateX(x);
	}

	public void translateYPointAt(float y) {
		this.pointing.translateY(y);
	}

	public void translateZPointAt(float z) {
		this.pointing.translateZ(z);
	}

	public void translatePointingAt(float x, float y, float z) {
		this.pointing.translate(x, y, z);
	}

	public void translateXLookAt(float x) {
		this.looking.translateX(x);
	}

	public void translateYLookAt(float y) {
		this.looking.translateY(y);
	}

	public void translateZLookAt(float z) {
		this.looking.translateZ(z);
	}

	public void translateLookingAt(float x, float y, float z) {
		this.looking.translate(x, y, z);
	}

	public float[] getPointingPosition() {
		return this.pointing.getPosition();
	}

	public float[] getLookingPosition() {
		return this.looking.getPosition();
	}

	@Override
	public void draw(GL2 gl, GLUT glut) {

		gl.glPushMatrix();
		{
			gl.glMaterialfv(GL_FRONT, GL_AMBIENT, ColorConverter.convertToFME(Color.BLACK), 0);
			gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, ColorConverter.convertToFME(Color.BLACK), 0);
			gl.glMaterialfv(GL_FRONT, GL_SPECULAR, ColorConverter.convertToFME(Color.BLACK), 0);
			gl.glMateriali(GL_FRONT, GL_SHININESS, 0);
			gl.glMaterialfv(GL_FRONT, GL_EMISSION, ColorConverter.convertToFME(Color.BLACK), 0);

			gl.glTranslatef(pointing.x, pointing.y, pointing.z);
			gl.glLineWidth(5);
			glut.glutSolidSphere(0.09f, 20, 20);
		}
		gl.glPopMatrix();

	}
}
