package sample.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.Service.Battle;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    private TextArea outputWindow;
    @FXML
    private TextField firstSquad;
    @FXML
    private TextField secondSquad;

    public void initialize() {
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
        if (getConditionOverHere(getFirstSquadName(), getSecondSquadName()))
            outputWindow.setText("Ошибка! Введите названия отрядов!\n" +
                    "Знаки препинания и пробелы в именах запрещены!");
        else if (getFirstSquadName().equals(getSecondSquadName()))
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
        else {
            Battle battle = new Battle(getFirstSquadName(), getSecondSquadName());
            outputWindow.setText(battle.getDateHelper().getFormattedStartDate() +
                    "\nБитва началась!\n");
            while (battle.getRedSquad().hasAliveUnits() && battle.getBlueSquad().hasAliveUnits()) {
                for(String string: battle.makeAttack(battle.getRedSquad(), battle.getBlueSquad()))
                    outputWindow.appendText(string);
                if (battle.getBlueSquad().hasAliveUnits())
                    for(String string: battle.makeAttack(battle.getBlueSquad(), battle.getRedSquad()))
                        outputWindow.appendText(string);
            }
            if (battle.getRedSquad().hasAliveUnits())
                outputWindow.appendText("\nПобедил " + battle.getRedSquad().toString() + " отряд.");
            else outputWindow.appendText("\nПобедил " + battle.getBlueSquad().toString() + " отряд.");
            outputWindow.appendText("\n" + "Битва продолжалась " +
                    battle.getDateHelper().getFormattedDiff());
        }
    }

    public void makeFirstSquadFieldBetter(){
        firstSquad.clear();
    }

    public void makeSecondSquadFieldBetter(){
        secondSquad.clear();
    }

    private static boolean getConditionOverHere(String firstSquadName, String secondSquadName) {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я0-9]+");
        return (!pattern.matcher(firstSquadName).matches() ||
                !pattern.matcher(secondSquadName).matches());
    }
}
