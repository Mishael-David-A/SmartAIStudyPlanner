import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Task {
    private int id;
    private String title;
    private String subject;
    private LocalDate deadline;
    private int difficulty;
    private double estimatedHours;
    private String notes;
    private boolean completed;

    public Task(
            int id,
            String title,
            String subject,
            LocalDate deadline,
            int difficulty,
            double estimatedHours,
            String notes) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.deadline = deadline;
        this.difficulty = difficulty;
        this.estimatedHours = estimatedHours;
        this.notes = notes;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setDifficulty(int difficulty) {
        if (difficulty >= 1 && difficulty <= 5) {
            this.difficulty = difficulty;
        }
    }

    public void setEstimatedHours(double estimatedHours) {
        if (estimatedHours > 0) {
            this.estimatedHours = estimatedHours;
        }
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void markCompleted() {
        completed = true;
    }

    public void markPending() {
        completed = false;
    }

    public long getDaysRemaining() {
        return ChronoUnit.DAYS.between(LocalDate.now(), deadline);
    }

    public int getUrgencyScore() {
        long daysLeft = getDaysRemaining();

        if (daysLeft <= 0) {
            return 10;
        } else if (daysLeft == 1) {
            return 8;
        } else if (daysLeft <= 3) {
            return 6;
        } else if (daysLeft <= 7) {
            return 4;
        } else {
            return 2;
        }
    }

    public double getPriorityScore() {
        if (completed) {
            return 0;
        }

        return (getUrgencyScore() * 5)
                + (difficulty * 3)
                + (estimatedHours * 1.5);
    }

    public String getPriorityLabel() {
        double score = getPriorityScore();

        if (completed) {
            return "Completed";
        } else if (score >= 50) {
            return "Critical";
        } else if (score >= 35) {
            return "High";
        } else if (score >= 20) {
            return "Medium";
        } else {
            return "Low";
        }
    }

    public String getStatus() {
        return completed ? "Completed" : "Pending";
    }
    public String toFileString() {
        String safeTitle = title.replace("|", "/");
        String safeSubject = subject.replace("|", "/");
        String safeNotes = notes.replace("|", "/").replace("\n", " ");

        return id
                + "|"
                + safeTitle
                + "|"
                + safeSubject
                + "|"
                + deadline
                + "|"
                + difficulty
                + "|"
                + estimatedHours
                + "|"
                + safeNotes
                + "|"
                + completed;
    }
    public void displayTask() {
        System.out.println("----------------------------------------");
        System.out.println("Task ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Subject: " + subject);
        System.out.println("Deadline: " + deadline);
        System.out.println("Days Remaining: " + getDaysRemaining());
        System.out.println("Difficulty: " + difficulty + "/5");
        System.out.println("Estimated Study Time: " + estimatedHours + " hours");
        System.out.println("Priority: " + getPriorityLabel());
        System.out.printf("Priority Score: %.1f%n", getPriorityScore());
        System.out.println("Status: " + getStatus());

        if (notes != null && !notes.isBlank()) {
            System.out.println("Notes: " + notes);
        }

        System.out.println("----------------------------------------");
    }
}