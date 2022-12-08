package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorkJobStream implements Iterator<WorkJob> {
    private Integer index;
    protected List<WorkJob> list = new ArrayList<WorkJob>();
    public WorkJobStream(List<WorkJob> localList) {
        this.index = 0;
        this.list.addAll(localList);
    }
    public WorkJobStream() {
        this.index = 0;
    }

    public void addJob(WorkJob job) {
        list.add(job);
    }
    public void ResetIndex() { this.index = 0;}

    public int size() { return this.list.size(); }

    @Override
    public boolean hasNext() {
        return this.index < this.list.size();
    }

    @Override
    public WorkJob next() {
        return this.list.get(this.index++);
    }
    public void mySort() {
        List<WorkJob> list1 = this.list;
        list1.sort(new JobComparator());
    }
}