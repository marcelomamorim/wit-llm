/*
 * @test
 * @summary Verifies Scanner.hasNextByte(int radix) for valid, overflowing, non-numeric, empty and radix-specific inputs
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        check(new Scanner("12").hasNextByte(10), "decimal 12 should be a byte");

        Scanner neg = new Scanner("-128");
        check(neg.hasNextByte(10), "-128 should be a byte");
        check(neg.nextByte(10) == -128, "nextByte should return -128");

        check(!new Scanner("128").hasNextByte(10), "128 must overflow byte");
        check(!new Scanner("300").hasNextByte(10), "300 must overflow byte");
        check(!new Scanner("abc").hasNextByte(10), "non-numeric token must not be a byte");
        check(!new Scanner("").hasNextByte(10), "empty input must not be a byte");

        check(new Scanner("7f").hasNextByte(16), "hex 7f should be a byte");
        check(!new Scanner("80").hasNextByte(16), "hex 80 (128) must overflow byte");

        Scanner bin = new Scanner("101");
        check(bin.hasNextByte(2), "binary 101 should be a byte");
        check(bin.nextByte(2) == 5, "binary 101 should equal 5");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
