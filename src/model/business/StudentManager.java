package model.business;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import tools.LogWritter;
import model.entity.Student;




public class StudentManager {

    private final String pathFile;
    private boolean isSaved;
    private List<Student> list = new ArrayList<>();

    public StudentManager() {
        this.pathFile = "src/data/registrations.dat";
        this.isSaved = true;
    }

    public boolean isSaved() {
        return isSaved;
    }


    
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

    
    public Student searchById(String id) {
        for (Student student : list) {
            if (student.getId().equalsIgnoreCase(id)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> searchByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student student : list) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

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
            String message = "Error saving registrations.dat: " + exception.getMessage();
            System.err.println(message);
            LogWritter.writeLog(message);
        }
    }

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
                    break;
                }
            }
        } catch (ClassNotFoundException | IOException exception) {
            String message = "Error reading registrations.dat: " + exception.getMessage();
            System.err.println(message);
            LogWritter.writeLog(message);
        }
    }

    public List<Student> getList() {
        return list;
    }
}
