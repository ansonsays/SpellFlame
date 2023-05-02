package model;

import java.util.ArrayList;

/*
 * Represents a playable or non-playable character
 */
public class Hero {
    protected String name;
    protected int level;
    protected int currentHealth;
    protected int maxHealth;
    protected ArrayList<Spell> spellList;

    // Constructs a Hero (used for enemies)
    // EFFECTS: EFFECTS: creates a Hero that has a name, a level, a hitpoint pool, and a list of spells
    public Hero(String name, int level, int hp, int maxHp) {
        this.name = name;
        this.level = level;
        this.currentHealth = hp;
        this.maxHealth = maxHp;
        this.spellList = new ArrayList<Spell>();
    }

    // Learns a spell
    // EFFECTS: adds a spell to the Hero's list of spells
    public void learnSpell(Spell s) {
        spellList.add(s);
    }

    // Hero takes damage from an attack
    // MODIFIES: this
    // EFFECTS: subtracts the damage value from the Hero's hp pool
    public void takeDamage(int damage) {
        this.currentHealth -= damage;
    }

    // Hero recovers and heals damage
    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: adds the amount healed to the Hero's hp pool
    public void heal(int amount) {
        this.currentHealth += amount;
        if (this.currentHealth > this.maxHealth) {
            this.currentHealth = this.maxHealth;
        }
    }

    // Getter
    public String getName() {
        return name;
    }

    // Getter
    public int getCurrentHealth() {
        return currentHealth;
    }

    // Setter
    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: sets the Hero's current health to the amount argument
    public void setCurrentHealth(int amount) {
        this.currentHealth = amount;
    }

    // Getter
    public int getMaxHealth() {
        return maxHealth;
    }

    // Hero levels up
    // MODIFIES: this
    // EFFECTS: increases the level of the Hero by 1, and the maxHealth of the Hero by 2
    public void levelUp() {
        this.level++;
        this.currentHealth++;
        this.maxHealth++;
    }

    // Getter
    public int getLevel() {
        return level;
    }

    // EFFECTS: returns the number of spells in the hero's spell list
    public int getNumberOfSpells() {
        return this.spellList.size();
    }

    // Method to display information about the Hero's spells
    // MODIFIES: this
    // EFFECTS: returns a string listing all the spells in the Hero's spell list
    public String showSpells() {
        String str;
        int count = 0;
        int increment = 1;

        str = "Your spells are: ";
        while (spellList.size() > count) {
            str += "\n" + "\t" + increment + ") " + this.spellList.get(count).toString();
            count++;
            increment++;
        }
        return str;
    }

    // Getter
    public ArrayList<Spell> getSpellList() {
        return spellList;
    }

    // REQUIRES: index > 0
    // EFFECTS: returns the Spell at the specified index
    public Spell selectSpell(int index) {
        return spellList.get(index - 1);
    }

    // Creates a string containing information about the Hero
    // EFFECTS: returns a string containing the name and level of the Hero
    public String toString() {
        String str;
        str = getName() + ", level " + getLevel();
        return str;
    }
}
