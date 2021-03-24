package sample.BackEnd.Squads.Units.Classes;

import sample.BackEnd.Squads.Units.Race;
import sample.BackEnd.Squads.Units.Unit;

public class Mage implements Unit, Cloneable {
    private final static double strengthModifier = 0.7;
    private final static double dexterityModifier = 0.9;
    private final static double intelligenceModifier = 1.4;
    private final int strength;
    private final int dexterity;
    private final int intelligence;
    private int currentVitality;
    private final int defense;
    private final int resistance;
    private String squadName;
    private final String className;
    private final Race race;
    private final int vitality;

    public Mage(Race race) {
        this.race = race;
        this.strength = (int) (race.strength * strengthModifier);
        this.dexterity = (int) (race.dexterity * dexterityModifier);
        this.intelligence = (int) (race.intelligence * intelligenceModifier);
        this.vitality = (int) (race.strength * strengthModifier * 10);
        this.currentVitality = this.vitality;
        this.defense = (int) (race.dexterity * dexterityModifier * 2);
        this.resistance = (int) (race.intelligence * intelligenceModifier * 2.5);
        switch (race) {
            case WOOD_ELF: {this.className = "Друид"; break;}
            case HIGH_ELF: {this.className = "Волшебник"; break;}
            case DWARF: {this.className = "Рунный жрец"; break;}
            case ORC: {this.className = "Шаман племени"; break;}
            default: {this.className = "Маг"; break;}
        }
    }

    @Override
    public String toString() {
        return this.squadName + " " + this.className;
    }

    @Override
    public Mage clone() throws CloneNotSupportedException {
        return (Mage) super.clone();
    }

    public int getStrength() {
        return this.strength;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getCurrentVitality() {
        return this.currentVitality;
    }

    public int getVitality() {
        return this.vitality;
    }

    public String getUnitVitalityCard() {
        return " (" + this.getCurrentVitality() + "/" + this.getVitality() + ")";
    }

    public int[] attack() {
        int[] output = new int[2];
        if ((Math.random() * 100) < (5 * this.race.intelligence * intelligenceModifier / 10)) {
            output[0] = ((int) ((this.race.intelligence * intelligenceModifier * 10) + 0.01));
            output[1] = 1;
        } else output[0] = ((int) ((this.race.intelligence * intelligenceModifier * 5) + 0.01));
        return output;
    }

    public int takeDamage(Unit unit, int damage) {
        if (unit instanceof Mage) {
            if (damage > this.resistance) {
                this.currentVitality = this.currentVitality - (damage - this.resistance);
                return damage - this.resistance;
            } else return 0;
        } else {
            if (damage > this.defense) {
                this.currentVitality = this.currentVitality - (damage - this.defense);
                return damage - this.defense;
            } else return 0;
        }
    }

    public boolean isAlive() {
        return this.currentVitality > 0;
    }

    public void setSquadName(String name) {
        this.squadName = name;
    }

    public int getDefense(){
        return this.defense;
    }

    public int getResistance(){
        return this.resistance;
    }

    public String getClassName(){
        return this.className;
    }

    public int getCriticalChance(){
        return (int)(5 * this.race.intelligence * intelligenceModifier / 10);
    }

    public void restoreUnit() {
        this.currentVitality = this.vitality;
    }
}
