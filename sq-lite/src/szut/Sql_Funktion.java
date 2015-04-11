package szut;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Sql_Funktion {
	
	private String CREATE_DATABASE = "CREATE DATABASE ";
	
	/**
	 * Diese Methode ruft die Datei auf und versucht die übergebene Querry auszuführen
	 * @param query
	 * @param location
	 * @throws SQLException
	 */
	public void runquery(String query, File location) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:sqlite:file:"+ location);
		Statement Statement = connection.createStatement();
		Statement.executeUpdate(query);
		return;
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
			e.printStackTrace();
		}
		return Tablelist;
	}
	
	public String[][] getTableData(String name, File location) throws SQLException {

		// Eine Select-Befehl wird erstellt und in der Datenbank abgefargt
		String query = "SELECT * FROM " + name;
		Connection connection = null;
		if (location.exists()) {
			connection = DriverManager.getConnection("jdbc:sqlite:file:" + location);
		} else {
			return null;
		}

		Statement m_Statement = connection.createStatement();

		// Das Ergebniss wird im Result zurück geben
		ResultSet m_ResultSet = m_Statement.executeQuery(query);

		// Umständliche größen bestimmung
		int lenght = 2;
		int rowcount = 0;
		while (m_ResultSet.next()) {
			rowcount++;
		}

		// extrahierung der Daten in zweidemensonialen Array
		m_ResultSet = m_Statement.executeQuery(query);
		String[][] data = new String[rowcount][lenght];
		int k = 0;
		while (m_ResultSet.next()) {
			for (int i = 0; i != lenght; i++) {
				System.out.println(m_ResultSet.getString(i + 1));
				data[k][i] = m_ResultSet.getString(i + 1);
			}
			k++;
		}
		// Daten werden zurück gegeben
		return data;
	}
	
//	#########################################################################
//	#
//	#
//	#
//	#
//	#########################################################################
	
	public void creatnewDatabase(String Databasename, Gui gui, File location) throws SQLException {
		runquery(CREATE_DATABASE + Databasename, location);
	}

}
