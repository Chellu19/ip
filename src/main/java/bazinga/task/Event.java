package bazinga.task;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a event task with a specific start date & time and end date & time.
 * <p>
 * Extends the Task class to include from and to timeline functionality.
 * <p>
 * The from and to date is stored as a LocalDateTime object and can be parsed
 * <p>
 * from and formatted to specific string representations.
 *
 * @author Chellappan
 * @version 1.0
 * @see Task
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");

    /**
     * Constructs a new Event task with the specified description and due date string.
     * <p>
     * The due date string should be in the format "yyyy-MM-dd".
     *
     * @param description the description of the event task
     * @param fromStr     the from datetime string in "yyyy-MM-dd HH:mm" format
     * @param toStr       the to datetime string in "yyyy-MM-dd HH:mm" format
     * @throws DateTimeParseException if the parameters cannot be parsed as a valid date
     */

    public Event(String description, String fromStr, String toStr) {
        super(description, Task.TaskType.EVENT);
        this.from = LocalDateTime.parse(fromStr, INPUT_FORMAT);
        this.to = LocalDateTime.parse(toStr, INPUT_FORMAT);
    }

    /**
     * Constructs a new Event task with the specified description and due date string.
     * <p>
     * The due date string should be in the format "yyyy-MM-dd".
     *
     * @param description the description of the event task
     * @param isDone      the completion status of the task (true if completed, false otherwise)
     * @param fromStr     the from datetime string in "yyyy-MM-dd HH:mm" format
     * @param toStr       the to datetime string in "yyyy-MM-dd HH:mm" format
     * @throws DateTimeParseException if the parameters cannot be parsed as a valid date
     */


    public Event(String description, boolean isDone, String fromStr, String toStr) {
        super(description, Task.TaskType.EVENT, isDone);
        this.from = LocalDateTime.parse(fromStr, INPUT_FORMAT);
        this.to = LocalDateTime.parse(toStr, INPUT_FORMAT);
    }

    public LocalDateTime getDeadline() {
        return this.to;
    }
    /**
     * Returns the string representation of the Event task.
     * <p>
     * The format includes the task type indicator [E], completion status,
     * <p>
     * description, and formatted due date.
     *
     * @return a formatted string representation of the deadline task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Converts the Deadline task to a standardized string format suitable for storage.
     * <p>
     * The format is: "E | [status] | [description] | [from date in input format] | [to date in input format]"
     * <p>
     * where status is 1 for completed tasks and 0 for incomplete tasks.
     *
     * @return a string representation suitable for persistent storage
     */
    @Override
    public String toSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }
}

