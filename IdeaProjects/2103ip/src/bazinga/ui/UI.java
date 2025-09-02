package bazinga.ui;

import java.util.Scanner;

public class UI {
    private Scanner scanner = new Scanner(System.in);

    public UI() {
        scanner = new Scanner(System.in);
    }

    public void Welcome() {
        System.out.println("Hello from BazingaBot!\nHow may I assist you today?\n!");
    }

    public String readLine() {
        System.out.print("> ");
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showGoodbye() {
        System.out.println("Live long and prosper, Bye Bye!");
        scanner.close();
    }

    public void showLine() {
        System.out.println("-------------------------------------------------");
    }
}
