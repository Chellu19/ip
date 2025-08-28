public class Event extends Task{
    protected String from;
    protected String by;

    public Event(String description, String from, String by) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.by = by;
    }

    public Event(String description, boolean isDone, String from, String by) {
        super(description, TaskType.EVENT, isDone);
        this.from = from;
        this.by = by;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + by + ")";
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from + " | " + by;
    }
}

