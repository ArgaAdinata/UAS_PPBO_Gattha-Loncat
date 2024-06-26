
import javax.swing.*;
public class Window extends JFrame {
	
	private final int HEIGHT = 800;
	private final int WIDTH = 800;
	public static Display dis;
	
	Window(boolean pause) {
		dis = new Display(pause);
		setTitle("Gata Loncat");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		add(dis);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		
		final boolean pause = true;
		
		new Window(pause);
	}
}