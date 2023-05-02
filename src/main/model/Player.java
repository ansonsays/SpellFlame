package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

/*
 * Represents a playable character
 */
public class Player extends Hero implements Writable {
    private int enemiesKilled;
    private int stage;
    private boolean spellSwitch;

    // Constructs a Player
    // EFFECTS: creates a Player that has a name, a level, a hitpoint pool, various game state variables,
    //          and a list of spells
    public Player(String name, int level, int hp, int maxHp, int killCount, int stage, boolean spellSwitch) {
        super(name, level, hp, maxHp);
        this.enemiesKilled = killCount;
        this.stage = stage;
        this.spellSwitch = spellSwitch;
    }

    @Override
    // Player levels up
    // MODIFIES: this
    // EFFECTS: increases the level of the Player by 1, and the maxHealth of the Hero by 3
    public void levelUp() {
        this.level++;
        this.currentHealth += 3;
        this.maxHealth += 3;
    }

    // Getter
    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    // Getter
    public int getStage() {
        return stage;
    }

    // Setter
    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets the Hero's current kill count
    public void setEnemiesKilled(int amount) {
        this.enemiesKilled = amount;
    }

    // Setter
    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets the Hero's current stage number
    public void setStage(int stage) {
        this.stage = stage;
    }

    // Setter
    // REQUIRES: a boolean
    // MODIFIES: this
    // EFFECTS: sets the Hero's spellSwitch
    public void setSpellSwitch(boolean bool) {
        this.spellSwitch = bool;
    }

    // Getter
    public boolean getSpellSwitch() {
        return spellSwitch;
    }

    // Creates a JSON object containing information about the Hero
    // EFFECTS: returns the Hero's information as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Level", level);
        json.put("CurrentHp", currentHealth);
        json.put("MaxHp", maxHealth);
        json.put("EnemiesKilled", enemiesKilled);
        json.put("Stage", stage);
        json.put("SpellList", spellListToJson());
        json.put("SpellSwitch", spellSwitch);
        return json;
    }

    // Creates a JSON Array containing the Hero's spell list
    // EFFECTS: returns the Spells of this Hero as a JSON array
    public JSONArray spellListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Spell spell : spellList) {
            jsonArray.put(spell.toJson());
        }
        return jsonArray;
    }

}
