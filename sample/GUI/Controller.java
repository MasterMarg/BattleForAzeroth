package sample.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Service.Battle;

public class Controller {

    @FXML
    private TextArea outputWindow;
    @FXML
    private TextField firstSquad;
    @FXML
    private TextField secondSquad;
    @FXML
    private ChoiceBox raceChoiceBox;
    @FXML
    private ChoiceBox classChoiceBox;

    public void initialize() {
        Battle.initialize(raceChoiceBox, classChoiceBox);
    }

    @FXML
    private String getFirstSquadName() {
        return firstSquad.getText();
    }

    @FXML
    private String getSecondSquadName() {
        return secondSquad.getText();
    }

    public void makeThisShitWorks() {
        if (Battle.getConditionOverHere(getFirstSquadName(), getSecondSquadName()))
            outputWindow.setText("Ошибка! Введите названия отрядов!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else {
            outputWindow.clear();
            for (String string: Battle.provideBattle(getFirstSquadName(), getSecondSquadName()))
                outputWindow.appendText(string);
        }
    }

    public void makeFirstSquadFieldBetter(){
        firstSquad.clear();
    }

    public void makeSecondSquadFieldBetter(){
        secondSquad.clear();
    }
}
