package szut;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Tabelfunction {

	public String[] currenttable;
	
	public String[] getCurrenttable() {
		return currenttable;
	}

	public void setCurrenttable(String[] currenttable) {
		this.currenttable = currenttable;
	}
	
	/**
	 * Diese Methode ruft die Datei auf und versucht die übergebene Querry auszuführen
	 * @param query
	 * @param location
	 * @throws SQLException
	 */
	public String[][] runquery(String query, File location) throws SQLException {
		if (query.startsWith("select") || query.startsWith("SELECT")){
			String data[][] = getTableData(query, location);
			return data;
		}else{
			Connection connection = DriverManager.getConnection("jdbc:sqlite:file:"+ location);
			Statement Statement = connection.createStatement();
			Statement.executeUpdate(query);
			connection.close();
			return null;

		}

	}
	
	/**
	 * Gibt eine Arraylist mit den Tabellennamen zurück
	 * @param location
	 * @return
	 */
	public ArrayList<String> opentabel(File location) throws ClassNotFoundException {

		ArrayList<String> Tablelist = new ArrayList<String>();
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:sqlite:file:" + location);
			DatabaseMetaData meta = connection.getMetaData();
			
			ResultSet res = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (res.next()) {
				Tablelist.add(res.getString("TABLE_NAME"));
			}
			res.close();
		} catch (SQLException e) {
			return null;
		}
		return Tablelist;
	}
	
	/**
	 * Gibt eine Liste mit Spaltenanem zurück
	 * @return
	 */
	public String[] getTableheader(String query, File location) throws SQLException{
		
		ArrayList<String> Tableheader = new ArrayList<String>();
		Connection connection = null;
		Statement Statement = null;
		ResultSet ResultSet = null;
		ResultSetMetaData metadata = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:file:" + location);
			Statement = connection.createStatement();
			ResultSet = Statement.executeQuery(query);
			
		} catch (SQLException e) {
			Statement.close();
			connection.close();

			return getCurrenttable();
		}
		metadata = ResultSet.getMetaData();
		int count = metadata.getColumnCount();
		for (int i = 1; i <= count; i++)
		{
			Tableheader.add(metadata.getColumnName(i));

		}
		String [] Tableheaderarray = new String[Tableheader.size()];
		Tableheaderarray = Tableheader.toArray(Tableheaderarray);
		ResultSet.close();
		Statement.close();
		return Tableheaderarray;
	}
	
	public String[][] getTableData(String query, File location) throws SQLException {
		// Eine Select-Befehl wird erstellt und in der Datenbank abgefargt
		//String query = "SELECT * FROM " + name;
		Connection connection = null;
		if (location.exists()) {connection = DriverManager.getConnection("jdbc:sqlite:file:" + location);
		} else {
			return null;
		}

		Statement m_Statement = connection.createStatement();
		ResultSet m_ResultSet = m_Statement.executeQuery(query);
		
		int rowcount = 0;
		while (m_ResultSet.next()) {
			rowcount++;
		}
		
		m_ResultSet = m_Statement.executeQuery(query);
		
		int k = 0;
		try {
			String[][] data = new String[rowcount][];
			
			while (m_ResultSet.next()) {
				
				String line[] = new String[m_ResultSet.getMetaData().getColumnCount()];
				
				for (int i = 0; i != m_ResultSet.getMetaData().getColumnCount(); i++) {
					line[i] = m_ResultSet.getString(i + 1);
				}
				data[k] = line;
				k++;
			}
			connection.close();
			m_ResultSet.close();
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}

	return null;
	}
	
	/**
	 * Überprüft ob der Übergebene String ein Integer ist
	 * @param integerstring
	 * @return boolean
	 */
	public boolean integerControll(String integerstring){
		
		try{
			Integer.parseInt(integerstring);
		}catch(Exception e){
			return false;
		}
		return true;
		
	}
	
	/**
	 * Mit Tabellennamen, Location und der Range angabe wird eine Limiterung der Daten erreicht
	 * @return data[][]
	 */
	public String[][] getLimitTableData(String name, File location,int parseInt, int parseInt2) throws SQLException {
		// Eine Select-Befehl wird erstellt und in der Datenbank abgefargt
		String query = "SELECT * FROM " + name + " LIMIT " + parseInt + ", " + parseInt2;
		Connection connection = null;
		if (location.exists()) {connection = DriverManager.getConnection("jdbc:sqlite:file:" + location);
		} else {
			return null;
		}

		Statement m_Statement = connection.createStatement();
		ResultSet m_ResultSet = m_Statement.executeQuery(query);
		
		int rowcount = 0;
		while (m_ResultSet.next()) {
			rowcount++;
		}

		m_ResultSet = m_Statement.executeQuery(query);
		
		int k = 0;
		try {
			String[][] data = new String[rowcount][];
			
			while (m_ResultSet.next()) {
				
				String line[] = new String[m_ResultSet.getMetaData().getColumnCount()];
				
				for (int i = 0; i != m_ResultSet.getMetaData().getColumnCount(); i++) {
					line[i] = m_ResultSet.getString(i + 1);
				}
				data[k] = line;
				k++;
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
