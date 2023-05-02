package persistence;

import org.json.JSONObject;

/*
 * Represents an interface for returning JSON objects
 * Citation: code modelled after JsonSerializationDemo
 *           URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
