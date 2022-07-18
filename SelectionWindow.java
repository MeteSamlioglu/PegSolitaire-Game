import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.io.File;  //Read from file
import java.io.FileNotFoundException;  //Read from file exception 
import java.io.IOException; //Write to file exception
import java.util.Scanner; 
import javax.swing.ButtonGroup;
public class SelectionWindow extends JFrame
{
    private JLabel label;
    private JPanel GameTypePanel, ChooseBoard;
    private JPanel Boards;
    private JButton Board1, Board2, Board3, Board4, Board5, Load;
    private JButton ComputerGame, UserGame, Start;
    private Icon Board1Icon, Board2Icon, Board3Icon, Board4Icon, Board5Icon;
    private PegSolitaire Game;
    private GameWindow GameScreen;
    private JRadioButton ComputerGameButton; // selects plain text 
    private JRadioButton UserGameButton; // selects bold text    
    private boolean isBoardChoosen = false;
    private boolean isGameTypeChoosen = false;
    private String boardFile, SpecialBoard;
    private Color RedBackground = Color.RED;
    private ButtonGroup group;
    private int gameType = 0; 
    public SelectionWindow( ){

        super("PegSolitaire Game Selections ");
        
        setLayout(null);
        ChooseBoard = new JPanel();
        label = new JLabel("Choose Your Board");
        label.setFont(new Font("Verdana",1,20));
        ChooseBoard.setBounds(0,0,600,50);
        ChooseBoard.add(label);
        add(ChooseBoard);
       

        Board1Icon = new ImageIcon(getClass().getResource("board1_icon.png"));
        Board2Icon = new ImageIcon(getClass().getResource("board2_icon.png"));
        Board3Icon = new ImageIcon(getClass().getResource("board3_icon.png"));
        Board4Icon = new ImageIcon(getClass().getResource("board4_icon.png"));    
        Board5Icon = new ImageIcon(getClass().getResource("board5_icon.png"));
        Boards = new JPanel( );
        Boards.setLayout(new GridLayout(3,3));  
        Board1 = new JButton(Board1Icon);
        Board2 = new JButton(Board2Icon);
        Board3 = new JButton(Board3Icon);
        Board4 = new JButton(Board4Icon);
        Board5 = new JButton(Board5Icon);
        Start = new JButton("START"); // Start Game Button
        Load = new JButton("Load From File"); 
        BoardButtonHandler handler = new BoardButtonHandler( );
        Board1.addActionListener(handler);
        Board2.addActionListener(handler);
        Board3.addActionListener(handler);
        Board4.addActionListener(handler);
        Board5.addActionListener(handler);
        Load.addActionListener(handler);
        Start.addActionListener(handler);
        
        Boards.add(Board1);
        Boards.add(Board2);
        Boards.add(Board3);
        Boards.add(Board4);
        Boards.add(Board5);
        Boards.add(Load);

        Boards.setBounds(10, 50, 600, 600);
        add(Boards);
        GameTypePanel = new JPanel( );
        ComputerGameButton = new JRadioButton("Computer Game",false);
        UserGameButton = new JRadioButton("User Game",false);
        group= new ButtonGroup( );
        GameTypeHandler RadioButtonHandler = new GameTypeHandler( );
        
        ComputerGameButton.addActionListener(RadioButtonHandler);
        UserGameButton.addActionListener(RadioButtonHandler);
        group.add(ComputerGameButton);
        group.add(UserGameButton);
        GameTypePanel.setLayout( new GridLayout(1 , 3));
        GameTypePanel.add(UserGameButton);
        GameTypePanel.add(ComputerGameButton);
        GameTypePanel.add(Start);
        
     
        GameTypePanel.setBounds(10,660,600,25);
        add(GameTypePanel);
    
    }
    public void modifyBoardScreen( ) //Show avaible boards to user
    {
        if(boardFile == "board1")
        {
            Board1.setBackground(RedBackground);
            Board2.setBackground(null);
            Board3.setBackground(null);
            Board4.setBackground(null);
            Board5.setBackground(null);
            Load.setBackground(null);
        }   
        else if (boardFile =="board2")
        {
            Board1.setBackground(null);
            Board2.setBackground(RedBackground);
            Board3.setBackground(null);
            Board4.setBackground(null);
            Board5.setBackground(null);
            Load.setBackground(null);
        }
        else if(boardFile == "board3")
        {
            Board1.setBackground(null);
            Board2.setBackground(null);
            Board3.setBackground(RedBackground);
            Board4.setBackground(null);
            Board5.setBackground(null);
            Load.setBackground(null);
        }
        else if(boardFile =="board4")
        {
            Board1.setBackground(null);
            Board2.setBackground(null);
            Board3.setBackground(null);
            Board4.setBackground(RedBackground);
            Board5.setBackground(null);
            Load.setBackground(null);
        }
        else if(boardFile =="board5")
        {
            Board1.setBackground(null);
            Board2.setBackground(null);
            Board3.setBackground(null);
            Board4.setBackground(null);
            Board5.setBackground(RedBackground);
            Load.setBackground(null);

        }
        else if(boardFile == SpecialBoard)
        {

            Board1.setBackground(null);
            Board2.setBackground(null);
            Board3.setBackground(null);
            Board4.setBackground(null);
            Board5.setBackground(null);
            Load.setBackground(RedBackground);
        }
        else
        {
            Board1.setBackground(null);
            Board2.setBackground(null);
            Board3.setBackground(null);
            Board4.setBackground(null);
            Board5.setBackground(null);
            Load.setBackground(null);
        }
        Boards.revalidate();
    
    }
    
    

    private class BoardButtonHandler implements ActionListener 
    {
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource( ) == Start)
            {
                if(isBoardChoosen == true && isGameTypeChoosen == true)     
                {
                    GameWindow newGameScreen = new GameWindow(boardFile,gameType); 
                    newGameScreen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                    newGameScreen.setSize( 800, 850 ); // set frame size
                    newGameScreen.setVisible( true ); // display frame
                }
                else
                    JOptionPane.showMessageDialog( null,"Please choose the game board and game type from the menu","Lack of game elements warning", JOptionPane.WARNING_MESSAGE );

                
            }
            else if(event.getSource( ) == Board1)
            {
            
                boardFile = "board1";
                isBoardChoosen = true;
                modifyBoardScreen( );
            }

            else if(event.getSource( ) == Board2)
            {
                boardFile = "board2";
                isBoardChoosen = true;
                modifyBoardScreen( );

            }
            else if(event.getSource( ) == Board3)
            {
                boardFile = "board3";
                isBoardChoosen = true;
                modifyBoardScreen( );
            }
            else if(event.getSource( ) == Board4)
            {

                boardFile = "board4";
                isBoardChoosen = true;
                modifyBoardScreen( );
            }    
            else if(event.getSource( ) == Board5 )
            {
                boardFile = "board5";
                isBoardChoosen = true;
                modifyBoardScreen( );

            }
            else if(event.getSource( ) == Load) //Check if file is exist first, then Load new board from input file
            {
                try{
                    String fileName = JOptionPane.showInputDialog( "Enter the name of the file to load from(without txt): ");    
                    fileName = fileName;
                    File ReadFile = new File(fileName+".txt");
                    Scanner FileReader = new Scanner(ReadFile);
                    boardFile = fileName;
                    SpecialBoard = boardFile;
                    isBoardChoosen = true;
                    modifyBoardScreen( );
                    FileReader.close( );
                   
                }
                catch (FileNotFoundException e) {
                    System.out.println("ERROR!, File is not Found !");
                    JOptionPane.showMessageDialog( null,"Error!,your File is not found in the given directiory.","File Error Pane", JOptionPane.WARNING_MESSAGE );
                    isBoardChoosen = false;
                    SpecialBoard = "NULL";
                    modifyBoardScreen( );
                }
            }
        }

    }
    private class GameTypeHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(ComputerGameButton.isSelected( ))
            {
                gameType = 1; //Computer Game
                isGameTypeChoosen = true;
            }
            else if(UserGameButton.isSelected())
            {
                gameType = 2; //UserGame
                isGameTypeChoosen = true;
             
            }
        
        }
    }

}