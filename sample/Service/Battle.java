package sample.Service;

import sample.BackEnd.DateHelper;
import sample.BackEnd.Squads.Squad;
import sample.BackEnd.Squads.Units.Unit;

import java.util.ArrayList;

public class Battle {
    private final DateHelper dateHelper;
    private final Squad redSquad;
    private final Squad blueSquad;

    public Battle(String firstName, String secondName) {
        this.dateHelper = new DateHelper();
        this.redSquad = new Squad(firstName);
        this.blueSquad = new Squad(secondName);
    }

    public ArrayList<String> makeAttack(Squad squad1, Squad squad2) {
        ArrayList<String> attackLog = new ArrayList<>();
        attackLog.add("\n" + this.dateHelper.getDate());
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
        if (squad2.hasAliveUnits()) this.dateHelper.skipTime();
        return attackLog;
    }

    public Squad getRedSquad() {
        return this.redSquad;
    }

    public Squad getBlueSquad() {
        return this.blueSquad;
    }

    public DateHelper getDateHelper() {
        return this.dateHelper;
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
