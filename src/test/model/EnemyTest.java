package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {
    private Enemy testEnemy;
    private Spell testSpell;

    @BeforeEach
    void runBefore() {
        testEnemy = new Enemy("Test Enemy", 1, 25);
        testSpell = new Spell("Flamebolt", 1,  "Fire");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Enemy", testEnemy.getName());
        assertEquals(1, testEnemy.getLevel());
        assertEquals(25, testEnemy.getCurrentHealth());
        assertEquals(25, testEnemy.getMaxHealth());
        assertEquals(0, testEnemy.getNumberOfSpells());
    }

    @Test
    void testLearnSpell() {
        testEnemy.learnSpell(testSpell);
        assertEquals(1, testEnemy.getNumberOfSpells());
        assertEquals("Flamebolt", testEnemy.getSpellList().get(0).getName());
    }

    @Test
    void testTakeDamage() {
        testEnemy.takeDamage(5);
        assertEquals(20, testEnemy.getCurrentHealth());
    }

    @Test
    void testHeal() {
        testEnemy.takeDamage(10);
        testEnemy.heal(5);
        assertEquals(20, testEnemy.getCurrentHealth());
        testEnemy.heal(10);
        assertEquals(25, testEnemy.getCurrentHealth());
    }

    @Test
    void testSetCurrentHealth() {
        testEnemy.setCurrentHealth(10);
        assertEquals(10, testEnemy.getCurrentHealth());
    }

    @Test
    void testLevelUp() {
        testEnemy.levelUp();
        assertEquals(2, testEnemy.getLevel());
    }

    @Test
    void testGetNumberOfSpells() {
        assertEquals(0, testEnemy.getNumberOfSpells());
    }

    @Test
    void testShowSpells() {
        testEnemy.learnSpell(testSpell);
        assertEquals("Your spells are: \n\t1) \tSpell Name: Flamebolt, Damage: 5, Level: 1, Type: Fire", testEnemy.showSpells());
    }

    @Test
    void testSelectSpell() {
        testEnemy.learnSpell(testSpell);
        assertEquals("\tSpell Name: Flamebolt, Damage: 5, Level: 1, Type: Fire", testEnemy.selectSpell(1).toString());
    }

    @Test
    void testToString() {
        assertEquals("Test Enemy, level 1", testEnemy.toString());
    }
}
