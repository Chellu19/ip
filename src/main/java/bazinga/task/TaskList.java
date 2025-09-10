package bazinga.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks and provides operations to manage them.
 * This class serves as a wrapper around an ArrayList of Task objects,
 * offering methods to add, retrieve, remove, and query tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList initialized with the given list of tasks.
     *
     * @param tasks the initial list of tasks to populate the TaskList
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
        sortByDeadline();
    }

    /**
     * Adds a task to the end of the task list.
     *
     * @param task the task to be added to the list
     */
    public void addTask(Task task) {
        tasks.add(task);
        sortByDeadline();
    }

    private void sortByDeadline() {
        tasks.sort((t1, t2) -> {
            if(t1.getDeadline() == null && t2.getDeadline() == null) {
                return 0;
            } else if (t1.getDeadline() == null) {
                return 1;
            } else if(t2.getDeadline() == null) {
                return -1;
            } else {
                return t1.getDeadline().compareTo(t2.getDeadline());
            }
        });
    }

    /**
     * Retrieves the task at the specified position in the list.
     *
     * @param i the index of the task to retrieve
     * @return the task at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (index < 0 || index >= size())
     */
    public Task getTask(int i) {
        return tasks.get(i);
    }

    /**
     * Removes and returns the task at the specified position in the list.
     *
     * @param i the index of the task to be removed
     * @return the task that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         (index < 0 || index >= size())
     */
    public Task removeTask(int i) {
        return tasks.remove(i);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the number of tasks in this list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     *
     * @return the ArrayList containing all tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the list contains no tasks, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
