package szut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class INI_Loader {
	
	/*
	 * 
	 * Der INI Loader läd Settings-Daten aus einer .ini Datei. Diese
	 * werden dann dazu genutzt um das Fenster an der, vorher gesschlossenden
	 * Stelle wieder erscheinen zu lassen.
	 * 
	 */

	private int sizehorizontal = 550;
	private int sizevertikal = 400;
	
	private int positionleft = 100;
	private int positionhight = 100;
	
	private File inidatabase = null;
	
	/**
	 * Läd die datei und speichert sie in Gettern/Settern
	 */
	public void load() {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("settings.props"));
			setSizehorizontal(Integer.parseInt(p.getProperty("DBSizehorizontal")));
			setSizevertikal(Integer.parseInt(p.getProperty("DBSizevertikal")));
			setPositionhight(Integer.parseInt(p.getProperty("DBPositionhight")));
			setPositionleft(Integer.parseInt(p.getProperty("DBPositionleft")));
			setInidatabase(new File(p.getProperty("DBDatabase")));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Sammelt alle Informationen und speichert sie in der .ini Datei
	 * @param gui
	 */
	public void save(Gui gui, File file) {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("settings.props"));
			
			String Sizevertikal = gui.frame.getHeight() + "";
			String Sizehorizontal = gui.frame.getWidth() + "";
			String Positionleft = gui.frame.getX() + "";
			String Positionhight = gui.frame.getY() + "";
			
			p.setProperty("DBSizehorizontal", Sizehorizontal);
			p.setProperty("DBSizevertikal", Sizevertikal);
			p.setProperty("DBPositionhight", Positionhight);
			p.setProperty("DBPositionleft", Positionleft);
			
			// Falls kein Dateipfad vorhanden ist, wird es auf "null" gesetzt
			if (file != null){
				System.out.println(file.toString());
				p.setProperty("DBDatabase", file.toString());
			}else{
				p.setProperty("DBDatabase", "null");
			}
			
		    FileOutputStream out = new FileOutputStream("settings.props");
		    p.store(out, "/* properties updated */");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Passende Getter für andere Methoden
	 * @return int
	 */
	
	public int getPositionhight() {
		return positionhight;
	}

	public void setPositionhight(int positionhight) {
		this.positionhight = positionhight;
	}

	public int getPositionleft() {
		return positionleft;
	}

	public void setPositionleft(int positionleft) {
		this.positionleft = positionleft;
	}

	public int getSizehorizontal() {
		return sizehorizontal;
	}

	public void setSizehorizontal(int sizehorizontal) {
		this.sizehorizontal = sizehorizontal;
	}

	public int getSizevertikal() {
		return sizevertikal;
	}

	public void setSizevertikal(int sizevertikal) {
		this.sizevertikal = sizevertikal;
	}

	public File getInidatabase() {
		return inidatabase;
	}

	public void setInidatabase(File inidatabase) {
		this.inidatabase = inidatabase;
	}

}
