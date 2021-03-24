package sample.Service;

import javafx.scene.control.ChoiceBox;
import sample.BackEnd.DateHelper;
import sample.BackEnd.Squads.Squad;
import sample.BackEnd.Squads.Units.Classes.Archer;
import sample.BackEnd.Squads.Units.Classes.Mage;
import sample.BackEnd.Squads.Units.Classes.Warrior;
import sample.BackEnd.Squads.Units.Race;
import sample.BackEnd.Squads.Units.Unit;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Battle {
    public static Squad redSquad;
    public static Squad blueSquad;

    public static ArrayList<String> provideBattle(/*String firstSquad, String secondSquad*/) {
        DateHelper dateHelper = new DateHelper();
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
                case 1:
                    return " единицу";
                case 2:
                case 3:
                case 4:
                    return " единицы";
                default:
                    return " единиц";
            }
        } else return " единиц";
    }

    public static boolean getConditionOverHere(String name) {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я0-9]+");
        return (!pattern.matcher(name).matches());
    }

    public static String addToRedSquad(ChoiceBox raceChoiceBox, ChoiceBox classChoiceBox, String name) {
        if (raceChoiceBox.getValue() == null) return "Выберите расу!";
        if (classChoiceBox.getValue() == null) return "Выберите класс!";
        if (redSquad == null || !redSquad.toString().equals(name)) redSquad = new Squad(name);
        Unit unit = createUnit(raceChoiceBox, classChoiceBox);
        redSquad.addUnit(unit);
        return unit.getClassName() + " добавлен в первый отряд.";
    }

    public static String  addToBlueSquad(ChoiceBox raceChoiceBox, ChoiceBox classChoiceBox, String name) {
        if (raceChoiceBox.getValue() == null) return "Выберите расу!";
        if (classChoiceBox.getValue() == null) return "Выберите класс!";
        if (blueSquad == null || !blueSquad.toString().equals(name)) blueSquad = new Squad(name);
        Unit unit = createUnit(raceChoiceBox, classChoiceBox);
        blueSquad.addUnit(unit);
        return unit.getClassName() + " добавлен во второй отряд.";
    }

    public static Unit createUnit(ChoiceBox raceChoiceBox, ChoiceBox classChoiceBox) {
        Race ourRace = (Race) raceChoiceBox.getValue();
        Unit unit = new Warrior(ourRace);
        if (classChoiceBox.getValue().equals("Лучник")) unit = new Archer(ourRace);
        if (classChoiceBox.getValue().equals("Маг")) unit = new Mage(ourRace);
        return unit;
    }

    public static ArrayList<String> provideUnitCard(ChoiceBox raceChoiceBox, ChoiceBox classChoiceBox) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Раса: " + raceChoiceBox.getValue());
        list.add("\nКласс: " + classChoiceBox.getValue());
        Unit unit = createUnit(raceChoiceBox, classChoiceBox);
        list.add("\nКлассовое имя: " + unit.getClassName());
        list.add("\nСила: " + unit.getStrength());
        list.add("\nЛовкость: " + unit.getDexterity());
        list.add("\nИнтеллект: " + unit.getIntelligence());
        list.add("\nЗдоровье: " + unit.getVitality());
        list.add("\nУрон: " + unit.attack()[0]);
        list.add("\nШанс критического удара: " + unit.getCriticalChance() + "%");
        list.add("\nЗащита: " + unit.getDefense());
        list.add("\nСопротивление: " + unit.getResistance());
        return list;
    }

    public static Squad getRedSquad() {
        return redSquad;
    }

    public static Squad getBlueSquad() {
        return blueSquad;
    }

    public static void reviveTheFallen() {
        for (Unit unit : redSquad.getUnits())
            unit.restoreUnit();
        for (Unit unit : blueSquad.getUnits())
            unit.restoreUnit();
    }
}
