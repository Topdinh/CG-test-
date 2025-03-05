package baiKiemTracuoiKyM2;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagementSystem {

    public static class NotFoundStudentException extends Exception {
        public NotFoundStudentException(String message) {
            super(message);
        }
    }

    public static abstract class Person {
        protected String id;
        protected String name;
        protected String dateOfBirth;
        protected String gender;
        protected String phoneNumber;

        public Person() {}

        public Person(String id, String name, String dateOfBirth, String gender, String phoneNumber) {
            this.id = id;
            this.name = name;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.phoneNumber = phoneNumber;
        }

        public abstract void inputData(Scanner scanner);

        public void displayData() {
            System.out.println("ID: " + id);
            System.out.println("Tên: " + name);
            System.out.println("Ngày sinh: " + dateOfBirth);
            System.out.println("Giới tính: " + gender);
            System.out.println("Số điện thoại: " + phoneNumber);
        }

        public String toCSV() {
            return id + "," + name + "," + dateOfBirth + "," + gender + "," + phoneNumber;
        }
    }

    public static class Student extends Person {
        private String classId;

        public Student() {
            super();
        }

        public Student(String id, String name, String dateOfBirth, String gender, String phoneNumber, String classId) {
            super(id, name, dateOfBirth, gender, phoneNumber);
            this.classId = classId;
        }

        @Override
        public void inputData(Scanner scanner) {
            // Không sử dụng trong quá trình thêm mới vì ta xử lý riêng từng trường
        }

        public String getStudentId() {
            return id;
        }

        public String getStudentName() {
            return name;
        }

        public String getBirthDate() {
            return dateOfBirth;
        }

        public String getGender() {
            return gender;
        }

        public String getClassId() {
            return classId;
        }

        @Override
        public String toCSV() {
            return id + "," + name + "," + dateOfBirth + "," + gender + "," + phoneNumber + "," + classId;
        }
    }

    public static class Teacher extends Person {
        public Teacher() {
            super();
        }

        public Teacher(String id, String name, String dateOfBirth, String gender, String phoneNumber) {
            super(id, name, dateOfBirth, gender, phoneNumber);
        }

        @Override
        public void inputData(Scanner scanner) {
            System.out.print("Nhập mã giáo viên: ");
            this.id = scanner.nextLine();
            System.out.print("Nhập tên giáo viên: ");
            this.name = scanner.nextLine();
            System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
            this.dateOfBirth = scanner.nextLine();
            System.out.print("Nhập giới tính: ");
            this.gender = scanner.nextLine();
            System.out.print("Nhập số điện thoại: ");
            this.phoneNumber = scanner.nextLine();
        }

        @Override
        public void displayData() {
            System.out.println("----- Giáo viên -----");
            super.displayData();
        }

        @Override
        public String toCSV() {
            return id + "," + name + "," + dateOfBirth + "," + gender + "," + phoneNumber;
        }
    }

    public static class ClassRoom {
        private String classId;
        private String className;
        private String teacherId;

        public ClassRoom() {}

        public ClassRoom(String classId, String className, String teacherId) {
            this.classId = classId;
            this.className = className;
            this.teacherId = teacherId;
        }

        public String getClassId() {
            return classId;
        }

        public String getClassName() {
            return className;
        }

        public void inputData(Scanner scanner) {
            System.out.print("Nhập mã lớp học: ");
            this.classId = scanner.nextLine();
            System.out.print("Nhập tên lớp học: ");
            this.className = scanner.nextLine();
            System.out.print("Nhập mã giáo viên: ");
            this.teacherId = scanner.nextLine();
        }

        public void displayData() {
            System.out.println("----- Lớp học -----");
            System.out.println("Mã lớp: " + classId);
            System.out.println("Tên lớp: " + className);
            System.out.println("Mã giáo viên: " + teacherId);
        }

        public String toCSV() {
            return classId + "," + className + "," + teacherId;
        }
    }

    public static class StudentManager {
        private List<Student> studentList;
        private final String studentFilePath = "data/students.csv";

        public StudentManager() {
            studentList = new ArrayList<>();
            loadStudents();
        }

        public void addStudent(Scanner scanner) {
            System.out.println("=== Thêm mới sinh viên ===");
            int newId = generateNextStudentId();
            String name, dob, gender, phone, classId;

            while (true) {
                System.out.print("Nhập tên sinh viên: ");
                name = scanner.nextLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Tên sinh viên không được để trống.");
                } else if (name.length() < 4 || name.length() > 50) {
                    System.out.println("Tên sinh viên phải từ 4 đến 50 ký tự.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
                dob = scanner.nextLine().trim();
                if (dob.isEmpty()) {
                    System.out.println("Ngày sinh không được để trống.");
                } else if (!isValidDate(dob)) {
                    System.out.println("Ngày sinh không đúng định dạng dd/MM/yyyy.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Nhập giới tính: ");
                gender = scanner.nextLine().trim();
                if (gender.isEmpty()) {
                    System.out.println("Giới tính không được để trống.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Nhập số điện thoại: ");
                phone = scanner.nextLine().trim();
                if (phone.isEmpty()) {
                    System.out.println("Số điện thoại không được để trống.");
                } else if (!isValidPhone(phone)) {
                    System.out.println("Số điện thoại phải là 10 số và bắt đầu bằng 090 hoặc 091.");
                } else if (!isUniquePhone(phone)) {
                    System.out.println("Số điện thoại đã tồn tại. Vui lòng nhập số khác.");
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Nhập mã lớp học: ");
                classId = scanner.nextLine().trim();
                if (classId.isEmpty()) {
                    System.out.println("Mã lớp học không được để trống.");
                } else if (!isValidClassId(classId)) {
                    System.out.println("Mã lớp học không tồn tại trong file data/batchs.csv.");
                } else {
                    break;
                }
            }

            Student student = new Student(String.valueOf(newId), name, dob, gender, phone, classId);
            studentList.add(student);
            saveStudents();
            System.out.println("Thêm mới sinh viên thành công với mã: " + newId);
        }

        public void removeStudent(String studentId, Scanner scanner) throws NotFoundStudentException {
            Student toRemove = null;
            for (Student s : studentList) {
                if (s.id.equals(studentId)) {
                    toRemove = s;
                    break;
                }
            }
            if (toRemove == null) {
                throw new NotFoundStudentException("Sinh viên không tồn tại.");
            }
            System.out.print("Bạn có chắc chắn muốn xóa sinh viên " + toRemove.id + " không (Yes/No)? ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("Yes")) {
                studentList.remove(toRemove);
                saveStudents();
                System.out.println("Xóa thành công!");
                displayStudents();
            } else if (confirm.equalsIgnoreCase("No")) {
                System.out.println("Không xóa. Quay lại menu chính.");
            } else {
                System.out.println("Lựa chọn không hợp lệ. Quay lại menu chính.");
            }
        }

        public void displayStudents() {
            if (studentList.isEmpty()) {
                System.out.println("Danh sách sinh viên trống!");
                return;
            }
            Map<String, String> classNames = loadClassNames();
            for (Student s : studentList) {
                System.out.println("----- Sinh viên -----");
                System.out.println("Mã: " + s.getStudentId());
                System.out.println("Tên: " + s.getStudentName());
                System.out.println("Ngày sinh: " + s.getBirthDate());
                System.out.println("Giới tính: " + s.getGender());
                String className = classNames.getOrDefault(s.getClassId(), "Unknown");
                System.out.println("Tên lớp: " + className);
                System.out.println("---------------------");
            }
        }

        public void searchStudents(String keyword) {
            boolean foundAny = false;
            Map<String, String> classNames = loadClassNames();
            String lowerKeyword = keyword.toLowerCase();
            for (Student s : studentList) {
                if (s.getStudentName().toLowerCase().contains(lowerKeyword)) {
                    System.out.println("----- Sinh viên -----");
                    System.out.println("Mã: " + s.getStudentId());
                    System.out.println("Tên: " + s.getStudentName());
                    System.out.println("Ngày sinh: " + s.getBirthDate());
                    System.out.println("Giới tính: " + s.getGender());
                    String className = classNames.getOrDefault(s.getClassId(), "Unknown");
                    System.out.println("Tên lớp: " + className);
                    System.out.println("---------------------");
                    foundAny = true;
                }
            }
            if (!foundAny) {
                System.out.println("Không tìm thấy sinh viên nào với từ khóa: " + keyword);
            }
        }

        private int generateNextStudentId() {
            int maxId = 0;
            for (Student s : studentList) {
                try {
                    int sid = Integer.parseInt(s.id);
                    if (sid > maxId) {
                        maxId = sid;
                    }
                } catch (NumberFormatException ex) {
                    // bỏ qua
                }
            }
            return maxId + 1;
        }

        private boolean isUniquePhone(String phone) {
            for (Student s : studentList) {
                if (s.phoneNumber.equals(phone)) {
                    return false;
                }
            }
            return true;
        }

        private boolean isValidPhone(String phone) {
            return phone.matches("^(090|091)\\d{7}$");
        }

        private boolean isValidDate(String dateStr) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(dateStr);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        private boolean isValidClassId(String classId) {
            File file = new File("data/batchs.csv");
            if (!file.exists()) {
                System.out.println("File data/batchs.csv không tồn tại.");
                return false;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] tokens = line.split(",");
                    if (tokens.length > 0 && tokens[0].trim().equals(classId)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                System.out.println("Lỗi khi đọc file batchs.csv: " + e.getMessage());
                return false;
            }
            return false;
        }

        private void loadStudents() {
            File file = new File(studentFilePath);
            if (!file.exists()) {
                try {
                    File directory = new File("data");
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    PrintWriter writer = new PrintWriter(new FileWriter(file));
                    writer.println("MaSV,TenSV,NgaySinh,GioiTinh,SoDienThoai,MaLop");
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Lỗi khi tạo file: " + e.getMessage());
                }
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        Student student = new Student(parts[0].trim(), parts[1].trim(), parts[2].trim(),
                                parts[3].trim(), parts[4].trim(), parts[5].trim());
                        studentList.add(student);
                    }
                }
            } catch (IOException e) {
                System.out.println("Lỗi khi đọc file: " + e.getMessage());
            }
        }

        private void saveStudents() {
            try (PrintWriter writer = new PrintWriter(new FileWriter(studentFilePath))) {
                writer.println("MaSV,TenSV,NgaySinh,GioiTinh,SoDienThoai,MaLop");
                for (Student s : studentList) {
                    writer.println(s.toCSV());
                }
            } catch (IOException e) {
                System.out.println("Lỗi khi ghi file: " + e.getMessage());
            }
        }

        private Map<String, String> loadClassNames() {
            Map<String, String> classNames = new HashMap<>();
            File file = new File("data/batchs.csv");
            if (!file.exists()) {
                System.out.println("File data/batchs.csv không tồn tại. Không thể hiển thị tên lớp.");
                return classNames;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] tokens = line.split(",");
                    if (tokens.length >= 2) {
                        String batchId = tokens[0].trim();
                        String batchName = tokens[1].trim();
                        classNames.put(batchId, batchName);
                    }
                }
            } catch (IOException e) {
                System.out.println("Lỗi khi đọc file batchs.csv: " + e.getMessage());
            }
            return classNames;
        }
    }

    public static class TeacherManager {
        private List<Teacher> teacherList;
        private final String teacherFilePath = "data/teachers.csv";

        public TeacherManager() {
            teacherList = new ArrayList<>();
            loadTeachers();
        }

        private void loadTeachers() {
            File file = new File(teacherFilePath);
            if (!file.exists()) {
                System.out.println("File " + teacherFilePath + " không tồn tại.");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        Teacher teacher = new Teacher(parts[0].trim(), parts[1].trim(), parts[2].trim(),
                                parts[3].trim(), parts[4].trim());
                        teacherList.add(teacher);
                    }
                }
            } catch (IOException e) {
                System.out.println("Lỗi khi đọc file teachers.csv: " + e.getMessage());
            }
        }

        public void displayTeacherById(String teacherId) {
            Teacher foundTeacher = null;
            for (Teacher t : teacherList) {
                if (t.id.equals(teacherId)) {
                    foundTeacher = t;
                    break;
                }
            }
            if (foundTeacher != null) {
                System.out.println("----- Thông tin Giáo viên -----");
                System.out.println("Mã: " + foundTeacher.id);
                System.out.println("Tên: " + foundTeacher.name);
                System.out.println("Ngày sinh: " + foundTeacher.dateOfBirth);
                System.out.println("Giới tính: " + foundTeacher.gender);
                System.out.println("Điện thoại: " + foundTeacher.phoneNumber);
            } else {
                System.out.println("Giáo viên không tồn tại với mã: " + teacherId);
            }
        }
    }
}

