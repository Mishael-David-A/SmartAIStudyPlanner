import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class FileManager {
    private static final Path DATA_FOLDER = Path.of("data");
    private static final Path TASKS_FILE = DATA_FOLDER.resolve("tasks.txt");
    private static final Path PROFILE_FILE = DATA_FOLDER.resolve("profile.txt");

    public static void saveData(StudyPlanner planner) {
        try {
            Files.createDirectories(DATA_FOLDER);

            List<String> taskLines = planner.getTasks()
                    .stream()
                    .map(Task::toFileString)
                    .toList();

            Files.write(TASKS_FILE, taskLines);

            if (planner.getProfile() != null) {
                Files.writeString(
                        PROFILE_FILE,
                        planner.getProfile().toFileString());
            }

            System.out.println("Data saved successfully.");
        } catch (IOException exception) {
            System.out.println("Could not save data: " + exception.getMessage());
        }
    }

    public static void loadData(StudyPlanner planner) {
        loadProfile(planner);
        loadTasks(planner);
    }

    private static void loadProfile(StudyPlanner planner) {
        if (!Files.exists(PROFILE_FILE)) {
            return;
        }

        try {
            String line = Files.readString(PROFILE_FILE).trim();

            if (line.isEmpty()) {
                return;
            }

            String[] parts = line.split("\\|", -1);

            if (parts.length == 3) {
                StudentProfile profile = new StudentProfile(
                        parts[0],
                        Double.parseDouble(parts[1]),
                        parts[2]);

                planner.setProfile(profile);
            }
        } catch (IOException | NumberFormatException exception) {
            System.out.println("Could not load profile data.");
        }
    }

    private static void loadTasks(StudyPlanner planner) {
        if (!Files.exists(TASKS_FILE)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(TASKS_FILE);

            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);

                if (parts.length == 8) {
                    Task task = new Task(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            parts[2],
                            LocalDate.parse(parts[3]),
                            Integer.parseInt(parts[4]),
                            Double.parseDouble(parts[5]),
                            parts[6]);

                    if (Boolean.parseBoolean(parts[7])) {
                        task.markCompleted();
                    }

                    planner.addLoadedTask(task);
                }
            }
        } catch (IOException
                | NumberFormatException
                | java.time.format.DateTimeParseException exception) {
            System.out.println("Could not load task data.");
        }
    }
}