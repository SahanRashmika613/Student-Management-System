public class Student {
    private int id;// Student ID
    private String name;// Student name
    private int module1Mark;// Mark for module 1
    private int module2Mark;
    private int module3Mark;

    // Constructor to initialize id and name
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setter method to set module marks
    public void setModuleMarks(int module1Mark, int module2Mark, int module3Mark) {
        this.module1Mark = module1Mark;
        this.module2Mark = module2Mark;
        this.module3Mark = module3Mark;
    }

    // Getter method for module1Mark
    public int getModule1Mark() {
        return module1Mark;
    }

    public int getModule2Mark() {
        return module2Mark;
    }

    public int getModule3Mark() {
        return module3Mark;
    }

    // Method to calculate and return the average mark
    public double getAverage() {
        return (module1Mark + module2Mark + module3Mark) / 3.0;
    }
}