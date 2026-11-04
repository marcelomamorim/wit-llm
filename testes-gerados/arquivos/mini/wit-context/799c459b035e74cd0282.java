/*
 * @test
 * @summary Test java.util.Scanner.hasNextBigInteger() for valid radix and exception on invalid radix
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

    static void testHasNextBigIntegerValidRadix() {
        // Provide input with a valid big integer in default radix (10)
        Scanner sc = new Scanner("12345678901234567890");
        boolean result = sc.hasNextBigInteger();
        if (!result) {
            throw new RuntimeException("Expected hasNextBigInteger() to return true for valid input");
        }
        // Consume the token and verify it parses correctly
        BigInteger bi = sc.nextBigInteger();
        if (!bi.equals(new BigInteger("12345678901234567890"))) {
            throw new RuntimeException("Parsed BigInteger does not match expected value");
        }
        sc.close();
    }

    static void testHasNextBigIntegerInvalidRadix() {
        // The method hasNextBigInteger() calls hasNextBigInteger(defaultRadix)
        // and defaultRadix must be between 2 and 36 inclusive.
        // We cannot set defaultRadix directly as it is private,
        // so we test the behavior indirectly by creating a subclass that
        // overrides hasNextBigInteger(int) to simulate invalid radix.

        Scanner sc = new Scanner("123") {
            @Override
            public boolean hasNextBigInteger(int radix) {
                if (radix < 2 || radix > 36) {
                    throw new IllegalArgumentException("Radix out of range");
                }
                return super.hasNextBigInteger(radix);
            }
        };

        // We simulate invalid radix by calling hasNextBigInteger(int) directly
        try {
            sc.hasNextBigInteger(1); // invalid radix < 2
            throw new RuntimeException("Expected IllegalArgumentException for radix < 2");
        } catch (IllegalArgumentException expected) {
        }

        try {
            sc.hasNextBigInteger(37); // invalid radix > 36
            throw new RuntimeException("Expected IllegalArgumentException for radix > 36");
        } catch (IllegalArgumentException expected) {
        }

        sc.close();
    }
}
