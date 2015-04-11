package szut;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Gui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JScrollPane scrollPanetree;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JMenuItem MenuItemÖffnen;
	private JMenuItem MenuItemSchließen;
	private JMenuItem mntmDatenbankSchlieen;
	private JPanel panel_1;
	private JButton btnactionquery;
	private JPanel panel_2;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	public JCheckBox chckbxNewCheckBox;
	
	public JTree tree = new JTree();;
	public JTable table = new JTable();
	public JTextField textField;
	public JFormattedTextField textField_von;
	public JFormattedTextField textField_bis;
	
	Controller ct = new Controller();
	Gui frame;
	LastNode ln = new LastNode();

	// Startet die GUI
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Alle GUI komponenten
	public Gui() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				ct.saveINIfile();
			}
		});
		setTitle("SQL - Litebrowser");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// (Standort r/l, Standort o/u, länge, hohe)
		setBounds(Controller.loader.getPositionleft(), Controller.loader.getPositionhight(), Controller.loader.getSizehorizontal(), Controller.loader.getSizevertikal());

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Das Menü
		mnNewMenu = new JMenu("Datei");
		menuBar.add(mnNewMenu);
		
		
		MenuItemÖffnen = new JMenuItem("\u00D6ffnen");
		MenuItemÖffnen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					do_MenuItemÖffnen_mouseClicked(e);
				} catch (ClassNotFoundException e1){
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		mnNewMenu.add(MenuItemÖffnen);
		MenuItemSchließen = new JMenuItem("Schlie\u00DFen");
		MenuItemSchließen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				do_MenuItemSchließen_mouseClicked(e);
			}
		});
		
		mntmDatenbankSchlieen = new JMenuItem("Datenbank schlie\u00DFen");
		mnNewMenu.add(mntmDatenbankSchlieen);
		mnNewMenu.add(MenuItemSchließen);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// Erstellung des JTrees
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Datenbank")));
		tree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		        if (node == null) return;
		        if (node.getChildCount() == 0){
		        	try {
						ct.loadtabel(node.toString());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		        }
		    }
		});
		tree.setBackground(Color.LIGHT_GRAY);

		scrollPanetree = new JScrollPane(tree);
		contentPane.add(scrollPanetree, BorderLayout.WEST);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		// Erstellt die eine leere default Tabelle
		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null }, }, 
				new String[] {
				"New column", "New column", "New column", "New column",
				"New column", "New column" }));

		scrollPane = new JScrollPane(table);
		panel.add(scrollPane);
		
		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		panel_1.add(textField);
		textField.setColumns(100);
		
		// Benutzer Quarry
		btnactionquery = new JButton("Enter");
		btnactionquery.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					do_userquaery(e);
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnactionquery, BorderLayout.EAST);
		
		panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		
		// Checkbox für den Limiter
		chckbxNewCheckBox = new JCheckBox("Limit");
		chckbxNewCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				// Wenn Aktiviert => Textfelder beschreibar
				if (chckbxNewCheckBox.isSelected()){
					textField_bis.setEditable(false);
					textField_von.setEditable(false);
				}else{
				// Wenn Deaktiviert => Textfelder unbeschreibar
					textField_bis.setEditable(true);
					textField_von.setEditable(true);

				}
			}
		});
		panel_2.add(chckbxNewCheckBox);
		
		// Verhindert, das Buchstaben in Textfelder kommen
		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance());
		numberFormatter.setAllowsInvalid(false);
		numberFormatter.setMinimum(0);
		
		lblNewLabel = new JLabel("Von:");
		panel_2.add(lblNewLabel);
		
		textField_von = new JFormattedTextField(numberFormatter);
		textField_von.setEditable(false);
		panel_2.add(textField_von);
		textField_von.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Bis:");
		panel_2.add(lblNewLabel_1);
		
		textField_bis = new JFormattedTextField(numberFormatter);
		textField_bis.setEditable(false);
		panel_2.add(textField_bis);
		textField_bis.setColumns(10);
	}
	
	/**
	 * Mit übergeben Werten, wird der JTree aktualisiert
	 * @param tablelist
	 * @param newdatabasename 
	 * @param gui 
	 * @param gui 
	 */
	@SuppressWarnings("serial")
	public void updateJTree(ArrayList<String> tablelist, String newdatabasename, Gui gui){
		frame.tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode(newdatabasename) {
					{	
						// Iteriert durch die ArrayList und fügt es dem JTree hinzu
						for (String s : tablelist){
							add(new DefaultMutableTreeNode(s));
						}
					}
				}
			));
	}
	
	
	/**
	 * Öffnet ein Fenster zur suche der zu öffnen Datei.
	 * @param e
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	protected void do_MenuItemÖffnen_mouseClicked(MouseEvent e) throws ClassNotFoundException, SQLException {
		
		File location = null;
		
		// Fenster wird geöffnet und ein Filter wird auf .db gesetzt, alles andere wird ausgeblendet
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("datenbank" ,"db"); 
		chooser.setFileFilter(filter);
		int datei = chooser.showOpenDialog(null);
		
		// Dateipfad wird ausgelsen
		if (datei == JFileChooser.APPROVE_OPTION){
			location = chooser.getSelectedFile();
			ct.opendatabase(location);
		}
		// Wenn keine Datei ausgewählt wurde gehts zurück
		return;
	}
	
	/**
	 * Programm wird geschlossen
	 * @param e
	 */
	protected void do_MenuItemSchließen_mouseClicked(MouseEvent e) {
		ct.saveINIfile();
		System.exit(0);
	}
	
	/**
	 * Führt die vom Nutzer angegebene Query aus
	 * @param e
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected void do_userquaery(MouseEvent e) throws ClassNotFoundException, SQLException{
		ct.runquery();
	}

	/**
	 * Tabelle wird geupdated
	 * @param data
	 * @param gui
	 */
	public void updateJTable(String[][] data, String[] header, Gui gui) {
		Object[][] datas = (Object[][]) data;
		frame.table.setModel(new DefaultTableModel(datas, header));
	}
}
