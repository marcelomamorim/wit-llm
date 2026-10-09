/*
 * @test
 * @summary Tests for java.util.regex.Matcher.start(String)
 * @run main MatcherWitupTest_P224436e869b8
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P224436e869b8 {

    public static void main(String[] args) {
        testStartWithNullName();
        testStartWithInvalidGroupName();
        testStartWithValidGroupName();
    }

    private static void testStartWithNullName() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        matcher.find();
        try {
            matcher.start(null);
            System.out.println("testStartWithNullName: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testStartWithNullName: PASSED");
        } catch (Exception e) {
            System.out.println("testStartWithNullName: FAILED (unexpected exception)");
        }
    }

    private static void testStartWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>a)");
        Matcher matcher = pattern.matcher("a");
        matcher.find();
        try {
            matcher.start("nonexistent");
            System.out.println("testStartWithInvalidGroupName: FAILED (no exception)");
        } catch (IllegalArgumentException e) {
            System.out.println("testStartWithInvalidGroupName: PASSED");
        } catch (Exception e) {
            System.out.println("testStartWithInvalidGroupName: FAILED (unexpected exception)");
        }
    }

    private static void testStartWithValidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>a)");
        Matcher matcher = pattern.matcher("a");
        matcher.find();
        try {
            int startIndex = matcher.start("foo");
            if (startIndex == 0) {
                System.out.println("testStartWithValidGroupName: PASSED");
            } else {
                System.out.println("testStartWithValidGroupName: FAILED (incorrect index)");
            }
        } catch (Exception e) {
            System.out.println("testStartWithValidGroupName: FAILED (exception)");
        }
    }
}
