import javax.swing.JFrame;
public class Driver

{
    public static void main (String args[])
    {
    
        SelectionWindow StartGame = new SelectionWindow( );
        StartGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StartGame.setSize(700,800);
        StartGame.setVisible(true);
       
    }

}