package UI.Controllers;
import InventoryAPI.Inventory;
import InventoryAPI.Part;
import InventoryAPI.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author David Brungardt
 */
public class ProductController {

    private Inventory inventory;
    private Product product;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private static boolean modifyProducts;
    @FXML private Label addProductLabel;
    @FXML private  Button cancelProduct;
    @FXML private TextField idTxt;
    @FXML private TextField nameTxt;
    @FXML private  TextField inventoryTxt;
    @FXML private TextField priceCostTxt;
    @FXML private  TextField minTxt;
    @FXML private TextField maxTxt;
    @FXML private TableView partTable;
    @FXML private TableColumn partIDColumn;
    @FXML private  TableColumn partNameColumn;
    @FXML private TableColumn partInventoryColumn;
    @FXML private TableColumn partCostColumn;
    @FXML private TableView productPartTable;
    @FXML private TableColumn productPartIDColumn;
    @FXML private TableColumn productPartNameColumn;
    @FXML private TableColumn productPartInventoryColumn;
    @FXML private TableColumn productPartCostColumn;
    @FXML private TextField partSearchTextField;
    private int productID;
    private String productName;
    private Double productCost;
    private int productStock;
    private int productMin;
    private int productMax;
    private boolean modifyProduct;
    private Controller homeController;

    /**
     * @param inventory the inventory we are adding products to (static from main form)
     */
    public void setInventory(Inventory inventory)
    {
        // static inventory passed in from main form
        this.inventory = inventory;
    }

    /**
     * @param product the product we are going to modify (only used when modifying product)
     */
    public void setProductToModify(Product product)
    {
        if (this.modifyProduct)
        {
            this.product = product;
            this.associatedParts = product.getAllAssociatedParts();
            updateAssociatedParts();
            this.idTxt.setText(String.valueOf(this.product.getId()));
        }
    }

    /**
     * @param controller the main form's controller, used to pass back information
     */
    public void setHomeController(Controller controller)
    {
        // assign home controller so we can interact with main form
        this.homeController = controller;
    }

    /**
     * @param modifyProduct a boolean value that indicates if we are modifying products, (false for adding new product)
     */
    public void setModifyProducts(boolean modifyProduct)
    {
        this.modifyProduct = modifyProduct;

        // update form to show correct labels for add/modify
        if (this.modifyProduct)
        {
            addProductLabel.setText("Modify Product");
        }
        else
        {
            addProductLabel.setText("Add Product");
            idTxt.setText("Auto Gen - Disabled");
        }
    }

    /**
     * method used for validating input on the form
     * @return true if form input is valid, false if input is invalid
     * */
    private boolean inputIsValid()
    {
        boolean isValid = true;

        // check that name exists
        if (nameTxt.getText().isEmpty())
            isValid = false;
        else
            productName = nameTxt.getText();

        // try to parse inventory #
        try {
            productStock = Integer.parseInt(inventoryTxt.getText());
        } catch (Exception e) {
            isValid = false;
        }

        // try to parse part cost
        try {
            productCost = Double.parseDouble(priceCostTxt.getText());
        } catch (Exception e) {
            isValid = false;
        }

        // try to parse part min
        try {
            productMin = Integer.parseInt(minTxt.getText());
        } catch (Exception e) {
            isValid = false;
        }

        // try to parse part max
        try {
            productMax = Integer.parseInt(maxTxt.getText());
        } catch (Exception e) {
            isValid = false;
        }

        // return true if input is valid and false if input is invalid
        return  isValid;
    }

    /**
     * Searches for part in table
     * If ID cannot be parsed, searches for part by name
     * */
    public void handleSearchParts()
    {
        int searchPartID;
        Part searchedPart;
        ObservableList<Part> searchedParts;
        if (!partSearchTextField.getText().isEmpty())
        {
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

    /**
     * updates parts in table based on parts in inventory
     * */
    public void updateParts()
    {
        ObservableList<Part> inventoryParts = this.inventory.getAllParts();

        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        partTable.setItems(inventory.getAllParts());
    }

    /**
     * updates parts in table based on parts associated with product
     * */
    public void updateAssociatedParts()
    {
        productPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        productPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        productPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        productPartCostColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        productPartTable.setItems(this.associatedParts);
    }

    /**
     * adds selected part to product and updates table for associated parts
     * */
    public void handleAddParts()
    {
        Part associatedPart = getPartToModify(partTable);

        if (associatedPart != null)
        {
            this.associatedParts.add(associatedPart);
            updateAssociatedParts();
            // Unselect parts in table after part is added
            partTable.getSelectionModel().select(null);
            productPartTable.getSelectionModel().select(null);
        }
    }

    /**
     * @param tableView takes in a tableview for input, returns selected part (if any)
     * @return returns the selected part if one is selected, otherwise returns null
     * */
    public Part getPartToModify(TableView tableView)
    {
        if (tableView.getSelectionModel().getSelectedItem() != null)
        {
            return ((Part) tableView.getSelectionModel().getSelectedItem());
        }
        return  null;
    }

    /**
     * removes part association with product and removes from product part table
     * */
    public void handleRemoveAssociatedPart()
    {
        Part partToRemove = getPartToModify(productPartTable);

        if (partToRemove != null)
        {
            this.associatedParts.remove(partToRemove);
            updateAssociatedParts();
            // Unselect parts in table after part is deleted
            partTable.getSelectionModel().select(null);
            productPartTable.getSelectionModel().select(null);
        }
    }

    /**
     * saves changes to the product, closes product form and updates product table in main form
     * */
    public void handleSaveProduct()
    {
        Product newProduct;

        if (inputIsValid())
        {
            if (modifyProduct)
            {
                // if we are modifying a product, use index to replace old product with new updated product
                productID = this.product.getId();
                newProduct = new Product(productID, productName, productCost, productStock, productMin, productMax);
                // add associated parts to product and update
                this.associatedParts.forEach((part -> {
                    newProduct.addAssociatedPart(part);
                }));
                this.inventory.updateProduct(productID, newProduct);
            }
            else
            {
                // if we are creating a new product
                productID = this.inventory.getAllProducts().size();
                newProduct = new Product(productID, productName, productCost, productStock, productMin, productMax);
                // add associated parts to product and add product to inventory
                this.associatedParts.forEach((part -> {
                    newProduct.addAssociatedPart(part);
                }));
                this.inventory.addProduct(newProduct);
            }
        }

        this.homeController.updateProducts();
        handleCancel();
    }

    /**
     * handles cancellation of product creation by closing the form
     * */
    @FXML void handleCancel()
    {
        Stage stage = (Stage) cancelProduct.getScene().getWindow();
        stage.close();
    }

    /**
     * @param event takes in a mouse click event
     * removes selection from part tables when form is clicked
     * */
    @FXML private void handleOnMouseClicked(MouseEvent event)
    {
        // Unselect parts in table when user clicks on border pane
        partTable.getSelectionModel().clearSelection();
        productPartTable.getSelectionModel().clearSelection();
    }
}
