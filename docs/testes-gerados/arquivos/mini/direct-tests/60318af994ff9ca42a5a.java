/*
 * @test
 * @summary Test java.util.Scanner.hasNextShort(int radix) method behavior
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextShortValidDecimal();
        testHasNextShortValidHex();
        testHasNextShortInvalidNumber();
        testHasNextShortOutOfRange();
        testHasNextShortEmptyInput();
    }

    private static void testHasNextShortValidDecimal() {
        String input = "123";
        Scanner sc = new Scanner(input);
        boolean hasShort = sc.hasNextShort(10);
        if (!hasShort) {
            throw new RuntimeException("Expected hasNextShort(10) to be true for '123'");
        }
        short val = sc.nextShort(10);
        if (val != 123) {
            throw new RuntimeException("Expected nextShort(10) to return 123 but got " + val);
        }
        sc.close();
    }

    private static void testHasNextShortValidHex() {
        String input = "7B"; // 123 decimal in hex
        Scanner sc = new Scanner(input);
        boolean hasShort = sc.hasNextShort(16);
        if (!hasShort) {
            throw new RuntimeException("Expected hasNextShort(16) to be true for '7B'");
        }
        short val = sc.nextShort(16);
        if (val != 123) {
            throw new RuntimeException("Expected nextShort(16) to return 123 but got " + val);
        }
        sc.close();
    }

    private static void testHasNextShortInvalidNumber() {
        String input = "abc";
        Scanner sc = new Scanner(input);
        boolean hasShort = sc.hasNextShort(10);
        if (hasShort) {
            throw new RuntimeException("Expected hasNextShort(10) to be false for 'abc'");
        }
        sc.close();
    }

    private static void testHasNextShortOutOfRange() {
        // Number larger than Short.MAX_VALUE
        String input = "40000";
        Scanner sc = new Scanner(input);
        boolean hasShort = sc.hasNextShort(10);
        if (hasShort) {
            throw new RuntimeException("Expected hasNextShort(10) to be false for out-of-range '40000'");
        }
        sc.close();
    }

    private static void testHasNextShortEmptyInput() {
        String input = "";
        Scanner sc = new Scanner(input);
        boolean hasShort = sc.hasNextShort(10);
        if (hasShort) {
            throw new RuntimeException("Expected hasNextShort(10) to be false for empty input");
        }
        sc.close();
    }
}
