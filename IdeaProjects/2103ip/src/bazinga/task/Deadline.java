package bazinga.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate by;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    public Deadline(String description, String byStr) {
        super(description, Task.TaskType.DEADLINE);
        this.by = LocalDate.parse(byStr, INPUT_FORMAT);;
    }

    public Deadline(String description, boolean isDone, String byStr) {
        super(description, Task.TaskType.DEADLINE, isDone);
        this.by = LocalDate.parse(byStr, INPUT_FORMAT);;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(INPUT_FORMAT);
    }
}