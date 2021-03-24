package sample.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.BackEnd.Squads.Units.Race;
import sample.Service.Battle;

import java.util.Arrays;

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
    @FXML
    private TextArea unitCardArea;

    public void initialize() {
        raceChoiceBox.setItems(FXCollections.observableArrayList(Arrays.asList(Race.values())));
        classChoiceBox.setItems(FXCollections.observableArrayList("Воин", "Лучник", "Маг"));
    }

    private String getFirstSquadName() {
        return firstSquad.getText();
    }

    private String getSecondSquadName() {
        return secondSquad.getText();
    }

    public void makeThisShitWorks() {
        if (Battle.getConditionOverHere(getFirstSquadName()) ||
                Battle.getConditionOverHere(getSecondSquadName()))
            outputWindow.setText("Ошибка! Введите названия отрядов!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else if (Battle.getRedSquad().isEmpty())
            outputWindow.setText("В первом отряде недостаточно бойцов!");
        else if (Battle.getBlueSquad().isEmpty())
            outputWindow.setText("Во втором отряде недостаточно бойцов!");
        else {
            outputWindow.clear();
            for (String string : Battle.provideBattle(getFirstSquadName(), getSecondSquadName()))
                outputWindow.appendText(string);
        }
    }

    public void getUnitInfo() {
        if (raceChoiceBox.getValue() != null && classChoiceBox.getValue() != null) {
            unitCardArea.clear();
            for (String string : Battle.provideUnitCard(raceChoiceBox, classChoiceBox))
                unitCardArea.appendText(string);
        }
    }

    public void addToFirstSquad() {
        if (Battle.getConditionOverHere(getFirstSquadName()) ||
                Battle.getConditionOverHere(getSecondSquadName()))
            outputWindow.setText("Ошибка! Введите названия отрядов!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else {
            Battle.addToRedSquad(raceChoiceBox, classChoiceBox, getFirstSquadName());
            outputWindow.setText(raceChoiceBox.getValue() + " " + classChoiceBox.getValue() +
                    " добавлен в первый отряд.");
        }
    }

    public void addToSecondSquad() {
        if (Battle.getConditionOverHere(getFirstSquadName()) ||
                Battle.getConditionOverHere(getSecondSquadName()))
            outputWindow.setText("Ошибка! Введите названия отрядов!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else {
            Battle.addToBlueSquad(raceChoiceBox, classChoiceBox, getSecondSquadName());
            outputWindow.setText(raceChoiceBox.getValue() + " " + classChoiceBox.getValue() +
                    " добавлен во второй отряд.");
        }
    }

    public void makeFirstSquadFieldBetter() {
        firstSquad.clear();
    }

    public void makeSecondSquadFieldBetter() {
        secondSquad.clear();
    }
}
