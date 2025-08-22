public class Task {
    public String description;
    public boolean isDone;

    public Task(String description) {
        this.description = description;
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

}