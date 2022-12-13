package models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public interface IDrawable {

	void draw(GL2 gl, GLUT glut);
}
