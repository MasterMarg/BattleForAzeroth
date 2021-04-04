package sample.Service;

import sample.BackEnd.DateHelper;
import sample.BackEnd.Squads.Squad;
import sample.BackEnd.Squads.Units.Classes.Archer;
import sample.BackEnd.Squads.Units.Classes.Mage;
import sample.BackEnd.Squads.Units.Classes.Warrior;
import sample.BackEnd.Squads.Units.Race;
import sample.BackEnd.Squads.Units.Unit;
import java.util.regex.Pattern;

public class Battle {
    public static Squad redSquad;
    public static Squad blueSquad;

    public static StringBuffer provideBattle() {
        DateHelper dateHelper = new DateHelper();
        StringBuffer output = new StringBuffer();
        output.append(dateHelper.getFormattedStartDate()).append("\nБитва началась!\n");
        while (redSquad.hasAliveUnits() && blueSquad.hasAliveUnits()) {
            output.append(makeAttack(redSquad, blueSquad, dateHelper));
            if (blueSquad.hasAliveUnits())
                output.append(makeAttack(blueSquad, redSquad, dateHelper));
        }
        if (redSquad.hasAliveUnits())
            output.append("\nПобедил ").append(redSquad.toString()).append(" отряд.");
        else output.append("\nПобедил ").append(blueSquad.toString()).append(" отряд.");
        output.append("\n" + "Битва продолжалась ").append(dateHelper.getFormattedDiff());
        return output;
    }

    private static StringBuffer makeAttack(Squad squad1, Squad squad2, DateHelper dateHelper) {
        StringBuffer attackLog = new StringBuffer();
        attackLog.append("\n").append(dateHelper.getDate());
        Unit unit1 = squad1.getRandomUnit();
        Unit unit2 = squad2.getRandomUnit();
        attackLog.append("\n").append(unit1).append(unit1.getUnitVitalityCard()).append(" атакует ").
                append(unit2).append(unit2.getUnitVitalityCard());
        int[] attackData = unit1.attack();
        int vitality = unit2.getCurrentVitality();
        int damage = unit2.takeDamage(unit1, attackData[0]);
        attackLog.append("\n").append(unit1).append(" наносит ").append(attackData[0]).append("(-").
                append(attackData[0] - damage).append(")").append(reformString(attackData[0])).
                append(" урона.");
        if (attackData[1] == 1)
            attackLog.append(" Критический удар!");
        if (unit2.isAlive())
            attackLog.append("\n").append(unit2).append(" теряет ").append(damage).
                    append(reformString(damage)).append(" здоровья.\n");
        else attackLog.append("\n").append(unit2).append(" теряет ").append(vitality).
                append(reformString(vitality)).append(" здоровья.\n");
        if (!unit2.isAlive()) attackLog.append("Боец убит.\n");
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

    public static String generateSquads(String firstSquad, String secondSquad) {
        redSquad = generateSquad(firstSquad);
        blueSquad = generateSquad(secondSquad);
        return "Отряды сгенерированы!";
    }

    private static Squad generateSquad(String name) {
        Squad squad = new Squad(name);
        int initialSize = (int) (Math.random() * 21) + 40;
        for (int index = 0; index < initialSize; index++) {
            switch ((int) (Math.random() * 3)) {
                case 0: {
                    squad.addUnit(new Warrior(Race.values()[(int) (Math.random() * Race.values().length)]));
                    break;
                }
                case 1: {
                    squad.addUnit(new Archer(Race.values()[(int) (Math.random() * Race.values().length)]));
                    break;
                }
                case 2: {
                    squad.addUnit(new Mage(Race.values()[(int) (Math.random() * Race.values().length)]));
                    break;
                }
            }
        }
        return squad;
    }

    public static String addToRedSquad(Race race, String classType, String name) {
        if (redSquad == null) redSquad = new Squad(name);
        boolean isNameChanged = !redSquad.toString().equals(name);
        if (isNameChanged) redSquad = new Squad(name);
        Unit unit = createUnit(race, classType);
        redSquad.addUnit(unit);
        StringBuilder output = new StringBuilder();
        if (isNameChanged) output.append("Название отряда было изменено, был создан новый отряд.\n");
        output.append(unit.getClassName()).append(" добавлен в первый отряд.");
        return output.toString();
    }

    public static String addToBlueSquad(Race race, String classType, String name) {
        if (blueSquad == null) blueSquad = new Squad(name);
        boolean isNameChanged = !blueSquad.toString().equals(name);
        if (isNameChanged) blueSquad = new Squad(name);
        Unit unit = createUnit(race, classType);
        blueSquad.addUnit(unit);
        StringBuilder output = new StringBuilder();
        if (isNameChanged) output.append("Название отряда было изменено, был создан новый отряд.\n");
        output.append(unit.getClassName()).append(" добавлен во второй отряд.");
        return output.toString();
    }

    public static Unit createUnit(Race race, String classType) {
        Unit unit = new Warrior(race);
        if (classType.equals("Лучник")) unit = new Archer(race);
        if (classType.equals("Маг")) unit = new Mage(race);
        return unit;
    }

    public static String provideUnitCard(Race race, String classType) {
        StringBuilder list = new StringBuilder();
        list.append("Раса: ").append(race);
        list.append("\nКласс: ").append(classType);
        Unit unit = createUnit(race, classType);
        list.append("\nКлассовое имя: ").append(unit.getClassName());
        list.append("\nСила: ").append(unit.getStrength());
        list.append("\nЛовкость: ").append(unit.getDexterity());
        list.append("\nИнтеллект: ").append(unit.getIntelligence());
        list.append("\nЗдоровье: ").append(unit.getVitality());
        list.append("\nУрон: ").append(unit.attack()[0]);
        list.append("\nШанс критического удара: ").append(unit.getCriticalChance()).append("%");
        list.append("\nЗащита: ").append(unit.getDefense());
        list.append("\nСопротивление: ").append(unit.getResistance());
        return list.toString();
    }

    public static Squad getRedSquad() {
        return redSquad;
    }

    public static Squad getBlueSquad() {
        return blueSquad;
    }

    public static void reviveTheFallen() {
        if (redSquad != null)
            for (Unit unit : redSquad.getUnits())
                unit.restoreUnit();
        if (blueSquad != null)
            for (Unit unit : blueSquad.getUnits())
                unit.restoreUnit();
    }
}
