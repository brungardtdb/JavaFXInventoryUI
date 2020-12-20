package UI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class PartController {

    private static boolean modifyParts;
    @FXML private Label addPartsLabel;
    @FXML private Label variableLabel;
    @FXML private RadioButton isInHouse;
    @FXML private RadioButton isOutsourced;

    public void handleSavePart()
    {

    }

    public void handleCancelPart()
    {

    }

    public void setModifyParts(boolean modifyParts)
    {
        this.modifyParts = modifyParts;
        if (this.modifyParts)
            addPartsLabel.setText("Modify Parts");
        else
            addPartsLabel.setText("Add Parts");
    }

    public void inHouseChecked()
    {
        if (isInHouse.isSelected())
        {
            variableLabel.setText("Machine ID");
        }
    }

    public void outsourcedChecked()
    {
        if (isOutsourced.isSelected())
        {
            variableLabel.setText("Company Name");
        }
    }
}
