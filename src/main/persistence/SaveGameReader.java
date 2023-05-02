package persistence;

import model.Player;
import model.Spell;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

/*
 * Represents a reader that reads Hero from JSON file
 * Citation: code modelled after JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class SaveGameReader {
    private String source;

    // REQUIRES: String
    // MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public SaveGameReader(String source) {
        this.source = source;
    }

    // MODIFIES: this
    // EFFECTS: reads Hero from file and returns it
    // throws IO Exception if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHero(jsonObject);
    }

    // REQUIRES: String
    // MODIFIES: this
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // REQUIRES: JSONObject
    // MODIFIES: this, player
    // EFFECTS: parses Hero from JSON object and returns it (creates a Hero from the parsed data)
    private Player parseHero(JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        int level = jsonObject.getInt("Level");
        int hp = jsonObject.getInt("CurrentHp");
        int maxHp = jsonObject.getInt("MaxHp");
        int enemiesKilled = jsonObject.getInt("EnemiesKilled");
        int stage = jsonObject.getInt("Stage");
        boolean spellSwitch = jsonObject.getBoolean("SpellSwitch");
        Player player = new Player(name, level, hp, maxHp, enemiesKilled, stage, spellSwitch);
        addSpells(player, jsonObject);
        return player;
    }

    // REQUIRES: Hero, JSONObject
    // MODIFIES: player
    // EFFECTS: parses Spells from JSON object and adds them to Hero
    private void addSpells(Player player, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("SpellList");
        for (Object json : jsonArray) {
            JSONObject nextSpell = (JSONObject) json;
            addSpell(player, nextSpell);
        }
    }

    // MODIFIES: player
    // EFFECTS: parses Spell from JSON object and adds it to Hero
    private void addSpell(Player player, JSONObject jsonObject) {
        String name = jsonObject.getString("Name");
        int level = jsonObject.getInt("Level");
        String type = jsonObject.getString("Type");
        Spell spell = new Spell(name, level, type);
        player.learnSpell(spell);
    }
}
