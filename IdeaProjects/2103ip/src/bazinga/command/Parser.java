package bazinga.command;

public class Parser {
    public static String[] parse(String input) {
        String[] parts = input.trim().split(" ", 2);
        if (parts.length == 1) {
            return new String[] {parts[0], ""};
        }
        return parts;
    }
}
