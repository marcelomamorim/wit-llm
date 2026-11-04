/*
 * @test
 * @summary Verify Scanner.hasNextBigInteger() default-radix behavior
 * @run main ScannerWitupTest
 */

import java.math.BigInteger;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        check(new Scanner("123").hasNextBigInteger(), "positive integer token");
        check(new Scanner("-45").hasNextBigInteger(), "negative integer token");
        check(!new Scanner("abc").hasNextBigInteger(), "non-numeric token");
        check(!new Scanner("1.5").hasNextBigInteger(), "decimal token");
        check(!new Scanner("").hasNextBigInteger(), "empty input");
        check(!new Scanner("ff").hasNextBigInteger(), "hex digits under default radix 10");

        Scanner s = new Scanner("12 abc");
        check(s.hasNextBigInteger(), "first call must report token");
        check(s.hasNextBigInteger(), "second call must not consume token");
        check(s.nextBigInteger().equals(new BigInteger("12")), "token value preserved");
        check(!s.hasNextBigInteger(), "next token is non-numeric");

        Scanner hex = new Scanner("ff");
        hex.useRadix(16);
        check(hex.hasNextBigInteger(), "hex digits under radix 16");

        Scanner closed = new Scanner("1");
        closed.close();
        try {
            closed.hasNextBigInteger();
            throw new RuntimeException("Expected IllegalStateException on closed scanner");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
