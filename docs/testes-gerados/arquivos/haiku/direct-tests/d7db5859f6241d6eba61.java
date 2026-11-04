/*
 * @test
 * @summary Verify Scanner.hasNextInt(int radix) honors radix, does not consume input, and returns false on integer overflow
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testMatchingRadix();
        testNonMatchingRadix();
        testDoesNotConsume();
        testEmptyInput();
        testOverflowReturnsFalse();
        testNextIntAfterHasNextInt();
    }

    static void testMatchingRadix() {
        check(new Scanner("ff").hasNextInt(16), "ff should be int in radix 16");
        check(new Scanner("777").hasNextInt(8), "777 should be int in radix 8");
        check(new Scanner("zz").hasNextInt(36), "zz should be int in radix 36");
    }

    static void testNonMatchingRadix() {
        check(!new Scanner("ff").hasNextInt(10), "ff should not be int in radix 10");
        check(!new Scanner("8").hasNextInt(8), "8 should not be int in radix 8");
        check(!new Scanner("abc").hasNextInt(10), "abc should not be int in radix 10");
    }

    static void testDoesNotConsume() {
        Scanner s = new Scanner("12");
        check(s.hasNextInt(10), "first hasNextInt should be true");
        check(s.hasNextInt(10), "second hasNextInt should still be true");
        check(s.nextInt(10) == 12, "nextInt(10) should return 12");
    }

    static void testEmptyInput() {
        check(!new Scanner("").hasNextInt(10), "empty input should not have int");
    }

    static void testOverflowReturnsFalse() {
        check(!new Scanner("99999999999").hasNextInt(10),
              "value overflowing int should return false");
    }

    static void testNextIntAfterHasNextInt() {
        Scanner s = new Scanner("101 abc");
        check(s.hasNextInt(2), "101 should be int in radix 2");
        check(s.nextInt(2) == 5, "nextInt(2) should return 5");
        check(!s.hasNextInt(2), "abc should not be int in radix 2");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
