package jrpg_ca2;

/**
 * Admin Number: 2429634 Class: DIT/FT/2A/01
 *
 * @author Junkai
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StudentManagement {

    private ArrayList<Student> studentStore;
    private String filePath = "src/jrpg_ca2/students.txt";

    private static SoundPlayer errorAudio = new SoundPlayer("jrpg_ca2/error.wav");

    private PrintWriter printWriter;

    public StudentManagement() {
        this.studentStore = new ArrayList<Student>();
    }

    public void loadAllData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

//            System.out.println("Students List:");
//            System.out.println("-----------------");
            String line;

            // Read the first line to get number of records for students
            String records = reader.readLine();
            records = records.trim().replaceAll(";$", "");
            int intRecords = Integer.parseInt(records);
//            Student[] addStudents = new Student[intRecords];

            System.out.println(records);

            // Read Student Data
            for (int i = 0; i < intRecords; i++) {
                /* Read Student Data */
                line = reader.readLine();

                // Read Student data
                //if (line.trim().isEmpty()) continue;
                // Remove trailing semicolon and split by semicolon
                line = line.trim().replaceAll(";$", "");
                String[] studentParts = line.split(";");

                final Student newStudent = new Student(studentParts[1], studentParts[0]);
                System.out.println("Student ID " + studentParts[1]);
                System.out.println("Student Name " + studentParts[0]);
                this.studentStore.add(newStudent);

                // Reading Borrowed Books data
                line = reader.readLine();
                line = line.trim().replaceAll(";$", "");
                int numBorrowedBooks = Integer.parseInt(line);

                for (int j = 0; j < numBorrowedBooks; j++) {
                    line = reader.readLine();
                    line = line.trim().replaceAll(";$", "");
                    String[] bookParts = line.split(";");

                    System.out.println("Book Title: " + bookParts[0]);
                    newStudent.addBorrowedBook(new Book(bookParts[0], bookParts[1], bookParts[2], Double.parseDouble(bookParts[3]), bookParts[4], false));
                }
            }

        } catch (IOException e) {
            System.err.println("Error: The file 'students.txt' was not found or could not be read. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void saveAllData() {
        try {
            printWriter = new PrintWriter(new FileWriter("src/jrpg_ca2/students.txt"));

            // Save student list length
            printWriter.println(studentStore.size() + ";");

            for (int i = 0; i < studentStore.size(); i++) {
                final Student currentStudent = studentStore.get(i);

                printWriter.println(
                        currentStudent.getName() + ";"
                        + currentStudent.getAdminNumber() + ";"
                );
                
                final ArrayList<Book> studentBooks = currentStudent.getBorrowedBooks();
                printWriter.println(studentBooks.size() + ";");
                
                for (int j = 0; j < studentBooks.size(); j++) {
                    final Book currentBook = studentBooks.get(j);
                    printWriter.println(
                            currentBook.getBookTitle() + ";"
                            + currentBook.getAuthor() + ";"
                            + currentBook.getISBN() + ";"
                            + currentBook.getPrice() + ";"
                            + currentBook.getCategory() + ";"
                    );
                }
            }
            
            printWriter.close();

        } catch (Exception e) {
            System.err.println("Error saving data to file: ");
            e.printStackTrace();
        }
    }

    public ArrayList<Student> getStudentStore() {
        return this.studentStore;
    }

    public void displayStudents() {
        Object[][] rows = new Object[studentStore.size()][4];
        Object[] cols = {"Student", "Admin #", "Name", "Books Borrowed"};

        for (int i = 0; i < studentStore.size(); i++) {
            rows[i] = new Object[]{
                i + 1,
                studentStore.get(i).getAdminNumber(),
                studentStore.get(i).getName(),
                studentStore.get(i).getBorrowedBooks().size()
            };
        }

        JTable table = new JTable(rows, cols);

        JOptionPane.showMessageDialog(null, new JScrollPane(table), "All Students", JOptionPane.INFORMATION_MESSAGE);
    }

    public Student searchForStudent(String searchTerm) {
        for (int i = 0; i < this.studentStore.size(); i++) {
            if (this.studentStore.get(i).getName().equalsIgnoreCase(searchTerm)) {
                final String foundMsg = "Admin #: " + this.studentStore.get(i).getAdminNumber() + "\n"
                        + "Name: " + this.studentStore.get(i).getName();
//                JOptionPane.showMessageDialog(null, foundMsg, "Search Result", JOptionPane.INFORMATION_MESSAGE);
                return this.studentStore.get(i);
            }
        }

        // If not found
        errorAudio.playSound();
//        JOptionPane.showMessageDialog(null,
//                "Cannot find the student \"" + searchTerm + "\"!",
//                "Student Not Found",
//                JOptionPane.ERROR_MESSAGE);
        return null;
    }

    public void advancedSearchForStudent(String partialName) {
        Object[][] rows = new Object[studentStore.size()][4];
        Object[] cols = {"Student", "Admin #", "Name", "Books Borrowed"};

        boolean found = false;

        for (int i = 0; i < studentStore.size(); i++) {
            if (studentStore.get(i).getName().toLowerCase().contains(partialName.toLowerCase())) {
                found = true;
                rows[i] = new Object[]{
                    i + 1,
                    studentStore.get(i).getAdminNumber(),
                    studentStore.get(i).getName(),
                    studentStore.get(i).getBorrowedBooks().size()
                };
            }
        }

        if (found) {
            JTable table = new JTable(rows, cols);
            JOptionPane.showMessageDialog(
                    null,
                    new JScrollPane(table),
                    "Search Results",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            errorAudio.playSound();
            JOptionPane.showMessageDialog(
                    null,
                    "No students found containing \"" + partialName + "\".",
                    "No Results",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void addStudent() {
        final String dialogTitle = "Add new student";
        final String studAdminNumber = JOptionPane.showInputDialog(
                null,
                "Enter the new student's admin number:", dialogTitle,
                JOptionPane.QUESTION_MESSAGE);

        // If user pressed cancel, stop student creation
        if (studAdminNumber == null) {
            return;
        }

        // Validation of studAdminNumber
        if (studAdminNumber.charAt(0) != 'p') {
            errorAudio.playSound();
            JOptionPane.showMessageDialog(
                    null,
                    "Admin Number must begin with a \'p\'.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        } else if (studAdminNumber.length() > 8) {
            errorAudio.playSound();
            JOptionPane.showMessageDialog(
                    null,
                    "Admin Number must be exactly 8 characters.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        final int adminNo = Integer.parseInt(studAdminNumber.split("[p]")[1]);
        if (adminNo < 1300000 || adminNo > 2600000) {
            errorAudio.playSound();
            JOptionPane.showMessageDialog(
                    null,
                    "Invalid admin number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        final String studName = JOptionPane.showInputDialog(
                null,
                "Enter the new student's name:", dialogTitle,
                JOptionPane.QUESTION_MESSAGE
        );

        // If user pressed cancel, stop student creation
        if (studName == null) {
            return;
        }

        final Pattern namePattern = Pattern.compile("^[a-zA-Z\s]+$");
        final Matcher matcher = namePattern.matcher(studName);

        if (!matcher.find()) {
            errorAudio.playSound();
            JOptionPane.showMessageDialog(
                    null,
                    "Name can only contain letters and spaces.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        studentStore.add(new Student(studAdminNumber, studName));

        JOptionPane.showMessageDialog(
                null,
                "Student added successfully.", dialogTitle,
                JOptionPane.INFORMATION_MESSAGE
        );

    }
    
    

    public int getStudentCount() {
        return this.studentStore.size();
    }

    public void initialiseStudents(Student[] studentsArr) {
        for (int i = 0; i < studentsArr.length; i++) {
            this.studentStore.add(studentsArr[i]);
        }
    }
}