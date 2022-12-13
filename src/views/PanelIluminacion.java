package views;

import com.jogamp.opengl.GL;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import models.CamaraModel;
import models.ColorConverter;
import models.FigureListModel;
import models.FigureModel;
import models.SphereModel;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class PanelIluminacion extends GLJPanel implements GLEventListener {

	private Color bg;
	private Color ambientColor;

	private CamaraModel camara;

	private FigureListModel listModel;

	public static final int FPS = 60; // animator's target frames per second

	private GLU glu;				// para las herramientas GL (GL Utilities)
	private GLUT glut;

	public PanelIluminacion() {
		this(null);
	}

	public PanelIluminacion(CamaraModel camara) {
		this(camara, null);
	}

	public PanelIluminacion(CamaraModel camara, FigureListModel listModel) {
		this.camara = camara;
		this.listModel = listModel;
		initComponents();
	}

	public void setBackground(Color bg) {
		this.bg = bg;
	}

	public void setAmbientColor(Color ambient) {
		this.ambientColor = ambient;
	}

	public float[] getCamPointing() {
		return this.camara.getPointingPosition();
	}

	public FigureListModel getModel() {
		return this.listModel;
	}

	public void setModel(FigureListModel listModel) {
		this.listModel = listModel;
	}

	private void initComponents() {
		addGLEventListener(this);

		this.bg = Color.CYAN;
		this.ambientColor = new Color(72, 109, 177);
	}

	private void clearColor(GL2 gl, Color color) {

		var red = ColorConverter.getRed(color);
		var green = ColorConverter.getGreen(color);
		var blue = ColorConverter.getBlue(color);
		var alpha = ColorConverter.getAlpha(color);

		gl.glClearColor(red, green, blue, alpha);
	}

	private void lightAmbientColor(GL2 gl, Color color) {

		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, ColorConverter.convertToFME(color), 0);

	}

	@Override
	public void init(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();

		// Establece un material por default.
		clearColor(gl, bg); // set background (clear) color

		gl.glClearDepth(1.0f);      // set clear depth value to farthest
		gl.glEnable(GL_DEPTH_TEST); // enables depth testing
		gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do

		gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting  

		// Alguna luz de ambiente global.
		lightAmbientColor(gl, ambientColor);

		// First Switch the lights on.
		gl.glEnable(GL2.GL_LIGHTING);

		glu = new GLU();                        // get GL Utilities
		glut = new GLUT();

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {

	}

	@Override
	public void display(GLAutoDrawable drawable) {

		GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context

		gl.glMatrixMode(GL_PROJECTION);			// choose projection matrix
		gl.glLoadIdentity();

		glu.gluPerspective(camara.getFov(), camara.getAspect(),
						camara.getNear(), camara.getFar());

		glu.gluLookAt(camara.getX(), camara.getY(), camara.getZ(),
						camara.getPointAtX(), camara.getPointAtY(), camara.getPointAtZ(),
						camara.getLookAtX(), camara.getLookAtY(), camara.getLookAtZ());

		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity();  // reset the model-view matrix

		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		camara.draw(gl, glut);

		// Draw figures
		if (listModel != null) {

			for (var model : listModel) {
				model.draw(gl, glut);
			}

		}

		gl.glFlush();

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context

		if (height == 0) {
			height = 1;   // prevent divide by zero
		}
		camara.setAspect((float) width / (float) height);

		// Set the view port (display area) to cover the entire window
		gl.glViewport(0, 0, width, height);

		// Setup perspective projection, with aspect ratio matches viewport
		gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix
		gl.glLoadIdentity();             // reset projection matrix
		glu.gluPerspective(camara.getFov(), camara.getAspect(),
						camara.getNear(), camara.getFar()); // fovy, aspect, zNear, zFar

		// Enable the model-view transform
		gl.glMatrixMode(GL_MODELVIEW);
		gl.glLoadIdentity(); // reset

	}

	public void setCamara(CamaraModel camara) {
		this.camara = camara;
	}
}
