import java.io.File;  //Read from file
import java.io.FileNotFoundException;  //Read from file exception 
import java.io.FileWriter; // Write to file
import java.io.IOException; //Write to file exception
import java.util.Scanner; 
import java.util.Random; // Random number generator

public class PegSolitaire implements PegSolitaireGameInterface
{
    
    public enum board_element {EMPTY,WALL,PEG};
    public enum Direction{LEFT, RIGHT, UP, DOWN};


    private static int TotalNumberOfPegs = 0;
    private Cell[][] game;
    private int game_type;
    private int total_row_count; //Total row count
    private int total_column_count;//Total column count
    private String boardContainer;
    public final board_element PegElement = board_element.PEG;
    public final board_element EmptyElement = board_element.EMPTY;
    public final board_element WallElement =  board_element.WALL;
    public final Direction LeftDirection = Direction.LEFT;
    public final Direction RightDirection = Direction.RIGHT;
    public final Direction UpDirection = Direction.UP;
    public final Direction DownDirection = Direction.DOWN;
    
    public PegSolitaire( )
    {

        game_type = 0;
        boardContainer ="board1";
        
        if(readFromFile(boardContainer)!=true)
            System.out.printf("\nAn error occured in read processing !\n");
    
    }
    public PegSolitaire(String boardContainerFile) 
    {
        boardContainer = boardContainerFile;
        
        if(readFromFile(boardContainerFile)!=true)
            System.out.printf("\nAn error occured in read processing !\n");
        
    }
    public final String getInitialBoard( )
    {
        return boardContainer;
    
    }
    public void setBoardName(String str)
    {
        boardContainer = str;
    }

    public boolean readFromFile(String boardFile)
    {
        int row_count = 0, counter = 0, column_counter = 0; 
        board_element PEG ;
        char column_letter = 97, letter;
        boardFile = boardFile + ".txt";
        try {
            File myReader = new File(boardFile);
            Scanner Reader = new Scanner(myReader);
                
            while (Reader.hasNextLine()) {
                    
                counter = 0;
                String str = Reader.nextLine();
                for(int i = 0; i < str.length( ) ; i++)
                {
                    if(str.charAt(i) == 'P' || str.charAt(i) =='.')
                        counter++;
                     
                }    
                
                if(counter >= column_counter)
                    column_counter = counter;

                row_count++;
            }
    
            total_row_count = row_count;
            total_column_count = column_counter;
            Reader.close();
            game = new Cell[row_count][column_counter];
             for(int i = 0 ; i < row_count; i++)
                 for(int j = 0; j < column_counter; j++)
                     game[i][j] = new Cell( );
            
            
            File myReader2 = new File(boardFile);
            Scanner Reader2 = new Scanner(myReader2);
            counter = 0;
            
            while (Reader2.hasNextLine()) {
                
                String str2 = Reader2.nextLine();
                
                for(int i = 0; i <str2.length( ) ; i++)
                {
                    letter = (char)(column_letter + i);
                    
                     if(str2.charAt(i) == 'P')
                         game[counter][i].setElement(counter,letter,board_element.PEG);
                     
                     else if(str2.charAt(i)=='.')
                         game[counter][i].setElement(counter,letter,board_element.EMPTY);
                    
                    else
                        game[counter][i].setElement(counter,letter,board_element.WALL);
                    
                }     
                
                counter++;
            }
          
            Reader2.close( ); 
            return true; //No error occured while reading process
        }
        catch (FileNotFoundException obj) {
            System.out.println("ERROR!, File Not Found !\n");
            return false;
        }
    
        
    }
    
    public boolean isValidMove(int row, int column, Direction Move) //Check whether given move is a legal move or not
    {
       
        boolean isValid = false;

        switch(Move){          
        
            case UP:
                if( (row-2)>=0 && game[row-1][column].checkElement(board_element.PEG) && game[row][column].checkElement(board_element.PEG))
                    if(game[row-2][column].checkElement(board_element.EMPTY))
                        isValid = true;
                    else
                        isValid =false;
                else
                    isValid = false;
                
                 break;
            
            case DOWN:
                if(( (row+2) < total_row_count) && game[row+1][column].checkElement(board_element.PEG) && game[row][column].checkElement(board_element.PEG))
                    if(game[row+2][column].checkElement(board_element.EMPTY))
                        isValid = true;
                    else
                        isValid = false;
                else
                    isValid = false;
                
                break;
            
            case LEFT:
                if( ((column-2)>=0 ) && game[row][column-1].checkElement(board_element.PEG) && game[row][column].checkElement(board_element.PEG))
                    if( game[row][column-2].checkElement(board_element.EMPTY))
                        isValid = true;
                    else
                        isValid = false;
                else
                    isValid = false;
                break;
            case RIGHT:
                if( ((column+2) < total_column_count) && game[row][column+1].checkElement(board_element.PEG) && game[row][column].checkElement(board_element.PEG))
                    if(game[row][column + 2].checkElement(board_element.EMPTY))
                        isValid = true;
                    else
                        isValid = false;
                else
                    isValid = false;        
                
                break;

            default:
                isValid = false;
                break;
                    
        }
        
        return isValid;
    }
    public final boolean isGameOver() 
    {
        boolean game_end_control = false; 
        
        for(int row = 0; row < total_row_count; row++){
            for(int column = 0; column < total_column_count; column++){
                if( (( row - 2 )>=0 ) && (isValidMove(row, column, Direction.UP)==true)){   /* UP */
                    game_end_control = true;
                    break;
                }
                if(((row + 2) < total_row_count) && (isValidMove(row,column,Direction.DOWN)==true)){ /* DOWN */
                    game_end_control = true;
                    break;
                }
                if( ((column-2) >= 0) && (isValidMove(row,column, Direction.LEFT)==true)){ /* LEFT */
                    game_end_control = true;
                    break;
                }
                if(((column + 2) < total_column_count) && (isValidMove(row,column, Direction.RIGHT)==true)){ /* RIGHT */
                    game_end_control = true;
                    break;
                }
            }
        }
        
         if(game_end_control == true)
            return false; /* The game is not finished */
        else 
            return true; /* The game is finished */  
            
        
    }
    public void play(int row_index, int column_index, char givenDirection) //Set user move
    {
        int row = row_index, column = column_index; 
        char column_element = 'a';
        Direction Move = Direction.RIGHT; // Assign a random random to initialize Direction
        
    


        if(givenDirection == 'L')
            Move = Direction.LEFT;
        else if(givenDirection == 'R')
            Move = Direction.RIGHT;
        else if(givenDirection =='U')
            Move = Direction.UP;
        else if(givenDirection == 'D')
            Move = Direction.DOWN;

        
            column_element = (char)(column_element + column_index );
    
        switch(Move){                    /* According to choosen movement direction and row and column index, modify the current game board */
            case LEFT:
                
                game[row][column].setElement(row, (char)(column_element), board_element.EMPTY);
                game[row][column-1].setElement(row, (char)(column_element - 1), board_element.EMPTY);
                game[row][column-2].setElement(row, (char)(column_element - 2), board_element.PEG);  
                break;
            
            case RIGHT:
                game[row][column].setElement(row, (char)(column_element), board_element.EMPTY);
                game[row][column+1].setElement(row, (char)(column_element + 1), board_element.EMPTY);
                game[row][column+2].setElement(row, (char)(column_element + 2), board_element.PEG);
                break;
            
            case UP:
    
                game[row][column].setElement(row, column_element, board_element.EMPTY);
                game[row-1][column].setElement(row-1, column_element, board_element.EMPTY);
                game[row-2][column].setElement(row-2, column_element, board_element.PEG);
                break;
    
            case DOWN:
                
                game[row][column].setElement(row, column_element, board_element.EMPTY);
                game[row+1][column].setElement(row+1, column_element, board_element.EMPTY);
                game[row+2][column].setElement(row+2, column_element, board_element.PEG);
                break;
        }
    
    }
    public void play( ){ //No argument void play function for Computer game
       
        int column_count = total_column_count, row_count = total_row_count;
        boolean valid_move = false, end_condition = false;
        char ch = 'a';
        Random rand = new Random( );
        
        while(end_condition != true){
            ch = 'a';
            int random_row_number = rand.nextInt(total_row_count); /* generate random row number in valid interval */
            int random_column_number = rand.nextInt(total_column_count); /* generate random column number in valid interval */
            for(int i = 0; i < random_column_number; ++i)  /* To turn random column number into regarding letter */
                ch = (char)(ch + 1);
                    
                for(int i = 0; i < column_count; ++i){
                    if( random_column_number == column_count ){
                        random_column_number = 0;
                        ch = 'a';
                    }
                        
                    if(isRandomMoveValid(random_row_number, random_column_number, ch)==true){ /* If return value is true, it means the move was legal and played */
                        valid_move = true;
                        break;                                                                /* Break the loop and make another random move till game is over */
                    }
                    random_column_number+=1;
                    ch = (char)(ch + 1);
                }        
                if(valid_move == true ){
                    end_condition = true;
                    break;
                }    
        }

    }
    public boolean isRandomMoveValid(int row, int column, char column_letter){
        
        int random_direction = 0;
        boolean value = false;
        Random rand = new Random( );
        random_direction = rand.nextInt(4);
        

        for(int i = 0; i < 4; ++i){       /* At first, check if random movement direction which is generated by number generator is legal or not*/
                                        /* If its not valid try other movement directions to get a random index */
            if(random_direction == 4)
                random_direction = 0;
            
            if(random_direction == 0){  /* LEFT */                      /* Increment row because setElement function will decrement it */
                if(isValidMove(row, column, Direction.LEFT) == true) { /* Check if computer's move is legal toward LEFT direction */
                    game[row][column].setElement(row,column_letter, board_element.EMPTY); /* Modify the game board */
                    game[row][column-1].setElement(row, (char)(column_letter - 1), board_element.EMPTY);
                    game[row][column-2].setElement(row, (char)(column_letter - 2), board_element.PEG);
                    value = true;
                    break;
                }
            }
            else if(random_direction == 1){/* RIGHT */
        
                if(isValidMove(row, column, Direction.RIGHT)==true){
                    game[row][column].setElement(row, column_letter, board_element.EMPTY);
                    game[row][column+1].setElement(row, (char)(column_letter + 1), board_element.EMPTY);
                    game[row][column+2].setElement(row, (char)(column_letter + 2), board_element.PEG);
                    value = true;
                    break;
                }
            }
            else if(random_direction == 2){ /* UP */
        
                if(isValidMove(row, column, Direction.UP)==true){
                    game[row][column].setElement(row, column_letter, board_element.EMPTY);
                    game[row-1][column].setElement(row - 1, column_letter, board_element.EMPTY);
                    game[row-2][column].setElement(row - 2, column_letter, board_element.PEG);
                    value=true;
                    break;
                }
            }
            else if(random_direction == 3){ /* DOWN */

                if(isValidMove(row, column, Direction.DOWN)==true){
                    game[row][column].setElement(row, column_letter, board_element.EMPTY);
                    game[row+1][column].setElement(row + 1, column_letter, board_element.EMPTY);
                    game[row+2][column].setElement(row + 2, column_letter, board_element.PEG);
                    value = true;
                    break;
                }
            }
            
                random_direction++;  
        }
        
        return value;
    }    
    

    public final void display( )
    {
        char ch='a';
        int counter = 1;
        
        System.out.printf("  ");
        
        for(int i=0; i < total_column_count; ++i){ /* Column letters */
            System.out.printf("%c ",ch);
            ch = (char)(ch + 1);
            
        }
        System.out.printf("\n");
        for(int i=0; i < total_row_count; ++i){
            System.out.printf("%d ",counter);
            for(int j=0; j < total_column_count; ++j){
                if(game[i][j].checkElement(board_element.WALL)==true)   
                    System.out.printf("  ");
                else if(game[i][j].checkElement(board_element.PEG)==true)
                    System.out.printf("P ");
                else if(game[i][j].checkElement(board_element.EMPTY)==true)
                    System.out.printf(". ");    
            }
            counter++;
            System.out.printf("\n");
        }
    }
    public final void write_to_file(String file_name)
    {
        String saveFile = file_name + ".txt";
        try{    
            FileWriter Writer = new FileWriter(saveFile);
            for(int i = 0; i < total_row_count; i++)
            {
                for(int j = 0; j < total_column_count; j++)
                {
                    if(game[i][j].checkElement(board_element.PEG))
                        Writer.write("P");
                    else if(game[i][j].checkElement(board_element.WALL))
                        Writer.write(" ");
                    else if(game[i][j].checkElement(board_element.EMPTY))
                        Writer.write(".");
                }
                Writer.write("\n");
            }
            Writer.close( );
            
        }
        catch(IOException e)
        {
            System.out.printf("An Error occured while writing to file\n");

        }

    }
    public final int getRowSize( )
    { 
        return total_row_count;
    }
    public final int getColumnSize( )
    {
        return total_column_count;
    }
    public final boolean checkCellElement(int row, int column, board_element Element)
    {
        if(game[row][column].checkElement(Element) == true)
            return true;
        else 
            return false;
    }
    
    public final int getScore( )
    {
        int score = 0;
        for(int i = 0; i < total_row_count; i++)
        {
            for(int j = 0; j < total_column_count; j++)
            {
                if(game[i][j].checkElement(board_element.PEG))
                    score++;
            }
        }
        return score;
    }    
    
    /* mapleri oluştur ve text içinde tut */
    /* initialize edilirken Load ile çekip 2D array oluştur */
    public class Cell
    {
    
        private int row;
        private char column;
        private board_element element;

        
        public Cell( )
        {
            
            row = 0;
            column = 0;
            element = board_element.WALL;

        }
        public final boolean checkElement(board_element elm)
        {
            if( elm == element)
                return true;
            else
                return false;
        }
        
        public void setElement(int row_index,char column_index, board_element element_type)
        {
            row = row_index;
            column = column_index; 
            element = element_type;

        }

   
    }



}