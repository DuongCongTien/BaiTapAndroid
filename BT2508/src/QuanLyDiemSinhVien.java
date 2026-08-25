import java.util.Scanner;

public class QuanLyDiemSinhVien {

    // Nhập và kiểm tra điểm hợp lệ
    public static double nhapDiem(Scanner scanner, String tenMon) {
        double diem;

        while (true) {
            System.out.print("Nhập điểm " + tenMon + " (0–10): ");

            if (scanner.hasNextDouble()) {
                diem = scanner.nextDouble();

                if (diem >= 0 && diem <= 10) {
                    return diem;
                }

                System.out.println("Điểm phải nằm trong khoảng từ 0 đến 10.");
            } else {
                System.out.println("Vui lòng nhập một số hợp lệ.");
                scanner.next(); // Xóa dữ liệu nhập sai
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập họ và tên: ");
        String hoTen = scanner.nextLine().trim();

        System.out.print("Nhập mã sinh viên: ");
        String maSinhVien = scanner.nextLine().trim();

        double diemToan = nhapDiem(scanner, "Toán");
        double diemVan = nhapDiem(scanner, "Văn");
        double diemAnh = nhapDiem(scanner, "Anh");

        double diemTrungBinh = (diemToan + diemVan + diemAnh) / 3;

        double diemCaoNhat = diemToan;
        String monCaoNhat = "Toán";

        if (diemVan > diemCaoNhat) {
            diemCaoNhat = diemVan;
            monCaoNhat = "Văn";
        }

        if (diemAnh > diemCaoNhat) {
            diemCaoNhat = diemAnh;
            monCaoNhat = "Anh";
        }

        System.out.println("\n========== KẾT QUẢ ==========");
        System.out.println("Họ và tên       : " + hoTen);
        System.out.println("Mã sinh viên    : " + maSinhVien);
        System.out.printf("Điểm Toán       : %.2f%n", diemToan);
        System.out.printf("Điểm Văn        : %.2f%n", diemVan);
        System.out.printf("Điểm Anh        : %.2f%n", diemAnh);
        System.out.printf("Điểm trung bình : %.2f%n", diemTrungBinh);
        System.out.printf("Điểm cao nhất   : %.2f (%s)%n",
                diemCaoNhat, monCaoNhat);

        if (diemTrungBinh > 5) {
            System.out.println("Kết luận        : Điểm trung bình trên 5.");
        } else {
            System.out.println("Kết luận        : Điểm trung bình không trên 5.");
        }

        scanner.close();
    }
}