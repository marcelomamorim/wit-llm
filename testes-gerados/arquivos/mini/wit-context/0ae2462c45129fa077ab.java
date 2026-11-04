/*
 * @test
 * @summary Test java.util.Scanner.hasNextBigInteger(int radix) for valid and invalid radix values
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.math.BigInteger;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextBigIntegerValidRadix();
        testHasNextBigIntegerInvalidRadix();
    }

    private static void testHasNextBigIntegerValidRadix() {
        // Input string contains a valid decimal BigInteger
        String input = "12345678901234567890";
        Scanner scanner = new Scanner(new StringReader(input));
        boolean hasBigInt = scanner.hasNextBigInteger(10);
        if (!hasBigInt) {
            throw new RuntimeException("Expected hasNextBigInteger(10) to return true for valid decimal input");
        }
        scanner.close();
    }

    private static void testHasNextBigIntegerInvalidRadix() {
        // According to source, hasNextBigInteger(int radix) calls setRadix(radix) which throws IllegalArgumentException
        // if radix < 2 or radix > 36. We test that behavior.
        String input = "123";
        Scanner scanner = new Scanner(new StringReader(input));
        try {
            scanner.hasNextBigInteger(1); // radix < 2
            throw new RuntimeException("Expected IllegalArgumentException for radix=1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            scanner.hasNextBigInteger(37); // radix > 36
            throw new RuntimeException("Expected IllegalArgumentException for radix=37");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        scanner.close();
    }
}
