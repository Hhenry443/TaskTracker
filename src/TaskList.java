package org.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class TaskList {
    String filepath;

    public TaskList(String fp) {
        filepath = fp;
    }

    public void addTask(Task t) {
        try {
            Object o = new JSONParser().parse(new FileReader("tasks.json"));

            JSONObject fullJSON = (JSONObject) o;
            JSONObject tasks = (JSONObject) fullJSON.get("Tasks");

            tasks.put(t.getId(), t.getJsonObject());

            JSONObject newJSON = new JSONObject();

            newJSON.put("Tasks", tasks);

            try (FileWriter myWriter = new FileWriter("tasks.json")) {
                myWriter.write(String.valueOf(newJSON));
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("I/O Failure");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Parse Exception");
            e.printStackTrace();
        }
    }
    public void deleteTask(int id) {
        try {
            Object o = new JSONParser().parse(new FileReader(filepath));

            JSONObject fullJSON = (JSONObject) o;
            JSONObject tasks = (JSONObject) fullJSON.get("Tasks");

            Set allIDs = tasks.keySet();
            JSONObject newJSON = new JSONObject();

            for (Object key : allIDs) {
                if (Integer.parseInt((String) key) != id) {
                    newJSON.put(key, tasks.get(key));
                }
            }

            JSONObject finalJSON = new JSONObject();

            finalJSON.put("Tasks", newJSON);

            try (FileWriter myWriter = new FileWriter("tasks.json")) {
                myWriter.write(String.valueOf(finalJSON));
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("I/O Failure");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Parse Exception");
            e.printStackTrace();
        }
    }

    public void updateTask(int id, String desc) {
        try {
            Object o = new JSONParser().parse(new FileReader(filepath));

            JSONObject fullJSON = (JSONObject) o;
            JSONObject tasks = (JSONObject) fullJSON.get("Tasks");

            Set allIDs = tasks.keySet();
            JSONObject newJSON = new JSONObject();

            for (Object key : allIDs) {
                if (Integer.parseInt((String) key) == id) {
                    JSONObject oldTask = (JSONObject) tasks.get(key);

                    JSONObject tempTask = new JSONObject();
                    tempTask.put("description", desc);
                    tempTask.put("status", oldTask.get("status"));
                    tempTask.put("createdAt", oldTask.get("createdAt"));
                    tempTask.put("updatedAt", LocalDateTime.now().toString());

                    newJSON.put(key, tempTask);
                } else {
                    newJSON.put(key, tasks.get(key));
                }
            }


            JSONObject finalJSON = new JSONObject();

            finalJSON.put("Tasks", newJSON);

            try (FileWriter myWriter = new FileWriter("tasks.json")) {
                myWriter.write(String.valueOf(finalJSON));
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("I/O Failure");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Parse Exception");
            e.printStackTrace();
        }
    }

    public void changeStatus(int id, String newStatus) {
        try {
            Object o = new JSONParser().parse(new FileReader(filepath));

            JSONObject fullJSON = (JSONObject) o;
            JSONObject tasks = (JSONObject) fullJSON.get("Tasks");

            Set allIDs = tasks.keySet();
            JSONObject newJSON = new JSONObject();

            for (Object key : allIDs) {
                if (Integer.parseInt((String) key) == id) {
                    JSONObject oldTask = (JSONObject) tasks.get(key);

                    JSONObject tempTask = new JSONObject();
                    tempTask.put("description", oldTask.get("description"));
                    tempTask.put("status", newStatus);
                    tempTask.put("createdAt", oldTask.get("createdAt"));
                    tempTask.put("updatedAt", LocalDateTime.now().toString());

                    newJSON.put(key, tempTask);
                } else {
                    newJSON.put(key, tasks.get(key));
                }
            }


            JSONObject finalJSON = new JSONObject();

            finalJSON.put("Tasks", newJSON);

            try (FileWriter myWriter = new FileWriter("tasks.json")) {
                myWriter.write(String.valueOf(finalJSON));
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("I/O Failure");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Parse Exception");
            e.printStackTrace();
        }
    }

    public void filterTasks(String filter) {
        try {
            Object o = new JSONParser().parse(new FileReader(filepath));

            JSONObject fullJSON = (JSONObject) o;
            JSONObject tasks = (JSONObject) fullJSON.get("Tasks");

            Set allIDs = tasks.keySet();
            JSONObject newJSON = new JSONObject();

            for (Object key : allIDs) {
                JSONObject oldTask = (JSONObject) tasks.get(key);
                
                if (Objects.equals(oldTask.get("status").toString(), filter)) {
                    outputDetails(key, oldTask);
                } else if (filter == "all") {
                    outputDetails(key, oldTask);
                }
            }

        } catch (IOException e) {
            System.err.println("I/O Failure");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Parse Exception");
            e.printStackTrace();
        }
    }

    private void outputDetails(Object key, JSONObject oldTask) {
        String id = key.toString();
        String description = oldTask.get("description").toString();
        String status = oldTask.get("status").toString();
        String createdAt = oldTask.get("createdAt").toString();
        String updatedAt = oldTask.get("updatedAt").toString();

        System.out.println(id + ": " + description + " (" + status + ") " + "Created at: " + createdAt + " Last updated: " + updatedAt);
    }
}
