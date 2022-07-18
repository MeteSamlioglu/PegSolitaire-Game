public interface PegSolitaireGameInterface
{
    
    public enum Direction{LEFT, RIGHT, UP, DOWN}; // Game Directions
    public enum board_element {EMPTY,WALL,PEG}; // Board elements
    public String getInitialBoard( ); // Get the name of the File which contains Board
    public boolean readFromFile(String boardFile); //Read board from file
    public boolean isGameOver( ); // Check whether game is over
    public void play(int row_index, int column_index, char givenDirection); //User plays one move
    public void play( ); //Computer plays one move
    public boolean isRandomMoveValid(int row, int column, char column_letter); //Check if computer's random move is valid
    public void display( ); // Display board on ubuntu terminal
    public void write_to_file(String file_name); //Write current board to file
    public int getRowSize( ); // Get total row count
    public int getColumnSize( ); // Get total column count
    public int getScore( ); // Get score
    public void setBoardName(String str); // Set Boards name

}