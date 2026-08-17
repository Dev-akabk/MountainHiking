package tools;

import model.entity.Student;
import model.business.MountainManager;

import java.util.Scanner;

public class Inputter {

    private Scanner scanner;

    public Inputter() {
        this.scanner = new Scanner(System.in);
    }

    public String getString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }


    public int getInt(String message) {
        while (true) {
            String temp = getString(message);
            if (Acceptable.isValid(temp, Acceptable.INTEGER_VALID)) {
                return Integer.parseInt(temp);
            }
            System.out.println("Please enter a valid integer! Re-enter ...");
        }
    }


    public double getDouble(String message) {
        while (true) {
            String temp = getString(message);
            if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
                return Double.parseDouble(temp);
            }
            System.out.println("Please enter a valid number! Re-enter ...");
        }
    }


    public String inputLoop(String message, String pattern) {
        String result;
        boolean running;
        do {
            result = getString(message);
            running = !Acceptable.isValid(result, pattern);
            if (running) {
                System.out.println("Data is invalid! Re-enter ...");
            }
        } while (running);
        return result.trim();
    }

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
            } else if (!Acceptable.isValid(name.trim(), Acceptable.NAME_VALID)) {
                System.out.println("Invalid name! Keeping old value: " + oldStudent.getName());
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
            } else if (!Acceptable.isValid(phone.trim(), Acceptable.PHONE_VALID)) {
                System.out.println("Invalid phone! Keeping old value: " + oldStudent.getPhone());
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
            } else if (!Acceptable.isValid(email.trim(), Acceptable.EMAIL_VALID)) {
                System.out.println("Invalid email! Keeping old value: " + oldStudent.getEmail());
                email = oldStudent.getEmail();
            }
        } else {
            email = inputLoop("Enter Email: ", Acceptable.EMAIL_VALID);
        }

        String mountainCode;
        if (isUpdate && oldStudent != null) {
            String input = getString("Enter Mountain Code (Enter to keep '" + oldStudent.getMountainCode() + "'): ");
            mountainCode = input.trim().isEmpty() ? oldStudent.getMountainCode() : MountainManager.normalize(input);
        } else {
            mountainCode = MountainManager.normalize(getString("Enter Mountain Code (e.g. MT01): "));
        }

        // --- Tuition Fee ---
        double tuitionFee = 6_000_000;
        if (Acceptable.isValid(phone, Acceptable.VIETTEL_VALID)
                || Acceptable.isValid(phone, Acceptable.VNPT_VALID)) {
            tuitionFee *= 0.65;
            System.out.println(">> Viettel/VNPT discount applied! Fee: " + String.format("%,.0f", tuitionFee));
        }

        return new Student(id, name, phone, email, mountainCode, tuitionFee);
    }


    public Student enterStudentInfo(boolean isUpdate) {
        return enterStudentInfo(isUpdate, null);
    }
}
