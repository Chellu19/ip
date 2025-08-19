import java.util.Scanner;

public class BazingaBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello from BazingaBot! \n");
        System.out.println("How may I assist you today? \n");

        while (true) {
            System.out.print("> ");  // Prompt
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Live long and prosper, Bye Bye!");
                break;
            }

            System.out.println("You said: " + input);
        }

        scanner.close();
    }
}