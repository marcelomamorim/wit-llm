/*
 * @test
 * @summary Tests for java.util.regex.Matcher.start(String name) method
 * @run main MatcherWitupTest_Pef23b4af9cdd
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_Pef23b4af9cdd {

    public static void main(String[] args) {
        testStartWithNullName();
        testStartWithInvalidGroupName();
        testStartWithValidGroupName();
    }

    private static void testStartWithNullName() {
        Pattern pattern = Pattern.compile("(abc)");
        Matcher matcher = pattern.matcher("abc");
        matcher.find();
        try {
            int result = matcher.start(null);
            System.out.println("testStartWithNullName failed: expected NullPointerException, got " + result);
        } catch (NullPointerException e) {
            System.out.println("testStartWithNullName passed");
        } catch (Exception e) {
            System.out.println("testStartWithNullName failed: unexpected exception: " + e);
        }
    }

    private static void testStartWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>abc)");
        Matcher matcher = pattern.matcher("abc");
        matcher.find();
        try {
            matcher.start("nonexistent");
            System.out.println("testStartWithInvalidGroupName failed: expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("testStartWithInvalidGroupName passed");
        } catch (Exception e) {
            System.out.println("testStartWithInvalidGroupName failed: unexpected exception: " + e);
        }
    }

    private static void testStartWithValidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>abc)");
        Matcher matcher = pattern.matcher("abc");
        matcher.find();
        try {
            int startIndex = matcher.start("foo");
            if (startIndex == 0) {
                System.out.println("testStartWithValidGroupName passed");
            } else {
                System.out.println("testStartWithValidGroupName failed: unexpected start index: " + startIndex);
            }
        } catch (Exception e) {
            System.out.println("testStartWithValidGroupName failed: unexpected exception: " + e);
        }
    }
}
