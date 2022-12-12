package views;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

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

	private void initComponents() {

		leftPanel = new JPanel();
		centerPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanelTitle = new JLabel();

		buttonGroup = new ButtonGroup();
		btnEsfera = new JToggleButton();
		btnCubo = new JToggleButton();
		btnDodecahedro = new JToggleButton();

		canvasPanel = new PanelIluminacion();
		animator = new FPSAnimator(canvasPanel, PanelIluminacion.FPS, true);
		scrollLogger = new JScrollPane();
		logger = new JTextArea();

		rightPanelTitle = new JLabel();
		scrollFiguras = new JScrollPane();
		listFiguras = new JList();

		addWindowListener(this);

		setTitle("Iluminación");
		setResizable(false);
		setLayout(new BorderLayout());

		/* leftPanel components & design */
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		leftPanelTitle.setText("Figuras");
		leftPanelTitle.setFont(new Font("Montserrat", Font.BOLD, 24));

		btnEsfera.setText("Esfera");
		btnEsfera.setFont(new Font("Montserrat", Font.BOLD, 16));

		btnCubo.setText("Cubo");
		btnCubo.setFont(new Font("Montserrat", Font.BOLD, 16));

		btnDodecahedro.setText("Dodecahedro");
		btnDodecahedro.setFont(new Font("Montserrat", Font.BOLD, 16));

		buttonGroup.add(btnEsfera);
		buttonGroup.add(btnCubo);
		buttonGroup.add(btnDodecahedro);

		leftPanel.add(leftPanelTitle);
		leftPanel.add(btnEsfera);
		leftPanel.add(btnCubo);
		leftPanel.add(btnDodecahedro);

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

		//listModel = (ListFigurasModel) listFiguras.getModel();
		//listFiguras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollFiguras.setViewportView(listFiguras);
		rightPanel.add(rightPanelTitle);
		rightPanel.add(scrollFiguras);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

		//add(canvasPanel);
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
	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JLabel leftPanelTitle;

	/* leftPanel components */
	private ButtonGroup buttonGroup;
	private JToggleButton btnEsfera;
	private JToggleButton btnCubo;
	private JToggleButton btnDodecahedro;

	/* centerPanel components */
	private PanelIluminacion canvasPanel;
	private FPSAnimator animator;
	private JScrollPane scrollLogger;
	private JTextArea logger;

	/* rightPanel components */
	private JLabel rightPanelTitle;
	private JScrollPane scrollFiguras;
	private JList listFiguras;
	//private ListFigurasModel listModel;

}
