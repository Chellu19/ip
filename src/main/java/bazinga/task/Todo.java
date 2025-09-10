package bazinga.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**

 Represents a Todo task.

 Extends the Task class.

 @author Chellappan

 @version 1.0

 @see Task
 */
public class Todo extends Task {
    /**
     Constructs a new Todo task with the specified description

     @param description the description of the deadline task
     */
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    /**
     Constructs a new Todo task with the specified description

     @param isDone the completion status of the task (true if completed, false otherwise)

     @param description the description of the deadline task
     */

    public Todo(String description, boolean isDone) {
        super(description, TaskType.TODO, isDone);
    }

    @Override
    public LocalDateTime getDeadline() {
        return null; // no deadline
    }

    /**

     Returns the string representation of the Todo task.

     The format includes the task type indicator [T], completion status,

     description.

     @return a formatted string representation of the deadline task
     */

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
    /**

     Converts the Todo task to a standardized string format suitable for storage.

     The format is: "T | [status] | [description] "

     where status is 1 for completed tasks and 0 for incomplete tasks.

     @return a string representation suitable for persistent storage
     */
    @Override
    public String toSaveFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
