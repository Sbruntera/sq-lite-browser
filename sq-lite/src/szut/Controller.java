package szut;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Controller {
	
	
	
	Tabelfunction tf = new Tabelfunction();
	Table table = new Table();
	Location lc = new Location();
	LastNode ln = new LastNode();
	ArrayList<String> Tablelist = new ArrayList<String>();
	static INI_Loader loader = new INI_Loader();
	static Gui gui;
	
	/**
	 * Startet die GUI
	 * @param args
	 */
	public static void main(String[] args){
		Controller controller = new Controller();
		
		gui = new Gui(controller);
		gui.run();
		controller.loadINIfile();
	}
	
	/**
	 * Öffnet die Datei, updated den Jtree und updated mit der ersten Table die JTable
	 * @param location
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void opendatabase(File location) throws ClassNotFoundException, SQLException{
		//Speichert den Speicherort
		lc.setLocation(location);
		// Öffnet die Datei und liest alle tabellen aus
		table.setTablename(tf.opentabel(lc.getLocation()));
		// Öffnet die erste Tabelle und entnimmt die Daten
		String query = "SELECT * FROM " + table.getTablename().get(0);
		
		String data[][] = tf.getTableData(query, lc.getLocation());
		// Updated den Jtree auf die aktuelle datei
		gui.updateJTree(table.getTablename(), "Database", gui);
		//
		String header[] = tf.getTableheader(query, lc.getLocation());
		// Updated den JTable auf die akteulle datei
		gui.updateJTable(data, header, gui);
	}
	
	/**
	 * Methode zur ausführung von Querys. Hierbei wird auf viele Factoren geachtet
	 * In manchen Query wir kein Wert zurückgegeben oder CREATE hat keine weiter Funktion
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void runquery() throws SQLException, ClassNotFoundException{
		String data[][];
		// Ruft die Query in der datenbank auf
		try {
			data = tf.runquery(gui.frame.textField.getText(), lc.getLocation());
		} catch (Exception e) {
				JOptionPane.showMessageDialog(gui, e.getMessage());
			return;
		}
		data = tf.runquery(gui.frame.textField.getText(), lc.getLocation());
		// Aktuallisiert den JTree
		table.setTablename(tf.opentabel(lc.getLocation()));
		// Updated in der GUI
		gui.updateJTree(table.getTablename(), table.getLocationname(), gui);
		
		// Bei CREATE muss keine weiter Funktion ausgeführt werden
		if (gui.frame.textField.getText().startsWith("CREATE") || gui.frame.textField.getText().startsWith("create")){
			return;
		}
		// Select-Befehl wird ausgeführt
		if (gui.frame.textField.getText().startsWith("select") || gui.frame.textField.getText().startsWith("SELECT")){
			//Updated die GUI bei jeder eingabe von befehlen
			if (ln.getLastnode() == null){
				//String query = "SELECT * FROM " + table.getTablename().get(0);
				String header[] = tf.getTableheader(gui.frame.textField.getText(), lc.getLocation());
				gui.updateJTable(data, header,gui);
			}else{
				String header[] = tf.getTableheader(gui.frame.textField.getText(), lc.getLocation());
				gui.updateJTable(data, header, gui);
			}		
			
			
		// Aktuelle Seite wird geupdated
		}else{
			String query = "SELECT * FROM " + ln.getLastnode();
			System.out.println(query);
			data = tf.runquery(query, lc.getLocation());
			String header[] = tf.getTableheader(query, lc.getLocation());
			gui.updateJTable(data, header, gui);
			
		}
		
	}
	
	
	public void loadtabel(String name) throws SQLException {
		
		if (lc.getLocation() == null){
			return;
		}
		ln.setLastnode(name);
		
		if (gui.frame.chckbxNewCheckBox.isSelected() == true){
			try {
				String data[][] = tf.getLimitTableData(name, lc.getLocation(), Integer.parseInt(gui.frame.textField_von.getText()), Integer.parseInt(gui.frame.textField_bis.getText()));
				String query = "SELECT * FROM " + name;
				String header[] = tf.getTableheader(query, lc.getLocation());
				gui.updateJTable(data, header, gui);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(gui, e.getMessage());
			}
		}else{
			String query = "SELECT * FROM " + name;
			String data[][] = tf.getTableData(query, lc.getLocation());
			String header[] = tf.getTableheader(query, lc.getLocation());
			gui.updateJTable(data, header, gui);
		}
	}
	
	/**
	 * Speichert die derzeitigen Daten
	 */
	public void saveINIfile(){
		System.out.println(lc.getLocation());
		loader.save(gui, lc.getLocation());
		
	}
	
	/**
	 * Läd vorhandene daten
	 */
	public void loadINIfile(){
		loader.load();
		try {
			if (!loader.getInidatabase().toString().equals("null")){
				opendatabase(loader.getInidatabase());
			}
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(gui, e.getMessage());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(gui, e.getMessage());
		}
	}
}
