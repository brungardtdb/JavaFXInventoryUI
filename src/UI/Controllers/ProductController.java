package UI.Controllers;
import InventoryAPI.Part;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import InventoryAPI.Inventory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.print.CancelablePrintJob;

/**
 *
 * @author David Brungardt
 */
public class ProductController {

    private Inventory inventory;
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
    public void setModifyParts(boolean modifyProduct)
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

    public void handleSearchParts()
    {
        int searchPartID;
        Part searchedPart;
        ObservableList<Part> searchedParts;
        if (!partSearchTextField.getText().isEmpty())
        {
            try {
                searchPartID = Integer.parseInt(partSearchTextField.getText());
                searchedPart = this.inventory.lookupPart(searchPartID);
                partTable.getSelectionModel().select(searchedPart);
            } catch (Exception e) {
                searchedParts = this.inventory.lookupPart(partSearchTextField.getText());
                searchedParts.forEach((part -> {
                    partTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                    partTable.getSelectionModel().select(part);
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
    }

    public void handleAddProducts()
    {

    }

    public void handleRemoveAssociatedPart()
    {

    }

    public void handleSaveProduct()
    {

    }

    @FXML void handleCancel()
    {
        Stage stage = (Stage) cancelProduct.getScene().getWindow();
        stage.close();
    }

    public void setModifyProducts(boolean modifyProducts)
    {
        this.modifyProducts = modifyProducts;
        if (this.modifyProducts)
            addProductLabel.setText("Modify Products");
        else
            addProductLabel.setText("Add Products");
    }

}
