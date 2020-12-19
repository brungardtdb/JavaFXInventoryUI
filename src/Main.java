import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.function.IntBinaryOperator;


public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("Inventory Manager");
        stage.setScene(new Scene(root,900,650));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// FOR TESTING INVENTORY
//public class Main
//{
//
//    public static void main(String[] args)
//    {
//        InventoryTest inventoryTest = new InventoryTest();
//    }
//}