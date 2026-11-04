/*
 * @test
 * @summary Tests for Matcher.replaceFirst(Function<MatchResult, String>) method.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplaceFirstWithNullReplacer();
        testReplaceFirstWithEmptyReplacer();
        testReplaceFirstWithoutMatch();
        testReplaceFirstWithMatch();
    }

    static void testReplaceFirstWithNullReplacer() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar foo");
        try {
            matcher.replaceFirst(null);
            System.out.println("FAIL: Expected NullPointerException for null replacer");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        }
    }

    static void testReplaceFirstWithEmptyReplacer() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar foo");
        String result = matcher.replaceFirst(m -> "");
        if (" bar foo".equals(result)) {
            System.out.println("PASS: replaceFirst with empty replacer");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }

    static void testReplaceFirstWithoutMatch() {
        Pattern pattern = Pattern.compile("baz");
        Matcher matcher = pattern.matcher("foo bar foo");
        String result = matcher.replaceFirst(m -> "baz");
        if ("foo bar foo".equals(result)) {
            System.out.println("PASS: replaceFirst with no match");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }

    static void testReplaceFirstWithMatch() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar foo");
        String result = matcher.replaceFirst(m -> "baz");
        if ("baz bar foo".equals(result)) {
            System.out.println("PASS: replaceFirst with match");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }
}
