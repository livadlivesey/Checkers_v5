package checkers_v5;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.transform.Translate;

/**
 *
 * @author liv
 */
public class Checkers_controller implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private HBox hbox;
    @FXML
    private VBox vbox;

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

    private final List<Pane> panes = new ArrayList<>();

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
    Hashtable<Circle, Checker> checkerState;
    Hashtable<Rectangle, Tile> tileState;
    Rectangle[] tiles;
    Circle[] circles;
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
        checkerState = new Hashtable<>();
        tileState = new Hashtable<>();
        gameState = new Tile[32];
        tiles = new Rectangle[32];
        circles = new Circle[32];
        ongoingMove = false;

        panes.add(row0);
        panes.add(row1);
        panes.add(row2);
        panes.add(row3);
        panes.add(row4);
        panes.add(row5);
        panes.add(row6);
        panes.add(row7);

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
            Tile tile = new Tile(i + 1);
            gameState[i] = tile;
            tileState.put(r, tile);
            tiles[i] = r;
        }

        // For all the circles which are for the computer, create a checker object 
        for (int i = 0; i < comp_checkers.size(); i++) {
            Circle circle = comp_checkers.get(i);
            Checker checker = new Checker(i + 1, "Computer");
            checkerState.put(circle, checker);
            gameState[i].setChecker(checker);
            circles[i] = circle;

            Circle circle2 = user_checkers.get(i);
            Checker checker2 = new Checker(i + 21, "User");
            checkerState.put(circle2, checker2);
            gameState[i + 20].setChecker(checker2);
            circles[i + 12] = circle2;

        }
        Integer levels[] = {1, 2, 3, 4, 5};
        ChoiceDialog<Integer> difficulty = new ChoiceDialog<>(levels[3], levels);
        difficulty.setTitle("Choose Game difficulty");
        difficulty.setContentText("Easy for beginners, Intermediate if you like a challenge, Difficult if you want to try the impossible!");
        // show the dialog
        difficulty.showAndWait();
        level = difficulty.getSelectedItem();

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

//        try {
//            root = FXMLLoader.load(getClass().getResource("Checkers_FXML.fxml"));
//        } catch (IOException ex) {
//            Logger.getLogger(Checkers_controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        rectangleGroup.getScene().setRoot(root);
    }

    @FXML
    public void newGame() throws IOException {
        blackTiles = new ArrayList<>();
        checkerState = new Hashtable<>();
        tileState = new Hashtable<>();
        gameState = new Tile[32];
        tiles = new Rectangle[32];
        circles = new Circle[32];
        ongoingMove = false;
        //Create the connection between the model tiles and the javaFX representation
        for (int i = 0; i < 32; i++) {
            Rectangle r = blackTiles.get(i);
            Tile tile = new Tile(i + 1);
            gameState[i] = tile;
            tileState.put(r, tile);
            tiles[i] = r;
        }

        // For all the circles which are for the computer, create a checker object 
        for (int i = 0; i < comp_checkers.size(); i++) {
            Circle circle = comp_checkers.get(i);
            Checker checker = new Checker(i + 1, "Computer");
            checkerState.put(circle, checker);
            gameState[i].setChecker(checker);
            circles[i] = circle;

            Circle circle2 = user_checkers.get(i);
            Checker checker2 = new Checker(i + 21, "User");
            checkerState.put(circle2, checker2);
            gameState[i + 20].setChecker(checker2);
            circles[i + 12] = circle2;

        }
        Integer levels[] = {1, 2, 3, 4, 5};
        ChoiceDialog<Integer> difficulty = new ChoiceDialog<>(levels[3], levels);
        difficulty.setTitle("Choose Game difficulty");
        difficulty.setContentText("Easy for beginners, Intermediate if you like a challenge, Difficult if you want to try the impossible!");
        // show the dialog
        difficulty.showAndWait();
        level = difficulty.getSelectedItem();

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

        root = FXMLLoader.load(getClass().getResource("Checkers_FXML.fxml"));
        rectangleGroup.getScene().setRoot(root);

        // show the dialog
    }

    @FXML
    public void selectChecker(MouseEvent event) {
        if (ongoingMove && event.getSource() instanceof Circle) {
//            potentialMoves = model.getLegalMoves(convertCircle(selectedChecker), model.gameState);
            if (!potentialMoves.isEmpty()) {
                for (Move move : potentialMoves) {
                    Tile t = move.getTile();
                    tiles[t.getPosition() - 1].setEffect(null);
                }
            }
            selectedChecker.setOpacity(1.0);

            //If a checker has already been selected, but the user has now selected a different one, change selection
            if (selectedChecker.equals(event.getSource())) {
                //If the checker is clicked twice, de select it and remove effect for all potential highlighted moves
                selectedChecker = null;
                ongoingMove = false;
                updateStates();
                return;
            }
            selectedChecker = null;
        } else if (event.getSource() instanceof Circle) {
            // Select the checker and change opacity 
            selectedChecker = (Circle) event.getSource();
            selectedChecker.setOpacity(0.5d);

            //Get the tile that the checker is on
            currentTile = getCurrentTile(event);

            //Add an effect to all the possible moves if the level is selected as Easy
            if (level == 1) {
                potentialMoves = model.getLegalMoves(convertCircle(selectedChecker), model.gameState);
                for (Move move : potentialMoves) {
                    Tile t = move.getTile();
                    //blackTiles.get(t.getPosition()-1).setEffect(tileShadow);
                    //blackTiles.get(t.getPosition()-1).setOpacity(1.0);
                    tiles[t.getPosition() - 1].setEffect(tileShadow);
                    tiles[t.getPosition() - 1].setOpacity(1.0);
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
            highlightedTile = (Rectangle) event.getSource();
            //highlightedTile.setFill(Color.BLUEVIOLET);
            highlightedTile.setEffect(tileShadow2);

        }
    }

    @FXML
    private void removeTileSelection(MouseEvent event) {
        if (event.getSource() instanceof Rectangle && highlightedTile != null && event.getSource().equals(highlightedTile)) { // 
            System.out.println(tileShadow2);
            if (highlightedTile.getEffect().equals(tileShadow2)) {
                //List<Move> legalMoves = model.getLegalMoves(convertCircle(selectedChecker), model.gameState);
                for (Move move : potentialMoves) {
                    if (move.getTile().equals(convertRectangle(highlightedTile)) && move.getChecker().equals(convertCircle(selectedChecker))) {
                        convertTile(move.getTile()).setEffect(tileShadow);
                    } else {
                        highlightedTile.setEffect(null);
                        highlightedTile = null;
                    }
                }
            } else {
                highlightedTile.setEffect(null);
                highlightedTile = null;
            }
        }
    }

    @FXML
    private void moveCurrentChecker(MouseEvent event) {
        if (event.getSource() instanceof Rectangle) {
            selectedTile = (Rectangle) event.getTarget();
            if (ongoingMove) { //selectedChecker != null && model.getCurrentPlayer().equals("User")
                Checker c = convertCircle(selectedChecker);
                Tile t = convertRectangle(selectedTile);
                List<Move> available = model.getAllLegalMoves(model.gameState);
                if (available.contains(new Move(c, t))) {
                    for (int i = 0; i < available.size(); i++) {
                        if (new Move(c, t).equals(available.get(i))) {
                            currentMove = available.get(i);

                        }
                    }

                    //Calculating the position of the tile and current checker in the same parent node
                    Point2D newPos = calc_position(selectedChecker, selectedTile);
                    Double newX = newPos.getX() + selectedChecker.getCenterX();
                    Double newY = newPos.getY() + selectedChecker.getCenterY();
                    int originalPos = c.position;

                    move_position(selectedChecker, newX, newY);

                    for (Move move : available) {
                        Tile tile = move.getTile();
                        tiles[tile.getPosition() - 1].setEffect(null);
                    }
                    

                    if (currentMove.isCapturingMove()) {
                        Checker captured = currentMove.getCapturedChecker();
                        capturedChecker = convertChecker(captured);
                        root.getChildren().remove(capturedChecker);
                        deleteChecker(capturedChecker);
                        model.removeChecker(currentMove.getChecker());
                        checkerState.remove(capturedChecker); 
                        model.move(c, t, model.gameState);
                        tileState.replace(selectedTile, t);
                    } else {
                        model.move(c, t, model.gameState);
                        tileState.replace(selectedTile, t);
                        checkerState.replace(selectedChecker, c);
                        circles[t.getPosition() - 1] = selectedChecker;
                        tiles[t.getPosition()-1] = selectedTile;
                    }

                    if (model.isGameOver(model.gameState)) {
                        Alert alert = new Alert(AlertType.WARNING, " ", ButtonType.OK);
                        alert.setContentText("The game is over, the winner is: TO DO ");
                        alert.setHeaderText("Game Over");
                        alert.setTitle("Game Over");
                        alert.showAndWait();
                    }

                    updateStates();

                    model.currentPlayer = "Computer";
                    System.out.println("\n I'm thinking...");
                    moveCompChecker();

                } else {
                    Alert alert = new Alert(AlertType.WARNING, " ", ButtonType.OK);
                    alert.setContentText("This is not a valid move. ");
                    alert.setHeaderText("Invalid Move");
                    alert.setTitle("Invalid Move");
                    alert.showAndWait();

                    for (Move move : available) {
                        Tile tile = move.getTile();
                        tiles[tile.getPosition() - 1].setEffect(null);
                    }
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
    }

    private void moveCompChecker() {
        //THen automatically do the computer move
        model.startEval();
        System.out.println("\n SE: " + model.seCount + " DE: " + model.deCount + " P: " + model.pCount);
        System.out.print("Model.bestMove: " + model.bestMove);

        bestMove = model.getBestMove2();

        selectedChecker = convertChecker(bestMove.getChecker());
        selectedTile = convertTile(bestMove.getTile());

        System.out.println(selectedChecker);
        System.out.println(selectedTile);

        Point2D newPos = calc_position(selectedChecker, selectedTile);
        Double newX = newPos.getX() + selectedChecker.getCenterX();
        Double newY = newPos.getY() + selectedChecker.getCenterY();

        move_position(selectedChecker, newX, newY);
        circles[bestMove.getTile().getPosition() - 1] = selectedChecker;
        tiles[bestMove.getTile().getPosition() - 1] = selectedTile;

        model.move(bestMove.getChecker(), bestMove.getTile(), model.gameState);

        if (bestMove.isCapturingMove()) {
            System.out.println("Capturing: " + bestMove.getCapturedChecker().toString());
            //Checker captured = model.getCapturedChecker(bestMove.getChecker(), bestMove.getTile());
            Checker captured = bestMove.getCapturedChecker();
            capturedChecker = convertChecker(captured);
            deleteChecker(capturedChecker);
            model.removeChecker(bestMove.getChecker());
            checkerState.remove(capturedChecker);

        } 
        
        tileState.replace(selectedTile, convertRectangle(selectedTile));
        checkerState.replace(selectedChecker, convertCircle(selectedChecker));
        
        model.currentPlayer = "User";
        updateStates();
    }

    /**
     * Identifies the tile that the selected checker is currently occupying
     *
     * TODO: see if holding a representation of the states - where each position
     * has corresponding tile is better
     *
     * @param event
     * @return
     */
    public Rectangle getCurrentTile(MouseEvent event) {
        double SceneX = event.getSceneX();
        double SceneY = event.getSceneY();
        Point2D eventInScene = new Point2D(SceneX, SceneY);
        //Iterate through tiles to find the tile that the selected Checker is on, to mark it as being available if the tile moves
        for (Rectangle tile : tiles) {
            if (tile.contains(tile.sceneToLocal(eventInScene))) {
                currentTile = tile;
            }
        }
        return currentTile;

    }

    /**
     * Calculates the new co-ordinates for the selected checker, given the new
     * tile location
     *
     * @param currentChecker
     * @param newPosition
     * @return
     */
    public Point2D calc_position(Circle currentChecker, Rectangle newPosition) {
        double newMinX = newPosition.getLayoutBounds().getMinX();
        double newMinY = newPosition.getLayoutBounds().getMinY();

        Point2D inScene = newPosition.localToScene(newMinX, newMinY);
        Point2D inLocal = currentChecker.sceneToLocal(inScene);
        Point2D inParent = currentChecker.localToParent(inLocal);

        double newX = inParent.getX();
        double newY = inParent.getY();

        //Creates a 2D point with the X and Y co ordinates
        Point2D finalPos = new Point2D(newX, newY);

        return finalPos;

    }

    /**
     * Moves the given checker to the given co-ordinates Uses Translate() to
     * retain the current transforms
     *
     * @param checker
     * @param newX
     * @param newY
     */
    public void move_position(Circle checker, Double newX, Double newY) {
        // Use translate to preserve the current scale, translate etc properties
        Point2D inScene = checker.localToParent(checker.getLayoutBounds().getMinX(), checker.getLayoutBounds().getMinY());
        Translate translate = new Translate();
        translate.setX(newX - inScene.getX());
        translate.setY(newY - inScene.getY());

        checker.getTransforms().addAll(translate);
        checker.setOpacity(1.0);
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
            selectedTile.setFill(null);
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
            highlightedTile.setFill(null);
            highlightedTile.setOpacity(0.0);
            highlightedTile = null;
        }

        if (capturedChecker != null) {
            capturedChecker.setEffect(null);
            capturedChecker.setOpacity(1.0);
            capturedChecker = null;
        }
        //reset the moves
        potentialMoves = new ArrayList<>();
    }

    public Checker convertCircle(Circle c) {
        return checkerState.get(c);
    }

    public Tile convertRectangle(Rectangle r) {
        Tile tile = null;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].equals(r)) {
                tile = gameState[i];
            }
        }
        //Tile tile = tileState.get(r);
        return tileState.get(r);
    }

    public Rectangle convertTile(Tile t) {
        int pos = t.getPosition();
        Rectangle r = tiles[pos - 1];
        return r;
    }

    public Circle convertChecker(Checker checker) {
        int pos = checker.getPosition();
        Circle c = circles[pos - 1];
        return c;
    }

    public void deleteChecker(Circle captured) {
        for (Pane row : panes) {
            if (row.getChildren().contains(captured)) {
                row.getChildren().remove(captured);
            }
        }

        for (int i = 0; i < rectangleGroup.getChildren().size(); i++) {
            Pane p = (Pane) rectangleGroup.getChildren().get(i);
            p.getChildren().remove(captured);
            p.getChildren().remove(p.lookup(captured.getId()));
        }

        Node lookup = vbox.lookup(captured.getId());
        vbox.getChildren().remove(lookup);

        if (user_checkers.contains(captured)) {
            user_checkers.remove(captured);
        } else if (comp_checkers.contains(captured)) {
            comp_checkers.remove(captured);
        }


        root.getChildren().remove(captured);
    }

    public Checkers_controller() {

    }

}
