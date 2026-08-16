package model.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.entity.Mountain;
import java.util.ArrayList;
import java.util.List;
import tools.LogWritter;


public class MountainManager {

    private final String pathFile;
    private List<Mountain> list = new ArrayList<>();

    public MountainManager() {
        this.pathFile = "src/data/MountainList.csv";
    }

    public static String normalize(String code) {
        if (code == null) return "";
        String trimmed = code.trim().toUpperCase();
        String numeric = trimmed.startsWith("MT") ? trimmed.substring(2) : trimmed;
        try {
            int num = Integer.parseInt(numeric);
            return String.format("MT%02d", num);
        } catch (NumberFormatException e) {
            return trimmed; 
        }
    }

    public Mountain get(String mountainCode) {
        String normalizedInput = normalize(mountainCode);
        for (Mountain m : list) {
            if (normalize(m.getMountainCode()).equalsIgnoreCase(normalizedInput)) {
                return m;
            }
        }
        return null;
    }


    public boolean isValidMountainCode(String mountainCode) {
        return get(mountainCode) != null;
    }

    public Mountain dataToObject(String text) {
        String[] parts = text.split(",", -1);
        if (parts.length < 3) {
            return null;
        }
        String description = (parts.length >= 4) ? parts[3].trim() : "";
        String mountainCode = normalize(parts[0].trim());
        return new Mountain(mountainCode, parts[1].trim(), parts[2].trim(), description);
    }

    public void readFromFile() {
        File file = new File(this.pathFile);
        if (!file.exists()) {
            String msg = "MountainList.csv file not found!";
            System.err.println(msg);
            LogWritter.writeLog(msg);
            return;
        }

        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String temp;
            boolean isFirstLine = true;
            while ((temp = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                if (temp.trim().isEmpty()) {
                    continue;
                }
                Mountain m = dataToObject(temp);
                if (m != null) {
                    list.add(m);
                }
            }
        } catch (FileNotFoundException ex) {
            String msg = "MountainList.csv not found: " + ex.getMessage();
            System.err.println(msg);
            LogWritter.writeLog(msg);
        } catch (IOException ex) {
            String msg = "Error reading MountainList.csv: " + ex.getMessage();
            System.err.println(msg);
            LogWritter.writeLog(msg);
        }
    }

    public List<Mountain> getList() {
        return list;
    }
}
