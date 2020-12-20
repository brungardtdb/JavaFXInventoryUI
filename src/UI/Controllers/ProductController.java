package UI.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import InventoryAPI.Inventory;
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

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    public void handleSearchProducts()
    {

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
