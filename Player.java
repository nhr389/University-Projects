import java.util.Arrays;
import java.util.Random;

// Class that handles all information connected to a player
public class Player{
    private int score = 0;
    protected char letter = 'S';
    
    public void setScore(int s){
        score += s;
    }

    public void setLetter(char c){
        letter = c;
    }

    public int getScore(){
        return score;
    }

    public char getLetter(){
        return letter;
    }
}

// A class that extends the player if it is a computer
class Computer extends Player {
    private int[][] scoringMoves;
    private int[][] nonScoringMoves; 
    private GameLogic logic;
 
    public void setLogic(GameLogic tempLogic){
        logic = tempLogic;

    }

    // Checks each move on the board and sorts them by scoring and nonscoring
    public void checkMoves(){
        int boardSize = logic.boardSize;
        scoringMoves = new int[0][3];
        nonScoringMoves = new int[0][3];
        for (int x=0; x < boardSize; x++){
            for (int y=0; y< boardSize; y++){
                if (logic.getBoard()[x][y] == 0){
                    logic.updateBoard(x, y, 'S');
                    if (logic.checkSOS(x, y, 'S', false) > 0){
                        scoringMoves = Arrays.copyOf(scoringMoves, scoringMoves.length + 1);
                        scoringMoves[scoringMoves.length - 1] = new int[]{x, y, 0};
                    }
                    else{
                        nonScoringMoves = Arrays.copyOf(nonScoringMoves, nonScoringMoves.length + 1);
                        nonScoringMoves[nonScoringMoves.length - 1] = new int[]{x, y, 0};
                    }
                    logic.clearBoard(x, y);
                    logic.updateBoard(x, y, 'O');
                    if (logic.checkSOS(x, y, 'O', false) > 0){
                        logic.updateBoard(x, y, 'O');
                        scoringMoves = Arrays.copyOf(scoringMoves, scoringMoves.length + 1);
                        scoringMoves[scoringMoves.length - 1] = new int[]{x, y, 1};
                    }
                    else{
                        nonScoringMoves = Arrays.copyOf(nonScoringMoves, nonScoringMoves.length + 1);
                        nonScoringMoves[nonScoringMoves.length - 1] = new int[]{x, y, 1};
                    }
                    logic.clearBoard(x, y);
                }
            }
        }
    }

    // Chooses a move from the avaliable move pools
    public void makeMove(){
        checkMoves();
        Random rand = new Random();
        int[] move = new int[3];
        if (scoringMoves.length != 0){
            move = scoringMoves[rand.nextInt(scoringMoves.length)];
        }
        else {
            int rando = rand.nextInt(nonScoringMoves.length);
            move = nonScoringMoves[rando];
        }
        if (move[2] == 0)
            letter = 'S';
        else
            letter = 'O';
        logic.getGUI().setLetter(letter);
        logic.handleTurn(move[0], move[1]);
    }
}
    
