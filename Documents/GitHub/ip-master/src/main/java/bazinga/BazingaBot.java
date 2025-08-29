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

/**
 * BazingaBot is the main class for the task management application.
 * It serves as the entry point and orchestrates the interaction between
 * the user interface, storage system, and task management components.
 * The bot supports various task operations including adding, listing,
 * marking, unmarking, and deleting tasks.
 */
public class BazingaBot {
    private static final String FILE_PATH = "./src/main/resources/bazinga.txt";

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
     * Starts the main execution loop of the BazingaBot application.
     * Continuously reads user input, processes commands, and manages tasks
     * until the user issues the exit command.
     */
    public void run() {

        ui.showMessage("Hello from BazingaBot!\nHow may I assist you today?\n");

        while (true) {
            String input = ui.readLine();
            if (input.isEmpty()) continue;

            String[] parts = Parser.parse(input);
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "todo":
                        handleTodo(parts, taskList.getTasks(), storage);
                        break;
                    case "deadline":
                        handleDeadline(parts, taskList.getTasks(), storage);
                        break;
                    case "event":
                        handleEvent(parts, taskList.getTasks(), storage);
                        break;
                    case "list":
                        handleList(taskList.getTasks());
                        break;
                    case "mark":
                    case "unmark":
                        handleMarkUnmark(parts, taskList.getTasks(), command, storage);
                        break;
                    case "find":
                        handleFind(parts, taskList.getTasks());
                        break;
                    case "delete":
                        handleDelete(parts, taskList.getTasks(), storage);
                        break;
                    case "bye":
                        ui.showGoodbye();
                        return;
                    default:
                        throw new TaskException("I only speak English, Klingon or Yiddish! Tell me what task clearly!");
                }
            } catch (TaskException e) {
                ui.showMessage(e.getMessage());
            } catch (NumberFormatException e) {
                ui.showMessage("How I wish I could handle imaginary, complex numbers but no just R");
            } catch (ArrayIndexOutOfBoundsException e) {
                ui.showMessage("Oops! Looks like something is missing in your input and your head.");
            }
        }
    }

    /**
     * The main entry point of the BazingaBot application.
     * Creates a new BazingaBot instance and starts the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new BazingaBot(FILE_PATH).run();
    }

    /**
     * Handles the creation and addition of a new Todo task.
     * Validates input and saves the updated task list to storage.
     *
     * @param parts the parsed command parts from user input
     * @param tasks the current list of tasks
     * @param storage the storage component for persisting tasks
     * @throws TaskException if the description is empty or invalid
     */
    private static void handleTodo(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("I don't need sleep, I need answers. What task? Specify it please?");
        }
        String description = parts[1].trim();
        Todo newTodo = new Todo(description);
        tasks.add(newTodo);
        System.out.println("I have added to my eidetic memory: " + newTodo);
        System.out.println("There is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.");
        storage.save(tasks);
    }

    /**
     * Handles the creation and addition of a new Deadline task.
     * Validates input format and saves the updated task list to storage.
     *
     * @param parts the parsed command parts from user input
     * @param tasks the current list of tasks
     * @param storage the storage component for persisting tasks
     * @throws TaskException if the description or deadline format is invalid
     */
    private static void handleDeadline(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Deadline description cannot be empty!");
        }

        String[] deadlineParts = parts[1].split("/by", 2);
        if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
            throw new TaskException("Please specify a valid deadline using /by.");
        }

        String description = deadlineParts[0].trim();
        String by = deadlineParts[1].trim();

        Deadline newDeadline = new Deadline(description, by);
        tasks.add(newDeadline);
        System.out.println("I have added to my eidetic memory: " + newDeadline);
        System.out.println("There is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.");
        storage.save(tasks);
    }

    /**
     * Handles the creation and addition of a new Event task.
     * Validates input format and saves the updated task list to storage.
     *
     * @param parts the parsed command parts from user input
     * @param tasks the current list of tasks
     * @param storage the storage component for persisting tasks
     * @throws TaskException if the description or time format is invalid
     */
    private static void handleEvent(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Event description cannot be empty!");
        }

        String[] eventParts = parts[1].split("/from|/to", 3);
        if (eventParts.length < 3 || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
            throw new TaskException("Please specify a valid event with /from and /to.");
        }

        String description = eventParts[0].trim();
        String from = eventParts[1].trim();
        String to = eventParts[2].trim();

        Event newEvent = new Event(description, from, to);
        tasks.add(newEvent);
        System.out.println("I have added to my eidetic memory: " + newEvent);
        System.out.println("There is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.");
        storage.save(tasks);
    }

    /**
     * Handles the listing of all current tasks.
     * Displays all tasks in the list or a message if no tasks exist.
     *
     * @param tasks the current list of tasks to display
     */
    private static void handleList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in memory. Relax, you have nothing to do!");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Handles marking or unmarking a task as done/not done.
     * Validates the task number and updates the task status.
     *
     * @param parts the parsed command parts from user input
     * @param tasks the current list of tasks
     * @param command the specific command ("mark" or "unmark")
     * @param storage the storage component for persisting tasks
     * @throws TaskException if the task number is invalid or out of range
     */
    private static void handleMarkUnmark(String[] parts, ArrayList<Task> tasks, String command, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Please specify the task number to " + command + "!");
        }
        int taskId = Integer.parseInt(parts[1].trim()) - 1;
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new TaskException("Invalid task number: " + (taskId + 1));
        }

        if (command.equals("mark")) {
            tasks.get(taskId).markAsDone();
            storage.save(tasks);
        } else {
            tasks.get(taskId).markAsNotDone();
            storage.save(tasks);
        }
    }

    /**
     * Handles the "find" command to search for tasks containing a specific keyword.
     * Searches the current list of tasks and prints all tasks whose descriptions
     * contain the provided keyword (case-insensitive).
     *
     * @param parts an array of command parts, where parts[1] should contain the keyword
     * @param tasks the current list of tasks to search within
     * @throws TaskException if no keyword is provided in the command
     */
    private static void handleFind(String[] parts, ArrayList<Task> tasks) throws TaskException {
        if(parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Please specify a keyword to find!!! I am not a Mind Reader");
        }
        String keyword = parts[1].trim().toLowerCase();
        ArrayList<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword)) {
                foundTasks.add(task);
            }
        }

        System.out.println("___________________________________________________________");
        if (foundTasks.isEmpty()) {
            System.out.println("No tasks found. Are you sure you want to find this?");
        } else {
            System.out.println("Here are the hits I got:");
            for (int i = 0; i < foundTasks.size(); i++) {
                System.out.println((i + 1) + ". " + foundTasks.get(i));
            }
        }
        System.out.println("___________________________________________________________");
    }

    /**
     * Handles the deletion of a task from the list.
     * Validates the task number and removes the specified task.
     *
     * @param parts the parsed command parts from user input
     * @param tasks the current list of tasks
     * @param storage the storage component for persisting tasks
     * @throws TaskException if the task number is invalid or out of range
     */
    private static void handleDelete(String[] parts, ArrayList<Task> tasks, Storage storage) throws TaskException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new TaskException("Please specify the task number to delete!");
        }
        int taskId = Integer.parseInt(parts[1].trim()) - 1;
        if (taskId < 0 || taskId >= tasks.size()) {
            throw new TaskException("Invalid task number: " + (taskId + 1));
        }
        Task removed = tasks.remove(taskId);
        System.out.println("Ok I have wiped this from my memory: " + removed);
        System.out.println("There are " + tasks.size() + " quests you have left fellow gladiator.");
        storage.save(tasks);
    }
}
