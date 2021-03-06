package UI.Controllers;
import InventoryAPI.Inventory;
import InventoryAPI.AbstractClasses.Part;
import InventoryAPI.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This is the controller for the product form.
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

    //region product logic

    /**
     * This method passes the inventory from the main form into the controller for the part form.
     * @param inventory The inventory we are adding products to (static from main form).
     */
    public void setInventory(Inventory inventory)
    {
        // static inventory passed in from main form
        this.inventory = inventory;
    }

    /**
     * This method passes the controller from the main form into the controller for the product form.
     * Passing the main form controller into this controller will allow us to update tables in the main form.
     * @param controller The main form's controller, used to pass back information.
     */
    public void setHomeController(Controller controller)
    {
        // assign home controller so we can interact with main form
        this.homeController = controller;
    }

    /**
     * If we are modifying a product, this method is used to pass that product into the controller.
     * @param product The product we are going to modify (only used when modifying product).
     */
    public void setProductToModify(Product product)
    {
        if (this.modifyProduct)
        {
            this.product = product;
            this.associatedParts = product.getAllAssociatedParts();
            updateAssociatedParts();
            this.idTxt.setText(String.valueOf(this.product.getId()));
            this.nameTxt.setText(this.product.getName());
            this.inventoryTxt.setText(String.valueOf(this.product.getStock()));
            this.minTxt.setText(String.valueOf(this.product.getMin()));
            this.maxTxt.setText(String.valueOf(this.product.getMax()));
            this.priceCostTxt.setText(String.valueOf(this.product.getPrice()));
        }
    }

    /**
     * Method responsible for defining whether or not we are adding a new product or modifying an existing product.
     * @param modifyProduct A boolean value that indicates if we are modifying products, (false for adding new product).
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
     * Method used for validating input on the form.
     * @return True if form input is valid, false if input is invalid.
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
     * Method used for validating inventory data.
     * @param inputIsValid A boolean indicating if the form input was valid.
     * @return True if form inventory data is valid, false if inventory data is invalid.
     * */
    private boolean inventoryIsValid(boolean inputIsValid)
    {
        boolean output = true;

        if (inputIsValid)
        {
            // check to see that min is less than max and inventory is between min and max
            if ((productMin > productMax) ||
                    (productStock < productMin) ||
                    (productStock > productMax))
            {
                output = false;
                // Alert user that there is a problem with the inventory data
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Inventory Data Invalid!");
                alert.setHeaderText("Inventory Data Invalid!");
                alert.setContentText("Please check inventory data!\n" +
                        "Min must be less than Max!\n" +
                        "Inv must be between Min and Max!");
                alert.show();
            }

        }
        else
        {
            output = false;
        }
        return  output;
    }

    /**
     * Searches for part in table.
     * If ID cannot be parsed, searches for part by name.
     * */
    public void handleSearchParts()
    {
        int searchPartID;
        Part searchedPart;
        ObservableList<Part> searchedParts;
        if (!partSearchTextField.getText().isEmpty())
        {
            // clear any selected parts in table
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
     * Updates parts in table based on parts in inventory.
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
     * Updates parts in table based on parts associated with product.
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
     * Adds selected part to product and updates table for associated parts.
     * */
    public void handleAddParts()
    {
        Part associatedPart = getPartToModify(partTable);

        if (associatedPart != null)
        {
            this.associatedParts.add(associatedPart);
            updateAssociatedParts();
            // Unselect parts in table after part is added
            partTable.getSelectionModel().clearSelection();
            productPartTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * This method gets the selected part from the table on the form.
     * @param tableView Takes in a tableview for input, returns selected part (if any).
     * @return Returns the selected part if one is selected, otherwise returns null.
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
     * Removes part association with product and removes from product part table.
     * Asks for confirmation before removing part.
     * */
    public void handleRemoveAssociatedPart()
    {
        Part partToRemove = getPartToModify(productPartTable);

        if (partToRemove != null)
        {

            // Ask user for confirmation to remove the part from associated parts
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Confirmation");
            alert.setContentText("Are you sure you want to remove this part?");
            ButtonType confirm = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType deny = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirm, deny, cancel);
            alert.showAndWait().ifPresent(type ->{
                if (type == confirm)
                {
                    // If user confirms, remove part
                    this.associatedParts.remove(partToRemove);
                    updateAssociatedParts();
                    // Unselect parts in table after part is deleted
                    partTable.getSelectionModel().clearSelection();
                    productPartTable.getSelectionModel().clearSelection();
                }
            });
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("No part was selected!");
            alert.setContentText("Please select an associated part to remove from the part table!");
            alert.show();
        }
        this.partTable.getSelectionModel().clearSelection();
        this.productPartTable.getSelectionModel().clearSelection();
    }

    /**
     * Saves changes to the product, closes product form and updates product table in main form.
     * */
    public void handleSaveProduct()
    {
        Product newProduct;

        if (inventoryIsValid(inputIsValid()))
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
            this.homeController.updateProducts();
            handleCancel();
        }
        else
        {
            // if input is invalid, alert user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Input was Invalid!");
            alert.setContentText("Please enter valid product attributes." +
                    "\nPlease check your input and try again!");
            alert.show();
        }
    }

    //endregion

    //region form controls

    /**
     * Handles cancellation of product creation by closing the form.
     * */
    @FXML void handleCancel()
    {
        Stage stage = (Stage) cancelProduct.getScene().getWindow();
        stage.close();
    }

    /**
     * Removes selection from part tables when form is clicked.
     * @param event Takes in a mouse click event.
     * */
    @FXML private void handleOnMouseClicked(MouseEvent event)
    {
        // Unselect parts in table when user clicks on border pane
        partTable.getSelectionModel().clearSelection();
        productPartTable.getSelectionModel().clearSelection();
    }

    //endregion
}
