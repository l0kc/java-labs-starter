package edu.course.lab02;

public class ProjectTask {
    private final String id;
    private final String title;
    private TaskStatus status;
    private int estimatedHours;

    public ProjectTask(String id, String title, TaskStatus status, int estimatedHours) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Идентификатор не может быть пустым или null.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Название задачи не может быть пустым или null.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Статус задачи не может быть null.");
        }
        if (estimatedHours < 0) {
            throw new IllegalArgumentException("Оценка трудоемкости не может быть отрицательной.");
        }

        this.id = id;
        this.title = title;
        this.status = status;
        this.estimatedHours = estimatedHours;
    }
    public ProjectTask(String id, String title, TaskStatus status) {
        this(id, title, status, 0);
    }

    public ProjectTask(String id, String title) {
        this(id, title, TaskStatus.OPEN, 0);
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
            throw new IllegalArgumentException("Количество часов для увеличения должно быть положительным.");
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
