/*
 * @test
 * @summary Test hasNextByte() method of java.util.Scanner
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class ScannerWitupTest {
    public static void main(String[] args) {
        testHasNextByteWithValidInput();
        testHasNextByteWithInvalidInput();
        testHasNextByteWithEmptyInput();
    }

    private static void testHasNextByteWithValidInput() {
        String input = "127 0 -128"; // valid byte values
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        boolean result1 = scanner.hasNextByte();
        scanner.next(); // consume token
        boolean result2 = scanner.hasNextByte();
        scanner.next(); // consume token
        boolean result3 = scanner.hasNextByte();
        scanner.close();
        assert result1 : "Expected hasNextByte() to be true for first token";
        assert result2 : "Expected hasNextByte() to be true for second token";
        assert result3 : "Expected hasNextByte() to be true for third token";
    }

    private static void testHasNextByteWithInvalidInput() {
        String input = "abc def"; // non-byte tokens
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        boolean result1 = scanner.hasNextByte();
        scanner.close();
        assert !result1 : "Expected hasNextByte() to be false for non-byte tokens";
    }

    private static void testHasNextByteWithEmptyInput() {
        String input = ""; // empty input
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        boolean result = scanner.hasNextByte();
        scanner.close();
        assert !result : "Expected hasNextByte() to be false for empty input";
    }
}
