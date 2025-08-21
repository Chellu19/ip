import java.util.Scanner;
import Task.java;

public class BazingaBot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello from BazingaBot! \n");
        System.out.println("How may I assist you today? \n");

        String [] arr = new String[100];
        int counter = 0;

        while (true) {
            System.out.print("> ");  // Prompt
            String input = scanner.nextLine();

            if(!input.equalsIgnoreCase("list") && !input.equalsIgnoreCase("bye")) {
                if (counter < arr.length) {
                    arr[counter] = input;
                    counter++;
                }
                System.out.println("I have added to my eidetic memory: " + input);
            }

            if (input.equalsIgnoreCase("list")) {
                for(int i = 0; i < counter; i++) {
                    System.out.println((i + 1) + ". " + arr[i]);
                }
            }

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Live long and prosper, Bye Bye!");
                break;
            }
        }
        scanner.close();
    }
}