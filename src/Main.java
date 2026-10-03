import java.time.LocalDate;

public class Main {
    private static final StudyPlanner planner = new StudyPlanner();

    public static void main(String[] args) {
        FileManager.loadData(planner);

        System.out.println("==============================================");
        System.out.println("       WELCOME TO SMART AI STUDY PLANNER");
        System.out.println("==============================================");

        if (planner.getProfile() == null) {
            createOrUpdateProfile();
        } else {
            System.out.println("Welcome back, " + planner.getProfile().getName() + "!");
        }

        boolean running = true;

        while (running) {
            displayMenu();

            int choice = InputHelper.readInt(
                    "Enter your choice: ",
                    1,
                    12);

            switch (choice) {
                case 1 -> createOrUpdateProfile();
                case 2 -> viewProfile();
                case 3 -> addTask();
                case 4 -> planner.viewAllTasks();
                case 5 -> planner.viewTasksByPriority();
                case 6 -> searchTasks();
                case 7 -> markTaskCompleted();
                case 8 -> editTask();
                case 9 -> deleteTask();
                case 10 -> planner.generateTodayPlan();
                case 11 -> planner.viewProgressReport();
                case 12 -> {
                    FileManager.saveData(planner);
                    System.out.println(
                            "Thank you for using Smart AI Study Planner. Keep learning!");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n==============================================");
        System.out.println("                 MAIN MENU");
        System.out.println("==============================================");
        System.out.println("1. Set / Update Student Profile");
        System.out.println("2. View Student Profile");
        System.out.println("3. Add Study Task");
        System.out.println("4. View All Tasks");
        System.out.println("5. View Tasks by Priority");
        System.out.println("6. Search Tasks");
        System.out.println("7. Mark Task as Completed");
        System.out.println("8. Edit Task");
        System.out.println("9. Delete Task");
        System.out.println("10. Generate Today's Smart Study Plan");
        System.out.println("11. View Progress Report");
        System.out.println("12. Save and Exit");
        System.out.println("==============================================");
    }

    private static void createOrUpdateProfile() {
        System.out.println("\n----------- STUDENT PROFILE SETUP -----------");

        String name = InputHelper.readNonEmptyText("Enter your name: ");
        double dailyHours = InputHelper.readPositiveDouble(
                "Enter daily available study hours: ");
        String preferredTime = InputHelper.readNonEmptyText(
                "Enter preferred study start time (example: 7:00 PM): ");

        StudentProfile profile = new StudentProfile(
                name,
                dailyHours,
                preferredTime);

        planner.setProfile(profile);
        FileManager.saveData(planner);

        System.out.println("Profile saved successfully.");
    }

    private static void viewProfile() {
        if (planner.getProfile() == null) {
            System.out.println("No profile found. Please set your profile first.");
            return;
        }

        planner.getProfile().displayProfile();
    }

    private static void addTask() {
        System.out.println("\n--------------- ADD STUDY TASK ---------------");

        String title = InputHelper.readNonEmptyText("Enter task title: ");
        String subject = InputHelper.readNonEmptyText("Enter subject: ");
        LocalDate deadline = InputHelper.readDate(
                "Enter deadline (YYYY-MM-DD): ");
        int difficulty = InputHelper.readInt(
                "Enter difficulty (1 to 5): ",
                1,
                5);
        double hours = InputHelper.readPositiveDouble(
                "Enter estimated study hours: ");
        String notes = InputHelper.readOptionalText(
                "Enter notes (optional): ");

        planner.addTask(
                title,
                subject,
                deadline,
                difficulty,
                hours,
                notes);

        FileManager.saveData(planner);
    }

    private static void searchTasks() {
        String keyword = InputHelper.readNonEmptyText(
                "Enter task title or subject to search: ");

        planner.searchTasks(keyword);
    }

    private static void markTaskCompleted() {
        int id = InputHelper.readInt(
                "Enter task ID to mark as completed: ",
                1,
                100000);

        planner.markTaskCompleted(id);
        FileManager.saveData(planner);
    }

    private static void editTask() {
        int id = InputHelper.readInt(
                "Enter task ID to edit: ",
                1,
                100000);

        Task existingTask = planner.findTaskById(id);

        if (existingTask == null) {
            System.out.println("Task ID not found.");
            return;
        }

        System.out.println("Enter new details for: " + existingTask.getTitle());

        String title = InputHelper.readNonEmptyText("Enter new task title: ");
        String subject = InputHelper.readNonEmptyText("Enter new subject: ");
        LocalDate deadline = InputHelper.readDate(
                "Enter new deadline (YYYY-MM-DD): ");
        int difficulty = InputHelper.readInt(
                "Enter new difficulty (1 to 5): ",
                1,
                5);
        double hours = InputHelper.readPositiveDouble(
                "Enter new estimated study hours: ");
        String notes = InputHelper.readOptionalText(
                "Enter new notes (optional): ");

        planner.editTask(
                id,
                title,
                subject,
                deadline,
                difficulty,
                hours,
                notes);

        FileManager.saveData(planner);
    }

    private static void deleteTask() {
        int id = InputHelper.readInt(
                "Enter task ID to delete: ",
                1,
                100000);

        Task task = planner.findTaskById(id);

        if (task == null) {
            System.out.println("Task ID not found.");
            return;
        }

        boolean confirmed = InputHelper.readYesNo(
                "Are you sure you want to delete '" + task.getTitle() + "'?");

        if (confirmed) {
            planner.deleteTask(id);
            FileManager.saveData(planner);
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }
}