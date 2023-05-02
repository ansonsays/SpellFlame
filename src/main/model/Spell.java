package model;

import org.json.JSONObject;
import persistence.Writable;

/*
 * Represents a spell that can be used by a character during a fight.
 */
public class Spell implements Writable {
    private String name;
    private int level;
    private int damage;
    private String type;

    // Constructs a Spell
    // EFFECTS: creates a spell that contains a name, a level, a damage value, and an elemental type
    public Spell(String name, int level, String type) {
        this.name = name;
        this.level = level;
        this.damage = level + 4;
        this.type = type;
    }

    // Level up the spell
    // MODIFIES: this
    // EFFECTS: increases the level and damage of the spell by 1
    public void levelUp() {
        this.level++;
        this.damage++;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Getter
    public int getLevel() {
        return level;
    }

    // Getter
    public int getDamage() {
        return damage;
    }

    // Getter
    public String getType() {
        return type;
    }

    // Creates a string containing the spell's information
    // EFFECTS: returns information on the spell as a string
    public String toString() {
        String str;
        str = "\tSpell Name: " + getName() + ", Damage: " + getDamage() + ", Level: " + getLevel() + ", Type: "
                + getType();
        return str;
    }

    // Creates a JSON object containing the Spell's information
    // EFFECTS: returns  the Spell's information as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Level", level);
        json.put("Type", type);
        return json;
    }
}
