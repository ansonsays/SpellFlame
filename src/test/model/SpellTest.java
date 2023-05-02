package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpellTest {
    private Spell testSpell;

    @BeforeEach
    void runBefore() {
        testSpell = new Spell("Flamebolt", 1, "Fire");
    }

    @Test
    void testConstructor() {
        assertEquals("Flamebolt", testSpell.getName());
        assertEquals(1, testSpell.getLevel());
        assertEquals(5, testSpell.getDamage());
        assertEquals("Fire", testSpell.getType());
    }

    @Test
    void testLevelUp() {
        testSpell.levelUp();
        assertEquals(2, testSpell.getLevel());
        assertEquals(6, testSpell.getDamage());
    }

    @Test
    void testToString() {
        assertEquals("\tSpell Name: Flamebolt, Damage: 5, Level: 1, Type: Fire", testSpell.toString());
    }

    @Test
    void testToJson() {
        JSONObject json;
        json = testSpell.toJson();
        assertEquals("Flamebolt", json.get("Name"));
        assertEquals(1, json.get("Level"));
        assertEquals("Fire", json.get("Type"));
    }
}
