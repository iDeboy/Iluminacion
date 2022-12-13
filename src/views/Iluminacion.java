package views;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_W;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import models.CamaraModel;
import models.CubeModel;
import models.DodecahedronModel;
import models.FigureListModel;
import models.FigureModel;
import models.IcosahedronModel;
import models.OctahedronModel;
import models.SphereModel;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class Iluminacion extends JFrame implements Runnable, WindowListener {

	@Override
	public void run() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);

		animator.start();
	}

	public Iluminacion() {
		initComponents();
	}

	private Constructor getDefaultContructor(Object obj) {

		for (var cont : currentFigure.getClass().getConstructors()) {
			if (cont.getParameterCount() == 0) {
				return cont;
			}
		}

		return null;
	}

	private void initComponents() {

		camara = new CamaraModel(2, 2, 8, 45, 0, 0.1f, 20, 0, 0, 0, 0, 1, 0);

		leftPanel = new JPanel();
		centerPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanelTitle = new JLabel();

		buttonGroup = new ButtonGroup();
		btnEsfera = new JButton();
		btnCubo = new JButton();
		btnDodecahedro = new JButton();
		btnIcosahedro = new JButton();
		btnOctahedro = new JButton();

		canvasPanel = new PanelIluminacion(camara);
		animator = new FPSAnimator(canvasPanel, PanelIluminacion.FPS, true);
		scrollLogger = new JScrollPane();
		logger = new JTextArea();

		rightPanelTitle = new JLabel();
		scrollFiguras = new JScrollPane();
		listFiguras = new JList<>(new FigureListModel());

		addWindowListener(this);

		setTitle("Iluminación");
		setResizable(false);
		setLayout(new BorderLayout());

		KeyboardFocusManager.
						getCurrentKeyboardFocusManager().
						addPropertyChangeListener("focusOwner", (PropertyChangeEvent e) -> {
							//System.out.println(e.toString());
							requestFocusInWindow();
						});

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				var keyCode = e.getKeyCode();
				var camMov = 0.1f;
				var selectedModel = listFiguras.getSelectedValue();

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

					if (selectedModel == null) {
						return;
					}

					// Selected figure movement
					switch (keyCode) {
						case VK_W -> {
							selectedModel.addY(0.05f);
						}
						case VK_S -> {
							selectedModel.addY(-0.05f);
						}
						case VK_D -> {
							selectedModel.addX(0.05f);
						}
						case VK_A -> {
							selectedModel.addX(-0.05f);
						}
						case VK_Q -> {
							selectedModel.addZ(0.05f);
						}
						case VK_E -> {
							selectedModel.addZ(-0.05f);
						}
					}
				}

				repaint();
				canvasPanel.setCamara(camara);
			}
		});

		/* leftPanel components & design */
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		leftPanelTitle.setText("Figuras");
		leftPanelTitle.setFont(new Font("Montserrat", Font.BOLD, 24));

		btnEsfera.setText("Esfera");
		btnEsfera.setFont(new Font("Montserrat", Font.BOLD, 16));

		btnEsfera.addActionListener((ActionEvent e) -> {

			logger.append("Se va a crear una esfera.\n");

			currentFigure = new SphereModel();

			currentFigure.translate(canvasPanel.getCamPointing());

			listModel.addElement(currentFigure);
			canvasPanel.setModel(listModel);

			logger.append(currentFigure.getName() + " creada.\n");

			currentFigure = null;
		});

		btnCubo.setText("Cubo");
		btnCubo.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnCubo.addActionListener((ActionEvent e) -> {

			logger.append("Se va a crear un cubo.\n");

			currentFigure = new CubeModel();

			currentFigure.translate(canvasPanel.getCamPointing());

			listModel.addElement(currentFigure);
			canvasPanel.setModel(listModel);

			logger.append(currentFigure.getName() + " creado.\n");

			currentFigure = null;

		});

		btnDodecahedro.setText("Dodecahedro");
		btnDodecahedro.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnDodecahedro.addActionListener((ActionEvent e) -> {

			logger.append("Se va a crear un dodecahedro.\n");

			currentFigure = new DodecahedronModel();

			currentFigure.translate(canvasPanel.getCamPointing());

			listModel.addElement(currentFigure);
			canvasPanel.setModel(listModel);

			logger.append(currentFigure.getName() + " creado.\n");

			currentFigure = null;

		});

		btnIcosahedro.setText("Icosahedro");
		btnIcosahedro.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnIcosahedro.addActionListener((ActionEvent e) -> {

			logger.append("Se va a crear un icosahedro.\n");

			currentFigure = new IcosahedronModel();

			currentFigure.translate(canvasPanel.getCamPointing());

			listModel.addElement(currentFigure);
			canvasPanel.setModel(listModel);

			logger.append(currentFigure.getName() + " creado.\n");

			currentFigure = null;

		});

		btnOctahedro.setText("Octahedro");
		btnOctahedro.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnOctahedro.addActionListener((ActionEvent e) -> {

			logger.append("Se va a crear un octahedro.\n");

			currentFigure = new OctahedronModel();

			currentFigure.translate(canvasPanel.getCamPointing());

			listModel.addElement(currentFigure);
			canvasPanel.setModel(listModel);

			logger.append(currentFigure.getName() + " creado.\n");

			currentFigure = null;

		});

		buttonGroup.add(btnEsfera);
		buttonGroup.add(btnCubo);
		buttonGroup.add(btnDodecahedro);
		buttonGroup.add(btnIcosahedro);
		buttonGroup.add(btnOctahedro);

		leftPanel.add(leftPanelTitle);
		leftPanel.add(btnEsfera);
		leftPanel.add(btnCubo);
		leftPanel.add(btnDodecahedro);
		leftPanel.add(btnIcosahedro);
		leftPanel.add(btnOctahedro);

		/* centerPanel components & design */
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);

		canvasPanel.setBackground(centerPanel.getBackground());
		canvasPanel.setBorder(new LineBorder(Color.BLACK, 2));

		logger.setRows(10);
		logger.setLineWrap(true);
		logger.setEditable(false);

		scrollLogger.setViewportView(logger);

		centerPanel.add(canvasPanel, BorderLayout.CENTER);
		centerPanel.add(scrollLogger, BorderLayout.PAGE_END);

		/* rightPanel components & design */
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		rightPanelTitle.setText("Vista y edición");
		rightPanelTitle.setFont(new Font("Montserrat", Font.BOLD, 24));

		listModel = (FigureListModel) listFiguras.getModel();
		listFiguras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listFiguras.addListSelectionListener((ListSelectionEvent e) -> {

			listModel.unselectAll();

			var selectedModel = listFiguras.getSelectedValue();

			if (selectedModel == null) {
				return;
			}

			selectedModel.setSelected(true);

			logger.append(selectedModel.getName() + " seleccionado.\n");

		});

		scrollFiguras.setViewportView(listFiguras);
		rightPanel.add(rightPanelTitle);
		rightPanel.add(scrollFiguras);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

	}

	@Override

	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		new Thread() {
			@Override
			public void run() {
				if (animator.isStarted()) {
					animator.stop();
				}
				System.exit(0);
			}
		}.start();
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	// Variable definition
	private CamaraModel camara;
	private FigureModel currentFigure;

	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JLabel leftPanelTitle;

	/* leftPanel components */
	private ButtonGroup buttonGroup;
	private JButton btnEsfera;
	private JButton btnCubo;
	private JButton btnDodecahedro;
	private JButton btnIcosahedro;
	private JButton btnOctahedro;

	/* centerPanel components */
	private PanelIluminacion canvasPanel;
	private FPSAnimator animator;
	private JScrollPane scrollLogger;
	private JTextArea logger;

	/* rightPanel components */
	private JLabel rightPanelTitle;
	private JScrollPane scrollFiguras;
	private JList<FigureModel> listFiguras;
	private FigureListModel listModel;

}
