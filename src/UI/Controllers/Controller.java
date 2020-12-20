package UI.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

    @FXML private RadioButton isOutsourced;

    public void handleAddParts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/parts.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            // Get controller so we can define if we are adding or modifying parts
            PartController partController = fxmlLoader.getController();
            partController.setModifyParts(false);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Add Parts");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleModifyParts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/parts.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            // Get controller so we can define if we are adding or modifying parts
            PartController partController = fxmlLoader.getController();
            partController.setModifyParts(true);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Modify Parts");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteParts()
    {

    }

    public void handleAddProducts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/product.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            // Get controller so we can define if we are adding or modifying parts
            ProductController productController = fxmlLoader.getController();
            productController.setModifyProducts(false);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Add Products");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleModifyProducts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/product.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            // Get controller so we can define if we are adding or modifying parts
            ProductController productController = fxmlLoader.getController();
            productController.setModifyProducts(true);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Modify Products");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteProducts()
    {

    }

    public  void handleExitForm()
    {

    }

    public void handleSearchParts()
    {

    }

    public void handleSearchProducts()
    {

    }
}
