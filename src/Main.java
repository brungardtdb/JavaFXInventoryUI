import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Main class and main method to start the application.
 * @author David Brungardt
 */
public class Main extends Application{


    @Override
    /**
     * Method that starts main window for the application.
     * @param stage Takes stage used for main form as an arguement.
     * */
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/Views/main.fxml"));
        stage.setTitle("Inventory Manager");
        stage.setScene(new Scene(root,900,650));
        stage.show();
    }

    /**
     * Main method that launches the application
     * @param args Arguments for main method.
     * */
    public static void main(String[] args) {
        launch(args);
    }
}
