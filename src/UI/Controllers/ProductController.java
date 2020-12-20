package UI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ProductController {

    private static boolean modifyProducts;
    @FXML private Label addProductLabel;

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

    public void handleCancel()
    {

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
