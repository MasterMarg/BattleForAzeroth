package sample.Service;

import sample.BackEnd.DateHelper;
import sample.BackEnd.Squads.Squad;
import sample.BackEnd.Squads.Units.Unit;

public class Battle {
    private final DateHelper dateHelper;
    private final Squad redSquad;
    private final Squad blueSquad;

    public Battle(String firstName, String secondName) {
        this.dateHelper = new DateHelper();
        this.redSquad = new Squad(firstName);
        this.blueSquad = new Squad(secondName);
    }

    public Object[] makeAttack(Squad squad1, Squad squad2){
        Unit unit1 = squad1.getRandomUnit();
        Unit unit2 = squad2.getRandomUnit();
        int[] attackData = unit1.attack();
        int vitality = unit2.getCurrentVitality();
        int damage = unit2.takeDamage(unit1, attackData[0]);
        return new Object[]{unit1, unit2, attackData[0], attackData[1], vitality, damage};
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
}
