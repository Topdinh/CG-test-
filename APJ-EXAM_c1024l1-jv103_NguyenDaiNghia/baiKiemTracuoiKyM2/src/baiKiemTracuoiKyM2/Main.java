package baiKiemTracuoiKyM2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ManagementSystem.StudentManager studentManager = new ManagementSystem.StudentManager();
        ManagementSystem.TeacherManager teacherManager = new ManagementSystem.TeacherManager();
        while (true) {
            System.out.println("\n=== QUẢN LÝ SINH VIÊN VÀ GIÁO VIÊN ===");
            System.out.println("1. Thêm mới sinh viên");
            System.out.println("2. Xóa sinh viên");
            System.out.println("3. Xem danh sách sinh viên");
            System.out.println("4. Tìm kiếm sinh viên");
            System.out.println("5. Xem thông tin giáo viên theo mã giáo viên");
            System.out.println("6. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    studentManager.addStudent(scanner);
                    break;
                case "2":
                    System.out.print("Nhập mã sinh viên cần xóa: ");
                    String studentId = scanner.nextLine();
                    try {
                        studentManager.removeStudent(studentId, scanner);
                    } catch (ManagementSystem.NotFoundStudentException ex) {
                        System.out.println(ex.getMessage());
                        System.out.println("Nhấn Enter để quay lại menu chính.");
                        scanner.nextLine();
                    }
                    break;
                case "3":
                    studentManager.displayStudents();
                    break;
                case "4":
                    System.out.print("Nhập từ khóa tìm kiếm (tên sinh viên): ");
                    String keyword = scanner.nextLine();
                    studentManager.searchStudents(keyword);
                    break;
                case "5":
                    System.out.print("Nhập mã giáo viên: ");
                    String teacherId = scanner.nextLine();
                    teacherManager.displayTeacherById(teacherId);
                    break;
                case "6":
                    System.out.println("Thoát chương trình.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
        }
    }
}
