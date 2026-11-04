/*
 * @test
 * @summary Test java.util.Scanner.hasNextInt(int radix) method behavior
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextIntValidDecimal();
        testHasNextIntValidHex();
        testHasNextIntInvalidNumberFormat();
        testHasNextIntEmptyInput();
        testHasNextIntRadixOutOfRange();
    }

    private static void testHasNextIntValidDecimal() {
        String input = "123 456";
        Scanner sc = new Scanner(input);
        boolean hasNext = sc.hasNextInt(10);
        if (!hasNext) {
            throw new RuntimeException("Expected hasNextInt(10) to be true for '123'");
        }
        int val = sc.nextInt(10);
        if (val != 123) {
            throw new RuntimeException("Expected nextInt(10) to return 123 but got " + val);
        }
        sc.close();
    }

    private static void testHasNextIntValidHex() {
        String input = "7B 1A"; // 7B hex = 123 decimal
        Scanner sc = new Scanner(input);
        boolean hasNext = sc.hasNextInt(16);
        if (!hasNext) {
            throw new RuntimeException("Expected hasNextInt(16) to be true for '7B'");
        }
        int val = sc.nextInt(16);
        if (val != 123) {
            throw new RuntimeException("Expected nextInt(16) to return 123 but got " + val);
        }
        sc.close();
    }

    private static void testHasNextIntInvalidNumberFormat() {
        String input = "999999999999999999999999999999999999"; // too large for int
        Scanner sc = new Scanner(input);
        boolean hasNext = sc.hasNextInt(10);
        if (hasNext) {
            throw new RuntimeException("Expected hasNextInt(10) to be false for too large number");
        }
        sc.close();
    }

    private static void testHasNextIntEmptyInput() {
        String input = "";
        Scanner sc = new Scanner(input);
        boolean hasNext = sc.hasNextInt(10);
        if (hasNext) {
            throw new RuntimeException("Expected hasNextInt(10) to be false for empty input");
        }
        sc.close();
    }

    private static void testHasNextIntRadixOutOfRange() {
        String input = "123";
        Scanner sc = new Scanner(input);
        try {
            sc.hasNextInt(1); // radix too low
            throw new RuntimeException("Expected IllegalArgumentException for radix=1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            sc.hasNextInt(37); // radix too high
            throw new RuntimeException("Expected IllegalArgumentException for radix=37");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        sc.close();
    }
}
