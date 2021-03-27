package sample.GUI;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.BackEnd.Squads.Units.Race;
import sample.Service.Battle;

import java.io.File;
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
    public static boolean isTrue;

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
        else if (Battle.getRedSquad() == null || !Battle.getRedSquad().hasAliveUnits()
                || !Battle.getRedSquad().toString().equals(getFirstSquadName()))
            outputWindow.setText("В первом отряде недостаточно бойцов!");
        else if (Battle.getBlueSquad() == null || !Battle.getBlueSquad().hasAliveUnits()
                || !Battle.getBlueSquad().toString().equals(getSecondSquadName()))
            outputWindow.setText("Во втором отряде недостаточно бойцов!");
        else {
            outputWindow.clear();
            for (String string : Battle.provideBattle())
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
        if (Battle.getConditionOverHere(getFirstSquadName()))
            outputWindow.setText("Ошибка! Введите название первого отряда!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else {
            outputWindow.setText(Battle.addToRedSquad(raceChoiceBox, classChoiceBox, getFirstSquadName()));
        }
    }

    public void generateSquads() throws Exception {
        if (Battle.getConditionOverHere(getFirstSquadName()) ||
                Battle.getConditionOverHere(getSecondSquadName()))
            outputWindow.setText("Ошибка! Введите названия отрядов!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else {
            isTrue = false;
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Внимание!");
            secondaryStage.setScene(new Scene(FXMLLoader.load(new File("src/sample/GUI/anotherSample.fxml").
                    toURI().toURL()), 450, 125));
            secondaryStage.setAlwaysOnTop(true);
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.showAndWait();
            if(isTrue) outputWindow.setText(Battle.generateSquads(getFirstSquadName(), getSecondSquadName()));
        }
    }

    public void addToSecondSquad() {
        if (Battle.getConditionOverHere(getSecondSquadName()))
            outputWindow.setText("Ошибка! Введите назваие второго отряда!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else {
            outputWindow.setText(Battle.addToBlueSquad(raceChoiceBox, classChoiceBox, getSecondSquadName()));
        }
    }

    public void reviveTheFallen() {
        if (Battle.getRedSquad() == null && Battle.getBlueSquad() == null)
            outputWindow.setText("Отряды не сформированы, некого лечить...");
        else {
            Battle.reviveTheFallen();
            outputWindow.setText("Существующие отряды вылечены!");
        }
    }

    public void makeFirstSquadFieldBetter() {
        firstSquad.clear();
    }

    public void makeSecondSquadFieldBetter() {
        secondSquad.clear();
    }
}
