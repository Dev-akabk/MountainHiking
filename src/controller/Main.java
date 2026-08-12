package controller;

/**
 * Điểm khởi chạy duy nhất (Entry Point) của chương trình.
 * Chỉ có trách nhiệm: khởi tạo Controller → gọi run().
 * Mọi logic nghiệp vụ được điều phối bởi Controller.
 *
 * @author Legion
 */
public class Main {

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.run();
    }
}
