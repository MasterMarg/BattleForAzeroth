package sample.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.BackEnd.Squads.Squad;
import sample.BackEnd.Squads.Units.Unit;
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
        if (getConditionOverHere(getFirstSquadName(), getSecondSquadName())) {
            outputWindow.setText("Ошибка! Введите названия отрядов!");
            firstSquad.clear();
            secondSquad.clear();
        }
        else if (getFirstSquadName().equals(getSecondSquadName())) {
            outputWindow.setText("Ошибка! Имена отрядов должны различаться!");
            firstSquad.clear();
            secondSquad.clear();
        }
        else {
            Battle battle = new Battle(getFirstSquadName(), getSecondSquadName());
            outputWindow.setText(battle.getDateHelper().getFormattedStartDate() +
                    "\nБитва началась!\n\n");
            while (battle.getRedSquad().hasAliveUnits() && battle.getBlueSquad().hasAliveUnits()) {
                provideBattleLog(battle, battle.makeAttack(battle.getRedSquad(),
                        battle.getBlueSquad()), battle.getBlueSquad());
                if (battle.getBlueSquad().hasAliveUnits())
                    provideBattleLog(battle, battle.makeAttack(battle.getBlueSquad(),
                            battle.getRedSquad()), battle.getRedSquad());
            }
            if (battle.getRedSquad().hasAliveUnits())
                outputWindow.appendText(getResult(battle.getRedSquad()));
            else outputWindow.appendText(getResult(battle.getBlueSquad()));
            outputWindow.appendText("\n" + "Битва продолжалась " +
                    battle.getDateHelper().getFormattedDiff());
        }
    }

    private static boolean getConditionOverHere(String firstSquadName, String secondSquadName) {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я0-9]+");
        return (!pattern.matcher(firstSquadName).matches() ||
                !pattern.matcher(secondSquadName).matches());
    }

    private static String getResult(Squad squad) {
        return "Победил " + squad.toString() + " отряд.";
    }

    private void provideBattleLog(Battle battle, Object[] data, Squad squad) {
        outputWindow.appendText(battle.getDateHelper().getDate());
        Unit unit1 = (Unit) data[0];
        Unit unit2 = (Unit) data[1];
        int attack = (Integer) data[2];
        int vitality = (Integer) data[4];
        int damage = (Integer) data[5];
        outputWindow.appendText("\n" + unit1 + unit1.getUnitVitalityCard() + " атакует " + unit2 +
                unit2.getUnitVitalityCard(vitality));
        outputWindow.appendText("\n" + unit1 + " наносит " + attack + "(-" + (attack - damage) +
                ")" + reformString(attack) + " урона.");
        if ((Integer) data[3] == 1)
            outputWindow.appendText(" Критический удар!");
        if (unit2.isAlive())
            outputWindow.appendText("\n" + unit2 + " теряет " + damage + reformString(damage) +
                    " здоровья.\n\n");
        else outputWindow.appendText("\n" + unit2 + " теряет " + vitality + reformString(vitality) +
                " здоровья.\n");
        if (!unit2.isAlive())
            outputWindow.appendText("Боец убит.\n\n");
        if (squad.hasAliveUnits()) battle.getDateHelper().skipTime();
    }

    private String reformString(int input) {
        String output;
        if (input % 100 > 20 || input % 100 < 10) {
            switch (input % 10) {
                case 1: {
                    output = " единицу";
                    break;
                }
                case 2:
                case 3:
                case 4: {
                    output = " единицы";
                    break;
                }
                default: {
                    output = " единиц";
                    break;
                }
            }
        } else output = " единиц";
        return output;
    }
}
