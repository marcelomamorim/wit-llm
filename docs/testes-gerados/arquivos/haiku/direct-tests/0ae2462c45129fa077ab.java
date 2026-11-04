/*
 * @test
 * @summary Black-box tests for Scanner.hasNextBigInteger(int radix)
 * @run main ScannerWitupTest
 */

import java.math.BigInteger;
import java.util.Scanner;

public class ScannerWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDecimalTokenIsDetected() {
        Scanner sc = new Scanner("123");
        check(sc.hasNextBigInteger(10), "decimal token should be a BigInteger in radix 10");
        check(sc.nextBigInteger().equals(new BigInteger("123")), "value should be 123");
    }

    static void testHexTokenRadix16() {
        Scanner sc = new Scanner("ff");
        check(sc.hasNextBigInteger(16), "'ff' should be a BigInteger in radix 16");
        check(sc.nextBigInteger(16).equals(BigInteger.valueOf(255)), "'ff' in radix 16 should be 255");
    }

    static void testHexTokenRejectedInDecimal() {
        Scanner sc = new Scanner("ff");
        check(!sc.hasNextBigInteger(10), "'ff' should not be a BigInteger in radix 10");
    }

    static void testNonNumericToken() {
        Scanner sc = new Scanner("abc");
        check(!sc.hasNextBigInteger(10), "'abc' should not be a BigInteger");
    }

    static void testEmptyInput() {
        Scanner sc = new Scanner("");
        check(!sc.hasNextBigInteger(10), "empty input should not have a BigInteger");
    }

    static void testNegativeHexToken() {
        Scanner sc = new Scanner("-1F");
        check(sc.hasNextBigInteger(16), "'-1F' should be a BigInteger in radix 16");
        check(sc.nextBigInteger(16).equals(BigInteger.valueOf(-31)), "'-1F' should be -31");
    }

    static void testDoesNotConsumeToken() {
        Scanner sc = new Scanner("123");
        check(sc.hasNextBigInteger(10), "first call should return true");
        check(sc.hasNextBigInteger(10), "second call should still return true");
        check("123".equals(sc.next()), "token should remain available to next()");
    }

    static void testOnlyFirstTokenChecked() {
        Scanner sc = new Scanner("12 abc");
        check(sc.hasNextBigInteger(10), "'12' should be a BigInteger");
        check("12".equals(sc.next()), "next token should be 12");
        check(!sc.hasNextBigInteger(10), "'abc' should not be a BigInteger");
    }

    static void testLargeValue() {
        String big = "123456789012345678901234567890";
        Scanner sc = new Scanner(big);
        check(sc.hasNextBigInteger(10), "large decimal token should be a BigInteger");
        check(sc.nextBigInteger().equals(new BigInteger(big)), "large value should round-trip");
    }

    public static void main(String[] args) {
        testDecimalTokenIsDetected();
        testHexTokenRadix16();
        testHexTokenRejectedInDecimal();
        testNonNumericToken();
        testEmptyInput();
        testNegativeHexToken();
        testDoesNotConsumeToken();
        testOnlyFirstTokenChecked();
        testLargeValue();
    }
}
