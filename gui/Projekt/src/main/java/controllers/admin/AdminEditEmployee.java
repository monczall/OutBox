package main.java.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;



public class AdminEditEmployee {

    @FXML
    private AnchorPane edit;

    public void showEdit(MouseEvent mouseEvent)throws IOException
        {
            SceneManager.loadScene("../../../resources/view/admin/adminEdit.fxml", edit);
        }
}
