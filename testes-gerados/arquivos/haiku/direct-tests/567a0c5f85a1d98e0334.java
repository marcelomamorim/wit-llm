/*
 * @test
 * @summary Verifies Scanner.hasNextLong(int radix) returns true or false based on radix-aware parsing and does not consume non-matching input
 * @run main ScannerWitupTest
 */

import java.util.Locale;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) {
        decimalTokenIsLong();
        hexTokenWithRadix16();
        negativeHexTokenWithRadix16();
        hexTokenNotLongInRadix10();
        nonNumericTokenReturnsFalse();
        emptyInputReturnsFalse();
        overflowReturnsFalse();
        maxLongIsAccepted();
        repeatedCallsAreIdempotent();
        octalTokenWithRadix8();
    }

    static void decimalTokenIsLong() {
        Scanner s = new Scanner("123").useLocale(Locale.US);
        check(s.hasNextLong(10), "hasNextLong(10) on 123 should be true");
        check(s.nextLong(10) == 123L, "nextLong(10) should return 123");
    }

    static void hexTokenWithRadix16() {
        Scanner s = new Scanner("ff").useLocale(Locale.US);
        check(s.hasNextLong(16), "hasNextLong(16) on ff should be true");
        check(s.nextLong(16) == 255L, "nextLong(16) should return 255");
    }

    static void negativeHexTokenWithRadix16() {
        Scanner s = new Scanner("-1A").useLocale(Locale.US);
        check(s.hasNextLong(16), "hasNextLong(16) on -1A should be true");
        check(s.nextLong(16) == -26L, "nextLong(16) should return -26");
    }

    static void hexTokenNotLongInRadix10() {
        Scanner s = new Scanner("ff").useLocale(Locale.US);
        check(!s.hasNextLong(10), "hasNextLong(10) on ff should be false");
        check(s.next().equals("ff"), "token ff should remain available after failed hasNextLong");
    }

    static void nonNumericTokenReturnsFalse() {
        Scanner s = new Scanner("abc").useLocale(Locale.US);
        check(!s.hasNextLong(10), "hasNextLong(10) on abc should be false");
        check(s.next().equals("abc"), "token abc should remain available");
    }

    static void emptyInputReturnsFalse() {
        Scanner s = new Scanner("").useLocale(Locale.US);
        check(!s.hasNextLong(10), "hasNextLong(10) on empty input should be false");
    }

    static void overflowReturnsFalse() {
        Scanner s = new Scanner("9223372036854775808").useLocale(Locale.US);
        check(!s.hasNextLong(10), "hasNextLong(10) on Long.MAX_VALUE + 1 should be false");
    }

    static void maxLongIsAccepted() {
        Scanner s = new Scanner("9223372036854775807").useLocale(Locale.US);
        check(s.hasNextLong(10), "hasNextLong(10) on Long.MAX_VALUE should be true");
        check(s.nextLong(10) == Long.MAX_VALUE, "nextLong(10) should return Long.MAX_VALUE");
    }

    static void repeatedCallsAreIdempotent() {
        Scanner s = new Scanner("42").useLocale(Locale.US);
        check(s.hasNextLong(10), "first hasNextLong(10) should be true");
        check(s.hasNextLong(10), "second hasNextLong(10) should be true");
        check(s.nextLong(10) == 42L, "nextLong(10) should return 42");
    }

    static void octalTokenWithRadix8() {
        Scanner s = new Scanner("17").useLocale(Locale.US);
        check(s.hasNextLong(8), "hasNextLong(8) on 17 should be true");
        check(s.nextLong(8) == 15L, "nextLong(8) should return 15");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
