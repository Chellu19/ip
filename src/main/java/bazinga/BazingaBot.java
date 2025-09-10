package bazinga;

import bazinga.task.Deadline;
import bazinga.task.Event;
import bazinga.task.Task;
import bazinga.command.Parser;
import bazinga.ui.UI;
import bazinga.storage.Storage;
import bazinga.task.TaskList;
import bazinga.task.TaskException;
import bazinga.task.Todo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * BazingaBot is the main class for the task management application.
 * It serves as the entry point and orchestrates the interaction between
 * the user interface, storage system, and task management components.
 * The bot supports various task operations including adding, listing,
 * marking, unmarking, and deleting tasks.
 */
public class BazingaBot {
    private static final String FILE_PATH = "./data/record.txt";

    private final UI ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Constructs a BazingaBot instance with the specified file path for data storage.
     * Initializes the user interface, storage system, and loads existing tasks.
     *
     * @param filePath the path to the file used for storing task data
     */
    public BazingaBot(String filePath) {
        this.ui = new UI();
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(storage.load());
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        if (input.isEmpty()) {
            return "Please enter a command!";
        }

        String[] parts = Parser.parse(input);
        String command = parts[0].toLowerCase();

        try {
            switch (command) {
            case "hi":
            case "hello":
                return "Hello from BazingaBot!\nHow may I assist you today?";

            case "todo":
                return handleTodo(parts, taskList.getTasks(), storage);

            case "deadline":
                return handleDeadline(parts, taskList.getTasks(), storage);

            case "event":
                return handleEvent(parts, taskList.getTasks(), storage);

            case "list":
                return handleList(taskList.getTasks());

            case "mark":
                return handleMark(parts, taskList.getTasks(), storage);

            case "unmark":
                return handleUnmark(parts, taskList.getTasks(), storage);

            case "find":
                return handleFind(parts, taskList.getTasks());

            case "delete":
                return handleDelete(parts, taskList.getTasks(), storage);

            case "bye":
                return handleBye();

            default:
                return "I only speak English, Klingon or Yiddish! Tell me what task clearly!";
            }
        } catch (TaskException e) {
            return e.getMessage();
        } catch (NumberFormatException e) {
            return "How I wish I could handle imaginary, complex numbers but no just R";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Oops! Looks like something is missing in your input and your head.";
        }
    }

    /**
     * Handles the creation and addition of a new Todo task.
     */
    private String handleTodo(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("I don't need sleep, I need answers. What task? Specify it please?");
        }
        String description = parts[1].trim();
        Todo newTodo = new Todo(description);
        tasks.add(newTodo);
        storage.save(tasks);

        assert storage.load().size() == tasks.size() : "Mismatch between memory and saved tasks";

        return "I have added to my eidetic memory: " + newTodo +
                "\nThere is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.";
    }

    /**
     * Handles the creation and addition of a new Deadline task.
     */
    private String handleDeadline(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Deadline description cannot be empty!");
        }

        String[] deadlineParts = parts[1].split("/by", 2);
        if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
            throw new TaskException("Please specify a valid deadline using /by.");
        }

        assert deadlineParts.length == 2 : "Deadline should have description and by";

        String description = deadlineParts[0].trim();
        String by = deadlineParts[1].trim();

        Deadline newDeadline = new Deadline(description, by);
        tasks.add(newDeadline);
        storage.save(tasks);

        assert storage.load().size() == tasks.size() : "Mismatch between memory and saved tasks";

        return "I have added to my eidetic memory: " + newDeadline +
                "\nThere is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.";
    }

    /**
     * Handles the creation and addition of a new Event task.
     */
    private String handleEvent(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Event description cannot be empty!");
        }

        String[] eventParts = parts[1].split("/from|/to", 3);
        if (eventParts.length < 3 || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
            throw new TaskException("Please specify a valid event with /from and /to.");
        }

        assert eventParts.length == 3 : "Event should have 3 parts, description, from and to";

        String description = eventParts[0].trim();
        String from = eventParts[1].trim();
        String to = eventParts[2].trim();

        Event newEvent = new Event(description, from, to);
        tasks.add(newEvent);
        storage.save(tasks);

        assert storage.load().size() == tasks.size() : "Mismatch between memory and saved tasks";

        return "I have added to my eidetic memory: " + newEvent +
                "\nThere is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.";
    }

    /**
     * Handles the listing of all current tasks.
     */
    private String handleList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            return "No tasks in memory. Relax, you have nothing to do!";
        }

        tasks.sort(Comparator.comparing(Task::getDeadline, Comparator.nullsLast(Comparator.naturalOrder())));

        StringBuilder sb = new StringBuilder("Your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Handles marking a task as done.
     */
    private String handleMark(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Please specify the task number to mark!");
        }
        int taskId = Integer.parseInt(parts[1].trim()) - 1;
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new TaskException("Invalid task number: " + (taskId + 1));
        }

        assert tasks != null : "Task list should not be null";
        assert taskId >= 0 && taskId < tasks.size() : "Task idx is out of bounds";

        tasks.get(taskId).markAsDone();
        storage.save(tasks);

        assert storage.load().size() == tasks.size() : "Mismatch between memory and saved tasks";

        return "Ok, done. Maybe that will help chip away at the mountain of procrastination you have\n" +
                tasks.get(taskId).getStatusIcon() + " " + tasks.get(taskId).getDescription();
    }

    /**
     * Handles unmarking a task as not done.
     */
    private String handleUnmark(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Please specify the task number to unmark!");
        }
        int taskId = Integer.parseInt(parts[1].trim()) - 1;
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new TaskException("Invalid task number: " + (taskId + 1));
        }

        assert tasks != null : "Task list should not be null";
        assert taskId >= 0 && taskId < tasks.size() : "Task idx is out of bounds";

        tasks.get(taskId).markAsNotDone();
        storage.save(tasks);

        assert storage.load().size() == tasks.size() : "Mismatch between memory and saved tasks";

        return "Not done! Man's inefficiencies are astounding\n" +
                tasks.get(taskId).getStatusIcon() + " " + tasks.get(taskId).getDescription();
    }

    /**
     * Handles the "find" command to search for tasks.
     */
    private String handleFind(String[] parts, ArrayList<Task> tasks) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Please specify a keyword to find!!! I am not a Mind Reader");
        }
        String keyword = parts[1].trim().toLowerCase();
        ArrayList<Task> foundTasks = tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword))
                .collect(Collectors.toCollection(ArrayList::new));

        if (foundTasks.isEmpty()) {
            return "No tasks found. Are you sure you want to find this?";
        } else {
            StringBuilder sb = new StringBuilder("Here are the hits I got:\n");
            for (int i = 0; i < foundTasks.size(); i++) {
                sb.append((i + 1)).append(". ").append(foundTasks.get(i)).append("\n");
            }
            return sb.toString();
        }
    }

    /**
     * Handles the deletion of a task.
     */
    private String handleDelete(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Please specify the task number to delete!");
        }
        int taskId = Integer.parseInt(parts[1].trim()) - 1;
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new TaskException("Invalid task number: " + (taskId + 1));
        }
        assert tasks != null : "Task list should not be null";
        assert taskId >= 0 && taskId < tasks.size() : "Task idx is out of bounds";

        Task removed = tasks.remove(taskId);
        storage.save(tasks);

        assert storage.load().size() == tasks.size() : "Mismatch between memory and saved tasks";

        return "Ok I have wiped this from my memory: " + removed +
                "\nThere are " + tasks.size() + " quests you have left fellow gladiator.";
    }

    /**
     * Handles the bye command.
     */
    private String handleBye() {
        return "Live long and prosper, Bye Bye!";
    }
}
