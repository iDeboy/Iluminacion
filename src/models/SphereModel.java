package models;

import static com.jogamp.opengl.GL.*;
import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class SphereModel extends FigureModel {

	public SphereModel(float x, float y, float z, float h, Color strokeColor, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super("Sphere", x, y, z, h, strokeColor, ambientColor, difuseColor, specularColor, shininess, emissionColor);
	}

	public SphereModel(float x, float y, float z, float h, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super("Sphere", x, y, z, h, ambientColor, difuseColor, specularColor, shininess, emissionColor);
	}

	public SphereModel(float x, float y, float z, Color strokeColor, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super("Sphere", x, y, z, strokeColor, ambientColor, difuseColor, specularColor, shininess, emissionColor);
	}

	public SphereModel(float x, float y, float z, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {
		super("Sphere", x, y, z, ambientColor, difuseColor, specularColor, shininess, emissionColor);
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
				glut.glutWireSphere(0.5f, 20, 20);
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
			glut.glutSolidSphere(0.5f, 20, 20);
		}
		gl.glPopMatrix();

	}

}
