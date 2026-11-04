/*
 * @test
 * @summary Behavioral tests for Matcher.replaceFirst(String), including the NullPointerException guard
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullReplacementThrowsNPE() {
        Matcher m = Pattern.compile("a").matcher("abc");
        boolean thrown = false;
        try {
            m.replaceFirst(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null replacement");
    }

    static void testNoMatchReturnsInputText() {
        String result = Pattern.compile("x").matcher("abc").replaceFirst("y");
        check("abc".equals(result), "no match should return input text, got: " + result);
    }

    static void testReplacesOnlyFirstOccurrence() {
        String result = Pattern.compile("a").matcher("a1a2").replaceFirst("b");
        check("b1a2".equals(result), "expected b1a2, got: " + result);
    }

    static void testGroupReferenceInReplacement() {
        String result = Pattern.compile("(\\d+)").matcher("ab12cd34").replaceFirst("<$1>");
        check("ab<12>cd34".equals(result), "expected ab<12>cd34, got: " + result);
    }

    static void testEscapedDollarInReplacement() {
        String result = Pattern.compile("a").matcher("a").replaceFirst("\\$");
        check("$".equals(result), "expected $, got: " + result);
    }

    static void testResetBeforeReplaceFirst() {
        Matcher m = Pattern.compile("a").matcher("aa");
        check(m.find(), "precondition: find should succeed");
        String result = m.replaceFirst("b");
        check("ba".equals(result), "replaceFirst should reset matcher, expected ba, got: " + result);
    }

    public static void main(String[] args) {
        testNullReplacementThrowsNPE();
        testNoMatchReturnsInputText();
        testReplacesOnlyFirstOccurrence();
        testGroupReferenceInReplacement();
        testEscapedDollarInReplacement();
        testResetBeforeReplaceFirst();
    }
}
