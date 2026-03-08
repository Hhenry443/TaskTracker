package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

public class Task {
    private int id;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task(String d) throws IOException, ParseException {
        try {
            Object o = new JSONParser().parse(new FileReader("tasks.json"));

            JSONObject fullJSON = (JSONObject) o;
            JSONObject tasks = (JSONObject) fullJSON.get("Tasks");

            Set allIDs = tasks.keySet();
            int highestKey = 0;
            for (Object key : allIDs) {
                if (Integer.parseInt((String) key) > highestKey) {
                    highestKey = Integer.parseInt((String) key);
                }
            }

            highestKey += 1;

            id = highestKey;
            description = d;
            status = "todo";
            createdAt = LocalDateTime.now();
            updatedAt = LocalDateTime.now();

        } catch (IOException e) {
            System.err.println("I/O Failure");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Parse Exception");
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public JSONObject getJsonObject() {
        JSONObject tempTask = new JSONObject();
        tempTask.put("description", description);
        tempTask.put("status", status);
        tempTask.put("createdAt", createdAt.toString());
        tempTask.put("updatedAt", updatedAt.toString());

        return tempTask;
    }
}
