package model.business;

import model.entity.Student;
import model.entity.StatisticalInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatisticManager {

    private final Map<String, StatisticalInfo> dataMap = new HashMap<>();

    public StatisticManager() {
    }

    public StatisticManager(List<Student> list) {
        statisticalize(list);
    }

    public final void statisticalize(List<Student> list) {
        dataMap.clear();
        for (Student student : list) {
            String code = student.getMountainCode();
            if (dataMap.containsKey(code)) {
                StatisticalInfo info = dataMap.get(code);
                info.setNumOfStudent(info.getNumOfStudent() + 1);
                info.setTotalCost(info.getTotalCost() + student.getTuitionFee());
            } else {
                dataMap.put(code, new StatisticalInfo(code, 1, student.getTuitionFee()));
            }
        }
    }

    public Map<String, StatisticalInfo> getDataMap() {
        return dataMap;
    }
}
