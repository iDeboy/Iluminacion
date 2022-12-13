package models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public abstract class FigureModel extends Object3D implements IDrawable {

	private static final ArrayList<Integer> ids = new ArrayList<>();

	private int id;

// Light properties
	protected boolean isLight;
	protected GlLight light;

	// Figure properties
	protected boolean selected;
	private int auxShininess;
	private int shininessLight;

	protected Color strokeColor;
	protected Color ambientColor;
	protected Color difuseColor;
	protected Color specularColor;
	protected int shininess;
	protected Color emissionColor;

	public FigureModel() {
		this("Figure", 0, 0, 0, 1, Color.RED, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, 4, Color.BLACK);
	}

	public FigureModel(String name, float x, float y, float z, float h,
					Color strokeColor, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super(name, x, y, z, h);

		this.id = generateId();

		this.selected = false;
		this.isLight = false;
		this.light = GlLight.None;

		this.strokeColor = strokeColor;
		this.ambientColor = ambientColor;
		this.difuseColor = difuseColor;
		this.specularColor = specularColor;
		this.shininess = shininess;
		this.emissionColor = emissionColor;

		this.shininessLight = this.shininess + 100;
		this.auxShininess = this.shininess;
	}

	public FigureModel(String name, float x, float y, float z, float h,
					Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		this(name, x, y, z, h, Color.RED, ambientColor, difuseColor, specularColor, shininess, emissionColor);

	}

	public FigureModel(String name, float x, float y, float z,
					Color strokeColor, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super(name, x, y, z);

		this.id = generateId();

		this.selected = false;
		this.isLight = false;
		this.light = GlLight.None;

		this.strokeColor = strokeColor;
		this.ambientColor = ambientColor;
		this.difuseColor = difuseColor;
		this.specularColor = specularColor;
		this.shininess = shininess;
		this.emissionColor = emissionColor;

		this.shininessLight = this.shininess + 100;
		this.auxShininess = this.shininess;
	}

	public FigureModel(String name, float x, float y, float z,
					Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		this(name, x, y, z, Color.RED, ambientColor, difuseColor, specularColor, shininess, emissionColor);

	}

	public boolean isLight() {
		return this.isLight;
	}

	@Override
	public void draw(GL2 gl, GLUT glut) {

		// Diseable unused lights & enable used lights
		var lights = GlLight.values();

		for (var l : lights) {

			if (!l.isUsed()) {
				gl.glDisable(l.getValue());
			} else {
				gl.glEnable(l.getValue());
			}

		}

		// Configure figure light if has any
		if (this.isLight()) {

			this.shininess = this.shininessLight;

			gl.glLightfv(light.getValue(), GL2.GL_DIFFUSE, ColorConverter.convertToFME(difuseColor), 0);
			gl.glLightfv(light.getValue(), GL2.GL_SPECULAR, ColorConverter.convertToFME(specularColor), 0);
			gl.glLightfv(light.getValue(), GL2.GL_POSITION, getPosition(), 0);

		} else {
			this.shininess = this.auxShininess;
		}

	}

	public boolean convertLight() {

		GlLight aux = GlLight.next();

		System.out.println(aux);

		setLight(aux.getValue());

		return isLight;
	}

	public boolean unconvertLight() {

		if (light.equals(GlLight.None)) {
			return false;
		}

		setLight(GlLight.None.getValue());
		return true;
	}

	public void setLight(int light) {
		this.light.disable();

		GlLight aux = GlLight.fromValue(light);

		if (aux.equals(GlLight.None)) {
			isLight = false;
			this.light = aux;
			return;
		}

		if (aux.isUsed()) {
			return;
		}

		aux.enable();
		isLight = true;
		this.light = aux;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public Color getAmbientColor() {
		return ambientColor;
	}

	public void setAmbientColor(Color ambientColor) {
		this.ambientColor = ambientColor;
	}

	public Color getDifuseColor() {
		return difuseColor;
	}

	public void setDifuseColor(Color difuseColor) {
		this.difuseColor = difuseColor;
	}

	public Color getSpecularColor() {
		return specularColor;
	}

	public void setSpecularColor(Color specularColor) {
		this.specularColor = specularColor;
	}

	public int getShininess() {
		return shininess;
	}

	public void setShininess(int shininess) {
		this.shininess = shininess;
		this.auxShininess = this.shininess;
	}

	public Color getEmissionColor() {
		return emissionColor;
	}

	public void setEmissionColor(Color emissionColor) {
		this.emissionColor = emissionColor;
	}

	public int getId() {
		return id;
	}

	private static int generateId() {

		int id = new Random().nextInt(1, Integer.MAX_VALUE);

		while (ids.contains(id)) {
			id = new Random().nextInt(1, Integer.MAX_VALUE);
		}

		ids.add(id);

		return id;
	}
}
