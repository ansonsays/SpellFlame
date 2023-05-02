package persistence;

import model.Player;
import model.Spell;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

/*
 * Citation: code modelled after JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class SaveGameWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Player testHero = new Player("Test Hero", 1, 25, 25, 0, 0, false);
            SaveGameWriter writer = new SaveGameWriter(".data/\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass (this should not happen
        }
    }

    // Test for a Hero who has not yet learned any Spells
    @Test
    void testWriterHeroWithNoSpells() {
        try {
            Player testHero = new Player("Test Hero", 1, 25, 25, 0, 0, false);
            SaveGameWriter writer = new SaveGameWriter("./data/testHeroWithNoSpells.json");
            writer.open();
            writer.write(testHero);
            writer.close();

            SaveGameReader reader = new SaveGameReader("./data/testHeroWithNoSpells.json");
            testHero = reader.read();
            assertEquals("Test Hero", testHero.getName());
            assertEquals(0, testHero.getNumberOfSpells());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // Test for a basic Hero at the start of the game
    @Test
    void testWriterBasicHero() {
        try {
            Player testHero = new Player("Test Hero", 1, 25, 25, 0, 0, false);
            Spell fireball = new Spell("Fireball", 1, "Fire");
            testHero.learnSpell(fireball);
            SaveGameWriter writer = new SaveGameWriter("./data/testBasicHero.json");
            writer.open();
            writer.write(testHero);
            writer.close();

            SaveGameReader reader = new SaveGameReader("./data/testBasicHero.json");
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
            fail("Exception should not have been thrown");
        }
    }

    // Test for a powerful Hero who has learned the maximum number of Spells and defeated many enemies
    void testWriterPowerfulHero() {
        try {
            Player testHero = new Player("Test Hero", 10, 45, 45, 15, 15, false);
            Spell fireball = new Spell("Fireball", 5, "Fire");
            Spell lightningBolt = new Spell("Lightning Bolt", 7, "Lightning");
            Spell windBlast = new Spell("Wind Blast", 9, "Wind");
            testHero.learnSpell(fireball);
            testHero.learnSpell(lightningBolt);
            testHero.learnSpell(windBlast);
            SaveGameWriter writer = new SaveGameWriter("./data/testPowerfulHero.json");
            writer.open();
            writer.write(testHero);
            writer.close();

            SaveGameReader reader = new SaveGameReader("./data/testPowerfulHero.json");
            testHero = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}
