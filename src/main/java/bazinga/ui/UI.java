package bazinga.ui;

/**
 * UI class for BazingaBot - now simplified for GUI use
 */
public class UI {
    public UI() {
        // No scanner needed for GUI
    }

    public String getWelcome() {
        return "Hello from BazingaBot!\nHow may I assist you today?";
    }

    public String getGoodbye() {
        return "Live long and prosper, Bye Bye!";
    }
}
