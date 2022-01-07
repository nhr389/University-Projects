import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class GUI extends Application {

    // Calls basic GUI elements present at start
    private char currentTurn = 'r';
    private Label systemMessage = new Label("Current Turn: ");
    private RadioButton sRed = new RadioButton("S");
    private RadioButton oRed = new RadioButton("O");
    private RadioButton sBlue = new RadioButton("S");
    private RadioButton oBlue = new RadioButton("O");
    private RadioButton humanRed = new RadioButton("Human Player");
    private RadioButton computerRed = new RadioButton("Computer Player");
    private RadioButton humanBlue = new RadioButton("Human Player");
    private RadioButton computerBlue = new RadioButton("Computer Player");
    private Text redScore = new Text("0");
    private Text blueScore = new Text("0");
    private GridPane pane = new GridPane();
    private BorderPane border = new BorderPane();
    private Pane lineLayer = new Pane();
    private GameLogic logic = new GameLogic();
    private EventHandler<ActionEvent> setBoard;
    Cell[][] board =  new Cell[0][0];

    // Sets up the GUI elements
    public void start(Stage primaryStage) {

        // Information to be presented at the top
        primaryStage.setTitle("S.O.S.");
        Text title = new Text("S.O.S.");
        RadioButton simpleGameType = new RadioButton("Simple Game");
        simpleGameType.setSelected(true);
        RadioButton generalGameType = new RadioButton("General Game");
        ToggleGroup gameType = new ToggleGroup();
        simpleGameType.setToggleGroup(gameType);
        generalGameType.setToggleGroup(gameType);
        Spinner<Integer> gameSizeSpinner = new Spinner<>(1, 10, 0, 1);
        CheckBox recordCheckBox = new CheckBox("Record");
        Button startButton = new Button("Start Game");
        VBox topVbox = new VBox();
        topVbox.getChildren().addAll(title, simpleGameType, generalGameType, gameSizeSpinner, recordCheckBox, startButton);

        // Player information to be presented on each side
        ToggleGroup blueLetters = new ToggleGroup();
        sBlue.setToggleGroup(blueLetters);
        sBlue.setSelected(true);
        oBlue.setToggleGroup(blueLetters);
        ToggleGroup bluePlayer = new ToggleGroup();
        humanBlue.setToggleGroup(bluePlayer);
        humanBlue.setSelected(true);
        computerBlue.setToggleGroup(bluePlayer);
        Text blueTitle = new Text("Blue Player");
        VBox rhsVbox = new VBox(5);
        rhsVbox.getChildren().addAll(blueTitle, sBlue, oBlue, blueScore, humanBlue, computerBlue);

        ToggleGroup redLetters = new ToggleGroup();
        sRed.setToggleGroup(redLetters);
        sRed.setSelected(true);
        oRed.setToggleGroup(redLetters);
        ToggleGroup redPlayer = new ToggleGroup();
        humanRed.setToggleGroup(redPlayer);
        humanRed.setSelected(true);
        computerRed.setToggleGroup(redPlayer);
        Text redTitle = new Text("Red Player");
        VBox lhsVbox = new VBox(5);
        lhsVbox.getChildren().addAll(redTitle, sRed, oRed, redScore, humanRed, computerRed);

        // Sets up a new game when new game button is pressed, reseting temporary elements
        setBoard = new EventHandler<ActionEvent>(){
          public void handle(ActionEvent e)
          {
            if (simpleGameType.isSelected()){
              logic = new SimpleGame();
            }
            else logic = new GeneralGame();
            logic.setRecord(recordCheckBox.isSelected());
            currentTurn = 'r';
            showCurrentTurn();
            logic.setGUI(GUI.this);
            logic.setPlayers(humanRed.isSelected(), humanBlue.isSelected());
            redScore.setText("0");
            blueScore.setText("0");
            sRed.setSelected(true);
            sBlue.setSelected(true);
            int gridSize = gameSizeSpinner.getValue();
            pane = new GridPane(); 
            board = new Cell[gridSize][gridSize];
            for (int i = 0; i < gridSize; i++)
              for (int j = 0; j < gridSize; j++)
                pane.add(board[i][j] = new Cell(), j, i);
            logic.buildBoard(gridSize);

            BorderPane border = new BorderPane();
            border.setTop(topVbox);
            border.setLeft(lhsVbox);
            border.setRight(rhsVbox);
            border.setCenter(lineLayer);
            border.setCenter(pane);
            border.setBottom(systemMessage);
            Scene scene = new Scene(border, 960, 900);
            primaryStage.setScene(scene);
            primaryStage.show();
          }
        };

        // Changes logic when a player changes their active letter
        EventHandler<ActionEvent> setRedS = e -> logic.setLetter('S', 'r');
        EventHandler<ActionEvent> setBlueS = e -> logic.setLetter('S', 'b');
        EventHandler<ActionEvent> setRedO = e -> logic.setLetter('O', 'r');
        EventHandler<ActionEvent> setBlueO = e -> logic.setLetter('O', 'b');
        
        startButton.setOnAction(setBoard);
        sRed.setOnAction(setRedS);
        sBlue.setOnAction(setBlueS);
        oRed.setOnAction(setRedO);
        oBlue.setOnAction(setBlueO);

        GridPane pane = new GridPane();
        border.setTop(topVbox);
        border.setLeft(lhsVbox);
        border.setRight(rhsVbox);
        border.setCenter(pane);
        border.getChildren().addAll(lineLayer);
        border.setBottom(systemMessage);
        
        Scene scene = new Scene(border, 900, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // updates the score in the GUI
    public void setScore(char player, int value){
      if (player == 'r'){
        redScore.setText(String.valueOf(value));
      }
      else if (player == 'b'){
        blueScore.setText(String.valueOf(value));
      }
    }
    
    // switches current turn in the GUI
    public void switchTurns(){
      if (currentTurn == 'r'){
        currentTurn = 'b';
      }
      else currentTurn = 'r';
    }

    // Shows current player as GUI text
    public void showCurrentTurn(){
      if (currentTurn == 'r')
        systemMessage.setText("Current Turn: Red Player");
      else systemMessage.setText("Current Turn: Blue Player");
    }

    // Changes letter in GUI with computer player input
    public void setLetter(char letter){
      char player = currentTurn;
      if (player == 'r'){
        if (letter == 'S')
          sRed.setSelected(true);
        else oRed.setSelected(true);
      }

      else if (player == 'b'){
        if (letter == 'S')
          sBlue.setSelected(true);
        else oBlue.setSelected(true);
      }
    }

    // Presents a message at the end when the game has ended
    public void printEndScreen(String message){
      Label label = new Label(message);
      VBox layout= new VBox(10);
      layout.getChildren().addAll(label);
      Scene scene1 = new Scene(layout, 300, 250);
      Stage popupWindow = new Stage();
      popupWindow.setScene(scene1);
      popupWindow.showAndWait();
    }

    // Draws a line across letters when there is a score
    public void drawLine(int x, int y, int xi, int yi){
      Line scoreMark = new Line();
      double startX = board[y][x].getLayoutX() + board[y][x].getWidth()/2;
      double startY = board[y][x].getLayoutY() + board[y][x].getHeight()/2;
      double endX = board[yi][xi].getLayoutX() + board[yi][xi].getWidth()/2;
      double endY = board[yi][xi].getLayoutY() + board[yi][xi].getHeight()/2;
      scoreMark.setStartX(startX);
      scoreMark.setStartY(startY);
      scoreMark.setEndX(endX);
      scoreMark.setEndY(endY);
      scoreMark.setStrokeWidth(board[y][x].getWidth()/12);
      if (currentTurn == 'r'){
        scoreMark.setStroke(Color.RED);
      }
      else scoreMark.setStroke(Color.BLUE);
      
      board[0][0].getChildren().add(scoreMark);
      scoreMark.toBack();
    }

    // Makes a human or computer move, based on what is selected for a player
    public void determineMove(int x, int y){
      if ((currentTurn == 'r' && computerRed.isSelected()) || (currentTurn == 'b' && computerBlue.isSelected())){
        logic.handleComputerTurn();
      }
      else logic.handleTurn(x, y);
    }


    public class Cell extends Pane {

        // creates black border around cell and handles mouse clicks with the right move
        public Cell() {
          setStyle("-fx-border-color: black"); 
          this.setPrefSize(800, 800);
          this.setOnMouseClicked(e -> determineMove(GridPane.getColumnIndex(this), GridPane.getRowIndex(this)));
        }

        // Fills the grid with the correct letter
        public void setToken(char c, int x, int y) {
          
          if ((c == 'b' && sBlue.isSelected()) || (c == 'r' && sRed.isSelected())){    
            Text s = new Text("S");
            s.setTranslateX(this.getWidth()/2);
            s.setTranslateY(this.getHeight()/2);
            s.setScaleX(this.getWidth()/12);
            s.setScaleY(this.getHeight()/12);
            s.setStroke(Color.BLACK);
            board[y][x].getChildren().add(s);
          }
          else if ((c == 'b' && oBlue.isSelected()) || (c == 'r' && oRed.isSelected())){  
            Text o = new Text("O");
            o.setTranslateX(this.getWidth()/2);
            o.setTranslateY(this.getHeight()/2);
            o.setScaleX(this.getWidth()/12);
            o.setScaleY(this.getHeight()/12);
            o.setStroke(Color.BLACK);
            board[y][x].getChildren().add(o);
          }
        }
      } 
  
    public static void main(String args[])
    {
        launch(args);
    }
}