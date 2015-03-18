package szut;

public class Controller {
	
	Tabelfunction tf = new Tabelfunction();
	static Gui gui;
	
	public static void main(String[] args){
		gui = new Gui();
		gui.run();
		
	}
	
	public void testtabel(){
		tf.testvalues(gui);
		
	}
	
	public void opendatabase(){
		tf.opentabel(gui);
	}

	public void loadtabel() {
		
		
		
		
	}
}
