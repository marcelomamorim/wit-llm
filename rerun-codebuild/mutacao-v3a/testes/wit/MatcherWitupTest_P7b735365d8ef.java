/*
 * @test
 * @summary Tests for Matcher.end(String name) method to verify exception throwing behavior.
 * @run main MatcherWitupTest_P7b735365d8ef
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P7b735365d8ef {

    public static void main(String[] args) {
        testEndWithNullName();
        testEndWithInvalidGroupName();
        testEndWithValidGroupName();
    }

    static void testEndWithNullName() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("123");
        try {
            matcher.end(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    static void testEndWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(?<digits>\d+)");
        Matcher matcher = pattern.matcher("123");
        if (matcher.matches()) {
            try {
                // 'nonexistent' is not a defined group name
                matcher.end("nonexistent");
                System.out.println("FAIL: Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                System.out.println("PASS: IllegalArgumentException thrown as expected");
            } catch (Throwable t) {
                System.out.println("FAIL: Unexpected exception type: " + t);
            }
        } else {
            System.out.println("FAIL: Matcher did not match as expected for testEndWithInvalidGroupName");
        }
    }

    static void testEndWithValidGroupName() {
        Pattern pattern = Pattern.compile("(?<digits>\d+)");
        Matcher matcher = pattern.matcher("123");
        if (matcher.matches()) {
            try {
                int endPos = matcher.end("digits");
                if (endPos == 3) {
                    System.out.println("PASS: end("digits") returned expected value");
                } else {
                    System.out.println("FAIL: end("digits") returned unexpected value: " + endPos);
                }
            } catch (Throwable t) {
                System.out.println("FAIL: Unexpected exception: " + t);
            }
        } else {
            System.out.println("FAIL: Matcher did not match as expected for testEndWithValidGroupName");
        }
    }
}
