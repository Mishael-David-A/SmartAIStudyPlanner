import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudyPlanner {
    private ArrayList<Task> tasks;
    private StudentProfile profile;
    private int nextTaskId;

    public StudyPlanner() {
        tasks = new ArrayList<>();
        nextTaskId = 1;
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void addLoadedTask(Task task) {
        tasks.add(task);

        if (task.getId() >= nextTaskId) {
            nextTaskId = task.getId() + 1;
        }
    }
    public void setProfile(StudentProfile profile) {
        this.profile = profile;
    }

    public StudentProfile getProfile() {
        return profile;
    }

    public void addTask(
            String title,
            String subject,
            LocalDate deadline,
            int difficulty,
            double estimatedHours,
            String notes) {
        Task task = new Task(
                nextTaskId,
                title,
                subject,
                deadline,
                difficulty,
                estimatedHours,
                notes);

        tasks.add(task);
        nextTaskId++;

        System.out.println("Task added successfully. Task ID: " + task.getId());
    }

    public Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }

        return null;
    }

    public void viewAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found. Add your first study task.");
            return;
        }

        System.out.println("\n============== ALL TASKS ==============");

        for (Task task : tasks) {
            task.displayTask();
        }
    }

    public void viewTasksByPriority() {
        List<Task> pendingTasks = getPendingTasksSortedByPriority();

        if (pendingTasks.isEmpty()) {
            System.out.println("No pending tasks available. Great work!");
            return;
        }

        System.out.println("\n======== TASKS BY PRIORITY ========");

        for (Task task : pendingTasks) {
            task.displayTask();
        }
    }
    public void searchTasks(String keyword) {
        boolean found = false;
        String lowerKeyword = keyword.toLowerCase();

        System.out.println("\n============= SEARCH RESULTS =============");

        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(lowerKeyword)
                    || task.getSubject().toLowerCase().contains(lowerKeyword)) {
                task.displayTask();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No task found for: " + keyword);
        }
    }

    public void editTask(
            int id,
            String title,
            String subject,
            LocalDate deadline,
            int difficulty,
            double estimatedHours,
            String notes) {
        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("Task ID not found.");
            return;
        }

        task.setTitle(title);
        task.setSubject(subject);
        task.setDeadline(deadline);
        task.setDifficulty(difficulty);
        task.setEstimatedHours(estimatedHours);
        task.setNotes(notes);

        System.out.println("Task updated successfully.");
    }
    public List<Task> getPendingTasksSortedByPriority() {
        List<Task> pendingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (!task.isCompleted()) {
                pendingTasks.add(task);
            }
        }

        pendingTasks.sort(
                Comparator.comparingDouble(Task::getPriorityScore)
                        .reversed()
                        .thenComparing(Task::getDeadline));

        return pendingTasks;
    }

    public void markTaskCompleted(int id) {
        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("Task ID not found.");
            return;
        }

        if (task.isCompleted()) {
            System.out.println("This task is already completed.");
            return;
        }

        task.markCompleted();
        System.out.println("Task marked as completed. Great job!");
    }

    public void deleteTask(int id) {
        Task task = findTaskById(id);

        if (task == null) {
            System.out.println("Task ID not found.");
            return;
        }

        tasks.remove(task);
        System.out.println("Task deleted successfully.");
    }

    public void generateTodayPlan() {
        if (profile == null) {
            System.out.println("Please set up your student profile first.");
            return;
        }

        List<Task> pendingTasks = getPendingTasksSortedByPriority();

        if (pendingTasks.isEmpty()) {
            System.out.println("No pending tasks. Enjoy your free time!");
            return;
        }

        double remainingHours = profile.getDailyAvailableHours();
        double plannedHours = 0;
        int planNumber = 1;

        System.out.println("\n========== TODAY'S SMART STUDY PLAN ==========");
        System.out.println("Student: " + profile.getName());
        System.out.println("Preferred Start Time: " + profile.getPreferredStartTime());
        System.out.println("Available Study Time: " + profile.getDailyAvailableHours() + " hours");
        System.out.println("-----------------------------------------------");

        for (Task task : pendingTasks) {
            if (task.getEstimatedHours() <= remainingHours) {
                System.out.println(planNumber + ". " + task.getTitle());
                System.out.println("   Subject: " + task.getSubject());
                System.out.println("   Study Time: " + task.getEstimatedHours() + " hours");
                System.out.println("   Priority: " + task.getPriorityLabel());
                System.out.println("   Take a 15-minute break after this task.");
                System.out.println();

                remainingHours -= task.getEstimatedHours();
                plannedHours += task.getEstimatedHours();
                planNumber++;
            }
        }

        if (planNumber == 1) {
            System.out.println("No task fits within your available study time.");
        } else {
            System.out.printf("Total Planned Study Time: %.1f hours%n", plannedHours);
            System.out.printf("Free Time Remaining: %.1f hours%n", remainingHours);
            System.out.println("Motivation: Small daily progress creates big results!");
        }

        System.out.println("===============================================");
    }

    public void viewProgressReport() {
        int totalTasks = tasks.size();
        int completedTasks = 0;
        int pendingTasks = 0;
        int criticalTasks = 0;
        double totalHours = 0;
        double completedHours = 0;

        for (Task task : tasks) {
            totalHours += task.getEstimatedHours();

            if (task.isCompleted()) {
                completedTasks++;
                completedHours += task.getEstimatedHours();
            } else {
                pendingTasks++;

                if (task.getPriorityLabel().equals("Critical")) {
                    criticalTasks++;
                }
            }
        }

        double completionPercentage =
                totalTasks == 0 ? 0 : (completedTasks * 100.0) / totalTasks;

        System.out.println("\n========== PROGRESS REPORT ==========");
        System.out.println("Total Tasks: " + totalTasks);
        System.out.println("Completed Tasks: " + completedTasks);
        System.out.println("Pending Tasks: " + pendingTasks);
        System.out.println("Critical Pending Tasks: " + criticalTasks);
        System.out.printf("Completion Percentage: %.1f%%%n", completionPercentage);
        System.out.printf("Total Estimated Study Hours: %.1f%n", totalHours);
        System.out.printf("Completed Study Hours: %.1f%n", completedHours);
        System.out.println("=====================================");
    }
}