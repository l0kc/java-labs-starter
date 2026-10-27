package edu.course.lab02;

import java.lang.classfile.constantpool.NameAndTypeEntry;

public class ProjectTask {
    private final String id;
    private final String title;
    private TaskStatus status;
    private int estimatedHours;

    public ProjectTask(String id, String title, TaskStatus status, int estimatedHours) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Кто я?");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Опиши задачу");
        }
        if (status == null) {
            throw new IllegalArgumentException("Что со мной?");
        }
        if (estimatedHours < 0) {
            throw new IllegalArgumentException("Что за бизнес с....");
        }

        this.id = id;
        this.title = title;
        this.status = status;
        this.estimatedHours = estimatedHours;
    }

    public void changeStatus() {
        if (getStatus() == TaskStatus.OPEN){
            this.status = TaskStatus.IN_PROGRESS;
        }
        if (getStatus() == TaskStatus.IN_PROGRESS){
            this.status = TaskStatus.DONE;
        }
    }

    public boolean isCompleted() {
        return this.status == TaskStatus.DONE;
    }


    public void IncEstHours(int hours) {
        if (hours <= 0) {
            throw new IllegalArgumentException("Окак");
        }
        while (getStatus() != TaskStatus.DONE){
            this.estimatedHours += hours;}
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getEstHours() {
        return estimatedHours;
    }
}
