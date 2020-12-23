package UI.Controllers;
import InventoryAPI.Inventory;
import InventoryAPI.Part;
import InventoryAPI.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.*;
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
    @FXML private TableView productTable;
    @FXML private TableColumn productIDColumn;
    @FXML private TableColumn productNameColumn;
    @FXML private TableColumn productInventoryColumn;
    @FXML private TableColumn productPriceColumn;
    @FXML private BorderPane mainBorderPane;
    @FXML private TextField partSearchTextField;
    @FXML private TextField productSearchTextField;


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

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Modify Parts");
                stage.setScene(new Scene(root));
                stage.show();

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Invalid");
                alert.setHeaderText("No part was selected!");
                alert.setContentText("Please select a part to modify from the part table!");
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteParts()
    {
        Part partToDelete = getPartToModify();

        if (partToDelete != null) {
            inventory.deletePart(partToDelete);
            updateParts();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("No part was selected!");
            alert.setContentText("Please select a part to delete from the part table!");
            alert.show();
        }
        // Unselect parts in table after part is deleted
        partTable.getSelectionModel().clearSelection();
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
            productController.setInventory(inventory);
            productController.setHomeController(this);
            productController.updateParts();


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

            if (getProductToModify() != null)
            {
                // Get controller so we can define if we are adding or modifying parts
                ProductController productController = fxmlLoader.getController();
                productController.setModifyProducts(true);
                productController.setProductToModify(getProductToModify());
                productController.setInventory(inventory);
                productController.setHomeController(this);
                productController.updateParts();

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.DECORATED);
                stage.setTitle("Modify Products");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Invalid");
                alert.setHeaderText("No product was selected!");
                alert.setContentText("Please select a product to modify from the product table!");
                alert.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteProducts()
    {
        Product productToDelete = getProductToModify();

        if (productToDelete != null)
        {
            inventory.deleteProduct(productToDelete);
            updateProducts();
            productTable.getSelectionModel().clearSelection();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("No product was selected!");
            alert.setContentText("Please select a product to delete from the part table!");
            alert.show();
        }
    }

    @FXML  void handleExitForm()
    {
        Stage stage = (Stage) exitForm.getScene().getWindow();
        stage.close();
    }

    public void handleSearchParts()
    {
        int searchPartID;
        Part searchedPart;
        ObservableList<Part> searchedParts;
        if (!partSearchTextField.getText().isEmpty())
        {
            // Clear selection from table
            partTable.getSelectionModel().clearSelection();

            try {
                // First try to search for part ID
                searchPartID = Integer.parseInt(partSearchTextField.getText());
                searchedPart = this.inventory.lookupPart(searchPartID);
                partTable.getSelectionModel().select(searchedPart);
            } catch (Exception e) {
                // If ID cannot be parsed, try to search by name
                searchedParts = this.inventory.lookupPart(partSearchTextField.getText());
                searchedParts.forEach((part -> {
                    partTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    partTable.getSelectionModel().select(part);
                }));
            }
        }
    }

    public void handleSearchProducts()
    {
        int searchProductID;
        Product searchedProduct;
        ObservableList<Product> searchedProducts;
        if (!productSearchTextField.getText().isEmpty())
        {
            // Clear selection from table
           productTable.getSelectionModel().clearSelection();

            try {
                // First try to search for part ID
                searchProductID = Integer.parseInt(productSearchTextField.getText());
                searchedProduct = this.inventory.lookupProduct(searchProductID);
                productTable.getSelectionModel().select(searchedProduct);
            } catch (Exception e) {
                // If ID cannot be parsed, try to search by name
                searchedProducts = this.inventory.lookupProduct(productSearchTextField.getText());
                searchedProducts.forEach((product -> {
                    productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    productTable.getSelectionModel().select(product);
                }));
            }
        }
    }

    public void updateParts()
    {
        ObservableList<Part> inventoryParts = this.inventory.getAllParts();

        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        partTable.setItems(inventory.getAllParts());
        // Unselect parts in table after part is deleted
        partTable.getSelectionModel().clearSelection();
        partTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public Part getPartToModify()
    {
        if (partTable.getSelectionModel().getSelectedItem() != null)
        {
            return ((Part) partTable.getSelectionModel().getSelectedItem());
        }
        return  null;
    }

    public Product getProductToModify()
    {
        if (productTable.getSelectionModel().getSelectedItem() != null)
        {
            return ((Product) productTable.getSelectionModel().getSelectedItem());
        }
        return  null;
    }

    public void updateProducts()
    {
        ObservableList<Product> inventoryProducts = this.inventory.getAllProducts();

        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));

        productTable.setItems(inventoryProducts);
        // Unselect parts in table after part is updated
        productTable.getSelectionModel().clearSelection();
        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * removes selection from part tables when form is clicked
     * */
    @FXML public void mainFormClicked(MouseEvent event)
    {
        // Unselect parts in table when user clicks on border pane
        partTable.getSelectionModel().clearSelection();
        productTable.getSelectionModel().clearSelection();
    }
}
