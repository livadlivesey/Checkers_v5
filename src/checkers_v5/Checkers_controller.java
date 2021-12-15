package checkers_v5;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author liv
 */
public class Checkers_controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Text gameText;

    @FXML
    private Group rectangleGroup;

    @FXML
    private Pane row7;

    @FXML
    private Pane row6;

    @FXML
    private Pane row5;

    @FXML
    private Pane row4;

    @FXML
    private Pane row3;

    @FXML
    private Pane row2;

    @FXML
    private Pane row1;

    @FXML
    private Pane row0;

    @FXML
    private Circle user_1;
    @FXML
    private Circle user_2;
    @FXML
    private Circle user_3;
    @FXML
    private Circle user_4;
    @FXML
    private Circle user_5;
    @FXML
    private Circle user_6;
    @FXML
    private Circle user_7;
    @FXML
    private Circle user_8;
    @FXML
    private Circle user_12;
    @FXML
    private Circle user_11;
    @FXML
    private Circle user_10;
    @FXML
    private Circle user_9;

    @FXML
    private Circle comp_1;
    @FXML
    private Circle comp_2;
    @FXML
    private Circle comp_3;
    @FXML
    private Circle comp_4;
    @FXML
    private Circle comp_5;
    @FXML
    private Circle comp_6;
    @FXML
    private Circle comp_7;
    @FXML
    private Circle comp_8;
    @FXML
    private Circle comp_9;
    @FXML
    private Circle comp_10;
    @FXML
    private Circle comp_11;
    @FXML
    private Circle comp_12;

    @FXML
    private Rectangle pos1;
    @FXML
    private Rectangle pos2;
    @FXML
    private Rectangle pos3;
    @FXML
    private Rectangle pos4;
    @FXML
    private Rectangle pos5;
    @FXML
    private Rectangle pos6;
    @FXML
    private Rectangle pos7;
    @FXML
    private Rectangle pos8;
    @FXML
    private Rectangle pos9;
    @FXML
    private Rectangle pos10;
    @FXML
    private Rectangle pos11;
    @FXML
    private Rectangle pos12;
    @FXML
    private Rectangle pos13;
    @FXML
    private Rectangle pos14;
    @FXML
    private Rectangle pos15;
    @FXML
    private Rectangle pos16;
    @FXML
    private Rectangle pos17;
    @FXML
    private Rectangle pos18;
    @FXML
    private Rectangle pos19;
    @FXML
    private Rectangle pos20;
    @FXML
    private Rectangle pos21;
    @FXML
    private Rectangle pos22;
    @FXML
    private Rectangle pos23;
    @FXML
    private Rectangle pos24;
    @FXML
    private Rectangle pos25;
    @FXML
    private Rectangle pos26;
    @FXML
    private Rectangle pos27;
    @FXML
    private Rectangle pos28;
    @FXML
    private Rectangle pos29;
    @FXML
    private Rectangle pos30;
    @FXML
    private Rectangle pos31;
    @FXML
    private Rectangle pos32;

    ObservableList<Circle> comp_checkers = FXCollections.observableArrayList();

    ObservableList<Circle> user_checkers = FXCollections.observableArrayList();

    //All the positions on the gameState
    List<Rectangle> blackTiles;
    //The tile that has been selected
    Rectangle selectedTile;
    //The tile that is being hovered over
    Rectangle highlightedTile;
    //The current selected checker piece
    private Circle selectedChecker;
    //The tile that the current checker is on
    private Rectangle currentTile;

    //The checker to be removed from the gameState
    private Circle capturedChecker;

    private boolean ongoingMove;

    private Move currentMove;

    //A game object to represent the model and deal with game logic
    Game model;

    //A string that is accessed from the model which identifies current player
    String currentPlayer;

    //To create connection between model and controller
    List<Move> potentialMoves;
    Tile[] gameState;
    private Circle highlightedChecker;
    int level;
    Move bestMove;
    InnerShadow tileShadow;
    InnerShadow tileShadow2;
    DropShadow checkerShadow;

    Timer timer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blackTiles = new ArrayList<>();
        gameState = new Tile[32];
        ongoingMove = false;

        user_checkers.add(user_1);
        user_checkers.add(user_2);
        user_checkers.add(user_3);
        user_checkers.add(user_4);
        user_checkers.add(user_5);
        user_checkers.add(user_6);
        user_checkers.add(user_7);
        user_checkers.add(user_8);
        user_checkers.add(user_9);
        user_checkers.add(user_10);
        user_checkers.add(user_11);
        user_checkers.add(user_12);

        comp_checkers.add(comp_1);
        comp_checkers.add(comp_2);
        comp_checkers.add(comp_3);
        comp_checkers.add(comp_4);
        comp_checkers.add(comp_5);
        comp_checkers.add(comp_6);
        comp_checkers.add(comp_7);
        comp_checkers.add(comp_8);
        comp_checkers.add(comp_9);
        comp_checkers.add(comp_10);
        comp_checkers.add(comp_11);
        comp_checkers.add(comp_12);

        blackTiles.add(pos1);
        blackTiles.add(pos2);
        blackTiles.add(pos3);
        blackTiles.add(pos4);
        blackTiles.add(pos5);
        blackTiles.add(pos6);
        blackTiles.add(pos7);
        blackTiles.add(pos8);
        blackTiles.add(pos9);
        blackTiles.add(pos10);
        blackTiles.add(pos11);
        blackTiles.add(pos12);
        blackTiles.add(pos13);
        blackTiles.add(pos14);
        blackTiles.add(pos15);
        blackTiles.add(pos16);
        blackTiles.add(pos17);
        blackTiles.add(pos18);
        blackTiles.add(pos19);
        blackTiles.add(pos20);
        blackTiles.add(pos21);
        blackTiles.add(pos22);
        blackTiles.add(pos23);
        blackTiles.add(pos24);
        blackTiles.add(pos25);
        blackTiles.add(pos26);
        blackTiles.add(pos27);
        blackTiles.add(pos28);
        blackTiles.add(pos29);
        blackTiles.add(pos30);
        blackTiles.add(pos31);
        blackTiles.add(pos32);

        //Create the connection between the model tiles and the javaFX representation
        for (int i = 0; i < 32; i++) {
            Rectangle r = blackTiles.get(i);
            Tile tile = new Tile(i + 1, r);
            gameState[i] = tile;
        }

        // For all the circles which are for the computer, create a checker object 
        for (int i = 0; i < comp_checkers.size(); i++) {
            Circle circle = comp_checkers.get(i);
            Checker checker = new Checker(i + 1, "Computer", circle);
            gameState[i].setChecker(checker);

            Circle circle2 = user_checkers.get(i);
            Checker checker2 = new Checker(i + 21, "User", circle2);
            gameState[i + 20].setChecker(checker2);

        }
        String levels[] = {"Easy", "Intermediate", "Difficult"};
        Integer levelInts[] = {3, 5, 9};
        ChoiceDialog<String> difficulty = new ChoiceDialog<>(levels[2], levels);
        difficulty.setTitle("Choose Game difficulty");
        difficulty.setContentText("Easy for beginners, Intermediate if you like a challenge, Difficult if you want to try the impossible!");
        // show the dialog
        difficulty.showAndWait();
        int pos = difficulty.getSelectedItem().indexOf(difficulty.getSelectedItem());
        level = levelInts[pos];

        //Create the model
        model = new Game(gameState, level);

        //Initializing the variables to avoid null pointer exception
        potentialMoves = new ArrayList<>();
        selectedChecker = null;
        selectedTile = null;
        highlightedChecker = null;
        highlightedTile = null;
        bestMove = null;
        currentMove = null;

        // Shadow for tile highlighting
        tileShadow = new InnerShadow();
        tileShadow.setColor(Color.YELLOW);
        tileShadow.setWidth(30);

        //Shadow for legal moves highlighting
        tileShadow2 = new InnerShadow();
        tileShadow2.setColor(Color.WHITE);
        tileShadow2.setWidth(30);

        //Shadow for checker highlighting
        checkerShadow = new DropShadow();
        checkerShadow.setRadius(25);
        checkerShadow.setOffsetX(15);
        checkerShadow.setOffsetY(20);
        checkerShadow.setColor(Color.web("#006666"));
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    public void newGame() throws IOException {
        try {

            root = FXMLLoader.load(getClass().getResource("Checkers_FXML.fxml"));
            rectangleGroup.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(Checkers_controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     */
    @FXML
    public void getBestHint() {
        model.startEval();
        bestMove = model.getBestMove();
        Checker bestChecker = bestMove.getChecker();
        Tile bestTile = bestMove.getTile();
        selectedChecker = bestChecker.getCircle();
        selectedTile = bestTile.getRectangle();
        selectedChecker.setEffect(tileShadow);
        selectedChecker.setOpacity(1.0);
        selectedTile.setEffect(tileShadow);
        selectedTile.setOpacity(1.0);
    }

    /**
     *
     */
    @FXML
    public void showRules() {
        Alert rules = new Alert(AlertType.INFORMATION);
        rules.setTitle("Checkers Rules");
        rules.setHeaderText("Checkers Rules");
        rules.setContentText("TODO: INSERT RULES HERE");
        rules.showAndWait();
    }

    /**
     *
     * @param event
     */
    @FXML
    public void selectChecker(MouseEvent event) {
        if (ongoingMove && selectedChecker != null) {
            ongoingMove = false;

            //If a checker has already been selected, but the user has now selected a different one, change selection
            if (selectedChecker.equals(event.getSource())) {
                updateStates();//If the checker is clicked twice, de select it and remove effect for all potential highlighted moves
                return;
            }
            updateStates();
        } else {
            // Select the checker and change opacity 
            selectedChecker = (Circle) event.getSource();
            selectedChecker.setOpacity(0.5d);

            //Get the tile that the checker is on
            currentTile = getCurrentTile(event);
            //Add an effect to all the possible moves if the level is selected as Easy
            if (level == 1 || level == 1 || level == 3) {
                potentialMoves = (model.getLegalMoves(convertCircle(selectedChecker), model.gameState));
                for (Move move : potentialMoves) {
                    Tile t = move.getTile();
                    t.getRectangle().setEffect(tileShadow);
                    t.getRectangle().setOpacity(0.5);
                }
            }
            //Assign that there is an ongoing move
            ongoingMove = true;
        }
    }

    @FXML
    /**
     * Highlights the object currently being hovered over by the mouse If the
     */
    private void highlightSelected(MouseEvent event) {
        if (event.getSource() instanceof Circle) {
            highlightedChecker = (Circle) event.getSource();
            DropShadow outerShadow = new DropShadow();
            outerShadow.setColor(Color.PINK);
            outerShadow.setWidth(20);
            highlightedChecker.setEffect(outerShadow);
        }
    }

    @FXML
    private void deHighlightSelected(MouseEvent event) {
        if (event.getSource().equals(highlightedChecker)) {
            highlightedChecker.setEffect(null);
            highlightedChecker = null;
        }
    }

    @FXML
    private void selectNewTile(MouseEvent event) {
        if (ongoingMove && event.getSource() instanceof Rectangle && !event.getSource().equals(currentTile)) { // && event.getSource() instanceof Rectangle
            Rectangle r = (Rectangle) event.getSource();
            for (Move move : potentialMoves) {
                if (move.getTile().getRectangle().equals(r)) {
                    r.setEffect(tileShadow2);
                    r.setOpacity(0.5);
                } else {
                    highlightedTile = (Rectangle) event.getSource();
                    highlightedTile.setEffect(tileShadow2);
                    highlightedTile.setOpacity(0.5);
                    break;
                }
            }
        }
    }

    @FXML
    private void removeTileSelection(MouseEvent event) {
        if (ongoingMove) {
            Rectangle r = (Rectangle) event.getSource();
            for (Move move : potentialMoves) {
                if (move.getTile().getRectangle().equals(r) && move.getChecker().getCircle().equals(selectedChecker)) {
                    move.getTile().getRectangle().setEffect(tileShadow);
                    move.getTile().getRectangle().setOpacity(0.5);
                }
            }
            if (event.getSource().equals(highlightedTile)) {  //&& !highlightedTile.equals(r)
                highlightedTile.setEffect(null);
                highlightedTile.setOpacity(0.0);
                highlightedTile = null;
            }
        }
    }

    @FXML
    private void moveCurrentChecker(MouseEvent event) {
        if (event.getSource() instanceof Rectangle) {
            selectedTile = (Rectangle) event.getTarget();
            if (ongoingMove) {
                Checker c = convertCircle(selectedChecker);
                Tile t = convertRectangle(selectedTile);
                if (model.isValidUserMove(c, t)) {
                    currentMove = model.getValidUserMove(c, t);

                    //If regicide
                    if (currentMove.isRegicide()) {
                        move_position(selectedChecker, selectedTile);
                        model.move(currentMove, model.gameState);
                        deleteChecker(currentMove.getCapturedChecker().getCircle());
                        return;
                    }

                    // If it's a forced capture situation, alert user
                    if (model.isForcedCapture(currentMove)) {
                        Alert forcedCap = new Alert(AlertType.WARNING, " ", ButtonType.OK);
                        forcedCap.setContentText("There is a possible capturing move that you can take, however you have not selected it. Due to forced capture rules you must capture an enemy piece if possible. Please try again.");
                        forcedCap.setHeaderText("Oops! Forced Capture Error");
                        forcedCap.setTitle("Invalid Move");
                        forcedCap.showAndWait();
                        updateStates();
                        return;
                    }
                    move_position(selectedChecker, selectedTile);
                    model.move(currentMove, model.gameState);

                    //If the move is a capturing move, but not regicide delete the checker
                    if (currentMove.isCapturingMove() && !currentMove.isRegicide()) {
                        deleteChecker(currentMove.getCapturedChecker().getCircle());

                        while (model.existsMultiLeg(c)) {
                            currentMove = model.getMultiLeg(c);
                            selectedTile = currentMove.getTile().getRectangle();
                            move_position(selectedChecker, selectedTile);
                            model.move(currentMove, model.gameState);
                        }
                    }
                    //Check if game over
                    gameOver();

                    //Switch current player
                    model.nextTurn();
                    // Change text value and only move opponent piece after delay
                    Timeline timeline = new Timeline();
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000), new KeyValue(gameText.textProperty(), "AI is thinking...")));
                    timeline.play();
                    timeline.setOnFinished(finish -> {
                        moveCompChecker();
                    });
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING, " ", ButtonType.OK);
                alert.setContentText("This is not a valid move as either the tile is occupied, or is more than one row away from the selected checker. Please see the rules tab for more information ");
                alert.setHeaderText("Invalid Move");
                alert.setTitle("Invalid Move");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, " ", ButtonType.OK);
            alert.setContentText("Please select a checker to move and then reselect the tile!");
            alert.setHeaderText("No checker selected");
            alert.setTitle("Invalid Move");
            alert.showAndWait();
        }
        ongoingMove = false;
        updateStates();
        

    }

    /**
     *
     */
    public void moveCompChecker() {
        // Retrieve best move from model
        model.startEval();
        bestMove = model.getBestMove();
        Checker bestChecker = bestMove.getChecker();
        Tile bestTile = bestMove.getTile();
        selectedChecker = bestChecker.getCircle();
        selectedTile = bestTile.getRectangle();

        //Move the selected checker to the tile 
        move_position(selectedChecker, selectedTile);

        //If the move is a capturing move, make sure to remove the piece from board and representations
        model.move(bestMove, model.gameState);

        //If it's a capturing move
        if (bestMove.isCapturingMove()) {
            deleteChecker(convertChecker(bestMove.getCapturedChecker()));
            //Multi leg capturing
            // Re evaluate board
//            model.startEval();
//            // If the best move is with the same checker and is a capturing move, carry it out
//            if (model.getBestMove().getChecker().equals(bestChecker) && model.getBestMove().isCapturingMove()) {
//                selectedTile = model.getBestMove().getTile().getRectangle();
//                move_position(selectedChecker, selectedTile);
//                model.move(model.getBestMove(), model.gameState);
//            }
            while (model.existsMultiLeg(bestChecker)) {
                currentMove = model.getMultiLeg(bestChecker);
                selectedTile = currentMove.getTile().getRectangle();
                move_position(selectedChecker, selectedTile);
                model.move(currentMove, model.gameState);
            }
        }
        model.nextTurn();
        gameText.setText("It's your turn!");
        updateStates();
        gameOver();
    }

    /**
     * Identifies the tile that the selected checker is currently occupying
     * 
     * @param event
     * @return
     */
    public Rectangle getCurrentTile(MouseEvent event) {
        double SceneX = event.getSceneX();
        double SceneY = event.getSceneY();
        Point2D eventInScene = new Point2D(SceneX, SceneY);
        //Iterate through tiles to find the tile that the selected Checker is on, to mark it as being available if the tile moves
        for (Rectangle tile : blackTiles) {
            if (tile.contains(tile.sceneToLocal(eventInScene))) {
                currentTile = tile;
            }
        }
        return currentTile;

    }

    /**
     *
     */
    public void gameOver() {
        String winner;
        if (model.isGameOver(model.gameState)) {
            if (model.compWin(model.gameState)) {
                winner = "Computer";
            } else {
                winner = "You!";
            }
            Alert alert = new Alert(AlertType.WARNING, " ", ButtonType.OK);
            alert.setContentText("The game is over, the winner is: " + winner);
            alert.setHeaderText("Game Over");
            alert.setTitle("Game Over");
            alert.showAndWait();
        }
    }

    /**
     * Calculates the new co-ordinates for the selected checker, given the new
     * tile location
     *
     * @param currentChecker
     * @param newPosition
     */
    public void move_position(Circle currentChecker, Rectangle newPosition) {

        Timeline timeline = new Timeline();
        //timeline.setDelay(Duration.millis(3000));
        if (model.getCurrentPlayer().equals("Computer")) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000), new KeyValue(gameText.textProperty(), "AI is thinking...")));

        }
        timeline.play();
        //Get upper left of layout bounds co-ordinate
        double newMinX = newPosition.getLayoutBounds().getMinX();
        double newMinY = newPosition.getLayoutBounds().getMinY();

        // Put co-ordinate in context with the scene
        Point2D inScene = newPosition.localToScene(newMinX, newMinY);
        Point2D inLocal = currentChecker.sceneToLocal(inScene);
        Point2D inParent = currentChecker.localToParent(inLocal);

        //Add center X,Y to align in tile
        double newX = inParent.getX() + currentChecker.getCenterX();
        double newY = inParent.getY() + currentChecker.getCenterY();

        Point2D checkerInParent = currentChecker.localToParent(currentChecker.getLayoutBounds().getMinX(), currentChecker.getLayoutBounds().getMinY());
        currentChecker.setOpacity(1.0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), currentChecker);
        translateTransition.setByX(newX - checkerInParent.getX());
        translateTransition.setByY(newY - checkerInParent.getY());
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }

    /**
     * Updates all the helper variables for moving a piece and identifying the
     * new position
     *
     * To be called after each completed move
     */
    public void updateStates() {
        if (selectedTile != null) {
            selectedTile.setEffect(null);
            selectedTile.setOpacity(0.0);
            selectedTile = null;
        }
        if (selectedChecker != null) {
            selectedChecker.setOpacity(1.0);
            selectedChecker.setEffect(null);
            selectedChecker = null;
        }
        if (currentTile != null) {
            currentTile.setEffect(null);
            currentTile = null;
        }

        if (highlightedTile != null) {
            highlightedTile.setEffect(null);
            highlightedTile.setOpacity(0.0);
            highlightedTile = null;
        }

        if (capturedChecker != null) {
            capturedChecker.setEffect(null);
            capturedChecker.setOpacity(1.0);
            capturedChecker = null;
        }
        //reset the moves
        for (Move move : potentialMoves) {
            move.getTile().getRectangle().setEffect(null);
            move.getTile().getRectangle().setOpacity(0.0);
        }
        potentialMoves = new ArrayList<>();

        bestMove = null;
        currentMove = null;
    }

    /**
     *
     * @param c
     * @return
     */
    public Checker convertCircle(Circle c) {
        int i = 0;
        for (Tile tile : model.gameState) {
            i++;
            if (tile.hasChecker() && tile.getChecker().getCircle().equals(c)) {
                break;
            }
        }
        return model.gameState[i - 1].getChecker();
    }

    /**
     *
     * @param r
     * @return
     */
    public Tile convertRectangle(Rectangle r) {
        int i = 0;
        for (Tile tile : model.gameState) {
            i++;
            if (tile.getRectangle().equals(r)) {
                break;
            }
        }
        return model.gameState[i - 1];
    }

    /**
     *
     * @param t
     * @return
     */
    public Rectangle convertTile(Tile t) {
        return t.getRectangle();
    }

    /**
     *
     * @param checker
     * @return
     */
    public Circle convertChecker(Checker checker) {
        return checker.getCircle();
    }

    /**
     *
     * @param captured
     */
    public void deleteChecker(Circle captured) {
        for (int i = 0; i < rectangleGroup.getChildren().size(); i++) {
            Pane p = (Pane) rectangleGroup.getChildren().get(i);
            p.getChildren().remove(captured);

        }

        if (user_checkers.contains(captured)) {
            user_checkers.remove(captured);
        } else if (comp_checkers.contains(captured)) {
            comp_checkers.remove(captured);
        }
    }

    /**
     *
     */
    public Checkers_controller() {

    }

}
