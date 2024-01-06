package GUI;
import javax.swing.*;

public class jFrame {
    public static void main(String[] args) {
//        JFrame = a GUI window to add components to
        JFrame frame = new JFrame();//creates frame
        frame.setVisible(true);//make frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );//Exit out of application
//        frame.setResizable(false);//prevent from resizing
        frame.setSize(420,420);//set x-y dimension of frame
        frame.setTitle("JFrame title goes here");//set jFrame title
//        frame.setIconImage('');//set icon image
    }
}
