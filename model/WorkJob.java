package model;

import java.time.LocalDateTime;

public class WorkJob extends Job implements Comparable<WorkJob>{
    int id;
    public WorkJob() {
        super();
        this.id = super.getId();
        super.setPriority(Priority.LOW);
        super.setAuthor("");
        super.setSubject("");
        super.setCreationDT(LocalDateTime.now());
        super.setDeadlineDT(LocalDateTime.now());
    }

    public WorkJob(
                   Priority priority,
                   String subject,
                   String author,
                   LocalDateTime createDT,
                   LocalDateTime deadline) {
        super();
        this.id = super.getId();
        setPriority(priority);
        setSubject(subject);
        setAuthor(author);
        setCreationDT(createDT);
        setDeadlineDT(deadline);
    }

    public Integer getId() { return this.id;}

    @Override
    public String toString() {
        return String.format("ID: %d, Priority: %s, Create: %s, Deadline: %s, Subject: %s, Author: %s"
                , getId()
                , super.getPriority()
                , super.getCreationDT().toString()
                , super.getDeadlineDT().toString()
                , super.getSubject()
                , super.getAuthor()
        );
    }

    @Override
    public int compareTo(WorkJob o) {
        Priority p = this.getPriority();
        return p.compareTo(o.priority);
    }
}