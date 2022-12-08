package model;

import java.time.LocalDateTime;

public abstract class Job {
    private static Integer id = 0;
    public Priority priority = new Priority(Priority.LOW);
    private String subject;
    private LocalDateTime createDateTime = LocalDateTime.now();
    private LocalDateTime deadline = LocalDateTime.now();
    private String author;

    public Job() {
        id++;
        priority.setPriorityLow();
    }

    public Integer getId() { return id;}
    public Priority getPriority() { return this.priority;}
    public String getSubject() { return this.subject;}
    public LocalDateTime getCreationDT() { return this.createDateTime;}
    public LocalDateTime getDeadlineDT() { return this.deadline;}
    public String getAuthor() { return this.author;}

    public void setPriority(int priority) { this.priority.setPriority(priority);}
    public void setPriority(Priority priority) { this.priority = priority;}
    public void setSubject(String subject) { this.subject = subject;}
    public void setCreationDT(LocalDateTime createDateTime) { this.createDateTime = createDateTime;}
    public void setDeadlineDT(LocalDateTime deadline) { this.deadline = deadline;}
    public void setAuthor(String author) { this.author = author;}
}