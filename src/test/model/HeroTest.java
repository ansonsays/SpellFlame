package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {
    private Hero testHero;
    private Hero testEnemy;
    private Spell testSpell;

    @BeforeEach
    void runBefore() {
        testHero = new Hero("Test Hero", 1, 25, 25);
        testSpell = new Spell("Flamebolt", 1,  "Fire");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Hero", testHero.getName());
        assertEquals(1, testHero.getLevel());
        assertEquals(25, testHero.getCurrentHealth());
        assertEquals(25, testHero.getMaxHealth());
        assertEquals(0, testHero.getNumberOfSpells());
    }

    /*@Test
    void testSecondConstructor() {
        assertEquals("Test Enemy", testEnemy.getName());
        assertEquals(1, testEnemy.getLevel());
        assertEquals(25, testEnemy.getCurrentHealth());
        assertEquals(25, testEnemy.getMaxHealth());
        assertEquals(0, testEnemy.getNumberOfSpells());
    }*/

    @Test
    void testLearnSpell() {
        testHero.learnSpell(testSpell);
        assertEquals(1, testHero.getNumberOfSpells());
        assertEquals("Flamebolt", testHero.getSpellList().get(0).getName());
    }

    @Test
    void testTakeDamage() {
        testHero.takeDamage(5);
        assertEquals(20, testHero.getCurrentHealth());
    }

    @Test
    void testHeal() {
        testHero.takeDamage(10);
        testHero.heal(5);
        assertEquals(20, testHero.getCurrentHealth());
        testHero.heal(10);
        assertEquals(25, testHero.getCurrentHealth());
    }

    @Test
    void testSetCurrentHealth() {
        testHero.setCurrentHealth(10);
        assertEquals(10, testHero.getCurrentHealth());
    }

    @Test
    void testLevelUp() {
        testHero.levelUp();
        assertEquals(2, testHero.getLevel());
    }

    @Test
    void testGetNumberOfSpells() {
        assertEquals(0, testHero.getNumberOfSpells());
    }

    @Test
    void testShowSpells() {
        testHero.learnSpell(testSpell);
        assertEquals("Your spells are: \n\t1) \tSpell Name: Flamebolt, Damage: 5, Level: 1, Type: Fire", testHero.showSpells());
    }

    @Test
    void testSelectSpell() {
        testHero.learnSpell(testSpell);
        assertEquals("\tSpell Name: Flamebolt, Damage: 5, Level: 1, Type: Fire", testHero.selectSpell(1).toString());
    }

    @Test
    void testToString() {
        assertEquals("Test Hero, level 1", testHero.toString());
    }

}