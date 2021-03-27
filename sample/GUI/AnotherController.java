package sample.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Service.Battle;

public class AnotherController {

    @FXML
    private Button cancelButton;

    @FXML
    private Button acceptButton;

    public void initialize(){}

    public void pressCancel() {
        Stage stage = (Stage)(cancelButton.getScene().getWindow());
        stage.close();
    }

    public void pressAccept(){
        Window window = acceptButton.getScene().getWindow();
        window.hide();
        Controller.isTrue = true;
    }
}
