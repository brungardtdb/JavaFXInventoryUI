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

    //region parts

    /**
     * handles adding parts to the inventory
     * opens the add part form and adds parts to the part table
     * */
    public void handleAddParts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/parts.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            // Get controller and configure controller settings
            PartController partController = fxmlLoader.getController();
            partController.setModifyParts(false);
            partController.setInventory(inventory);
            partController.setHomeController(this);

            // Spin up part form
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

    /**
     * handles modifying parts in the inventory
     * opens the modify part form, and loads the selected part
     * */
    public void handleModifyParts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/parts.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            // Get controller and configure controller settings
            PartController partController = fxmlLoader.getController();

            if (getPartToModify() != null)
            {
                partController.setModifyParts(true);
                partController.setPartToModify(getPartToModify());
                partController.setInventory(inventory);
                partController.setHomeController(this);

                // Spin up part form
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

    /**
     * handles deleting parts from the inventory and removing parts from the part table
     * */
    public void handleDeleteParts()
    {
        Part partToDelete = getPartToModify();

        if (partToDelete != null) {
            inventory.deletePart(partToDelete);
            updateParts();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("The part has been deleted!");
            alert.setHeaderText("The part has been deleted!");
            alert.setContentText("The part has been removed from the part table!");
            alert.show();

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

    /**
     * searches for parts in the part table and selects part(s) if found.
     * */
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
                if (searchedPart != null)
                    partTable.getSelectionModel().select(searchedPart);
                else // if ID parsed and not found
                    throw new Exception("Item not found");

            } catch (Exception e) {
                // If ID cannot be parsed, try to search by name
                searchedParts = this.inventory.lookupPart(partSearchTextField.getText());
                if (searchedParts != null && searchedParts.size() > 0)
                {
                    // If part search yields results
                    searchedParts.forEach((part -> {
                        partTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                        partTable.getSelectionModel().select(part);
                    }));
                }
                else
                {
                    // Alert user that no part was found
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No part was found!");
                    alert.setHeaderText("No part was found!");
                    alert.setContentText("Your search returned no results.\n" +
                            "Please enter the part ID or part name and try again.");
                    alert.show();
                }
            }
        }
        else
        {
            partTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * updates parts in the part table and removes row selection
     * */
    public void updateParts()
    {
        ObservableList<Part> inventoryParts = this.inventory.getAllParts();

        // Configure part table and bind with inventory parts
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        partTable.setItems(inventory.getAllParts());
        // Unselect parts in table after part is deleted
        partTable.getSelectionModel().clearSelection();
        partTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * @return selected part in part table
     * */
    public Part getPartToModify()
    {
        if (partTable.getSelectionModel().getSelectedItem() != null)
        {
            return ((Part) partTable.getSelectionModel().getSelectedItem());
        }
        return  null;
    }

    //endregion

    //region products

    /**
     * handles adding products to the inventory
     * opens the add product form and adds products to the product table
     * */
    public void handleAddProducts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/product.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            // Get controller and configure controller settings
            ProductController productController = fxmlLoader.getController();
            productController.setModifyProducts(false);
            productController.setInventory(inventory);
            productController.setHomeController(this);
            productController.updateParts();

            // Spin up product form
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

    /**
     * handles modifying products in the inventory
     * opens the modify product form, and loads the selected part
     * */
    public void handleModifyProducts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/product.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();

            if (getProductToModify() != null)
            {
                // Get controller and configure controller settings
                ProductController productController = fxmlLoader.getController();
                productController.setModifyProducts(true);
                productController.setProductToModify(getProductToModify());
                productController.setInventory(inventory);
                productController.setHomeController(this);
                productController.updateParts();

                // Spin up product form
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

    /**
     * handles deleting products from the inventory and removing products from the product table
     * */
    public void handleDeleteProducts()
    {
        Product productToDelete = getProductToModify();

        if (productToDelete != null)
        {
            // User cannot delete products with associated parts
            if (productToDelete.getAllAssociatedParts().size() > 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Action is Forbidden!");
                alert.setHeaderText("Action is Forbidden!");
                alert.setContentText("Products with associated parts cannot be deleted!\n  " +
                        "Please remove associated parts from product and try again!");
                alert.show();
            }
            else
            {
                inventory.deleteProduct(productToDelete);
                updateProducts();
                productTable.getSelectionModel().clearSelection();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product has been deleted!");
                alert.setHeaderText("Product has been deleted!");
                alert.setContentText("The product has been removed from the product table!");
                alert.show();
            }

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

    /**
     * searches for products in the product table and selects product(s) if found.
     * */
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
                if (searchedProduct != null)
                    productTable.getSelectionModel().select(searchedProduct);
                else // if ID parsed and not found
                    throw new Exception("Item not found");

            } catch (Exception e) {
                // If ID cannot be parsed, try to search by name
                searchedProducts = this.inventory.lookupProduct(productSearchTextField.getText());

                if (searchedProducts != null && searchedProducts.size() > 0)
                {
                    // If product search yields results
                    searchedProducts.forEach((product -> {
                        productTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                        productTable.getSelectionModel().select(product);
                    }));
                }
                else
                {   // If no products found alert user
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No product was found!");
                    alert.setHeaderText("No product was found!");
                    alert.setContentText("Your search returned no results.\n" +
                            "Please enter the product ID or part name and try again.");
                    alert.show();
                }
            }
        }
        else
        {
            productTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * updates products in the product table and removes row selection
     * */
    public void updateProducts()
    {
        ObservableList<Product> inventoryProducts = this.inventory.getAllProducts();

        // Configure product table and bind with inventory products
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
     * @return selected product in product table
     * */
    public Product getProductToModify()
    {
        if (productTable.getSelectionModel().getSelectedItem() != null)
        {
            return ((Product) productTable.getSelectionModel().getSelectedItem());
        }
        return  null;
    }

    //endregion

    //region form controls

    /**
     * handles closing of the inventory management system window
     * */
    @FXML  void handleExitForm()
    {
        Stage stage = (Stage) exitForm.getScene().getWindow();
        stage.close();
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

    //endregion
}
