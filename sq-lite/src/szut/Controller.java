package szut;

public class Controller {
	
	Tabelfunction tf;
	static Gui gui;
	
	public static void main(String[] args){
		gui = new Gui();
		gui.run();
		
	}
	
	public void testtabel(){
		System.out.println("hay");
		tf.testvalues(gui);
		
	}

	public void loadtabel() {
		
		
		
		
	}
}
