/*
 * @test
 * @summary Test for java.util.regex.Matcher.group(String) method.
 * @run main MatcherWitupTest_P9d3b03ffa4ff
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P9d3b03ffa4ff {

    public static void main(String[] args) {
        testGroupWithValidName();
        testGroupWithInvalidName();
        testGroupWithNullName();
        testGroupWithNegativeIndex();
    }

    private static void testGroupWithValidName() {
        Pattern pattern = Pattern.compile("(?<name>abc)");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.matches()) {
            String result = matcher.group("name");
            if ("abc".equals(result)) {
                System.out.println("testGroupWithValidName passed");
            } else {
                System.err.println("testGroupWithValidName failed: unexpected result: " + result);
            }
        } else {
            System.err.println("testGroupWithValidName failed: matcher did not match");
        }
    }

    private static void testGroupWithInvalidName() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.matches()) {
            try {
                matcher.group("nonexistent");
                System.err.println("testGroupWithInvalidName failed: expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                System.out.println("testGroupWithInvalidName passed");
            } catch (Exception e) {
                System.err.println("testGroupWithInvalidName failed: unexpected exception: " + e);
            }
        } else {
            System.err.println("testGroupWithInvalidName failed: matcher did not match");
        }
    }

    private static void testGroupWithNullName() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.matches()) {
            try {
                matcher.group(null);
                System.err.println("testGroupWithNullName failed: expected NullPointerException");
            } catch (NullPointerException e) {
                System.out.println("testGroupWithNullName passed");
            } catch (Exception e) {
                System.err.println("testGroupWithNullName failed: unexpected exception: " + e);
            }
        } else {
            System.err.println("testGroupWithNullName failed: matcher did not match");
        }
    }

    private static void testGroupWithNegativeIndex() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.matches()) {
            try {
                matcher.group(-1);
                System.err.println("testGroupWithNegativeIndex failed: expected IndexOutOfBoundsException");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("testGroupWithNegativeIndex passed");
            } catch (Exception e) {
                System.err.println("testGroupWithNegativeIndex failed: unexpected exception: " + e);
            }
        } else {
            System.err.println("testGroupWithNegativeIndex failed: matcher did not match");
        }
    }
}
