import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("modifyParts.fxml"));
        stage.setTitle("Inventory Manager");
        stage.setScene(new Scene(root,400,400 ));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}