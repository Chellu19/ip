import java.util.ArrayList;
import java.util.Scanner;


public class BazingaBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello from BazingaBot! \n");
        System.out.println("How may I assist you today? \n");

        ArrayList<Task> tasks = new ArrayList<>();

        while (true) {
            System.out.print("> ");  // Prompt
            String input = scanner.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];
            Task newTask = new Task(input);

            if(!command.equalsIgnoreCase("list") && !command.equalsIgnoreCase("bye") && !command.equalsIgnoreCase("mark") && !command.equalsIgnoreCase("unmark")) {
                tasks.add(newTask);
                System.out.println("I have added to my eidetic memory: " + input);
            }

            else if (command.equalsIgnoreCase("list")) {
                for(int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i).getStatusIcon() + " " + tasks.get(i).description);
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
        }
        scanner.close();
    }
}