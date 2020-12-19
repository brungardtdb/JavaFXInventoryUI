package UI.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

/*  Button Example*/

    public Button button;
    public void handleButtonClick()
    {
        System.out.println("Button has been clicked.");
    }

    public void handleAddParts()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/Views/parts.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Add Parts");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleModifyParts()
    {

    }

    public void handleDeleteParts()
    {

    }

    public void handleAddProducts()
    {

    }

    public void handleModifyProducts()
    {

    }

    public void handleDeleteProducts()
    {

    }

    public  void handleExitForm()
    {

    }

    public void handleSearchParts()
    {

    }

    public void handleSearchProducts()
    {

    }

}
