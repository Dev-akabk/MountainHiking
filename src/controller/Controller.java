package controller;

import model.business.MountainManager;
import model.business.StudentManager;
import model.entity.Student;
import tools.Inputter;
import view.Menu;

import java.util.List;

/**
 * CONTROLLER — điều phối luồng xử lý chính của ứng dụng.
 *
 * Trách nhiệm:
 *   1. Đọc lựa chọn menu từ user (qua Inputter)
 *   2. Gọi Inputter để nhập + validate dữ liệu, nhận lại Entity
 *   3. Gọi Manager (StudentManager / MountainManager) để xử lý nghiệp vụ
 *   4. Gọi Menu (View) để hiển thị thông báo — View không phản hồi ngược
 *
 * FIX:
 *   - Đổi tên biến: rl → studentManager, mountainmanager → mountainManager,
 *     scanner → inputter (Java Naming Convention + tên có ý nghĩa).
 *   - Hoàn thiện tất cả TODO (case 1-9).
 */
public class Controller {

    private final Menu menu = new Menu();
    private final Inputter inputter = new Inputter();
    private final StudentManager studentManager = new StudentManager();
    private final MountainManager mountainManager = new MountainManager();

    public void run() {
        mountainManager.readFromFile();
        studentManager.readFromFile();

        int choice;
        do {
            menu.display();
            choice = inputter.getInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    handleNewRegistration();
                    break;
                case 2:
                    handleUpdateRegistration();
                    break;
                case 3:
                    studentManager.showAll();
                    break;
                case 4:
                    handleDeleteRegistration();
                    break;
                case 5:
                    handleSearchByName();
                    break;
                case 6:
                    handleFilterByCampus();
                    break;
                case 7:
                    studentManager.statisticalizeByMountainPeak();
                    break;
                case 8:
                    studentManager.saveToFile();
                    menu.showMessage("Registration data has been successfully saved.");
                    break;
                case 9:
                    handleExit();
                    break;
                default:
                    menu.showInvalidChoice();
            }
        } while (choice != 9);
    }

    // ==================== Handler Methods ====================

    /**
     * Case 1: Đăng ký mới.
     * Luồng: Nhập thông tin → Kiểm tra trùng ID → Kiểm tra Mountain Code → Thêm vào list.
     */
    private void handleNewRegistration() {
        Student newStudent = inputter.enterStudentInfo(false);
        if (newStudent == null) {
            return;
        }
        // Guard clause: kiểm tra trùng Student ID
        if (studentManager.searchById(newStudent.getId()) != null) {
            menu.showMessage("Error: Student ID '" + newStudent.getId() + "' already exists!");
            return;
        }
        // Guard clause: kiểm tra Mountain Code tồn tại
        if (!mountainManager.isValidMountainCode(newStudent.getMountainCode())) {
            menu.showMessage("Error: Mountain code '" + newStudent.getMountainCode() + "' does not exist!");
            return;
        }
        studentManager.add(newStudent);
        menu.showMessage("Registration successful!");
    }

    /**
     * Case 2: Cập nhật thông tin đăng ký.
     * Luồng: Nhập ID → Tìm student cũ → Nhập thông tin mới (Enter để giữ cũ) → Cập nhật.
     */
    private void handleUpdateRegistration() {
        String updateId = inputter.getString("Enter Student ID to update: ");
        Student existing = studentManager.searchById(updateId);
        if (existing == null) {
            menu.showMessage("Error: Student ID '" + updateId + "' not found!");
            return;
        }
        // Truyền existing vào để hỗ trợ "Enter to keep old value"
        Student updatedStudent = inputter.enterStudentInfo(true, existing);
        // Validate mountain code mới (nếu user đổi)
        if (!mountainManager.isValidMountainCode(updatedStudent.getMountainCode())) {
            menu.showMessage("Error: Mountain code '" + updatedStudent.getMountainCode() + "' does not exist!");
            return;
        }
        studentManager.update(updatedStudent);
        menu.showMessage("Update successful!");
    }

    /**
     * Case 4: Xóa đăng ký.
     * Luồng: Nhập ID → Kiểm tra tồn tại → Xóa.
     */
    private void handleDeleteRegistration() {
        String deleteId = inputter.getString("Enter Student ID to delete: ");
        if (studentManager.searchById(deleteId) == null) {
            menu.showMessage("Error: Student ID '" + deleteId + "' not found!");
            return;
        }
        studentManager.delete(deleteId);
        menu.showMessage("Deleted successfully!");
    }

    /**
     * Case 5: Tìm kiếm theo tên (partial match, case-insensitive).
     */
    private void handleSearchByName() {
        String searchName = inputter.getString("Enter name to search: ");
        List<Student> searchResult = studentManager.searchByName(searchName);
        studentManager.showAll(searchResult);
    }

    /**
     * Case 6: Lọc theo mã campus (2 ký tự đầu của Student ID, ví dụ CE, DE, HE).
     */
    private void handleFilterByCampus() {
        String campusCode = inputter.getString("Enter Campus Code (e.g. CE, DE, HE, SE, QE): ");
        List<Student> filterResult = studentManager.filterByCampusCode(campusCode);
        studentManager.showAll(filterResult);
    }

    /**
     * Case 9: Thoát chương trình.
     * Fail-Safe: nếu có dữ liệu chưa lưu → hỏi user xác nhận trước khi thoát.
     */
    private void handleExit() {
        if (!studentManager.isSaved()) {
            String confirm = inputter.getString("Data not saved. Save before exit? (Y/N): ");
            if (confirm.equalsIgnoreCase("Y")) {
                studentManager.saveToFile();
                menu.showMessage("Data saved successfully. Goodbye!");
            } else {
                menu.showMessage("Exiting without saving. Goodbye!");
            }
        } else {
            menu.showMessage("Goodbye!");
        }
    }
}
