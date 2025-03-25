import java.io.*;
import java.util.*;

// Main class for the UoW Student Activity Management System
public class Main {
    private static final int MAX_STUDENTS = 100;// Maximum number of students
    private static Student[] students = new Student[MAX_STUDENTS];// Array to store student objects
    private static int numStudents = 0;// Counter for the number of registered students


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);// Scanner for user input
        int choice;

        do {
            displayMenu();// Display the menu
            choice = scanner.nextInt();// Get user choice
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    checkAvailableSeats();// Check available seats
                    break;
                case 2:
                    registerStudent(scanner);// Register a new student
                    break;
                case 3:
                    deleteStudent(scanner);// Delete a student
                    break;
                case 4:
                    findStudent(scanner);// Find a student by ID
                    break;
                case 5:
                    storeStudentDetails();// Store student details to a file
                    break;
                case 6:
                    loadStudentDetails();// Load student details from a file
                    break;
                case 7:
                    viewStudentsByName();// View students sorted by name
                    break;
                case 8:
                    additionalcontrollers(scanner);// Manage student results
                    break;
                case 0:
                    System.out.println("Exiting the program...");// Exit the program
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");// Handle invalid choice
                    break;
            }
        } while (choice != 0);// Loop until the user chooses to exit
    }
    // Display the menu options
    private static void displayMenu() {
        System.out.println("\nUoW Student Activity Management System");
        System.out.println("1. Check available seats");
        System.out.println("2. Register student (with ID)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student (with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from the file to the system");
        System.out.println("7. View the list of students based on their names");
        System.out.println("8. Additional controllers");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
    // Check available seats
    private static void checkAvailableSeats() {
        int availableSeats = MAX_STUDENTS - numStudents;
        System.out.println("Available seats: " + availableSeats);
    }
    // Register a new student
    private static void registerStudent(Scanner scanner) {
        System.out.println("You are in student registration option!\n");

        checkAvailableSeats();// Check if seats are available
        if (numStudents >= MAX_STUDENTS) {
            System.out.println("Sorry, no seats are available!");
            return;
        }

        String id;
        boolean correctFormat;

        // Loop to ensure the ID format is correct
        do {
            System.out.print("Enter student ID (must be 8 characters e.g. w1234567): ");
            id = scanner.nextLine();
            if (id.length() != 8 || id.charAt(0) != 'w') {
                System.out.println("ID must be 8 characters and start with 'w' (e.g. w1234567):");
                correctFormat = false;
            } else {
                correctFormat = true;
            }
        } while (!correctFormat);// Validate the ID format

        // Check if the ID is already in the system
        int numericId = Integer.parseInt(id.substring(1));// Extract numeric part of ID
        boolean studentFound = false;
        // Check if the ID is already in the system
        for (int i = 0; i < numStudents; i++) {
            if (students[i].getId() == numericId) {
                studentFound = true;
                break;
            }
        }
        // If the ID is already in the system, notify the user
        if (studentFound) {
            System.out.println(id + " is already in the system!");
        } else {

            // Otherwise, register the new student
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            students[numStudents++] = new Student(numericId, name); // Add new student to the array
            System.out.println("\nRegistered ID is " + id);
            System.out.println("A student registration has been successfully completed!\n");
        }
    }

    // Delete a student by ID
    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();

        // Validate the ID format
        if (id.length() != 8 || id.charAt(0) != 'w') {
            System.out.println("Invalid ID format. Please ensure the ID starts with 'w' followed by 7 digits.");
            return;// Exit the method if the format is incorrect
        }

        int numericId;
        try {
            numericId = Integer.parseInt(id.substring(1));// Extract the numeric part of the ID and convert it to an integer
        } catch (NumberFormatException e) {
            // Handle the case where the numeric part is not a valid integer
            System.out.println("Invalid ID format. Please ensure the ID starts with 'w' followed by 7 digits.");
            return;
        }

        boolean found = false;
        // Iterate through the students array to find the student with the given ID
        for (int i = 0; i < numStudents; i++) {
            if (students[i].getId() == numericId) {
                found = true;// Mark the student as found
                for (int j = i; j < numStudents - 1; j++) {
                    students[j] = students[j + 1];// Shift students in the array
                }
                numStudents--;// Decrement the number of students
                System.out.println("Student deleted successfully.");
                break;// Exit the loop once the student is found and deleted
            }
        }
        // If the student with the given ID was not found, print a message
        if (!found) {
            System.out.println("Student not found.");
        }
    }
    // Find a student by ID
    private static void findStudent(Scanner scanner) {
        System.out.print("Enter student ID to find: ");
        String id = scanner.nextLine();

        boolean found = false;
        for (int i = 0; i < numStudents; i++) {
            // Compare the stored student ID with the input ID
            if (("w" + students[i].getId()).equals(id)) {
                found = true;
                System.out.println("Student found: " + students[i].getName());
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }
    // Store student details to a file
    private static void storeStudentDetails() {
        try {
            FileWriter writer = new FileWriter("student_data.txt");// Create a FileWriter object to write to the file "student_data.txt"
            // Loop through all the students in the array
            for (int i = 0; i < numStudents; i++) {
                writer.write(students[i].getId() + "," + students[i].getName() + "\n");
            }
            writer.close();// Close the FileWriter to ensure all data is written and resources are released
            System.out.println("Student details stored successfully.");// Print a success message indicating that the student details have been stored
        } catch (IOException e) {
            // If an Input Output error occurs, print an error message with the exception details
            System.out.println("Error storing student details: " + e.getMessage());
        }
    }

    // Load student details from a file
    private static void loadStudentDetails() {
        try {
            FileReader reader = new FileReader("student_data.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            numStudents = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);// Extract the student ID and convert  from String to int
                String name = parts[1];// Extract the student name
                students[numStudents++] = new Student(id, name);// Load student data into the array
            }
            reader.close();
            System.out.println("Student details loaded successfully.");// Print a success message indicating that the student details have been loaded
        } catch (IOException e) {// If an Input output error occurs during file reading, catch it here
            // Print an error message with the exception details
            System.out.println("Error loading student details: " + e.getMessage());
        }
    }


    // View students sorted by name
    private static void viewStudentsByName() {
        sortStudentsByName(students, 0, numStudents - 1);// Sort students by name using merge sort
        System.out.println("List of students sorted by name:");// Print the list of students sorted by name
        Set<String> seenStudents = new HashSet<>(); // Set to keep track of seen student entries to avoid duplicates
        for (int i = 0; i < numStudents; i++) { // Iterate through the sorted student array
            String studentEntry = students[i].getId() + " - " + students[i].getName();
            if (!seenStudents.contains(studentEntry)) {
                System.out.println(studentEntry);
                seenStudents.add(studentEntry);
            }
        }
    }

    // Manage student results
    private static void additionalcontrollers(Scanner scanner) {
        System.out.println("\nAdditional controllers");
        System.out.println("a. Add student name and module marks");
        System.out.println("b. Generate report");
        System.out.println("c. Generate summary");
        System.out.print("Enter your choice: ");
        char choice = scanner.next().charAt(0);
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 'a':
                addStudentNameAndModuleMarks(scanner);// Add student marks
                break;
            case 'b':
                generateReport(); // Generate report
                break;
            case 'c':
                generateSummary();// Generate summary
                break;
            default:
                System.out.println("Invalid choice. Please try again.");// Handle invalid choice
                break;
        }
    }


    // Add student name and module marks
    private static void addStudentNameAndModuleMarks(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        int numericId;// Validate the ID format
        if (studentId.length() == 8 && studentId.charAt(0) == 'w') {
            try {
                numericId = Integer.parseInt(studentId.substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format. Please ensure the ID starts with 'w' followed by 7 digits.");
                return;
            }
        } else {
            System.out.println("Invalid ID format. Please ensure the ID starts with 'w' followed by 7 digits.");
            return;
        }

        // Prompt for student name
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        // Prompt for module marks
        System.out.print("Enter module 1 mark: ");
        int module1Mark = scanner.nextInt();
        System.out.print("Enter module 2 mark: ");
        int module2Mark = scanner.nextInt();
        System.out.print("Enter module 3 mark: ");
        int module3Mark = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Check if the student already exists
        boolean studentExists = false;
        for (int i = 0; i < numStudents; i++) {
            if (students[i].getId() == numericId) {
                // Update existing student's marks
                students[i].setModuleMarks(module1Mark, module2Mark, module3Mark);// Update student marks
                System.out.println("Student marks updated successfully.");
                studentExists = true;
                break;
            }
        }

        if (!studentExists) {
            if (numStudents < MAX_STUDENTS) {
                Student student = new Student(numericId, name);// Create new student and add to array
                student.setModuleMarks(module1Mark, module2Mark, module3Mark);// Add new student with marks
                students[numStudents++] = student;
                System.out.println("Student added successfully with module marks.");
            } else {
                System.out.println("Maximum capacity reached. Cannot add more students.");
            }
        }
    }

    // Generate a report of students with their marks and grades
    private static void generateReport() {
        // Check if there are no students registered
        if (numStudents == 0) {
            System.out.println("No students registered.");
            return;
        }
        // Create a list to store students with at least one module mark
        List<Student> studentsWithMarks = new ArrayList<>();
        for (int i = 0; i < numStudents; i++) {
            Student s = students[i];// Check if the student has marks in any module
            if (s.getModule1Mark() > 0 || s.getModule2Mark() > 0 || s.getModule3Mark() > 0) {
                studentsWithMarks.add(s);
            }
        }

        // Check if no students have marks entered
        if (studentsWithMarks.isEmpty()) {// Check if no students have marks entered
            System.out.println("No students have marks entered.");
            return;
        }

        Student[] sortedStudents = studentsWithMarks.toArray(new Student[0]);
        bubbleSortByAverage(sortedStudents);// Sort students by average marks
        // Print the header for the student report
        System.out.println("\n--- Complete Student Report ---");
        System.out.printf("%-10s %-20s %-10s %-10s %-10s %-10s %-10s %-10s\n",
                "ID", "Name", "Module 1", "Module 2", "Module 3", "Total", "Average", "Grade");
        System.out.println("-------------------------------------------------------------------------------------");


        // Iterate through sorted students and print their details
        for (Student s : sortedStudents) {
            int total = s.getModule1Mark() + s.getModule2Mark() + s.getModule3Mark();
            double average = total / 3.0;
            char grade = getGrade(average);// Calculate grade based on average
            System.out.printf("%-10d %-20s %-10d %-10d %-10d %-10d %-10.2f %-10c\n",
                    s.getId(), s.getName(), s.getModule1Mark(), s.getModule2Mark(), s.getModule3Mark(),
                    total, average, grade);
        }
    }

    // Generate a summary of student results
    private static void generateSummary() {
        // Calculate total number of students registered
        int totalStudents = numStudents;
        // Array to store count of students passing each module
        int[] passCountPerModule = new int[3];

        // Iterate through each student
        for (int i = 0; i < numStudents; i++) {
            // Iterate through each module
            for (int j = 0; j < 3; j++) {
                // Check if the student's mark for this module is greater than 40
                if (getModuleMark(students[i], j) > 40) {
                    // Increment the count of students passing this module
                    passCountPerModule[j]++;
                }
            }
        }

        // Print system summary
        System.out.println("\n--- System Summary ---");
        System.out.println("Total student registrations: " + totalStudents);
        System.out.println("Students who scored more than 40 marks:");
        System.out.println("Module 1: " + passCountPerModule[0]);
        System.out.println("Module 2: " + passCountPerModule[1]);
        System.out.println("Module 3: " + passCountPerModule[2]);
    }

    private static char getGrade(double average) {
        if (average >= 80) {
            return 'D'; // Distinction
        } else if (average >= 70) {
            return 'M'; // Merit
        } else if (average >= 40) {
            return 'P'; // Pass
        } else {
            return 'F'; // Fail
        }
    }
    // Get module mark for a specific student and module
    private static int getModuleMark(Student student, int module) {
        switch (module) {
            case 0:
                return student.getModule1Mark();
            case 1:
                return student.getModule2Mark();
            case 2:
                return student.getModule3Mark();
            default:
                return 0;
        }
    }

    // Sort students by average marks using bubble sort
    private static void bubbleSortByAverage(Student[] arr) {
        // Outer loop iterates over the array from the beginning to the second last element
        for (int i = 0; i < arr.length - 1; i++) {
            // Inner loop iterates over the array from the beginning to (second last - i) element
            for (int j = 0; j < arr.length - i - 1; j++) {
                // Compare average of current element with average of next element
                if (arr[j].getAverage() < arr[j + 1].getAverage()) {
                    Student temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // Sort students by name using merge sort
    private static void sortStudentsByName(Student[] students, int left, int right) {
        // Check if there is more than one element in the array segment
        if (left < right) {
            int mid = left + (right - left) / 2;// Calculate the middle index
            sortStudentsByName(students, left, mid);// Recursively sort the left and right halves
            sortStudentsByName(students, mid + 1, right);
            mergeStudents(students, left, mid, right);
        }
    }
    // Merge function for merge sort
    private static void mergeStudents(Student[] students, int left, int mid, int right) {
        int i = left, j = mid + 1;// Initialize pointers for left and right subarrays
        Student[] temp = new Student[right - left + 1];
        int k = 0;// Initialize index for temporary array


        // Merge the two halves into the temporary array
        while (i <= mid && j <= right) {
            // Compare names of students at current pointers
            if (students[i].getName().compareTo(students[j].getName()) < 0) {
                temp[k++] = students[i++];// Copy the student from the left sub array to temp
            } else {
                temp[k++] = students[j++];// Copy the student from the right subarray to temp
            }
        }
        // Copy remaining elements from left sub array to temp
        while (i <= mid) {
            temp[k++] = students[i++];
        }
        // Copy remaining elements from right sub array to temp
        while (j <= right) {
            temp[k++] = students[j++];
        }
        // Copy the merged elements from temp back to the original array
        for (i = left; i <= right; i++) {
            students[i] = temp[i - left];
        }
    }
}

