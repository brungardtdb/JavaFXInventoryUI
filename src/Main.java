import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


//public class Main extends Application{
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//        stage.setTitle("Inventory Manager");
//        stage.setScene(new Scene(root,900,650));
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

public class Main
{

    public static void main(String[] args)
    {
        InHouse inHouse = new InHouse(1, "testPart", 5.00, 5, 1, 10, 55);

        System.out.println(inHouse.getMachineId());
        inHouse.setMachineId(15);
        System.out.println(inHouse.getMachineId());
    }
}