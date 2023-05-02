package persistence;

import model.Player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

/*
 * Citation: code modelled after JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class SaveGameReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        SaveGameReader reader = new SaveGameReader("./data/FileNotFound.json");
        try {
            Player testHero = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass (this should not happen)
        }
    }

    // Test for a Hero who has not yet learned any Spells
    @Test
    void testReaderHeroWithNoSpells() {
        SaveGameReader reader = new SaveGameReader("./data/testHeroWithNoSpells.json");
        try {
            Player testHero = reader.read();
            assertEquals("Test Hero", testHero.getName());
            assertEquals(0, testHero.getNumberOfSpells());
        } catch (IOException e) {
            fail("Couldn't read from file.");
        }
    }

    // Test for a basic Hero at the start of the game
    @Test
    void testReaderBasicHero() {
        SaveGameReader reader = new SaveGameReader("./data/testBasicHero.json");
        try {
            Player testHero = reader.read();
            assertEquals("Test Hero", testHero.getName());
            assertEquals(1, testHero.getNumberOfSpells());
            assertEquals(1, testHero.getLevel());
            assertEquals(25, testHero.getCurrentHealth());
            assertEquals(25, testHero.getMaxHealth());
            assertEquals(0, testHero.getEnemiesKilled());
            assertEquals(0, testHero.getStage());
            assertEquals(false, testHero.getSpellSwitch());
            checkSpell("Fireball", 1, 5, "Fire", testHero.selectSpell(1));
        } catch (IOException e) {
            fail("Couldn't read from file.");
        }
    }

    // Test for a powerful Hero who has learned the maximum number of Spells and defeated many enemies
    @Test
    void testReaderPowerfulHero() {
        SaveGameReader reader = new SaveGameReader("./data/testPowerfulHero.json");
        try {
            Player testHero = reader.read();
            assertEquals("Test Hero", testHero.getName());
            assertEquals(3, testHero.getNumberOfSpells());
            assertEquals(10, testHero.getLevel());
            assertEquals(45, testHero.getCurrentHealth());
            assertEquals(45, testHero.getMaxHealth());
            assertEquals(15, testHero.getEnemiesKilled());
            assertEquals(15, testHero.getStage());
            checkSpell("Fireball", 5, 9, "Fire", testHero.selectSpell(1));
            checkSpell("Lightning Bolt", 7, 11, "Lightning", testHero.selectSpell(2));
            checkSpell("Wind Blast", 9, 13, "Wind", testHero.selectSpell(3));
        } catch (IOException e) {
            fail("Couldn't read from file.");
        }
    }
}
