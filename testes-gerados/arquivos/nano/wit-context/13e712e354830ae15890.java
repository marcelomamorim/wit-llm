/*
 * @test
 * @summary Tests for java.util.regex.Matcher.replaceFirst(String)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplaceFirstWithNullReplacement();
        testReplaceFirstWithEmptyString();
        testReplaceFirstWithNoMatch();
        testReplaceFirstWithMatch();
    }

    private static void testReplaceFirstWithNullReplacement() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaabbb");
        try {
            matcher.replaceFirst(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testReplaceFirstWithEmptyString() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaabbb");
        String result = matcher.replaceFirst("");
        if ("bbb".equals(result)) {
            System.out.println("PASS: replaceFirst with empty string produces expected result");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }

    private static void testReplaceFirstWithNoMatch() {
        Pattern pattern = Pattern.compile("z+");
        Matcher matcher = pattern.matcher("aaabbb");
        String result = matcher.replaceFirst("X");
        if ("aaabbb".equals(result)) {
            System.out.println("PASS: No match, original string returned");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }

    private static void testReplaceFirstWithMatch() {
        Pattern pattern = Pattern.compile("a+");
        Matcher matcher = pattern.matcher("aaabbb");
        String result = matcher.replaceFirst("X");
        if ("Xbbb".equals(result)) {
            System.out.println("PASS: replaceFirst with match produces expected result");
        } else {
            System.out.println("FAIL: Unexpected result: " + result);
        }
    }
}
