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

        System.out.println("\nIn-House\n");
        System.out.println(inHouse.getMachineId());
        inHouse.setMachineId(15);
        System.out.println(inHouse.getMachineId());

        Outsourced outSourced = new Outsourced(2, "testPart", 5.00, 5, 1, 10, "Boeing");

        System.out.println("\nOutsourced\n");
        System.out.println(outSourced.getCompanyName());
        outSourced.setCompanyName("Smith Optics");
        System.out.println((outSourced.getCompanyName()));

        Product product = new Product(505, "The Cat's Pajamas", 5.00, 10, 1, 50);
        product.addAssociatedPart(inHouse);
        product.addAssociatedPart(outSourced);

        System.out.println("\nProduct\n");
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getStock());
        System.out.println(product.getMin());
        System.out.println(product.getMax());
        System.out.println(product.getAllAssociatedParts());

        if (product.deleteAssociatedPart(outSourced))
        {
            System.out.println(product.getAllAssociatedParts());
        }

    }
}