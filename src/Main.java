import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import InventoryAPI.Inventory;
import InventoryAPI.Test.InventoryTest;
import InventoryAPI.Part;
import InventoryAPI.Product;
import InventoryAPI.InHouse;
import InventoryAPI.Outsourced;
import javafx.collections.ObservableList;

public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/Views/main.fxml"));
        stage.setTitle("Inventory.Inventory Manager");
        stage.setScene(new Scene(root,900,650));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// //FOR TESTING INVENTORY
//public class Main
//{
//
//    public static void main(String[] args)
//    {
//        InventoryTest inventoryTest = new InventoryTest();
//    }
//}