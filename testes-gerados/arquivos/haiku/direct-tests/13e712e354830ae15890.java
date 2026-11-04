/*
 * @test
 * @summary Behavioral tests for Matcher.replaceFirst(String)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplacesFirstOccurrenceOnly();
        testNoMatchReturnsInputUnchanged();
        testGroupReferenceInReplacement();
        testResetBeforeReplaceAfterFind();
        testEscapedDollarIsLiteral();
        testNullReplacementThrowsNPE();
        testMissingGroupReferenceThrowsIOOBE();
        System.out.println("All MatcherWitupTest checks passed");
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected [" + expected + "] but was [" + actual + "]");
        }
    }

    static void testReplacesFirstOccurrenceOnly() {
        Matcher m = Pattern.compile("\\d").matcher("a1b2");
        check("aXb2", m.replaceFirst("X"));
    }

    static void testNoMatchReturnsInputUnchanged() {
        Matcher m = Pattern.compile("\\d").matcher("abc");
        check("abc", m.replaceFirst("X"));
    }

    static void testGroupReferenceInReplacement() {
        Matcher m = Pattern.compile("(\\d)").matcher("a1b2");
        check("a<1>b2", m.replaceFirst("<$1>"));
    }

    static void testResetBeforeReplaceAfterFind() {
        Matcher m = Pattern.compile("\\d").matcher("a1b2");
        m.find();
        check("aXb2", m.replaceFirst("X"));
    }

    static void testEscapedDollarIsLiteral() {
        Matcher m = Pattern.compile("\\d").matcher("a1");
        check("a$", m.replaceFirst("\\$"));
    }

    static void testNullReplacementThrowsNPE() {
        Matcher m = Pattern.compile("\\d").matcher("a1");
        try {
            m.replaceFirst(null);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testMissingGroupReferenceThrowsIOOBE() {
        Matcher m = Pattern.compile("(\\d)").matcher("a1");
        try {
            m.replaceFirst("$2");
            throw new RuntimeException("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
