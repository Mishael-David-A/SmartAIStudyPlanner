# Smart AI Study Planner

A console-based Java application that helps students organize study tasks using a priority-based recommendation system.

## Features

- Create and manage a student profile
- Add, view, search, edit, and delete study tasks
- Mark tasks as completed
- Sort tasks based on calculated priority
- Generate a daily smart study plan
- View a progress report
- Validate user input
- Save and load task data using local files

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- `ArrayList`
- `LocalDate`
- File handling with `Files` and `Path` APIs
- Visual Studio Code

## Priority Formula

Tasks are prioritized using the following formula:

```text
Priority Score = (Urgency × 5) + (Difficulty × 3) + (Estimated Study Hours × 1.5)
```

A higher score represents a task that should be handled sooner.

## Project Structure

```text
src/
├── Main.java
├── StudentProfile.java
├── Task.java
├── StudyPlanner.java
├── FileManager.java
├── InputHelper.java
├── README.md
├── .gitignore
└── data/
    ├── profile.txt
    └── tasks.txt
```

> The files in `data/` store local user information and are excluded from GitHub through `.gitignore`.

## How to Run

1. Open the project folder in VS Code.
2. Open the terminal.
3. Move into the `src` folder:

```bash
cd src
```

4. Compile the program:

```bash
javac *.java
```

5. Run the application:

```bash
java Main
```

## Author

Mishael David A
