import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        bai1();
        bai2();
        bai3();
    }

    public static void bai1() {
        System.out.println("Bài 1: In các số nguyên từ 1 đến 10");

        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();
    }

    public static void bai2() {
        System.out.println("Bài 2: Tính tổng từ 1 đến 100");
        int tong = 0;

        for (int i = 1; i <= 100; i++) {
            tong += i;
        }
        System.out.println("Tổng 1 + 2 + 3 + ... + 100 = " + tong);
        System.out.println();
    }

    public static void bai3() {
        System.out.println("Bài 3: In các số chẵn từ 1 đến 20");

        for (int i = 2; i <= 20; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
