package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player testPlayer;
    private Spell testSpell;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Test Player", 1, 25, 25, 0, 0, false);
        testSpell = new Spell("Flamebolt", 1,  "Fire");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Player", testPlayer.getName());
        assertEquals(1, testPlayer.getLevel());
        assertEquals(25, testPlayer.getCurrentHealth());
        assertEquals(25, testPlayer.getMaxHealth());
        assertEquals(0, testPlayer.getEnemiesKilled());
        assertEquals(0, testPlayer.getStage());
        assertFalse(testPlayer.getSpellSwitch());
        assertEquals(0, testPlayer.getNumberOfSpells());
    }

    @Test
    void testLearnSpell() {
        testPlayer.learnSpell(testSpell);
        assertEquals(1, testPlayer.getNumberOfSpells());
        assertEquals("Flamebolt", testPlayer.getSpellList().get(0).getName());
    }

    @Test
    void testTakeDamage() {
        testPlayer.takeDamage(5);
        assertEquals(20, testPlayer.getCurrentHealth());
    }

    @Test
    void testHeal() {
        testPlayer.takeDamage(10);
        testPlayer.heal(5);
        assertEquals(20, testPlayer.getCurrentHealth());
        testPlayer.heal(10);
        assertEquals(25, testPlayer.getCurrentHealth());
    }

    @Test
    void testSetCurrentHealth() {
        testPlayer.setCurrentHealth(10);
        assertEquals(10, testPlayer.getCurrentHealth());
    }

    @Test
    void testSetEnemiesKilled() {
        testPlayer.setEnemiesKilled(3);
        assertEquals(3, testPlayer.getEnemiesKilled());
    }

    @Test
    void testSetStage() {
        testPlayer.setStage(5);
        assertEquals(5, testPlayer.getStage());
    }

    @Test
    void testSetSpellSwitch() {
        testPlayer.setSpellSwitch(true);
        assertTrue(testPlayer.getSpellSwitch());
    }

    @Test
    void testLevelUp() {
        testPlayer.levelUp();
        assertEquals(2, testPlayer.getLevel());
    }

    @Test
    void testGetNumberOfSpells() {
        assertEquals(0, testPlayer.getNumberOfSpells());
    }

    @Test
    void testShowSpells() {
        testPlayer.learnSpell(testSpell);
        assertEquals("Your spells are: \n\t1) \tSpell Name: Flamebolt, Damage: 5, Level: 1, Type: Fire", testPlayer.showSpells());
    }

    @Test
    void testSelectSpell() {
        testPlayer.learnSpell(testSpell);
        assertEquals("\tSpell Name: Flamebolt, Damage: 5, Level: 1, Type: Fire", testPlayer.selectSpell(1).toString());
    }

    @Test
    void testToString() {
        assertEquals("Test Player, level 1", testPlayer.toString());
    }

    @Test
    void testToJson() {
        JSONObject json;
        json = testPlayer.toJson();
        assertEquals("Test Player", json.get("Name"));
        assertEquals(1, json.get("Level"));
        assertEquals(25, json.get("CurrentHp"));
        assertEquals(25, json.get("MaxHp"));
        assertEquals(0, json.get("EnemiesKilled"));
        assertEquals(0, json.get("Stage"));
        assertEquals(false, json.get("SpellSwitch"));
    }

    @Test
    void testSpellListToJson() {
        JSONArray jsonArray;
        testPlayer.learnSpell(testSpell);
        jsonArray = testPlayer.spellListToJson();
        assertEquals(1, jsonArray.length());
    }
}
