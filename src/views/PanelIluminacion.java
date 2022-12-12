package views;

import com.jogamp.opengl.GL;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL.GL_LEQUAL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
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
import models.SphereModel;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class PanelIluminacion extends GLJPanel implements GLEventListener, KeyListener {

	private Color bg;
	private Color ambientColor;

	private CamaraModel camara;

	public static final int FPS = 60; // animator's target frames per second

	//////////////// Variables /////////////////////////
	// Referencias de rotacion
	private final float[] rotation = new float[]{90f, 0f, 0f, 1.f};
//	float rotX = 90.0f;
//	float rotY = 0.0f;
//	float rotZ = 0.0f;

	// Posicion de la luz.
	private float[] lightPosition = new float[]{1f, 1f, 1f, 1.f};
//	float lightX = 1f;
//	float lightY = 1f;
//	float lightZ = 1f;
// float dLight = 0.05f;

	// Posicion de la camara
	//private float[] camPosition = new float[]{2f, 2f, 8f, 1f};
	//private float[] camPosition = new float[]{0f, 0f, 5f, 1f};
//	float camX = 2.0f;
//	float camY = 2.0f;
//	float camZ = 8.0f;
	// Material y luces.       R        G       B      A
	final float ambient[] = {0.282f, 0.427f, 0.694f, 1.0f};
//	final float position[] = {lightX, lightY, lightZ, 1.0f};

	//														R     G     B     A          
	final float mat_diffuse[] = {0.6f, 0.6f, 0.6f, 1.0f};
	final float mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
	final float mat_shininess[] = {50.0f};
	private float aspect;

	private GLU glu;				// para las herramientas GL (GL Utilities)
	private GLUT glut;

	private SphereModel sphere;

	public PanelIluminacion() {
		initComponents();
		//this.addKeyListener(this);
	}

	public void setBackground(Color bg) {
		this.bg = bg;
	}

	public void setAmbientColor(Color ambient) {
		this.ambientColor = ambient;
	}

	private void initComponents() {
		addGLEventListener(this);
		addKeyListener(this);

		KeyboardFocusManager.
						getCurrentKeyboardFocusManager().
						addPropertyChangeListener("focusOwner", (PropertyChangeEvent e) -> {
							//System.out.println(e.toString());
							requestFocusInWindow();
						});

		this.bg = Color.CYAN;
		this.ambientColor = new Color(72, 109, 177);

		camara = new CamaraModel(2, 2, 8, 45, 0, 0.1f, 20, 0, 0, 0, 0, 1, 0);
		sphere = new SphereModel(1, 1, 1, Color.YELLOW, Color.YELLOW, Color.YELLOW, 100, Color.YELLOW);
		sphere.setLight(GL2.GL_LIGHT0);
		sphere.setSelected(true);
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

//		gl.glEnable(GL2.GL_LIGHT0);
		// Light 0.
		//	      
		//
		// gl.glLightfv( GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0 );
//		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, ColorConverter.convertToFME(Color.WHITE), 0);
//		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, ColorConverter.convertToFME(Color.WHITE), 0);
//		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
		//gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
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

		gl.glTranslatef(-2.0f, 0.0f, -2.0f);

		drawFigures(gl, glut);

		sphere.draw(gl, glut);

		animate(gl, this.glu, this.glut);

		gl.glFlush();

	}

	public void setMaterialColor(GL2 gl, int face, Color ambientColor, Color difuseColor, Color specularColor, int shininess, Color emissionColor) {

		gl.glMaterialfv(face, GL2.GL_AMBIENT, ColorConverter.convertToFME(ambientColor), 0);
		gl.glMaterialfv(face, GL2.GL_DIFFUSE, ColorConverter.convertToFME(difuseColor), 0);
		gl.glMaterialfv(face, GL2.GL_SPECULAR, ColorConverter.convertToFME(specularColor), 0);
		gl.glMateriali(face, GL2.GL_SHININESS, shininess);
		gl.glMaterialfv(face, GL2.GL_EMISSION, ColorConverter.convertToFME(emissionColor), 0);

	}

	public void drawFigures(GL2 gl, GLUT glut) {

		gl.glPushMatrix();
		{
			setMaterialColor(gl, GL.GL_FRONT, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, 4, Color.BLACK);
			gl.glTranslatef(0f, 1f, 0f);
			gl.glLineWidth(10);
			glut.glutWireDodecahedron();
			//glut.glutSolidDodecahedron();
			//glut.glutSolidTeapot(1.0f, false);
		}
		gl.glPopMatrix();

		setMaterialColor(gl, GL.GL_FRONT, Color.RED, Color.RED, Color.WHITE, 100, Color.BLACK);
		gl.glTranslatef(2.0f, 1.0f, -1.0f);
		//glut.glutSolidTorus(0.5, 1, 20, 20);

		glut.glutSolidOctahedron();

	}

	public void animate(GL2 gl, GLU glu, GLUT glut) {
		//gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
		//drawLight(gl, glu, glut);
		//lightX += 0.003f;
		//lightY += 0.003f;
	}

	public void drawLight(GL2 gl, GLU glu, GLUT glut) {
		setMaterialColor(gl, GL.GL_FRONT, Color.YELLOW, Color.YELLOW, Color.YELLOW, 100, Color.YELLOW);

		gl.glPushMatrix();
		{
			gl.glTranslatef(lightPosition[0], lightPosition[1], lightPosition[2]);
			glut.glutSolidSphere(0.5f, 20, 20);
		}
		gl.glPopMatrix();
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

	@Override
	public void keyTyped(KeyEvent e) {

	}

	// Light Movement
	public void moveLightX(int value) {
		lightPosition[0] += (float) value / 100f;
		sphere.addX((float) value / 100f);
	}

	public void moveLightY(int value) {
		lightPosition[1] += (float) value / 100f;
		sphere.addY((float) value / 100f);
	}

	public void moveLightZ(int value) {
		lightPosition[2] += (float) value / 100f;
		sphere.addZ((float) value / 100f);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		var keyCode = e.getKeyCode();
		var camMov = 0.1f;

		if (e.isShiftDown()) {
			// Camara movement
			switch (keyCode) {
				case VK_W -> {
					camara.addY(camMov);
				}
				case VK_UP -> {
					camara.addFov(camMov);
				}
				case VK_S -> {
					camara.addY(-camMov);
				}
				case VK_DOWN -> {
					camara.addFov(-camMov);
				}
				case VK_D -> {
					camara.addX(camMov);
				}
				case VK_A -> {
					camara.addX(-camMov);
				}
				case VK_Q -> {
					camara.addZ(camMov);
				}
				case VK_E -> {
					camara.addZ(-camMov);
				}
			}

		} else if (e.isControlDown()) {

			// Point movement (Head movement)
			switch (keyCode) {
				case VK_W -> {
					camara.addYPoint(camMov);
				}
				case VK_UP -> {
					camara.addFar(camMov);
				}
				case VK_S -> {
					camara.addYPoint(-camMov);
				}
				case VK_DOWN -> {
					camara.addFar(-camMov);
				}
				case VK_D -> {
					camara.addXPoint(camMov);
				}
				case VK_A -> {
					camara.addXPoint(-camMov);
				}
				case VK_Q -> {
					camara.addZPoint(camMov);
				}
				case VK_E -> {
					camara.addZPoint(-camMov);
				}
			}

		} else {
			// Selected figure movement
			switch (keyCode) {
				case VK_W -> {
					moveLightY(5);
				}
				case VK_S -> {
					moveLightY(-5);
				}
				case VK_D -> {
					moveLightX(5);
				}
				case VK_A -> {
					moveLightX(-5);
				}
				case VK_Q -> {
					moveLightZ(20);
				}
				case VK_E -> {
					moveLightZ(-20);
				}
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
