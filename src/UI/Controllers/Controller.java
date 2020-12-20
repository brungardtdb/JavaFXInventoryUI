package UI.Controllers;

import InventoryAPI.Inventory;
import InventoryAPI.Part;
import com.sun.jdi.PrimitiveValue;
import com.sun.scenario.effect.impl.prism.PrRenderInfo;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

    private static Inventory inventory = new Inventory();
    @FXML private RadioButton isOutsourced;
    @FXML private Button exitForm;
    @FXML private TableView partTable;
    @FXML private TableColumn partIDColumn;
    @FXML private  TableColumn partNameColumn;
    @FXML private TableColumn partInventoryColumn;
    @FXML private TableColumn partCostColumn;


    public void handleAddParts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/parts.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            // Get controller so we can define if we are adding or modifying parts
            PartController partController = fxmlLoader.getController();
            partController.setModifyParts(false);
            partController.setInventory(inventory);
            partController.setHomeController(this);

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

            if (getPartToModify() != null)
            {
                partController.setModifyParts(true);
                partController.setPartToModify(getPartToModify());
                partController.setInventory(inventory);
                partController.setHomeController(this);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Invalid");
                alert.setHeaderText("No part was selected!");
                alert.setContentText("Please select a part to modify from the part table!");
                alert.show();
            }

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

    @FXML  void handleExitForm()
    {
        Stage stage = (Stage) exitForm.getScene().getWindow();
        stage.close();
    }

    public void handleSearchParts()
    {

    }

    public void handleSearchProducts()
    {

    }

    public void updateParts()
    {
        ObservableList<Part> inventoryParts = this.inventory.getAllParts();

        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        partTable.setItems(inventory.getAllParts());
    }

    public Part getPartToModify()
    {
        if (partTable.getSelectionModel().getSelectedItem() != null)
        {
           return ((Part) partTable.getSelectionModel().getSelectedItem());
        }
        return  null;
    }
}
