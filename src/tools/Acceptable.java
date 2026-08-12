package tools;

/**
 * Utility Class chứa các hằng số Regex dùng cho validation dữ liệu đầu vào
 * và phương thức kiểm tra tĩnh.
 *
 * Thiết kế: final class + private constructor → không ai khởi tạo hoặc kế thừa được.
 * Trước đây là interface (Constant Interface Antipattern) — vi phạm mục đích
 * thiết kế của interface (interface dùng để định nghĩa hành vi, không phải chứa hằng số).
 * Tham khảo: Effective Java, Item 22.
 */
public final class Acceptable {

    // Chặn khởi tạo — đây là Utility Class
    private Acceptable() {
    }

    public static final String STUDENT_ID_VALID = "^[CcDdHhSsQq][Ee]\\d{6}$";
    public static final String NAME_VALID = "^.{2,20}$";
    public static final String DOUBLE_VALID = "^-?\\d+(\\.\\d+)?$";
    public static final String INTEGER_VALID = "^\\d+$";
    public static final String PHONE_VALID = "^[0]\\d{9}$";
    public static final String VIETTEL_VALID = "^(086|096|097|098|032|033|034|035|036|037|038|039)\\d{7}$";
    public static final String VNPT_VALID = "^(081|082|083|084|085|088|091|094)\\d{7}$";
    public static final String EMAIL_VALID = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /**
     * Kiểm tra dữ liệu có khớp pattern Regex hay không.
     * Thêm null-check để tránh NullPointerException khi data = null.
     *
     * @param data    chuỗi dữ liệu cần kiểm tra
     * @param pattern biểu thức Regex
     * @return true nếu data khớp pattern, false nếu không khớp hoặc data là null
     */
    public static boolean isValid(String data, String pattern) {
        return data != null && data.matches(pattern);
    }
}
