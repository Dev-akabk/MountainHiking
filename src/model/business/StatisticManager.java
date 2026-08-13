package model.business;

import model.entity.Student;
import model.entity.StatisticalInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Quản lý thống kê số lượng đăng ký và tổng chi phí theo từng đỉnh núi.
 *
 * THIẾT KẾ:
 *   Code cũ: extends HashMap<String, StatisticalInfo>
 *     → Vi phạm nguyên lý "Composition over Inheritance" (Effective Java, Item 18).
 *     → StatisticManager KHÔNG PHẢI LÀ (IS-A) một HashMap.
 *     → Kế thừa HashMap làm lộ toàn bộ method nội bộ (clear, put, remove...)
 *       mà class không kiểm soát được → phá vỡ Encapsulation.
 *
 *   Code mới: dùng private Map field (HAS-A) — Composition.
 *     → StatisticManager SỞ HỮU một Map bên trong.
 *     → Chỉ lộ ra các method cần thiết (statisticalize, show).
 */
public class StatisticManager {

    private final Map<String, StatisticalInfo> dataMap = new HashMap<>();

    public StatisticManager() {
    }

    public StatisticManager(List<Student> list) {
        statisticalize(list);
    }

    /**
     * Tổng hợp thống kê từ danh sách sinh viên.
     * Với mỗi mountainCode: đếm số sinh viên + cộng dồn tuitionFee.
     * Dùng HashMap để nhóm (group) theo key = mountainCode → O(n).
     */
    public final void statisticalize(List<Student> list) {
        dataMap.clear(); // Xóa dữ liệu cũ tránh cộng dồn nếu gọi lại
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
