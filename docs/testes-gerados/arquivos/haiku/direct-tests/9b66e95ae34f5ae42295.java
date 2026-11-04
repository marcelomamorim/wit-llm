/*
 * @test
 * @summary Tests observable behavior of Matcher.appendReplacement
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testNoMatchThrows();
        testBasicReplacement();
        testGroupReferences();
        testEscapedDollar();
        testSequentialMatches();
        testReturnsSameMatcher();
        testPreservesExistingBuilderContent();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNoMatchThrows() {
        Matcher m = Pattern.compile("a").matcher("b");
        StringBuilder sb = new StringBuilder();
        boolean thrown = false;
        try {
            m.appendReplacement(sb, "x");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalStateException when no match is available");
        check(sb.length() == 0, "builder must not change when no match is available");
    }

    static void testBasicReplacement() {
        Matcher m = Pattern.compile("cat").matcher("one cat two");
        StringBuilder sb = new StringBuilder();
        check(m.find(), "expected a match for 'cat'");
        m.appendReplacement(sb, "dog");
        m.appendTail(sb);
        check(sb.toString().equals("one dog two"),
              "unexpected result: " + sb);
    }

    static void testGroupReferences() {
        Matcher m = Pattern.compile("(\\w+)@(\\w+)").matcher("a@b");
        StringBuilder sb = new StringBuilder();
        check(m.find(), "expected a match for group pattern");
        m.appendReplacement(sb, "$2-$1");
        m.appendTail(sb);
        check(sb.toString().equals("b-a"),
              "unexpected group substitution result: " + sb);
    }

    static void testEscapedDollar() {
        Matcher m = Pattern.compile("a").matcher("a");
        StringBuilder sb = new StringBuilder();
        check(m.find(), "expected a match for 'a'");
        m.appendReplacement(sb, "\\$x");
        m.appendTail(sb);
        check(sb.toString().equals("$x"),
              "escaped dollar not treated literally: " + sb);
    }

    static void testSequentialMatches() {
        Matcher m = Pattern.compile("\\d").matcher("a1b2");
        StringBuilder sb = new StringBuilder();
        check(m.find(), "expected first digit match");
        m.appendReplacement(sb, "X");
        check(sb.toString().equals("aX"),
              "unexpected intermediate result after first match: " + sb);
        check(m.find(), "expected second digit match");
        m.appendReplacement(sb, "Y");
        m.appendTail(sb);
        check(sb.toString().equals("aXbY"),
              "unexpected result after sequential matches: " + sb);
    }

    static void testReturnsSameMatcher() {
        Matcher m = Pattern.compile("b").matcher("abc");
        StringBuilder sb = new StringBuilder();
        check(m.find(), "expected a match for 'b'");
        Matcher returned = m.appendReplacement(sb, "B");
        check(returned == m, "appendReplacement must return the same matcher");
    }

    static void testPreservesExistingBuilderContent() {
        Matcher m = Pattern.compile("x").matcher("axb");
        StringBuilder sb = new StringBuilder("pre:");
        check(m.find(), "expected a match for 'x'");
        m.appendReplacement(sb, "Y");
        m.appendTail(sb);
        check(sb.toString().equals("pre:aYb"),
              "existing builder content was not preserved: " + sb);
    }
}
