package model.business;

import model.entity.Student;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Quản lý danh sách đăng ký (CRUD) và lưu trữ file nhị phân (.dat).
 */
public class StudentManager {

    private final String pathFile;
    private boolean isSaved;
    private List<Student> list = new ArrayList<>();

    /*
     * HEADER/FOOTER cập nhật thêm cột Email so với bản gốc.
     * Dùng static final vì đây là hằng số không đổi giữa các instance.
     */
    private static final String HEADER_TABLE
            = "----------------------------------------------------------------------------------------------\n"
            + "Student ID | Name            | Phone       | Email                    | Peak Code | Fee\n"
            + "----------------------------------------------------------------------------------------------";
    private static final String FOOTER_TABLE
            = "----------------------------------------------------------------------------------------------";

    public StudentManager() {
        this.pathFile = "data/registrations.dat";
        this.isSaved = true;
    }

    public boolean isSaved() {
        return isSaved;
    }

    // ==================== CRUD Operations ====================

    public void add(Student student) {
        list.add(student);
        this.isSaved = false;
    }

    public void update(Student student) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equalsIgnoreCase(student.getId())) {
                list.set(i, student);
                this.isSaved = false;
                return;
            }
        }
    }

    public void delete(String id) {
        Student student = searchById(id);
        if (student != null) {
            list.remove(student);
            this.isSaved = false;
        }
    }

    // ==================== Search & Filter ====================

    public Student searchById(String id) {
        for (Student student : list) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Tìm kiếm sinh viên theo tên (case-insensitive, partial match).
     *
     * FIX: Code cũ chỉ toLowerCase() vế trái nhưng giữ nguyên vế phải:
     *   student.getName().toLowerCase().contains(name)
     *   → "nguyen van an".contains("AN") = false (sai!)
     *
     * Code mới: chuẩn hóa CẢ HAI vế về lowercase trước khi so sánh.
     */
    public List<Student> searchByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : list) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    /**
     * Lọc sinh viên theo mã campus (2 ký tự đầu của Student ID).
     * Ví dụ: campusCode = "CE" → lọc tất cả student có ID bắt đầu bằng "CE".
     *
     * FIX: Code cũ duyệt trên `result` (danh sách rỗng vừa khởi tạo)
     *   thay vì `this.list` (nguồn dữ liệu chính) → luôn trả về rỗng!
     *
     *   for (Student student : result)   ← BUG: result rỗng, vòng lặp không chạy
     *   for (Student student : list)     ← FIX: duyệt trên danh sách tổng
     */
    public List<Student> filterByCampusCode(String campusCode) {
        List<Student> result = new ArrayList<>();
        for (Student student : list) {
            if (student.getId().length() >= 2
                    && student.getId().substring(0, 2).equalsIgnoreCase(campusCode)) {
                result.add(student);
            }
        }
        return result;
    }

    // ==================== Display ====================

    public void showAll() {
        showAll(this.list);
    }

    public void showAll(List<Student> displayList) {
        if (displayList.isEmpty()) {
            System.out.println("No students have registered yet.");
            return;
        }
        System.out.println(HEADER_TABLE);
        for (Student student : displayList) {
            System.out.println(student);
        }
        System.out.println(FOOTER_TABLE);
    }

    // ==================== Statistics ====================

    public void statisticalizeByMountainPeak() {
        StatisticManager statistic = new StatisticManager(this.list);
        statistic.show();
    }

    // ==================== File I/O ====================

    /**
     * Lưu danh sách Student ra file nhị phân (.dat) bằng ObjectOutputStream.
     * Yêu cầu: Student phải implements Serializable, nếu không → NotSerializableException.
     */
    public void saveToFile() {
        if (this.isSaved) {
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(this.pathFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Student student : list) {
                oos.writeObject(student);
            }
            this.isSaved = true;
        } catch (IOException exception) {
            Logger.getLogger(StudentManager.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    /**
     * Đọc danh sách Student từ file nhị phân (.dat) bằng ObjectInputStream.
     * Dùng EOFException để phát hiện hết file (cách tiêu chuẩn khi
     * ghi nhiều object liên tiếp bằng ObjectOutputStream).
     */
    public void readFromFile() {
        File file = new File(this.pathFile);
        if (!file.exists()) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    Student student = (Student) ois.readObject();
                    list.add(student);
                } catch (EOFException e) {
                    // Đã đọc hết file — thoát vòng lặp
                    break;
                }
            }
        } catch (ClassNotFoundException | IOException exception) {
            Logger.getLogger(StudentManager.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    // ==================== Getter ====================

    /**
     * Trả về danh sách sinh viên.
     * Khai báo kiểu List<> (interface) thay vì ArrayList<> (implementation)
     * → Program to Interface, not Implementation.
     */
    public List<Student> getList() {
        return list;
    }
}
