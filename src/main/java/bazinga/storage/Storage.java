package bazinga.storage;

import bazinga.task.Deadline;
import bazinga.task.Event;
import bazinga.task.Task;
import bazinga.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        try{
            if(!file.exists()){
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                return tasks;
            }

            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                try{
                    String line = sc.nextLine();
                    Task task = parseTask(line);
                    if(task != null){
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.out.println("Skipping line in save file");
                }
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks, starting with empty list");
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        File file = new File(filePath);
        try{
            if(file.getParentFile() != null){
                file.getParentFile().mkdirs();
            }
            FileWriter fw = new FileWriter(filePath);
            for(Task task : tasks){
                fw.write(task.toSaveFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks");
        }
    }

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String typeCode = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task.TaskType type;
        switch (typeCode) {
            case "T":
                type = Task.TaskType.TODO;
                return new Todo(description, isDone);
            case "D":
                type = Task.TaskType.DEADLINE;
                if (parts.length < 4) return null;
                return new Deadline(description, isDone, parts[3]);
            case "E":
                type = Task.TaskType.EVENT;
                if (parts.length < 5) return null;
                return new Event(description, isDone, parts[3], parts[4]);
            default:
                return null;
        }
    }
}
