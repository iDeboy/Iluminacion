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
public class CubeModel extends FigureModel {

	public CubeModel() {
		super();
		this.name = "Cube";
	}

	public CubeModel(float x, float y, float z, float h, Color strokeColor, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super("Cube", x, y, z, h, strokeColor, ambientColor, difuseColor, specularColor, shininess, emissionColor);
	}

	public CubeModel(float x, float y, float z, float h, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super("Cube", x, y, z, h, ambientColor, difuseColor, specularColor, shininess, emissionColor);
	}

	public CubeModel(float x, float y, float z, Color strokeColor, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super("Cube", x, y, z, strokeColor, ambientColor, difuseColor, specularColor, shininess, emissionColor);
	}

	public CubeModel(float x, float y, float z, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super("Cube", x, y, z, ambientColor, difuseColor, specularColor, shininess, emissionColor);
	}

	@Override
	public void draw(GL2 gl, GLUT glut) {
		super.draw(gl, glut);

		// Draw stoke if selected
		if (this.isSelected()) {
			gl.glPushMatrix();
			{
				gl.glMaterialfv(GL_FRONT, GL_AMBIENT, ColorConverter.convertToFME(strokeColor), 0);
				gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, ColorConverter.convertToFME(strokeColor), 0);
				gl.glMaterialfv(GL_FRONT, GL_SPECULAR, ColorConverter.convertToFME(strokeColor), 0);
				gl.glMateriali(GL_FRONT, GL_SHININESS, shininess);
				gl.glMaterialfv(GL_FRONT, GL_EMISSION, ColorConverter.convertToFME(strokeColor), 0);

				gl.glTranslatef(x, y, z);
				gl.glLineWidth(5);
				glut.glutWireCube(0.5f);
			}
			gl.glPopMatrix();
		}

		// Configure material properties
		gl.glPushMatrix();
		{
			gl.glMaterialfv(GL_FRONT, GL_AMBIENT, ColorConverter.convertToFME(ambientColor), 0);
			gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, ColorConverter.convertToFME(difuseColor), 0);
			gl.glMaterialfv(GL_FRONT, GL_SPECULAR, ColorConverter.convertToFME(specularColor), 0);
			gl.glMateriali(GL_FRONT, GL_SHININESS, shininess);
			gl.glMaterialfv(GL_FRONT, GL_EMISSION, ColorConverter.convertToFME(emissionColor), 0);

			gl.glTranslatef(x, y, z);
			glut.glutSolidCube(0.5f);
		}
		gl.glPopMatrix();

	}

}
