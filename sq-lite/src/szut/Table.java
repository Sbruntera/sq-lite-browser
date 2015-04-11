package szut;

import java.util.ArrayList;

public class Table {
	
	/**
	 * Speicherort für Tabelennamen und locationnamen
	 */
	
	public String locationname = "Database";
	public ArrayList<String> tablename;
	
	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}
	
	public ArrayList<String> getTablename() {
		return tablename;
	}

	public void setTablename(ArrayList<String> tablename) {
		this.tablename = tablename;
	}

}
