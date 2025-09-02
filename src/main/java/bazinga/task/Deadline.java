package bazinga.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**

 Represents a deadline task with a specific due date.

 Extends the Task class to include deadline-specific functionality.

 The deadline date is stored as a LocalDate object and can be parsed

 from and formatted to specific string representations.

 @author Chellappan

 @version 1.0

 @see Task
 */
public class Deadline extends Task {

    protected LocalDate by;

    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**

     Constructs a new Deadline task with the specified description and due date string.

     The due date string should be in the format "yyyy-MM-dd".

     @param description the description of the deadline task

     @param byStr the due date string in "yyyy-MM-dd" format

     @throws DateTimeParseException if the byStr parameter cannot be parsed as a valid date
     */
    public Deadline(String description, String byStr) {
        super(description, Task.TaskType.DEADLINE);
        this.by = LocalDate.parse(byStr, INPUT_FORMAT);
    }

    /**

     Constructs a Deadline task with the specified description, completion status, and due date string.

     This constructor is typically used when loading tasks from storage.

     @param description the description of the deadline task

     @param isDone the completion status of the task (true if completed, false otherwise)

     @param byStr the due date string in "yyyy-MM-dd" format

     @throws DateTimeParseException if the byStr parameter cannot be parsed as a valid date
     */
    public Deadline(String description, boolean isDone, String byStr) {
        super(description, Task.TaskType.DEADLINE, isDone);
        this.by = LocalDate.parse(byStr, INPUT_FORMAT);
    }

    /**

     Returns the string representation of the Deadline task.

     The format includes the task type indicator [D], completion status,

     description, and formatted due date.

     @return a formatted string representation of the deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**

     Converts the Deadline task to a standardized string format suitable for storage.

     The format is: "D | [status] | [description] | [due date in input format]"

     where status is 1 for completed tasks and 0 for incomplete tasks.

     @return a string representation suitable for persistent storage
     */
    @Override
    public String toSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(INPUT_FORMAT);
    }
}

