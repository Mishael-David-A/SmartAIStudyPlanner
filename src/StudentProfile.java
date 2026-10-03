public class StudentProfile {
    private String name;
    private double dailyAvailableHours;
    private String preferredStartTime;

    public StudentProfile(
            String name,
            double dailyAvailableHours,
            String preferredStartTime) {
        this.name = name;
        this.dailyAvailableHours = dailyAvailableHours;
        this.preferredStartTime = preferredStartTime;
    }

    public String getName() {
        return name;
    }

    public double getDailyAvailableHours() {
        return dailyAvailableHours;
    }

    public String getPreferredStartTime() {
        return preferredStartTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDailyAvailableHours(double dailyAvailableHours) {
        if (dailyAvailableHours > 0) {
            this.dailyAvailableHours = dailyAvailableHours;
        }
    }

    public void setPreferredStartTime(String preferredStartTime) {
        this.preferredStartTime = preferredStartTime;
    }
    public String toFileString() {
        return name.replace("|", "/")
                + "|"
                + dailyAvailableHours
                + "|"
                + preferredStartTime.replace("|", "/");
    }
    public void displayProfile() {
        System.out.println("========================================");
        System.out.println("           STUDENT PROFILE");
        System.out.println("========================================");
        System.out.println("Name: " + name);
        System.out.println("Daily Available Study Hours: " + dailyAvailableHours);
        System.out.println("Preferred Study Start Time: " + preferredStartTime);
        System.out.println("========================================");
    }
}