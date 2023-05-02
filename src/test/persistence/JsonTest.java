package persistence;

import model.Spell;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Citation: code modelled after JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonTest {
    protected void checkSpell(String name, int level, int damage, String type, Spell spell) {
        assertEquals(name, spell.getName());
        assertEquals(level, spell.getLevel());
        assertEquals(damage, spell.getDamage());
        assertEquals(type, spell.getType());
    }
}
