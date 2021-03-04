package sample.BackEnd.Squads;

import sample.BackEnd.Squads.Units.*;
import sample.BackEnd.Squads.Units.Classes.Archer;
import sample.BackEnd.Squads.Units.Classes.Mage;
import sample.BackEnd.Squads.Units.Classes.Warrior;

public class Squad implements Cloneable {
    private Unit[] units;
    private String name;

    public Squad(String name) {
        this.name = name;
        this.units = new Unit[(int) (Math.random() * 21) + 40];
        for (int index = 0; index < this.units.length; index++) {
            switch ((int) (Math.random() * 3)) {
                case 0: {
                    this.units[index] = new Warrior(Race.values()[(int) (Math.random() * Race.values().length)]);
                    this.units[index].setSquadName(this.name);
                    break;
                }
                case 1: {
                    this.units[index] = new Archer(Race.values()[(int) (Math.random() * Race.values().length)]);
                    this.units[index].setSquadName(this.name);
                    break;
                }
                case 2: {
                    this.units[index] = new Mage(Race.values()[(int) (Math.random() * Race.values().length)]);
                    this.units[index].setSquadName(this.name);
                    break;
                }
            }
        }
    }

    @Override
    public Squad clone() throws CloneNotSupportedException {
        Squad squad = (Squad) super.clone();
        squad.name = "Другой " + squad.name;
        squad.units = new Unit[this.units.length];
        int index = 0;
        for (Unit unit : this.units) {
            if (unit instanceof Warrior) {
                squad.units[index] = ((Warrior) unit).clone();
                squad.units[index].setSquadName(squad.name);
                index = index + 1;
            }
            if (unit instanceof Archer) {
                squad.units[index] = (((Archer) unit).clone());
                squad.units[index].setSquadName(squad.name);
                index = index + 1;
            }
            if (unit instanceof Mage) {
                squad.units[index] = ((Mage) unit).clone();
                squad.units[index].setSquadName(squad.name);
                index = index + 1;
            }
        }
        return squad;
    }

    public Unit getRandomUnit() {
        int index;
        do index = (int) (Math.random() * this.units.length);
        while (!this.units[index].isAlive());
        return this.units[index];
    }

    public boolean hasAliveUnits() {
        boolean hasAliveUnits = false;
        for (Unit unit : this.units) {
            if (unit.isAlive()) hasAliveUnits = true;
        }
        return hasAliveUnits;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
