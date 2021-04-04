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
    @FXML
    private TextArea unitCardArea1;
    public static boolean isTrue;

    public void initialize() {
        raceChoiceBox.setItems(FXCollections.observableArrayList("Раса", Race.HUMAN, Race.WOOD_ELF,
                Race.HIGH_ELF, Race.DWARF, Race.ORC));
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
        else outputWindow.setText(Battle.provideBattle());
    }

    public void getUnitInfo() {
        if (!raceChoiceBox.getValue().equals("Раса") && !classChoiceBox.getValue().equals("Класс")) {
            String[] temp = Battle.provideUnitCard((Race) raceChoiceBox.getValue(),
                    (String) classChoiceBox.getValue());
            unitCardArea.setText(temp[0]);
            unitCardArea1.setText(temp[1]);
        } else {
            unitCardArea.clear();
            unitCardArea1.clear();
        }
    }

    public void addToFirstSquad() {
        if (Battle.getConditionOverHere(getFirstSquadName()))
            outputWindow.setText("Ошибка! Введите название первого отряда!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else if (raceChoiceBox.getValue().equals("Раса")) outputWindow.setText("Выберите расу!");
        else if (classChoiceBox.getValue().equals("Класс")) outputWindow.setText("Выберите класс!");
        else outputWindow.setText(Battle.addToRedSquad((Race) raceChoiceBox.getValue(),
                    (String) classChoiceBox.getValue(), getFirstSquadName()));
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
            secondaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(
                    "anotherSample.fxml")), 450, 125));
            secondaryStage.setAlwaysOnTop(true);
            secondaryStage.setResizable(false);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.showAndWait();
            if (isTrue) outputWindow.setText(Battle.generateSquads(getFirstSquadName(), getSecondSquadName()));
        }
    }

    public void addToSecondSquad() {
        if (Battle.getConditionOverHere(getSecondSquadName()))
            outputWindow.setText("Ошибка! Введите назваие второго отряда!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else if (raceChoiceBox.getValue().equals("Раса")) outputWindow.setText("Выберите расу!");
        else if (classChoiceBox.getValue().equals("Класс")) outputWindow.setText("Выберите класс!");
        else outputWindow.setText(Battle.addToBlueSquad((Race) raceChoiceBox.getValue(),
                    (String) classChoiceBox.getValue(), getSecondSquadName()));
    }

    public void reviveTheFallen() {
        if (Battle.getRedSquad() == null && Battle.getBlueSquad() == null)
            outputWindow.setText("Отряды не сформированы, некого лечить...");
        else {
            Battle.reviveTheFallen();
            outputWindow.setText("Существующие отряды вылечены!");
        }
    }

    public void showSquads() {
        if (Battle.getRedSquad() != null) {
            outputWindow.setText(Battle.showSquad(Battle.getRedSquad()));
        }
        if (Battle.getBlueSquad() != null) {
            outputWindow.appendText("\n\n" + Battle.showSquad(Battle.getBlueSquad()));
        }
        if (Battle.getRedSquad() == null && Battle.getBlueSquad() == null)
            outputWindow.setText("Отряды еще не созданы!");
    }

    public void makeFirstSquadFieldBetter() {
        firstSquad.clear();
    }

    public void makeSecondSquadFieldBetter() {
        secondSquad.clear();
    }
}