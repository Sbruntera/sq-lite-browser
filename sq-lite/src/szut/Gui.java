package szut;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JMenuItem;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class Gui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JTree tree = new JTree();;
	private JTable table = new JTable();
	Controller ct = new Controller();
	private JScrollPane scrollPanetree;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JTextField textField;
	private JMenuItem MenuItemÖffnen;
	private JMenuItem MenuItemSchließen;
	private JMenuItem mntmDatenbankffnen;

	// Startet die GUI
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Alle GUI komponenten
	@SuppressWarnings("serial")
	public Gui() {
		setTitle("SQL - Litebrowser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("Datei");
		menuBar.add(mnNewMenu);
		
		MenuItemÖffnen = new JMenuItem("\u00D6ffnen");
		MenuItemÖffnen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				do_MenuItemÖffnen_mouseClicked(e);
			}
		});
		mnNewMenu.add(MenuItemÖffnen);
		
		MenuItemSchließen = new JMenuItem("Schlie\u00DFen");
		MenuItemSchließen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				do_MenuItemSchließen_mouseClicked(e);
			}
		});
		
		mntmDatenbankffnen = new JMenuItem("Datenbank \u00F6ffnen");
		mntmDatenbankffnen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				do_mntmDatenbankffnen_mouseClicked(e);
			}
		});
		mnNewMenu.add(mntmDatenbankffnen);
		mnNewMenu.add(MenuItemSchließen);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Datenbank") {
				{
					add(new DefaultMutableTreeNode("hay"));
				}
			}
		));
		
		tree.addTreeSelectionListener(e -> {
			
		});
		
		tree.setBackground(Color.LIGHT_GRAY);
		// contentPane.add(tree, BorderLayout.WEST);

		scrollPanetree = new JScrollPane(tree);
		contentPane.add(scrollPanetree, BorderLayout.WEST);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		// Erstellt die eine leere default Tabelle
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null }, }, new String[] {
				"New column", "New column", "New column", "New column",
				"New column", "New column" }));

		scrollPane = new JScrollPane(table);
		panel.add(scrollPane);

		textField = new JTextField();
		panel.add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
	}
	
	public void updateJTree(){
		
	}

	public void updateTable(String[][] name) {

		
		
		for (int i = 0; i < name.length; i++) {
			for (int k = 0; k < name[i].length; k++) {
				System.out.println(name[i][k] + ", ");
				table.getModel().setValueAt(name[i][k], i, k);
			}
		System.out.print("'''''########''''''");
		}
	}
	protected void do_MenuItemÖffnen_mouseClicked(MouseEvent e) {
		ct.testtabel();
	
	}
	protected void do_MenuItemSchließen_mouseClicked(MouseEvent e) {
		ct.opendatabase();
	}
	protected void do_mntmDatenbankffnen_mouseClicked(MouseEvent e) {
		
	}
}
