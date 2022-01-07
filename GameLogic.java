import java.io.FileWriter;
import java.io.IOException;

public class GameLogic {
    private char board[][];
    protected int moves;
    protected int boardSize;
    private char turn = 'r';
    private GUI gui;
    private FileWriter f;
    private boolean record=false;
    protected Player red;
    protected Player blue;
    
    
        public void buildBoard(int size){
            boardSize = size;
            board = new char[boardSize][boardSize];
        }

        public void setBoard(char[][] tempBoard){
            board = tempBoard;
        }

        public void setGUI(GUI guiClass){
            gui = guiClass;
        }

        public GUI getGUI(){
          return gui;
        }

        public void setRecord(boolean rec){
          record = rec;
        }

        public void setPlayers(Boolean redPlayer, Boolean bluePlayer){
          if (redPlayer){
            red = new Player();
          }
          else {
          red = new Computer();
          ((Computer) red).setLogic(this);
          }
          if (bluePlayer){
            blue = new Player();
          }
          else {
          blue = new Computer();
          ((Computer) blue).setLogic(this);
          }
        }

        public char[][] getBoard(){
            return board;
        }
        
        public void updateBoard(int x, int y, char currentLetter){
            int xpos = x;
            int ypos = y;
            board[xpos][ypos] = currentLetter;
        }

        public void clearBoard(int x, int y){
          board[x][y] = 0;
        }

        public void setLetter(char letter, char playerTurn){
          if (playerTurn == 'r')
            red.setLetter(letter);
          if (playerTurn == 'b')
            blue.setLetter(letter);
        }

        public void handleComputerTurn(){
          if (turn == 'r')
            ((Computer) red).makeMove();
          else 
            ((Computer) blue).makeMove();
        }

        public void startRecord() throws IOException{
            f = new FileWriter("Output.txt");
          if (this instanceof SimpleGame){
            f.write("Simple Game \n");
          }
          else if (this instanceof GeneralGame){
            f.write("General Game \n");
          }
        }

        public void recordTurn(int x, int y, char turn, char letter) throws IOException{
          if (moves == 0){
              startRecord();
            }
          if (turn == 'r')
            f.write("Red Turn: " + (x+1) + ", " + (y+1) + " - " + letter + "\n");
          else 
            f.write("Blue Turn: " + (x+1) + ", " + (y+1) + " - " + letter + "\n");
            f.write("\tRed Score: " + red.getScore() + " Blue Score: " + blue.getScore() + "\n");
        }

        public void endRecord(String end) throws IOException{
          f.write(end);
          f.close();
        }

        // Checks the around the letter placement to see if SOS's have been completed
        public int checkSOS(int x, int y, char currentLetter, boolean draw){
          int xpos = x;
          int ypos = y;
          int score = 0;
    
            // Checks all 8 S placement configurations and adds them up
            if (board[xpos][ypos] == 'S'){
                if ((xpos <= boardSize-3) && (board[xpos+1][ypos] == 'O') && (board[xpos+2][ypos] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos, xpos+2, ypos);
                }
                if (((xpos <= boardSize-3) && (ypos <= boardSize-3)) && (board[xpos+1][ypos+1] == 'O') && (board[xpos+2][ypos+2] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos, xpos+2, ypos+2);
                }
                if ((ypos <= boardSize-3) && (board[xpos][ypos+1] == 'O') && (board[xpos][ypos+2] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos, xpos, ypos+2);
                }
                if (((xpos >= 2) && (ypos <= boardSize-3)) && (board[xpos-1][ypos+1] == 'O') && (board[xpos-2][ypos+2] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos, xpos-2, ypos+2);
                }
                if ((xpos >= 2) && (board[xpos-1][ypos] == 'O') && (board[xpos-2][ypos] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos, xpos-2, ypos);
                }
                if (((xpos >= 2) && (ypos >= 2)) && (board[xpos-1][ypos-1] == 'O') && (board[xpos-2][ypos-2] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos, xpos-2, ypos-2);
                }
                if ((ypos >= 2) && (board[xpos][ypos-1] == 'O') && (board[xpos][ypos-2] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos, xpos, ypos-2);
                }
                if (((xpos <= boardSize-3) && (ypos >= 2)) && (board[xpos+1][ypos-1] == 'O') && (board[xpos+2][ypos-2] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos, xpos+2, ypos-2);
                }
            }
 
            // Checks all 4 O placement configurations and adds them up
            else if (board[xpos][ypos] == 'O'){
                if (((xpos >= 1) && (xpos <= boardSize-2)) && (board[xpos-1][ypos] == 'S') && (board[xpos+1][ypos] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos-1, ypos, xpos+1, ypos);
                }
                if (((xpos <= boardSize-2) && (ypos <= boardSize-2) && (xpos >= 1) && (ypos >= 1)) && (board[xpos-1][ypos-1] == 'S') && (board[xpos+1][ypos+1] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos-1, ypos-1, xpos+1, ypos+1);
                }
                if ((ypos >= 1) && (ypos <= boardSize-2) && (board[xpos][ypos+1] == 'S') && (board[xpos][ypos-1] == 'S')){
                    score++;
                    if (draw)
                        gui.drawLine(xpos, ypos+1, xpos, ypos-1);
                }
                if ((xpos <= boardSize-2) && (ypos <= boardSize-2) && (xpos >= 1) && (ypos >= 1) && ((board[xpos-1][ypos+1] == 'S') && (board[xpos+1][ypos-1] == 'S'))){
                    score++;
                    if (draw)
                        gui.drawLine(xpos-1, ypos+1, xpos+1, ypos-1);
                }
            }
          return score;
        }

        // Checks if a game has ended
        public boolean checkEnd(int redScore, int blueScore){
            return false;
        }

        // Ends a game if the turns are being recored to an output text
        public void signalEnd(){
          String endState="";
                  if (red.getScore() == blue.getScore()){
                    endState = "Draw";
                  }
                  else if (red.getScore() > blue.getScore()){
                    endState = "Red Wins";
                  }
                  else if (red.getScore() < blue.getScore())
                    endState = "Blue Wins";
                  if (record){
                    try {
                      endRecord(endState);
                    } catch (IOException e) {
             }
          }
          gui.printEndScreen(endState);
        }

        // Updates logical and GUI elements for a player turn
        public void handleTurn(int x, int y) {
            if (board[x][y] == 0) {
                
                gui.board[x][y].setToken(turn, x, y);
                int score = 0;
                char letter = ' ';
                if (turn == 'r'){
                  letter = red.getLetter();
                  if (letter == 'S'){
                    updateBoard(x, y, 'S');
                    score = checkSOS(x, y, 'S', true);
                  }
                  else {
                    updateBoard(x, y, 'O');
                    score = checkSOS(x, y, 'O', true);
                  }
                  if (score > 0){
                    red.setScore(score);
                    gui.setScore('r', red.getScore());
                  }
                  else {turn = 'b';
                  gui.switchTurns();
                  }
                  if (record)
                    try {
                      recordTurn(x, y, 'r', letter);
                    } catch (IOException e) {
                  }
                  }
                else if (turn == 'b'){
                  letter = blue.getLetter();
                  if (letter == 'S'){
                    updateBoard(x, y, 'S');
                    score = checkSOS(x, y, 'S', true);
                  }
                  else {
                    updateBoard(x, y, 'O');
                    score = checkSOS(x, y, 'O', true);
                  }
                  if (score > 0){
                    blue.setScore(score);
                    gui.setScore('b', blue.getScore());
                  }
                  else {
                    turn = 'r';
                    gui.switchTurns();
                  }
                  if (record)
                    try {
                      recordTurn(x, y, 'b', letter);
                    } catch (IOException e) {
                  }
                }
                moves += 1;
                gui.showCurrentTurn();
                if (checkEnd(red.getScore(), blue.getScore())){
                  signalEnd();
                }
            }
        }
    }

// A class for the simple game type to handle the unique win condition
class SimpleGame extends GameLogic {
  public boolean checkEnd(int redScore, int blueScore){
    if ((redScore > 0)  || (blueScore> 0) || (moves == (boardSize*boardSize))) {
      return true;
  }
  else return false;
  }
}

// A class for the simple game type to handle the unique win condition
class GeneralGame extends GameLogic {
  public boolean checkEnd(int redScore, int blueScore){
    if (moves == (boardSize*boardSize)){
      return true;
    }
    else return false;
  }
}

