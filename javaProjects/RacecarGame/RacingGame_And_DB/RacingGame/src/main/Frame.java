package main;

import java.awt.Image;

import javax.swing.JFrame;

public class Frame{

	private static JFrame frame;
	
	public static int WIDTH = 750;
	public static int HEIGHT = 750;
	
	private static int actualwidth;
	private static int actualheight;
	
	public static String TITLE = "The Drag Racing Experience";
	
	public static void main(String[] args) {
		actualwidth = WIDTH + 15;
		actualheight = HEIGHT + 15;
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Controller());
		frame.pack();	
		frame.setSize(actualwidth, actualheight);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//Simple items to help us set up the actual runnable window.
	    Image gameIcon = new ImageLoader(ImageLoader.LOGO).getImage();
	    frame.setIconImage(gameIcon);//Game logo
	}
}
