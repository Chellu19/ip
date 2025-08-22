import java.util.ArrayList;
import java.util.Scanner;


public class BazingaBot {
    public static void main(String[] args) throws TaskException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello from BazingaBot! \n");
        System.out.println("How may I assist you today? \n");

        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            System.out.print("> ");  // Prompt
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];


            if(command.equalsIgnoreCase("deadline")) {
                String [] deadlineParts = parts[1].split("/by", 2);
                String description = deadlineParts[0].trim();
                if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
                    throw new TaskException("I don't need sleep, I need answers. What task? specify it please???");
                }
                String by  = deadlineParts[1].trim();
                Deadline newDeadline = new Deadline(description, by);
                tasks.add(newDeadline);
                System.out.println("I have added to my eidetic memory: " + input);
                System.out.println(" " + newDeadline);
                System.out.println("There is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.");
            }

            else if(command.equalsIgnoreCase("event")) {
                String [] eventParts = parts[1].split("/from|/to", 3);
                String description = eventParts[0].trim();
                if (eventParts.length < 2 || eventParts[1].trim().isEmpty()) {
                    throw new TaskException("I don't need sleep, I need answers. What task? specify it please???");
                }
                String from = eventParts[1].trim();

                String to = eventParts[2].trim();
                Event newEvent = new Event(description, from, to);
                tasks.add(newEvent);
                System.out.println("I have added to my eidetic memory: " + input);
                System.out.println(" " + newEvent);
                System.out.println("There is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.");
            }

            else if(command.equalsIgnoreCase("todo")) {
                String description = parts[1].trim();
                if (parts.length < 2 || parts[1].trim().isEmpty()) {
                    throw new TaskException("I don't need sleep, I need answers. What task? specify it please?");
                }
                Todo newTodo = new Todo(description);
                tasks.add(newTodo);
                System.out.println("I have added to my eidetic memory: " + input);
                System.out.println(" " + newTodo);
                System.out.println("There is now " + tasks.size() + " tasks to do. Go ahead procrastinate more.");
            }

            else if (command.equalsIgnoreCase("list")) {
                for(int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i).toString());
                }
            }

            else if(command.equalsIgnoreCase("mark") || command.equalsIgnoreCase("unmark") && parts.length == 2) {
                int taskId = Integer.parseInt(parts[1]) - 1;

                if(taskId < 0 || taskId >= tasks.size()) {
                    System.out.println("Invalid task id: " + parts[1]);
                    continue;
                }
                if(command.equalsIgnoreCase("mark")) {
                    tasks.get(taskId).markAsDone();
                } else {
                    tasks.get(taskId).markAsNotDone();
                }
            }

            else if (command.equalsIgnoreCase("bye")) {
                System.out.println("Live long and prosper, Bye Bye!");
                break;
            }

            else if (!command.equalsIgnoreCase("deadline") && !command.equalsIgnoreCase("event")
                    && !command.equalsIgnoreCase("todo") && !command.equalsIgnoreCase("mark") &&
                    !command.equalsIgnoreCase("unmark") && !command.equalsIgnoreCase("list")) {
                throw new TaskException("I only speak English, Klingon or Yiddish!, Tell me what task clearly!");
            }
        }
        scanner.close();
    }
}