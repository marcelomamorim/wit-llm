/*
 * @test
 * @summary Witup tests for Scanner.findInLine(String pattern)
 * @run main ScannerWitupTest
 */

import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        Scanner s1 = new Scanner("hello world\nfoo");
        check("wor".equals(s1.findInLine("wor")), "literal match before line terminator");

        Scanner s2 = new Scanner("hello world");
        check(s2.findInLine("xyz") == null, "no match returns null");

        Scanner s3 = new Scanner("bar\nfoo");
        check(s3.findInLine("foo") == null, "match beyond line terminator returns null");

        Scanner s4 = new Scanner("abc def");
        check("abc".equals(s4.findInLine("abc")), "match returned");
        check("def".equals(s4.next()), "scanner advanced past match");

        Scanner s5 = new Scanner("abc\ndef");
        check(s5.findInLine("zzz") == null, "miss returns null");
        check("abc".equals(s5.next()), "position unchanged after miss");

        Scanner s6 = new Scanner("ab 123 cd");
        check("123".equals(s6.findInLine("\\d+")), "regex pattern matched");

        Scanner s7 = new Scanner("   abc");
        check("abc".equals(s7.findInLine("abc")), "leading delimiters do not prevent match");

        Scanner s8 = new Scanner("");
        check(s8.findInLine("a") == null, "empty input returns null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
