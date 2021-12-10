package checkers_v5;

import com.sun.deploy.uitoolkit.DragContext;
import com.sun.javafx.tk.TKClipboard;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

/**
 *
 * @author liv
 */
public class Checkers_controller implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private Pane boardPane;
    @FXML
    private Group rectangleGroup;

    @FXML
    private Pane row7;
    @FXML
    private Rectangle sq0_7;
    @FXML
    private Rectangle sq2_7;
    @FXML
    private Rectangle sq4_7;
    @FXML
    private Rectangle sq6_7;

    @FXML
    private Pane row6;
    @FXML
    private Rectangle sq1_6;
    @FXML
    private Rectangle sq3_6;
    @FXML
    private Rectangle sq5_6;
    @FXML
    private Rectangle sq7_6;

    @FXML
    private Pane row5;
    @FXML
    private Rectangle sq0_5;
    @FXML
    private Rectangle sq2_5;
    @FXML
    private Rectangle sq3_5;
    @FXML
    private Rectangle sq4_5;
    @FXML
    private Rectangle sq6_5;

    @FXML
    private Pane row4;
    @FXML
    private Rectangle sq1_4;
    @FXML
    private Rectangle sq3_4;
    @FXML
    private Rectangle sq5_4;
    @FXML
    private Rectangle sq7_4;

    @FXML
    private Pane row3;
    @FXML
    private Rectangle sq0_3;
    @FXML
    private Rectangle sq2_3;
    @FXML
    private Rectangle sq4_3;
    @FXML
    private Rectangle sq6_3;
    @FXML
    private Pane row2;
    @FXML
    private Rectangle sq1_2;
    @FXML
    private Rectangle sq3_2;
    @FXML
    private Rectangle sq5_2;
    @FXML
    private Rectangle sq7_2;

    @FXML
    private Pane row1;
    @FXML
    private Rectangle sq0_1;
    @FXML
    private Rectangle sq2_1;
    @FXML
    private Rectangle sq4_1;
    @FXML
    private Rectangle sq6_1;

    @FXML
    private Pane row0;
    @FXML
    private Rectangle sq1_0;
    @FXML
    private Rectangle sq3_0;
    @FXML
    private Rectangle sq5_0;
    @FXML
    private Rectangle sq7_0;

    private final List<Pane> panes = new ArrayList<>();

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

    ObservableList<Circle> user_checkers = FXCollections.observableArrayList();

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

    ObservableList<Circle> comp_checkers = FXCollections.observableArrayList();

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

    //A game object to represent the model and deal with game logic
    Game model;

    //A string that is accessed from the model which identifies current player
    String currentPlayer;

    //To create connection between model and controller
    Hashtable<Circle, Checker> checkerState;
    Hashtable<Rectangle, Tile> tileState;
    Rectangle[] tiles;
    Circle[] circles;
    List<Rectangle> potentialMoves;
    Tile[] gameState;
    private Circle highlightedChecker;
    int level;

    Timer timer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blackTiles = new ArrayList<>();
        checkerState = new Hashtable<>();
        tileState = new Hashtable<>();
        gameState = new Tile[32];
        tiles = new Rectangle[32];
        circles = new Circle[32];

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
        Integer levels[] = {1, 2, 3};
        ChoiceDialog<Integer> difficulty = new ChoiceDialog<>(levels[0], levels);
        difficulty.setTitle("Choose Game difficulty");
        difficulty.setContentText("Easy for beginners, Intermediate if you like a challenge, Difficult if you want to try the impossible!");
        // show the dialog
        difficulty.show();
        level = difficulty.getSelectedItem();
        difficulty.close();

        //System.out.println(Arrays.toString(gameState));
        model = new Game(gameState, level);

        //currentPlayer = model.getCurrentPlayer();
        //Initializing the variables to avoid null pointer exception
        potentialMoves = new ArrayList<>();
        selectedChecker = null;
        selectedTile = null;
        highlightedChecker = null;
        highlightedTile = null;

    }

    @FXML
    public void newGame() throws IOException {
        Scene scene = vbox.getScene();
        scene.setRoot(FXMLLoader.load(getClass().getResource("FXMLDocument.fmxl")));
        Integer levels[] = {1, 2, 3};
        ChoiceDialog<Integer> difficulty = new ChoiceDialog<>(levels[0], levels);
        difficulty.setTitle("Choose Game difficulty");
        difficulty.setContentText("Easy for beginners, Intermediate if you like a challenge, Difficult if you want to try the impossible!");
        // show the dialog
        difficulty.show();
        level = difficulty.getSelectedItem();
        difficulty.close();
        model = new Game(gameState, level);
    }

    @FXML
    public void selectChecker(MouseEvent event
    ) {
        if (model.getCurrentPlayer().equals("User")) {
            if (selectedChecker != null) {
                selectedChecker.setOpacity(1.0);
//                tileState.keySet().stream().map(r -> {
//                    r.setOpacity(1.0);
//
//                    return r;
//                }).forEachOrdered(r -> {
//                    r.setFill(Color.BLACK);
//                });

                for (Tile tile : model.getLegalMoves(convertCircle(selectedChecker))) {
                    tiles[tile.getPosition() - 1].setEffect(null);

                }

                //If a checker has already been selected, but the user has now selected a different one, change selection
                if (selectedChecker.equals(event.getSource())) {
                    //If the checker is clicked twice, de select it and remove effect for all potential highlighted moves
                    selectedChecker = null;
                    return;
                }
            }
            selectedChecker = (Circle) event.getSource();

            currentTile = getCurrentTile(event);
            selectedChecker.setOpacity(0.5d);
            DropShadow outerShadow = new DropShadow();
            outerShadow.setColor(Color.YELLOW);
            outerShadow.setWidth(10);

            List<Tile> legalMoves = model.getLegalMoves(convertCircle(selectedChecker));
            for (Tile tile : legalMoves) {
                // tiles[tile.getPosition() - 1].setFill(Color.GREENYELLOW);
                tiles[tile.getPosition() - 1].setEffect(outerShadow);
            }
        }
    }

    @FXML
    /**
     * Highlights the object currently being hovered over by the mouse If the
     */
    private void highlightSelected(MouseEvent event) {
        highlightedChecker = (Circle) event.getSource();
        DropShadow outerShadow = new DropShadow();
        outerShadow.setColor(Color.PINK);
        outerShadow.setWidth(10);
        highlightedChecker.setEffect(outerShadow);
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
        if (selectedChecker != null) {
            highlightedTile = (Rectangle) event.getSource();
            highlightedTile.setOpacity(0.5d);

        }
    }

    @FXML
    private void removeTileSelection(MouseEvent event) {
        if (highlightedTile != null) {
            highlightedTile.setOpacity(1.0);
            highlightedTile = null;
        }
    }

    @FXML
    private void moveCurrentChecker(MouseEvent event) {
        selectedTile = (Rectangle) event.getTarget();
        
        if (selectedChecker != null && model.getCurrentPlayer().equals("User")) {
            Checker c = convertCircle(selectedChecker);
            Tile t = convertRectangle(selectedTile);
            //Move m = new Move(c, t);
            //System.out.println("New Move: " + m);
            //List<Move> available = model.getAllLegalMoves(model.gameState);
            List<Tile> legalMoves = model.getLegalMoves(convertCircle(selectedChecker));
            //System.out.println("GetAllLegalMoves: " + available);
            if (legalMoves.contains(t)) {
                //Calculating the position of the tile and current checker in the same parent node
                Point2D newPos = calc_position(selectedChecker, selectedTile);
                Double newX = newPos.getX() + selectedChecker.getCenterX();
                Double newY = newPos.getY() + selectedChecker.getCenterY();
                int originalPos = c.position;

                move_position(selectedChecker, newX, newY);

                for (Tile tile : legalMoves) {
                    tiles[tile.getPosition() - 1].setEffect(null);
                }
                //Updating the model
//                tileState.keySet().forEach(r -> {
//                    r.setFill(Color.BLACK);
//                });
                model.move(c, t, model.gameState);

                if (model.isGameOver(model.gameState)) {
                    Alert alert = new Alert(AlertType.WARNING, " ", ButtonType.OK);
                    alert.setContentText("The game is over, the winner is: TO DO ");
                    alert.setHeaderText("Game Over");
                    alert.setTitle("Game Over");
                    alert.showAndWait();
                }
                //tileState.replace(selectedTile, t);
                //checkerState.replace(selectedChecker, c);
                circles[t.getPosition() - 1] = selectedChecker;

                selectedChecker = null;
                selectedTile = null;

                //if (t.getRow() == c.getRow() + -2) {
                //model.nextTurn(model.getCurrentPlayer());
                model.currentPlayer = "Computer";

                System.out.println("\n I'm thinking...");
                moveCompChecker();

            } else {
                Alert alert = new Alert(AlertType.WARNING, " ", ButtonType.OK);
                alert.setContentText("This is not a valid move. ");
                alert.setHeaderText("Invalid Move");
                alert.setTitle("Invalid Move");
                alert.showAndWait();
                for (Tile tile : legalMoves) {
                    tiles[tile.getPosition() - 1].setEffect(null);
                }
                selectedTile = null;
                selectedChecker.setOpacity(1.0);
                selectedChecker.setEffect(null);
                selectedChecker = null;
            }
        } else {
            Alert alert = new Alert(AlertType.WARNING, " ", ButtonType.OK);
            alert.setContentText("Please select a checker to move and then reselect the tile!");
            alert.setHeaderText("No checker selected");
            alert.setTitle("Invalid Move");
            alert.showAndWait();

        }

        selectedChecker = null;
        selectedTile = null;
        //model.currentPlayer = "User";

    }

    private void moveCompChecker() {
        //THen automatically do the computer move
        model.startEval();
        System.out.println("\n SE: " + model.seCount + " DE: " + model.deCount + " P: " + model.pCount);
        System.out.print("Model.bestMove: " + model.bestMove);

        Move bestMove = model.getBestMove2();
        System.out.println(bestMove);

        int tilePos = bestMove.getNewPos();
        int checkPos = bestMove.getOriginalPos();
        //System.out.println(" /n Circles: " + Arrays.toString(circles));
        //System.out.println(" /n GameState: " + Arrays.toString(model.gameState));
        //System.out.println(" /n Tiles: " + Arrays.toString(tiles));
        selectedChecker = circles[checkPos - 1];
        //selectedChecker = convertChecker(bestMove.getChecker());
        selectedTile = tiles[tilePos - 1];
        System.out.println(selectedChecker);
        System.out.println(selectedTile);
        Point2D newPos = calc_position(selectedChecker, selectedTile);
        Double newX = newPos.getX() + selectedChecker.getCenterX();
        Double newY = newPos.getY() + selectedChecker.getCenterY();

        this.move_position(selectedChecker, newX, newY);
        selectedChecker.setVisible(true);

        model.move(bestMove.getChecker(), bestMove.getTile(), model.gameState);
        circles[bestMove.getTile().getPosition() - 1] = selectedChecker;

        //tileState.replace(selectedTile, convertRectangle(selectedTile));
        //checkerState.replace(selectedChecker, convertCircle(selectedChecker));
        
        selectedChecker = null;
        selectedTile = null;
        model.currentPlayer = "User";

    }

    @FXML
    private void captureChecker(Circle c, Rectangle newTile
    ) {
        if (selectedChecker != null) {
            Checker selected = convertCircle(selectedChecker);
            int originalPos = selected.position;
            capturedChecker = c;
            Checker captured = convertCircle(capturedChecker);
            int capPos = captured.position;

            //Get tile for relocating the checker
            //currentTile = getCurrentTile(event);
            currentTile = convertTile(model.gameState[capPos - 1]);
            //Point2D newPos = calc_position(selectedChecker, currentTile);
            Point2D newPos = calc_position(selectedChecker, newTile);
            Double newX = newPos.getX() + selectedChecker.getCenterX();
            Double newY = newPos.getY() + selectedChecker.getCenterY();
            move_position(selectedChecker, newX, newY);

            //Remove the captured checker from the game
            for (Pane row : panes) {
                if (row.getChildren().contains(capturedChecker)) {
                    row.getChildren().remove(capturedChecker);
                }
            }
            //Remove captured checker from state representation
            model.removeChecker(captured);
            model.move(selected, convertRectangle(currentTile), model.gameState);
            comp_checkers.remove(capturedChecker);

            capturedChecker = null;
            currentTile = null;
            // Record the move somehow?
        }
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

        //Gets the X and Y co ordinates of the new tiles position
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
        //checker.setTranslateX(newX - inScene.getX());
        //checker.setTranslateY(newY - inScene.getY());

        checker.setOpacity(1.0);
        //checker.toFront();

        //Updating the available positions based on the move
    }

    /**
     * Updates all the helper variables for moving a piece and identifying the
     * new position
     *
     * To be called after each completed move
     */
    public void updateStates() {
        selectedTile.setOpacity(1.0);
        //selectedTile.toFront();

        model.gameState[convertRectangle(selectedTile).getPosition()].removeChecker();
        model.gameState[convertRectangle(currentTile).getPosition()].setChecker(convertCircle(selectedChecker));

        currentTile = null;
        selectedTile = null;
        selectedChecker = null;
        //highlightedTile = null;
        //highlightedTile.setEffect(null);
    }

    public Checker convertCircle(Circle c) {

        Checker checker = checkerState.get(c);

        return checker;
    }

    public Tile convertRectangle(Rectangle r) {
        Tile tile = tileState.get(r);
        return tile;
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

    public Checkers_controller() {

    }

}