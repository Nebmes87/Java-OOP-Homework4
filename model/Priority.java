package model;

public class Priority implements Comparable<Priority>{
    public final int ERROR = -1;
    public static final int LOW = 0;
    public static final int MIDDLE = 1;
    public static final int HIGH = 2;
    protected int priority;

    public Priority() {
        setPriorityLow();
    }

    public Priority(int priority) {
        setPriority(priority);
    }
    public void setPriority(int priority) {
        switch (priority) {
            case LOW -> { setPriorityLow(); }
            case MIDDLE -> { setPriorityMiddle(); }
            case HIGH -> { setPriorityHigh(); }
            default -> { this.priority = ERROR;}
        }
    }

    public void setPriorityLow() {
        this.priority = LOW;
    }

    public void setPriorityMiddle() {
        this.priority = MIDDLE;
    }

    public void setPriorityHigh() {
        this.priority = HIGH;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        switch (this.priority) {
            case LOW -> { return "LOW"; }
            case MIDDLE -> { return "MIDDLE"; }
            case HIGH -> { return "HIGH"; }
            default -> { return "ERROR";}
        }
    }

    @Override
    public int compareTo(Priority o) {
        return Integer.compare(this.priority, o.priority);
    }
}