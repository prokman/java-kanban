package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id;
    private String name;
    private String description;
    private Status status;
    private LocalDateTime startTime;
    private Duration duration;

    public Task(String name, String description, Status statusOfTask) {
        this.name = name;
        this.description = description;
        this.status = statusOfTask;
        this.duration = Duration.ofMinutes(10);
        this.startTime = LocalDateTime.now();
    }

    public Task(String name, String description, Status statusOfTask,
                LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.status = statusOfTask;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, Status statusOfTask,
                LocalDateTime startTime, Duration duration, int id) {
        this.name = name;
        this.description = description;
        this.status = statusOfTask;
        this.duration = duration;
        this.startTime = startTime;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }

    public TypeOfTask getType() {
        return TypeOfTask.TASK;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }
}
