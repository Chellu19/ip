package bazinga.task;

import java.time.LocalDateTime;

/**
 * Represents a generic task in the task management system.
 * This is an abstract base class for specific task types (Todo, Deadline, Event).
 * It provides common functionality such as description, completion status, and task type.
 */
public abstract class Task {
    /**
     * Enumeration of possible task types.
     */
    public enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    protected String description;
    protected boolean isDone;
    protected TaskType taskType;
    public abstract LocalDateTime getDeadline();

    /**
     * Constructs a new Task with the given description and type.
     * The task is initially not done.
     *
     * @param description the description of the task
     * @param taskType the type of the task (TODO, DEADLINE, or EVENT)
     */
    public Task(String description, TaskType taskType) {
        this.description = description;
        this.taskType = taskType;
        this.isDone = false;
    }

    /**
     * Constructs a Task with the given description, type, and completion status.
     *
     * @param description the description of the task
     * @param taskType the type of the task (TODO, DEADLINE, or EVENT)
     * @param isDone the completion status of the task (true if done, false otherwise)
     */
    public Task(String description, TaskType taskType, boolean isDone) {
        this.description = description;
        this.taskType = taskType;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon representing the completion status of the task.
     *
     * @return "[X]" if the task is done, "[ ]" otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }
    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of the task, including its status and description.
     *
     * @return a formatted string representing the task
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Converts the task to a format suitable for saving to persistent storage.
     * This is an abstract method that must be implemented by subclasses.
     *
     * @return a string representation of the task in save format
     */
    public abstract String toSaveFormat();
}