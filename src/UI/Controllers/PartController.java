package UI.Controllers;
import InventoryAPI.InHouse;
import InventoryAPI.Outsourced;
import InventoryAPI.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import InventoryAPI.Inventory;
import javafx.stage.Stage;
import javax.sound.midi.ControllerEventListener;

/**
 *
 * @author David Brungardt
 */
public class PartController {

    private Inventory inventory;
    private static boolean modifyParts;
    @FXML private Label addPartsLabel;
    @FXML private Label variableLabel;
    @FXML private RadioButton isInHouse;
    @FXML private RadioButton isOutsourced;
    @FXML private Button cancelPart;
    @FXML private TextField idTxt;
    @FXML private TextField nameTxt;
    @FXML private  TextField inventoryTxt;
    @FXML private TextField priceCostTxt;
    @FXML private  TextField minTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField variableTxt;
    private int partID;
    private String partName;
    private Double partCost;
    private int partStock;
    private int partMin;
    private int partMax;
    private int partMachineID;
    private String partCompanyName;
    private Part partToModify;
    private int partIndex;
    private Part returnPart;
    private Controller homeController;

    /**
     * @param inventory the inventory we are adding parts to (static from main form)
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
     * @param part the part we are modifying if we are modifying a part (as opposed to adding a new part)
     */
    public void setPartToModify(Part part)
    {
        // If we are modifying parts, assign part to controller so we have access to part info
        // We are mostly using this to grab the part ID, I haven't added a lot of constraints
        // So users can pretty much change whatever they want
        if (modifyParts)
        {
            this.partToModify = part;
            this.partIndex = part.getId();
            idTxt.setText(String.valueOf(this.partToModify.getId()));
        }
    }

    /**
     * checks if input is valid and saves the part to the inventory
     * this is used for adding new parts as well as modifying existing parts
     * */
    public void handleSavePart()
    {
        // only create and modify parts if we have valid input
        if (inputIsValid())
        {
            if (modifyParts)
            {
                // if we are modifying parts, replace old part with new in inventory
                if (isInHouse.isSelected())
                {
                    this.returnPart = new InHouse(this.partToModify.getId(), partName, partCost, partStock, partMin, partMax, partMachineID);
                    this.inventory.updatePart(this.partIndex, this.returnPart);
                }
                else {
                    this.returnPart = new Outsourced(partID, partName, partCost, partStock, partMin, partMax, partCompanyName);
                    this.inventory.updatePart(this.partIndex, this.returnPart);
                }
            }
            else {
                // if we are adding parts, use the current size to index the part and add to inventory
                // this ensures each part has a unique ID and makes it easy to find the index for each part
                partID = this.inventory.getAllParts().size();
                if (isInHouse.isSelected()) {
                    this.returnPart = new InHouse(partID, partName, partCost, partStock, partMin, partMax, partMachineID);
                    inventory.addPart(this.returnPart);

                } else if (isOutsourced.isSelected()) {
                    this.returnPart = new Outsourced(partID, partName, partCost, partStock, partMin, partMax, partCompanyName);
                    inventory.addPart(this.returnPart);
                }
            }
            // update part table on main form before closing part form
            this.homeController.updateParts();
            handleCancelPart();
        }
        else {
            // if input is invalid, alert user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Invalid");
            alert.setHeaderText("Input was Invalid!");
            alert.setContentText("Please enter valid part attributes." +
                    "\nPlease check your input and try again!");
            alert.show();
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
            partName = nameTxt.getText();

        // try to parse inventory #
        try {
            partStock = Integer.parseInt(inventoryTxt.getText());
        } catch (Exception e) {
            isValid = false;
        }

        // try to parse part cost
        try {
            partCost = Double.parseDouble(priceCostTxt.getText());
        } catch (Exception e) {
            isValid = false;
        }

        // try to parse part min
        try {
            partMin = Integer.parseInt(minTxt.getText());
        } catch (Exception e) {
            isValid = false;
        }

        // try to parse part max
        try {
            partMax = Integer.parseInt(maxTxt.getText());
        } catch (Exception e) {
            isValid = false;
        }

        // if part is in house, try to parse machine ID
        if (isInHouse.isSelected())
        {
            try {
                partMachineID = Integer.parseInt(variableTxt.getText());
            } catch (Exception e) {
                isValid = false;
            }
        }

        // if part is outsourced, check for company name
        if (isOutsourced.isSelected())
        {
            if (variableTxt.getText().isEmpty())
                isValid = false;
            else
                partCompanyName = variableTxt.getText();
        }

        // return true if input is valid and false if input is invalid
        return  isValid;
    }

    /**
     * method for the "cancel" button on the form
     * responsible for closing the form when user cancels a part entry/modification
     * */
    @FXML public void handleCancelPart()
    {
        // close form
        Stage stage = (Stage) cancelPart.getScene().getWindow();
        stage.close();
    }

    /**
     * @param modifyParts a boolean value that indicates if we are modifying parts, (false for adding new parts)
     */
    public void setModifyParts(boolean modifyParts)
    {
        this.modifyParts = modifyParts;

        // update form to show correct labels for add/modify
        if (this.modifyParts)
        {
            addPartsLabel.setText("Modify Parts");
        }
        else
        {
            addPartsLabel.setText("Add Parts");
            idTxt.setText("Auto Gen - Disabled");
        }
        // default label to in-house
        variableLabel.setText("Machine ID");
    }

    /**
     * checks to see if in-house radio button is selected
     * updates form based on which radio button is selected
     * */
    public void inHouseChecked()
    {
        // if part is in-house ,update form label
        if (isInHouse.isSelected())
        {
            variableLabel.setText("Machine ID");
        }
    }

    /**
     * checks to see if outsourced radio button is selected
     * updates form based on which radio button is selected
     * */
    public void outsourcedChecked()
    {
        // if part is outsourced, update form label
        if (isOutsourced.isSelected())
        {
            variableLabel.setText("Company Name");
        }
    }
}
