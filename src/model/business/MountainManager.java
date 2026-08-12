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

/**
 * Quản lý danh sách đỉnh núi, đọc từ file CSV.
 */
public class MountainManager {

    private final String pathFile;
    private List<Mountain> list = new ArrayList<>();

    public MountainManager() {
        this.pathFile = "data/MountainList.csv";
    }

    /**
     * Tìm Mountain theo mã (case-insensitive).
     */
    public Mountain get(String mountainCode) {
        for (Mountain m : list) {
            if (m.getMountainCode().equalsIgnoreCase(mountainCode)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Kiểm tra mã núi có tồn tại trong danh sách hay không.
     */
    public boolean isValidMountainCode(String mountainCode) {
        return get(mountainCode) != null;
    }

    /**
     * Chuyển đổi 1 dòng CSV thành đối tượng Mountain.
     *
     * FIX 1: Dùng split(",", -1) thay vì split(",").
     *   - split(",")   → loại bỏ chuỗi rỗng ở cuối mảng
     *     Ví dụ: "5, Da Do Mountain, Ninh Thuan, ".split(",") → chỉ 3 phần tử
     *   - split(",", -1) → giữ nguyên tất cả phần tử, kể cả rỗng
     *     Ví dụ: "5, Da Do Mountain, Ninh Thuan, ".split(",", -1) → 4 phần tử
     *
     * FIX 2: Description là trường tùy chọn — nhiều núi trong CSV không có.
     *   Code cũ yêu cầu >= 4 cột → bỏ qua hoàn toàn các dòng thiếu description.
     *   Code mới chỉ cần >= 3 cột, description mặc định rỗng nếu không có.
     */
    public Mountain dataToObject(String text) {
        String[] parts = text.split(",", -1);
        if (parts.length < 3) {
            return null;
        }
        String description = (parts.length >= 4) ? parts[3].trim() : "";
        return new Mountain(parts[0].trim(), parts[1].trim(), parts[2].trim(), description);
    }

    /**
     * Đọc danh sách núi từ file CSV.
     *
     * FIX: Skip dòng header (dòng đầu tiên chứa "Code, Mountain, Province, Description").
     *   Code cũ đọc luôn header vào list → tạo ra Mountain với mountainCode = "Code".
     *   Code mới: bỏ qua dòng đầu tiên + bỏ qua dòng trống.
     */
    public void readFromFile() {
        File f = new File(this.pathFile);
        if (!f.exists()) {
            System.out.println("MountainList.csv file not found!");
            return;
        }

        try (FileReader fr = new FileReader(f);
             BufferedReader br = new BufferedReader(fr)) {

            String temp;
            boolean isFirstLine = true;
            while ((temp = br.readLine()) != null) {
                // Skip dòng header CSV
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                // Skip dòng trống
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

    /**
     * Khai báo List<> (interface) thay vì ArrayList<> (implementation).
     */
    public List<Mountain> getList() {
        return list;
    }
}
