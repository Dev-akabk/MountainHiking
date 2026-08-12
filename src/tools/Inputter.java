package tools;

import model.entity.Student;

import java.util.Scanner;

/**
 * Lớp tiện ích xử lý toàn bộ nhập liệu từ Console.
 * Kết hợp với Acceptable (Utility Class) để validate dữ liệu theo Regex.
 */
public class Inputter {

    private Scanner scanner;

    public Inputter() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Nhập chuỗi từ bàn phím.
     */
    public String getString(String mess) {
        System.out.print(mess);
        return scanner.nextLine();
    }

    /**
     * Nhập số nguyên — lặp liên tục cho đến khi user nhập đúng định dạng.
     * Code cũ: trả về 0 nếu nhập sai (âm thầm, user không biết mình sai).
     * Code mới: bắt buộc nhập lại, có thông báo lỗi rõ ràng.
     */
    public int getInt(String mess) {
        while (true) {
            String temp = getString(mess);
            if (Acceptable.isValid(temp, Acceptable.INTEGER_VALID)) {
                return Integer.parseInt(temp);
            }
            System.out.println("Please enter a valid integer! Re-enter ...");
        }
    }

    /**
     * Nhập số thực — lặp liên tục cho đến khi user nhập đúng định dạng.
     */
    public double getDouble(String mess) {
        while (true) {
            String temp = getString(mess);
            if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
                return Double.parseDouble(temp);
            }
            System.out.println("Please enter a valid number! Re-enter ...");
        }
    }

    /**
     * Nhập chuỗi và lặp cho đến khi khớp pattern Regex.
     * Đã có sẵn từ code gốc — logic tốt, giữ nguyên.
     */
    public String inputLoop(String mess, String pattern) {
        String result;
        boolean running;
        do {
            result = getString(mess);
            running = !Acceptable.isValid(result, pattern);
            if (running) {
                System.out.println("Data is invalid! Re-enter ...");
            }
        } while (running);
        return result.trim();
    }

    /**
     * Nhập toàn bộ thông tin Student.
     * Dùng chung cho cả New Registration (isUpdate=false)
     * và Update Registration (isUpdate=true).
     *
     * Khi isUpdate=true và oldStudent != null:
     *   - Student ID: giữ nguyên ID cũ (không cho đổi)
     *   - Các trường khác: nhấn Enter để giữ giá trị cũ,
     *     hoặc nhập mới để thay thế.
     *
     * Khi isUpdate=false:
     *   - Student ID: bắt buộc nhập mới, validate theo Regex.
     *   - Các trường khác: bắt buộc nhập, validate theo Regex.
     *
     * Tính tuitionFee:
     *   - Mặc định: 6,000,000
     *   - Nếu SĐT thuộc đầu số Viettel hoặc VNPT → giảm 35% (nhân 0.65)
     *
     * @param isUpdate   true nếu đang cập nhật, false nếu đăng ký mới
     * @param oldStudent đối tượng Student cũ (chỉ cần khi isUpdate=true)
     * @return đối tượng Student mới chứa thông tin đã nhập
     */
    public Student enterStudentInfo(boolean isUpdate, Student oldStudent) {
        // --- Student ID ---
        String id;
        if (isUpdate && oldStudent != null) {
            id = oldStudent.getId();
            System.out.println(">> Updating Student: " + id);
        } else {
            id = inputLoop("Enter Student ID (e.g. CE123456): ", Acceptable.STUDENT_ID_VALID);
        }

        // --- Name ---
        String name;
        if (isUpdate && oldStudent != null) {
            name = getString("Enter Name (Enter to keep '" + oldStudent.getName() + "'): ");
            if (name.trim().isEmpty()) {
                name = oldStudent.getName();
            }
        } else {
            name = inputLoop("Enter Name: ", Acceptable.NAME_VALID);
        }

        // --- Phone ---
        String phone;
        if (isUpdate && oldStudent != null) {
            phone = getString("Enter Phone (Enter to keep '" + oldStudent.getPhone() + "'): ");
            if (phone.trim().isEmpty()) {
                phone = oldStudent.getPhone();
            }
        } else {
            phone = inputLoop("Enter Phone (10 digits, start with 0): ", Acceptable.PHONE_VALID);
        }

        // --- Email ---
        String email;
        if (isUpdate && oldStudent != null) {
            email = getString("Enter Email (Enter to keep '" + oldStudent.getEmail() + "'): ");
            if (email.trim().isEmpty()) {
                email = oldStudent.getEmail();
            }
        } else {
            email = inputLoop("Enter Email: ", Acceptable.EMAIL_VALID);
        }

        // --- Mountain Code ---
        String mountainCode;
        if (isUpdate && oldStudent != null) {
            mountainCode = getString("Enter Mountain Code (Enter to keep '" + oldStudent.getMountainCode() + "'): ");
            if (mountainCode.trim().isEmpty()) {
                mountainCode = oldStudent.getMountainCode();
            }
        } else {
            mountainCode = getString("Enter Mountain Code: ");
        }

        // --- Tuition Fee (auto-calculated) ---
        double tuitionFee = 6_000_000;
        if (Acceptable.isValid(phone, Acceptable.VIETTEL_VALID)
                || Acceptable.isValid(phone, Acceptable.VNPT_VALID)) {
            tuitionFee *= 0.65; // Giảm 35% cho đầu số Viettel/VNPT
            System.out.println(">> Viettel/VNPT discount applied! Fee: " + String.format("%,.0f", tuitionFee));
        }

        return new Student(id, name, phone, email, mountainCode, tuitionFee);
    }

    /**
     * Overload cho trường hợp đăng ký mới (không cần truyền oldStudent).
     */
    public Student enterStudentInfo(boolean isUpdate) {
        return enterStudentInfo(isUpdate, null);
    }
}
