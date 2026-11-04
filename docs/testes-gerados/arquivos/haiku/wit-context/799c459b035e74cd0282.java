/*
 * @test
 * @summary Behavioral checks for Scanner.hasNextBigInteger() using the default radix and radix changes
 * @run main ScannerWitupTest
 */

import java.math.BigInteger;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        Scanner s = new Scanner("123456789012345678901234567890");
        check(s.hasNextBigInteger());
        check(s.hasNextBigInteger());
        BigInteger v = s.nextBigInteger();
        check(v.equals(new BigInteger("123456789012345678901234567890")));
        check(!s.hasNextBigInteger());

        s = new Scanner("abc");
        check(!s.hasNextBigInteger());

        s = new Scanner("ff");
        s.setRadix(16);
        check(s.hasNextBigInteger());
        check(s.nextBigInteger().equals(BigInteger.valueOf(255)));

        s = new Scanner("102");
        s.setRadix(2);
        check(!s.hasNextBigInteger());

        s = new Scanner("42");
        boolean thrown = false;
        try {
            s.setRadix(37);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown);
        check(s.hasNextBigInteger());
    }

    static void check(boolean condition) {
        if (!condition) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
