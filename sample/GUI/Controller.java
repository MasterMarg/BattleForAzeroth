package sample.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.BackEnd.Squads.Squad;
import sample.BackEnd.Squads.Units.Classes.Mage;
import sample.BackEnd.Squads.Units.Unit;
import sample.Service.Battle;
import sample.Service.Painter;

import java.io.IOException;
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
        try {
            if (getConditionOverHere(getFirstSquadName(), getSecondSquadName()))
            /*if (getFirstSquadName().equals("") || getSecondSquadName().equals(""))*/ throw new Exception();
            try {
                if (getFirstSquadName().equals(getSecondSquadName())) throw new IOException();
                Battle battle = new Battle(getFirstSquadName(), getSecondSquadName());
                outputWindow.setText(Painter.getPainted(battle.getDateHelper().getFormattedStartDate() +
                        "\nБитва началась!\n\n", Painter.Paint.ANSI_BOLD));
                while (battle.getRedSquad().hasAliveUnits() && battle.getBlueSquad().hasAliveUnits()) {
                    provideBattleLog(battle, battle.makeAttack(battle.getRedSquad(),
                            battle.getBlueSquad()), battle.getBlueSquad());
                    if (battle.getBlueSquad().hasAliveUnits())
                        provideBattleLog(battle, battle.makeAttack(battle.getBlueSquad(),
                                battle.getRedSquad()), battle.getRedSquad());
                }
                if (battle.getRedSquad().hasAliveUnits()) outputWindow.appendText(getResult(battle.getRedSquad()));

                else outputWindow.appendText(getResult(battle.getBlueSquad()));
                outputWindow.appendText("\n" + Painter.getPainted("Битва продолжалась " +
                        battle.getDateHelper().getFormattedDiff(), Painter.Paint.ANSI_BOLD));
            } catch (IOException exception) {
                outputWindow.setText(Painter.getPainted("Ошибка! Имена отрядов должны различаться!",
                        Painter.Paint.ANSI_BOLD));
            }
        } catch (Exception exception) {
            outputWindow.setText(Painter.getPainted("Ошибка! Введите названия отрядов!",
                    Painter.Paint.ANSI_BOLD));
        }
    }

    private static boolean getConditionOverHere(String firstSquadName, String secondSquadName){
        boolean isNameNotValid = true;
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я]+");
        if(pattern.matcher(firstSquadName).matches()&&pattern.matcher(secondSquadName).matches())
            isNameNotValid = false;
        return isNameNotValid;
    }

    private static String getResult(Squad squad) {
        String result;
        switch (squad.toString()) {
            case "Красный": {
                result = Painter.getPainted("Победил красный отряд", Painter.Paint.ANSI_BOLD_RED);
                break;
            }
            case "Синий": {
                result = Painter.getPainted("Победил синий отряд", Painter.Paint.ANSI_BOLD_BLUE);
                break;
            }
            default: {
                result = "Победил " + squad.toString() + " отряд.";
                break;
            }
        }
        return result;
    }

    private void provideBattleLog(Battle battle, Object[] data, Squad squad) {
        outputWindow.appendText(battle.getDateHelper().getDate());
        Unit unit1 = (Unit) data[0];
        Unit unit2 = (Unit) data[1];
        int attack = (Integer) data[2];
        int vitality = (Integer) data[4];
        int damage = (Integer) data[5];
        outputWindow.appendText("\n" + getPainted(unit1) + Painter.getPainted(unit1.getUnitVitalityCard(),
                Painter.Paint.ANSI_BOLD) + " атакует " + getPainted(unit2) +
                Painter.getPainted(unit2.getUnitVitalityCard(vitality), Painter.Paint.ANSI_BOLD));
        String attackString = "" + attack;
        if ((Integer) data[3] == 1) attackString = Painter.getPainted(attackString, Painter.Paint.ANSI_BOLD_YELLOW);
        else attackString = Painter.getPainted(attackString, Painter.Paint.ANSI_WHITE_BOLD);
        if (unit1 instanceof Mage) {
            outputWindow.appendText("\n" + getPainted(unit1) + " наносит " + attackString +
                    Painter.getPainted("(-" + (attack - damage) + ")", Painter.Paint.ANSI_CYAN_BOLD) +
                    reformString(attack) + " урона.");
        } else {
            outputWindow.appendText("\n" + getPainted(unit1) + " наносит " + attackString +
                    Painter.getPainted("(-" + (attack - damage) + ")", Painter.Paint.ANSI_GREEN_BOLD) +
                    reformString(attack) + " урона.");
        }
        if ((Integer) data[3] == 1)
            outputWindow.appendText(Painter.getPainted(" Критический удар!", Painter.Paint.ANSI_BOLD_YELLOW));
        if (unit2.isAlive())
            outputWindow.appendText("\n" + getPainted(unit2) + " теряет " + damage + reformString(damage) +
                    " здоровья.\n\n");
        else outputWindow.appendText("\n" + getPainted(unit2) + " теряет " + vitality + reformString(vitality) +
                " здоровья.\n");
        if (!unit2.isAlive())
            outputWindow.appendText(Painter.getPainted("Боец убит.\n\n", Painter.Paint.ANSI_VIOLET));
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

    private String getPainted(Unit unit) {
        String output;
        switch (unit.getSquadName()) {
            case "Красный": {
                output = Painter.getPainted(unit.toString(), Painter.Paint.ANSI_RED);
                break;
            }
            case "Синий": {
                output = Painter.getPainted(unit.toString(), Painter.Paint.ANSI_BLUE);
                break;
            }
            default: {
                output = unit.toString();
                break;
            }
        }
        return output;
    }
}
