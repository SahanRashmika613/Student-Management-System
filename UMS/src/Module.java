public class Module {
    private int mark1;
    private int mark2;
    private int mark3;

    // Constructor to initialize marks for three Module
    public Module(int mark1, int mark2, int mark3) {
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
    }

    public int getMark1() {
        return mark1;
    }

    public int getMark2() {
        return mark2;
    }

    public int getMark3() {
        return mark3;
    }

    // Method to calculate and return the grade based on average mark
    public char getGrade() {
        int average = (mark1 + mark2 + mark3) / 3;
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
}
