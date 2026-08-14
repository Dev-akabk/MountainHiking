package model.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.entity.Mountain;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MountainManager {

    private final String pathFile;
    private List<Mountain> list = new ArrayList<>();

    public MountainManager() {
        this.pathFile = "data/MountainList.csv";
    }

    public Mountain get(String mountainCode) {
        for (Mountain m : list) {
            if (m.getMountainCode().equalsIgnoreCase(mountainCode)) {
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
        return new Mountain(parts[0].trim(), parts[1].trim(), parts[2].trim(), description);
    }

    public void readFromFile() {
        File file = new File(this.pathFile);
        if (!file.exists()) {
            System.out.println("MountainList.csv file not found!");
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
            Logger.getLogger(MountainManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MountainManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Mountain> getList() {
        return list;
    }
}
