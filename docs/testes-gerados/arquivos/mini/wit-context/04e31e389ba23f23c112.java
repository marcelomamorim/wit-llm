/*
 * @test
 * @summary Test java.util.Scanner.hasNextShort() for valid radix range and behavior
 * @run main ScannerWitupTest
 */

import java.util.Scanner;
import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        testHasNextShortValidRadix();
        testHasNextShortDefaultRadixBehavior();
    }

    static void testHasNextShortValidRadix() {
        // The defaultRadix is private, so we test indirectly by creating a Scanner
        // with input that can be parsed as short in base 10 (default radix)
        Scanner sc = new Scanner("123");
        boolean result = sc.hasNextShort();
        if (!result) {
            throw new AssertionError("Expected hasNextShort() to return true for input '123'");
        }
        sc.close();
    }

    static void testHasNextShortDefaultRadixBehavior() {
        // Test that hasNextShort() returns false for input that is not a valid short
        Scanner sc = new Scanner("notANumber");
        boolean result = sc.hasNextShort();
        if (result) {
            throw new AssertionError("Expected hasNextShort() to return false for input 'notANumber'");
        }
        sc.close();
    }
}
