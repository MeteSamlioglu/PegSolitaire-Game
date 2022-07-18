import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import javax.swing.Timer;


public class GameWindow extends JFrame implements Cloneable
{
    private JPanel GameBoard;
    private JPanel Selections;
    private JButton GameButton[][];
    private JButton PegButton;
    private JButton EmptyButton;
    private JButton ChoosenPegButton;
    private JButton Save;
    private JButton Load;
    private JButton Undo;
    private JButton Reset;
    private Icon PegIcon;
    private Icon EmptyIcon;
    private Icon ChoosenPegIcon;
    private PegSolitaire Game;
    private boolean isPressed = false;
    private final boolean isComputerGame;
    private int PressedRowIndex;
    private int PressedColumnIndex;
    private Timer ComputerPlayTimer;


    public GameWindow(String boardFile, int GameType)
    {
        super("PegSolitaire Game");
        setLayout(null);
        Selections = new JPanel( ); //Selection Panel
        Selections.setLayout(new GridLayout(4,1));
        Save = new JButton("Save");
        Load = new JButton("Load");
        Undo = new JButton("Undo");
        Reset = new JButton("Reset");
        Selections.add(Save);
        Selections.add(Load);
        Selections.add(Undo);
        Selections.add(Reset);
        Game = new PegSolitaire(boardFile);
        PegIcon = new ImageIcon(getClass().getResource("peg.png"));
        EmptyIcon = new ImageIcon(getClass().getResource("empty.png"));
        ChoosenPegIcon = new ImageIcon(getClass().getResource("choosenCell.png"));
        
        SelectionHandler handler2 = new SelectionHandler( );
        Save.addActionListener(handler2);
        Load.addActionListener(handler2);
        Reset.addActionListener(handler2);
        Undo.addActionListener(handler2);
        
        Selections.setBounds(650,0,80,750);
        add(Selections);
        
        if(GameType == 1){  
            isComputerGame = true;
            ComputerGame( );
        }
        else
            isComputerGame = false;   
        
        CreateGameBoard( );
        
    }
    
    public void ComputerGame(){ // Computer plays randomly, I used swing Timer to repaint GameBoard 

        AutoPlay  CompueterPlayHandler = new AutoPlay( );
        ComputerPlayTimer = new Timer(500, CompueterPlayHandler);
        ComputerPlayTimer.setInitialDelay(500);
        ComputerPlayTimer.start( );
       
    }

    public void modifyGameBoard( ) // Make some modifications and after that repaint game board 
    {
        for(int i = 0; i < Game.getRowSize( ); i++)
        {
            for(int j = 0; j <Game.getColumnSize( ); j++)
            {
                if(Game.checkCellElement(i,j,Game.PegElement))
                   GameButton[i][j].setIcon(PegIcon);
                
                else if(Game.checkCellElement(i,j,Game.EmptyElement))
                    GameButton[i][j].setIcon(EmptyIcon);
              
                else if(Game.checkCellElement(i,j,Game.WallElement))
                    GameButton[i][j].setIcon(null);      
            }
        }
        GameBoard.revalidate( );
        GameBoard.repaint();
    
    
    }
    public void CreateGameBoard( ) //Create a new game board
    {
        GameBoard = new JPanel( ); // Game Board Panel
        GameButton = new JButton[Game.getRowSize( )][Game.getColumnSize( )];
        GameBoard.setLayout( new GridLayout(Game.getRowSize( ),Game.getColumnSize( )));
        ButtonHandler handler = new ButtonHandler( );
        
        for(int i = 0; i < Game.getRowSize( ); i++){
            for(int j = 0; j < Game.getColumnSize( ); j++ )
            {
                if(Game.checkCellElement(i,j,Game.PegElement)){
                    GameButton[i][j] = new JButton(PegIcon);
                    GameButton[i][j].setContentAreaFilled(true);
                    GameButton[i][j].setBorderPainted(false);
                    GameButton[i][j].addActionListener(handler);
                    GameBoard.add(GameButton[i][j]);
                    
                }
                else if(Game.checkCellElement(i,j,Game.EmptyElement))
                {
                    GameButton[i][j] = new JButton(EmptyIcon);
                    GameButton[i][j].setContentAreaFilled(true);
                    GameButton[i][j].setBorderPainted(false);
                    GameButton[i][j].addActionListener(handler);
                    GameBoard.add(GameButton[i][j]);
                }
                else if(Game.checkCellElement(i,j,Game.WallElement)){
                    GameButton[i][j] = new JButton( );
                    GameButton[i][j].setContentAreaFilled(true);
                    GameButton[i][j].setBorderPainted(false);
                    GameButton[i][j].addActionListener(handler);
                    GameBoard.add(GameButton[i][j]);
                }
            }
        
        }
        
        GameBoard.setBounds(0,0,650,750);
        add(GameBoard);
    }

    public int findDirection(int row, int column) //Returning 0 represents Right, 1 represents Left, 2 represents Up, 3 Represents Down, -1  Not a valid direction
    {
        int direction = -1;
        
        if(Game.checkCellElement(row, column, Game.EmptyElement))
        {
            if(PressedColumnIndex == column){
                if(row < PressedRowIndex)
                    direction = 2; //UP
                else
                    direction = 3; //DOWN

            }
            else if(PressedRowIndex == row)
            {
                if(column < PressedColumnIndex)
                    direction = 1; //LEFT
                else 
                    direction = 0; // RIGHT
            }
        }

        return direction;
    }
    
    public void setUserMove(int row, int column, char Element) //Set user's move on GameBoard
    {
       
        
        if(isPressed == false){ //If user clicks any PegElements on the board this if block will start
           
            if(Element=='P'){
                isPressed = true;
                PressedRowIndex = row;
                PressedColumnIndex = column;
                GameButton[row][column].setIcon(ChoosenPegIcon);
                GameBoard.revalidate( );
                GameBoard.repaint( );
           }
        }
        else
        {
            
            if( (PressedColumnIndex == column ) && (PressedRowIndex == row) )
            {
                GameButton[PressedRowIndex][PressedColumnIndex].setIcon(PegIcon);
                GameBoard.revalidate( );
                GameBoard.repaint( );
            }
            else if(Element=='P')
            {
                GameButton[PressedRowIndex][PressedColumnIndex].setIcon(PegIcon);
                PressedRowIndex = row;
                PressedColumnIndex = column;
                GameButton[PressedRowIndex][PressedColumnIndex].setIcon(ChoosenPegIcon);

            }
            else{
                
                int direction = findDirection(row,column);
                Game.write_to_file("Undo");
                if(direction == 0) //RIGHT
                {
                    if(Game.isValidMove(PressedRowIndex,PressedColumnIndex,Game.RightDirection) && (PressedColumnIndex + 2 == column)){
                        Game.play(PressedRowIndex,PressedColumnIndex, 'R');
                        isPressed = false;
                        modifyGameBoard( );
                    }
                }
                else if(direction == 1) //LEFT
                {
                    if(Game.isValidMove(PressedRowIndex,PressedColumnIndex,Game.LeftDirection) && (PressedColumnIndex - 2 == column)){
                        Game.play(PressedRowIndex, PressedColumnIndex, 'L');
                        isPressed = false;
                        modifyGameBoard( );
                    }
                }
                else if(direction == 2) //UP
                {
                    if(Game.isValidMove(PressedRowIndex,PressedColumnIndex,Game.UpDirection) && (PressedRowIndex - 2 == row)){
                        Game.play(PressedRowIndex, PressedColumnIndex, 'U');
                        isPressed = false;
                        modifyGameBoard( );
                    }
                }
                else if(direction == 3)//DOWN
                {
                    if(Game.isValidMove(PressedRowIndex,PressedColumnIndex,Game.DownDirection) && (PressedRowIndex + 2 == row)){
                        Game.play(PressedRowIndex, PressedColumnIndex, 'D');
                        isPressed = false;
                        modifyGameBoard( );
                    }
                }
                else
                    modifyGameBoard( );
            
            
                if(Game.isGameOver( )==true)
                JOptionPane.showMessageDialog( null, String.format("Game is over! Your score is %d",Game.getScore( )),"Game Over Pane", JOptionPane.INFORMATION_MESSAGE );
            
            }    
        }
    }
    
    public Object clone( ) throws CloneNotSupportedException
    {
    	 PegSolitaire cloneGame = (PegSolitaire)super.clone(); 				
    	 return cloneGame;
    
    }

    private class ButtonHandler implements ActionListener
    
    {
        public void actionPerformed(ActionEvent event)
        {
            
        
            for(int i = 0; i < Game.getRowSize( ); i++)
            {
                for(int j = 0; j < Game.getColumnSize( ); j++ )
                {
                    if(event.getSource( ) == GameButton[i][j])
                    {
                        if(Game.checkCellElement(i,j,Game.PegElement)){
                          
                            setUserMove(i,j,'P');
                          
                            break;
                        }
                        else if(Game.checkCellElement(i,j,Game.EmptyElement))
                            setUserMove(i,j,'E');
                    }
                
                
                
                }   
            }
        }
    
    }
    private class SelectionHandler implements ActionListener
    {
        
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource() == Save){ //Save Button
                
                if(isComputerGame == true)
                    ComputerPlayTimer.stop( );
                
                String fileName = JOptionPane.showInputDialog( "Enter the name of the file to save to(without txt): ");
                Game.write_to_file(fileName);
                if(isComputerGame == true)
                    ComputerGame( );
            }
            if(event.getSource() == Load){//Load Button
                 if(isComputerGame == true)
                    ComputerPlayTimer.stop( );
                 
                String fileName2  = Game.getInitialBoard( );
                fileName2 = JOptionPane.showInputDialog( "Enter the name of the file to load from(without txt): ");
                 
                 if(Game.readFromFile(fileName2) == true)
                 {  
                    Game.setBoardName(fileName2);
                    getContentPane().remove(GameBoard);
                    CreateGameBoard( );
                    Game.write_to_file("Undo");
                    revalidate();
                    repaint();
                 }
                 else
                    JOptionPane.showMessageDialog( null,"Your input file is not valid !","ILLEGAL LOAD INPUT ", JOptionPane.WARNING_MESSAGE );
                
                
                 if(isComputerGame == true)
                    ComputerGame( );
            
            }
            if(event.getSource() == Reset)
            {
                if(isComputerGame == true)
                    ComputerPlayTimer.stop( );
                
                 String initialFile = Game.getInitialBoard( );
                 
                 if(Game.readFromFile(initialFile)==true)
                 {
                    Game.write_to_file("Undo");
                    modifyGameBoard( );  
                 }
                 if(isComputerGame == true)    
                    ComputerGame( );
            }
            if(event.getSource( ) == Undo)
            {
                if(isComputerGame == true)
                    ComputerPlayTimer.stop( );
                
                if(Game.readFromFile("Undo")==true);
                    modifyGameBoard( );  
                
                if(isComputerGame == true)
                    ComputerGame( );
            }   
        }
    }
    private class AutoPlay implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
                Game.write_to_file("Undo");
                Game.play( );
                modifyGameBoard( );
                if(Game.isGameOver( )){
                    JOptionPane.showMessageDialog( null, String.format("Game is over! Computer's score is %d",Game.getScore( )),"Game Over Pane", JOptionPane.INFORMATION_MESSAGE );
                    Timer timer2 = (Timer)e.getSource( );
                    timer2.stop( );
               }         
        }
    }

}