package persistence;

import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

/*
 * Represents a writer that saves game progress by writing a JSON representation to file
 * Citation: code modelled after JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class SaveGameWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // REQUIRES: String
    // MODIFIES: this
    // EFFECTS: constructs writer to write to destination file
    public SaveGameWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the destination file
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // REQUIRES: Hero
    // MODIFIES: this
    // EFFECTS: writes a JSON representation of Hero to file
    public void write(Player player) {
        JSONObject json = player.toJson();
        JSONArray jsonArray = player.spellListToJson();
        saveToFile(json.toString(TAB));
        saveToFile(jsonArray.toString(TAB));
    }

    // REQUIRES: String
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }
}
