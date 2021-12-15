package checkers_v5;

import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author 215865
 */
public class Checkers_v5 extends Application {
    List<Circle> user_checkers;    
    //String fxmlPath = "FXMLDocument.fxml";
    List<Checker> userCheckers;
    Game game;
    Checkers_controller userController;
    FXMLLoader loader;
    
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        loader = new FXMLLoader(getClass().getResource("Checkers_FXML.fxml"));
        // Create a reference toe the controller
        userController = loader.getController();
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
