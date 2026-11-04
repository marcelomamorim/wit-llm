/*
 * @test
 * @summary Behavioral tests for StringBuffer.charAt(int index)
 * @run main StringBufferWitupTest
 */

import java.util.Arrays;

public class StringBufferWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCharAtFromString() {
        StringBuffer sb = new StringBuffer("abc");
        check(sb.charAt(0) == 'a', "charAt(0) should be 'a'");
        check(sb.charAt(1) == 'b', "charAt(1) should be 'b'");
        check(sb.charAt(2) == 'c', "charAt(2) should be 'c'");
    }

    static void testCharAtFromCharSequence() {
        CharSequence seq = "xyz";
        StringBuffer sb = new StringBuffer(seq);
        check(sb.charAt(0) == 'x', "charAt(0) from CharSequence should be 'x'");
        check(sb.charAt(1) == 'y', "charAt(1) from CharSequence should be 'y'");
        check(sb.charAt(2) == 'z', "charAt(2) from CharSequence should be 'z'");
    }

    static void testCharAtAfterAppend() {
        StringBuffer sb = new StringBuffer(10);
        sb.append("q");
        check(sb.charAt(0) == 'q', "charAt(0) after append should be 'q'");
        sb.append("rs");
        check(sb.charAt(1) == 'r', "charAt(1) after append should be 'r'");
        check(sb.charAt(2) == 's', "charAt(2) after append should be 's'");
    }

    static void testCharAtAfterInsert() {
        StringBuffer sb = new StringBuffer("start");
        sb.insert(4, "le");
        check(sb.toString().equals("starlet"), "unexpected content after insert");
        check(sb.charAt(4) == 'l', "charAt(4) after insert should be 'l'");
        check(sb.charAt(5) == 'e', "charAt(5) after insert should be 'e'");
        check(sb.charAt(6) == 't', "charAt(6) after insert should be 't'");
    }

    static void testCharAtMatchesToString() {
        StringBuffer sb = new StringBuffer("hello world");
        String s = sb.toString();
        for (int i = 0; i < s.length(); i++) {
            check(sb.charAt(i) == s.charAt(i),
                  "charAt(" + i + ") mismatch with toString()");
        }
    }

    static void testCharAtOutOfRangeThrows() {
        StringBuffer sb = new StringBuffer("ab");
        boolean thrownAtLength = false;
        try {
            sb.charAt(sb.length());
        } catch (RuntimeException e) {
            thrownAtLength = true;
        }
        check(thrownAtLength, "charAt(length) should throw");

        boolean thrownNegative = false;
        try {
            sb.charAt(-1);
        } catch (RuntimeException e) {
            thrownNegative = true;
        }
        check(thrownNegative, "charAt(-1) should throw");
    }

    public static void main(String[] args) {
        testCharAtFromString();
        testCharAtFromCharSequence();
        testCharAtAfterAppend();
        testCharAtAfterInsert();
        testCharAtMatchesToString();
        testCharAtOutOfRangeThrows();
    }
}
