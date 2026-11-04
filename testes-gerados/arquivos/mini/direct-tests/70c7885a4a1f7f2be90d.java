/*
 * @test
 * @summary Test java.util.Scanner.hasNextByte() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.ByteArrayInputStream;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextByteWithValidByte();
        testHasNextByteWithInvalidByte();
        testHasNextByteWithEmptyInput();
        testHasNextByteWithMultipleTokens();
    }

    private static void testHasNextByteWithValidByte() {
        Scanner scanner = new Scanner("127");
        boolean result = scanner.hasNextByte();
        if (!result) {
            throw new AssertionError("Expected hasNextByte() to return true for valid byte input '127'");
        }
        scanner.close();
    }

    private static void testHasNextByteWithInvalidByte() {
        Scanner scanner = new Scanner("128"); // 128 is out of byte range
        boolean result = scanner.hasNextByte();
        if (result) {
            throw new AssertionError("Expected hasNextByte() to return false for out-of-range byte input '128'");
        }
        scanner.close();
    }

    private static void testHasNextByteWithEmptyInput() {
        Scanner scanner = new Scanner("");
        boolean result = scanner.hasNextByte();
        if (result) {
            throw new AssertionError("Expected hasNextByte() to return false for empty input");
        }
        scanner.close();
    }

    private static void testHasNextByteWithMultipleTokens() {
        Scanner scanner = new Scanner("100 200 -50");
        // First token 100 is valid byte
        if (!scanner.hasNextByte()) {
            throw new AssertionError("Expected hasNextByte() to return true for first token '100'");
        }
        scanner.nextByte(); // consume 100

        // Second token 200 is out of byte range
        if (scanner.hasNextByte()) {
            throw new AssertionError("Expected hasNextByte() to return false for second token '200'");
        }
        scanner.next(); // consume 200 as string

        // Third token -50 is valid byte
        if (!scanner.hasNextByte()) {
            throw new AssertionError("Expected hasNextByte() to return true for third token '-50'");
        }
        scanner.close();
    }
}
