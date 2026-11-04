/*
 * @test
 * @summary Test java.util.Scanner.hasNextBigInteger() method behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.math.BigInteger;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextBigIntegerWithValidBigInteger();
        testHasNextBigIntegerWithInvalidInput();
        testHasNextBigIntegerWithEmptyInput();
    }

    private static void testHasNextBigIntegerWithValidBigInteger() {
        Scanner scanner = new Scanner("123456789012345678901234567890");
        boolean result = scanner.hasNextBigInteger();
        if (!result) {
            throw new AssertionError("Expected hasNextBigInteger() to return true for valid big integer input");
        }
        scanner.close();
    }

    private static void testHasNextBigIntegerWithInvalidInput() {
        Scanner scanner = new Scanner("notANumber");
        boolean result = scanner.hasNextBigInteger();
        if (result) {
            throw new AssertionError("Expected hasNextBigInteger() to return false for invalid input");
        }
        scanner.close();
    }

    private static void testHasNextBigIntegerWithEmptyInput() {
        Scanner scanner = new Scanner("");
        boolean result = scanner.hasNextBigInteger();
        if (result) {
            throw new AssertionError("Expected hasNextBigInteger() to return false for empty input");
        }
        scanner.close();
    }
}
