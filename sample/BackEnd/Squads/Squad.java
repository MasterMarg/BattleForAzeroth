package sample.BackEnd.Squads;

import sample.BackEnd.Squads.Units.*;
import sample.BackEnd.Squads.Units.Classes.Archer;
import sample.BackEnd.Squads.Units.Classes.Mage;
import sample.BackEnd.Squads.Units.Classes.Warrior;
import java.util.ArrayList;

public class Squad implements Cloneable {
    private ArrayList<Unit> units;
    private String name;

    public Squad(String name) {
        this.name = name;
        this.units = new ArrayList<>();
    }

    @Override
    public Squad clone() throws CloneNotSupportedException {
        Squad squad = (Squad) super.clone();
        squad.name = "Другой " + squad.name;
        squad.units = new ArrayList<>();
        for (Unit unit : this.units) {
            if (unit instanceof Warrior) {
                Unit ourUnit = ((Warrior) unit).clone();
                ourUnit.setSquadName(squad.name);
                squad.units.add(ourUnit);
            }
            if (unit instanceof Archer) {
                Unit ourUnit = ((Archer) unit).clone();
                ourUnit.setSquadName(squad.name);
                squad.units.add(ourUnit);
            }
            if (unit instanceof Mage) {
                Unit ourUnit = ((Mage) unit).clone();
                ourUnit.setSquadName(squad.name);
                squad.units.add(ourUnit);
            }
        }
        return squad;
    }

    public Unit getRandomUnit() {
        int index;
        do index = (int) (Math.random() * this.units.size());
        while (!this.units.get(index).isAlive());
        return this.units.get(index);
    }

    public boolean hasAliveUnits() {
        boolean hasAliveUnits = false;
        for (Unit unit : this.units) {
            if (unit.isAlive()) hasAliveUnits = true;
        }
        return hasAliveUnits;
    }

    public void addUnit(Unit unit){
        unit.setSquadName(this.name);
        this.units.add(unit);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ArrayList<Unit> getUnits(){
        return this.units;
    }
}