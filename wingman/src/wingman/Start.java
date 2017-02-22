package wingman;


public class Start {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		System.out.println("This is starting from the main");
		game1942WithObserver c = new game1942WithObserver();
		c.init();
		c.start();
		 javax.swing.JFrame window = new javax.swing.JFrame("WingMan++");
         window.setContentPane(c);
         window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
         window.pack();              // Arrange the components.
         window.setSize(1060, 800);
         window.setVisible(true);    // Make the window visible.

	}

}
