package sample.BackEnd.Squads.Units;

public enum Race {
    HUMAN("Человек", 10, 10, 10),
    WOOD_ELF("Лесной эльф", 8, 13, 9),
    HIGH_ELF("Высший эльф", 8, 8, 14),
    DWARF("Гном", 12, 9, 9),
    ORC("Орк", 14, 9, 7);

    public final String name;
    public final int strength;
    public final int dexterity;
    public final int intelligence;

    Race(String name, int strength, int dexterity, int intelligence) {
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
