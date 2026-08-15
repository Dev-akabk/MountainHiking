package model.business;

import java.util.ArrayList;
import model.entity.Student;
import model.entity.StatisticalInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.entity.Mountain;

public class StatisticManager {

    private final Map<String, StatisticalInfo> dataMap = new HashMap<>();

    public StatisticManager() {
    }

    public StatisticManager(List<Student> list, List<Mountain> mountainList) {
        statisticalize(list, mountainList);
    }

    //get mountain name by code mountain of student list
    public String getMountainNameByCode(String code, List<Mountain> mountainList) {
        if (mountainList != null & !mountainList.isEmpty()) {
            for (Mountain mountain : mountainList) {
                if (mountain.getMountainCode().trim().substring(2).equalsIgnoreCase(code.trim())) {
                    return mountain.getMountainName();
                }
            }
        }
        return "Unknown";
    }
    public final void statisticalize(List<Student> list, List<Mountain> mountainList) {
        dataMap.clear();
        for (Student student : list) {
            String code = student.getMountainCode();
            
            if (dataMap.containsKey(code)) {
                StatisticalInfo info = dataMap.get(code);
                info.setNumOfStudent(info.getNumOfStudent() + 1);
                info.setTotalCost(info.getTotalCost() + student.getTuitionFee());
            } else {
                String name = getMountainNameByCode(code, mountainList);
                dataMap.put(code, new StatisticalInfo(name, code, 1, student.getTuitionFee()));
            }
        }
    }

    public Map<String, StatisticalInfo> getDataMap() {
        return dataMap;
    }
}
