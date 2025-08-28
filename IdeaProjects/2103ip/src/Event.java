import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    public Event(String description, String fromStr, String toStr) {
        super(description, TaskType.EVENT);
        this.from = LocalDateTime.parse(fromStr, INPUT_FORMAT);
        this.to = LocalDateTime.parse(toStr, INPUT_FORMAT);
    }

    public Event(String description, boolean isDone, String fromStr, String toStr) {
        super(description, TaskType.EVENT, isDone);
        this.from = LocalDateTime.parse(fromStr, INPUT_FORMAT);
        this.to = LocalDateTime.parse(toStr, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }
}

