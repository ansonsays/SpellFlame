package model;

/*
 * Represents a non-playable character
 */
public class Enemy extends Hero {

    // Constructs an Enemy
    // EFFECTS: creates an Enemy that has a name, a level, a hitpoint pool, and a list of spells
    public Enemy(String name, int level, int hp) {
        super(name, level, hp, hp);
    }

    @Override
    // Enemy levels up
    // MODIFIES: this
    // EFFECTS: increases the level of the Enemy by 1, and the maxHealth of the Enemy by 1
    public void levelUp() {
        this.level++;
        this.currentHealth += 2;
        this.maxHealth += 2;
    }
}
