package view;

import model.entity.StatisticalInfo;
import model.entity.Student;

import java.util.Collection;
import java.util.List;

public class Menu {

    private static final String STUDENT_HEADER
            = "----------+---------------------+-------------+--------------------------+-----------+---------------\n"
            + "Student ID| Name                | Phone       | Gmail                    |Peak Code  | Fee\n"
            + "----------+---------------------+-------------+--------------------------+-----------+---------------";
    private static final String STUDENT_FOOTER
            = "----------+---------------------+-------------+--------------------------+-----------+---------------";

    private static final String STAT_HEADER
            = "--------------------+----------+-------------------------+----------------\n"
            + "Peak Name           |Peak Code | Number of Participants  | Total Cost\n"
            + "--------------------+----------+-------------------------+----------------";
    private static final String STAT_FOOTER
            = "--------------------+----------+-------------------------+----------------";

    public void display() {
        System.out.println("\n===== MOUNTAIN HIKING CHALLENGE REGISTRATION =====");
        System.out.println("1. New Registration");
        System.out.println("2. Update Registration Information");
        System.out.println("3. Display Registered List");
        System.out.println("4. Delete Registration Information");
        System.out.println("5. Search Participants by Name");
        System.out.println("6. Filter Data by Campus");
        System.out.println("7. Statistics of Registration Numbers by Location");
        System.out.println("8. Save Data to File");
        System.out.println("9. Exit the Program");
    }


    public void showMessage(String message) {
        System.out.println(message);
    }


    public void showInvalidChoice() {
        System.out.println("This function is not available.");
    }

    public void displayStudentList(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students have registered yet.");
            return; 
        }
        System.out.println(STUDENT_HEADER);
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println(STUDENT_FOOTER);
    }

    public void displayStatistics(Collection<StatisticalInfo> statistics) {
        if (statistics.isEmpty()) {
            System.out.println("No statistical data available.");
            return;
        }
        System.out.println(STAT_HEADER);
        for (StatisticalInfo info : statistics) {
            System.out.println(info);
        }
        System.out.println(STAT_FOOTER);
    }
}
