/*
 * @test
 * @summary Test java.util.Scanner.hasNextLong(int radix) method behavior
 * @run main java.util.ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextLongValidDecimal();
        testHasNextLongValidHex();
        testHasNextLongInvalidNumberFormat();
        testHasNextLongEmptyInput();
        testHasNextLongRadixOutOfRange();
    }

    private static void testHasNextLongValidDecimal() {
        String input = "12345";
        Scanner scanner = new Scanner(input);
        boolean hasLong = scanner.hasNextLong(10);
        if (!hasLong) {
            throw new RuntimeException("Expected hasNextLong(10) to be true for '12345'");
        }
        long value = scanner.nextLong(10);
        if (value != 12345L) {
            throw new RuntimeException("Expected nextLong(10) to return 12345 but got " + value);
        }
        scanner.close();
    }

    private static void testHasNextLongValidHex() {
        String input = "7fff";
        Scanner scanner = new Scanner(input);
        boolean hasLong = scanner.hasNextLong(16);
        if (!hasLong) {
            throw new RuntimeException("Expected hasNextLong(16) to be true for '7fff'");
        }
        long value = scanner.nextLong(16);
        if (value != 0x7fffL) {
            throw new RuntimeException("Expected nextLong(16) to return 0x7fff but got " + value);
        }
        scanner.close();
    }

    private static void testHasNextLongInvalidNumberFormat() {
        String input = "123abc";
        Scanner scanner = new Scanner(input);
        boolean hasLong = scanner.hasNextLong(10);
        if (hasLong) {
            throw new RuntimeException("Expected hasNextLong(10) to be false for '123abc'");
        }
        scanner.close();
    }

    private static void testHasNextLongEmptyInput() {
        String input = "";
        Scanner scanner = new Scanner(input);
        boolean hasLong = scanner.hasNextLong(10);
        if (hasLong) {
            throw new RuntimeException("Expected hasNextLong(10) to be false for empty input");
        }
        scanner.close();
    }

    private static void testHasNextLongRadixOutOfRange() {
        String input = "123";
        Scanner scanner = new Scanner(input);
        try {
            // radix less than Character.MIN_RADIX (2)
            scanner.hasNextLong(1);
            throw new RuntimeException("Expected NumberFormatException for radix=1");
        } catch (NumberFormatException e) {
            // expected
        }
        try {
            // radix greater than Character.MAX_RADIX (36)
            scanner.hasNextLong(37);
            throw new RuntimeException("Expected NumberFormatException for radix=37");
        } catch (NumberFormatException e) {
            // expected
        }
        scanner.close();
    }
}
