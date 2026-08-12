package view;

import model.entity.StatisticalInfo;
import model.entity.Student;

import java.util.Collection;
import java.util.List;

/**
 * View layer — chịu trách nhiệm HIỂN THỊ duy nhất.
 * Không chứa logic nghiệp vụ, không sửa đổi dữ liệu.
 *
 * [Sửa lỗi #11] Chuyển logic hiển thị từ Model layer (StudentManager, StatisticManager)
 * sang View layer (Menu) — đúng nguyên tắc MVC: Model cung cấp dữ liệu, View hiển thị.
 *
 * @author Legion
 */
public class Menu {

    private static final String STUDENT_HEADER
            = "----------------------------------------------------------------\n"
            + "Student ID | Name            | Phone       | Peak Code | Fee\n"
            + "----------------------------------------------------------------";
    private static final String STUDENT_FOOTER
            = "----------------------------------------------------------------";

    private static final String STAT_HEADER
            = "-----------------------------------------------------------------\n"
            + "Peak Name  | Number of Participants  | Total Cost\n"
            + "-----------------------------------------------------------------";
    private static final String STAT_FOOTER
            = "-----------------------------------------------------------------";

    /**
     * Hiển thị menu chính của chương trình.
     */
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

    /**
     * Hiển thị một thông báo bất kỳ.
     *
     * @param message nội dung thông báo
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Hiển thị thông báo lựa chọn không hợp lệ.
     */
    public void showInvalidChoice() {
        System.out.println("This function is not available.");
    }

    /**
     * Hiển thị bảng danh sách sinh viên.
     * Chuyển từ StudentManager.showAll() sang đây — đúng trách nhiệm View.
     *
     * @param students danh sách sinh viên cần hiển thị
     */
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

    /**
     * Hiển thị bảng thống kê theo đỉnh núi.
     * Chuyển từ StatisticManager.show() sang đây — đúng trách nhiệm View.
     *
     * @param statistics tập hợp các bản ghi thống kê
     */
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
