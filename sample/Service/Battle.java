package sample.Service;

import sample.BackEnd.DateHelper;
import sample.BackEnd.Squads.Squad;
import sample.BackEnd.Squads.Units.Unit;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Battle {

    public static ArrayList<String> provideBattle(String firstSquad, String secondSquad) {
        DateHelper dateHelper = new DateHelper();
        Squad redSquad = new Squad(firstSquad);
        Squad blueSquad = new Squad(secondSquad);
        ArrayList<String> output = new ArrayList<>();
        output.add(dateHelper.getFormattedStartDate() +
                "\nБитва началась!\n");
        while (redSquad.hasAliveUnits() && blueSquad.hasAliveUnits()) {
            output.addAll(makeAttack(redSquad, blueSquad, dateHelper));
            if (blueSquad.hasAliveUnits())
                output.addAll(makeAttack(blueSquad, redSquad, dateHelper));
        }
        if (redSquad.hasAliveUnits())
            output.add("\nПобедил " + redSquad.toString() + " отряд.");
        else output.add("\nПобедил " + blueSquad.toString() + " отряд.");
        output.add("\n" + "Битва продолжалась " +
                dateHelper.getFormattedDiff());
        return output;
    }

    private static ArrayList<String> makeAttack(Squad squad1, Squad squad2, DateHelper dateHelper) {
        ArrayList<String> attackLog = new ArrayList<>();
        attackLog.add("\n" + dateHelper.getDate());
        Unit unit1 = squad1.getRandomUnit();
        Unit unit2 = squad2.getRandomUnit();
        attackLog.add("\n" + unit1 + unit1.getUnitVitalityCard() + " атакует " + unit2 + unit2.getUnitVitalityCard());
        int[] attackData = unit1.attack();
        int vitality = unit2.getCurrentVitality();
        int damage = unit2.takeDamage(unit1, attackData[0]);
        attackLog.add("\n" + unit1 + " наносит " + attackData[0] + "(-" + (attackData[0] - damage) + ")" +
                reformString(attackData[0]) + " урона.");
        if (attackData[1] == 1)
            attackLog.add(" Критический удар!");
        if (unit2.isAlive())
            attackLog.add("\n" + unit2 + " теряет " + damage + reformString(damage) + " здоровья.\n");
        else attackLog.add("\n" + unit2 + " теряет " + vitality + reformString(vitality) + " здоровья.\n");
        if (!unit2.isAlive()) attackLog.add("Боец убит.\n");
        if (squad2.hasAliveUnits()) dateHelper.skipTime();
        return attackLog;
    }

    private static String reformString(int input) {
        if (input % 100 > 20 || input % 100 < 10) {
            switch (input % 10) {
                case 1: return " единицу";
                case 2:
                case 3:
                case 4: return " единицы";
                default: return " единиц";
            }
        } else return " единиц";
    }

    public static boolean getConditionOverHere(String firstSquadName, String secondSquadName) {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я0-9]+");
        return (!pattern.matcher(firstSquadName).matches() ||
                !pattern.matcher(secondSquadName).matches());
    }
}
