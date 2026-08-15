package controller;

import model.business.StatisticManager;
import model.business.MountainManager;
import model.business.StudentManager;
import model.entity.Student;
import tools.Inputter;
import view.Menu;

import java.util.List;


public class Controller {

    private final Menu menu = new Menu();
    private final Inputter inputter = new Inputter();
    private final StudentManager studentManager = new StudentManager();
    private final MountainManager mountainManager = new MountainManager();
    private final StatisticManager statisticManager = new StatisticManager();

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
                    menu.displayStudentList(studentManager.getList());
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
                    statisticManager.statisticalize(studentManager.getList(), mountainManager.getList());
                    menu.displayStatistics(statisticManager.getDataMap().values());
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


    private void handleNewRegistration() {
        Student newStudent = inputter.enterStudentInfo(false);
        boolean running = true;
        do {
            if (newStudent == null) {
                return;
            }
            if (studentManager.searchById(newStudent.getId()) != null) {
                menu.showMessage("Error: Student ID '" + newStudent.getId() + "' already exists!");
                return;
            }
            if (!mountainManager.isValidMountainCode(newStudent.getMountainCode())) {
                menu.showMessage("Error: Mountain code '" + newStudent.getMountainCode() + "' does not exist!");
                return;
            }
            running = false;
        } while (running); 
        studentManager.add(newStudent);
        menu.showMessage("Registration successful!");
    }

    private void handleUpdateRegistration() {
        String updateId = inputter.getString("Enter Student ID to update: ");
        Student existing = studentManager.searchById(updateId);
        if (existing == null) {
            menu.showMessage("Error: Student ID '" + updateId + "' not found!");
            return;
        }
        Student updatedStudent = inputter.enterStudentInfo(true, existing);
        if (!mountainManager.isValidMountainCode(updatedStudent.getMountainCode())) {
            menu.showMessage("Error: Mountain code '" + updatedStudent.getMountainCode() + "' does not exist!");
            return;
        }
        studentManager.update(updatedStudent);
        menu.showMessage("Update successful!");
    }


    private void handleDeleteRegistration() {
        String deleteId = inputter.getString("Enter Student ID to delete: ");
        if (studentManager.searchById(deleteId) == null) {
            menu.showMessage("Error: Student ID '" + deleteId + "' not found!");
            return;
        }
        studentManager.delete(deleteId);
        menu.showMessage("Deleted successfully!");
    }


    private void handleSearchByName() {
        String searchName = inputter.getString("Enter name to search: ");
        List<Student> searchResult = studentManager.searchByName(searchName);
        menu.displayStudentList(searchResult);
    }

 
    private void handleFilterByCampus() {
        String campusCode = inputter.getString("Enter Campus Code (e.g. CE, DE, HE, SE, QE): ");
        List<Student> filterResult = studentManager.filterByCampusCode(campusCode);
        menu.displayStudentList(filterResult);
    }


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
