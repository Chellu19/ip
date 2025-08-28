import java.util.ArrayList;
import java.util.Scanner;

public class BazingaBot {
    private static final String FILE_PATH = "./src/main/resources/bazinga.txt";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello from BazingaBot!\nHow may I assist you today?\n");

        Storage storage = new Storage(FILE_PATH);
        ArrayList<Task> tasks = storage.load();

        while (true) {
            System.out.print("> ");  // Prompt
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue; // ignore empty input

            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "todo":
                        handleTodo(parts, tasks, storage);
                        break;
                    case "deadline":
                        handleDeadline(parts, tasks, storage);
                        break;
                    case "event":
                        handleEvent(parts, tasks, storage);
                        break;
                    case "list":
                        handleList(tasks);
                        break;
                    case "mark":
                    case "unmark":
                        handleMarkUnmark(parts, tasks, command, storage);
                        break;
                    case "delete":
                        handleDelete(parts, tasks, storage);
                        break;
                    case "bye":
                        System.out.println("Live long and prosper, Bye Bye!");
                        scanner.close();
                        return;
                    default:
                        throw new TaskException("I only speak English, Klingon or Yiddish! Tell me what task clearly!");
                }
            } catch (TaskException e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("How I wish I could handle imaginary, complex numbers but no just R");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Oops! Looks like something is missing in your input and your head.");
            }
        }
    }

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

    private static void handleList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in memory. Relax, you have nothing to do!");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

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
