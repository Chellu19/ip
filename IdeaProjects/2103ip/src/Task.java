public class Task {
    public enum TaskType {
        TODO,
        DEADLINE,
        EVENT
    }

    protected String description;
    protected boolean isDone;
    protected TaskType taskType;

    public Task(String description, TaskType taskType) {
        this.description = description;
        this.taskType = taskType;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[X]" : "[ ]"); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
        System.out.println("Ok, done. Maybe that will help chip away at the mountain of procrastination you have");
        System.out.println(getStatusIcon() + " " + description);
    }

    public void markAsNotDone() {
        isDone = false;
        System.out.println("Not done! Man's inefficiencies are astounding");
        System.out.println(getStatusIcon() + " " + description);
    }

    @Override
    public String toString() {
        return  getStatusIcon() + " " + description;
    }
}