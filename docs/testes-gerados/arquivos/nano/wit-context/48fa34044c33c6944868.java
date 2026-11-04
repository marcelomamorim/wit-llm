/*
 * @test
 * @summary Tests for java.util.regex.Matcher.start(int group)
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartWithNoMatch();
        testStartWithInvalidGroup();
        testStartWithValidGroup();
    }

    private static void testStartWithNoMatch() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xyz");
        try {
            matcher.start(0);
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testStartWithInvalidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.find()) {
            try {
                matcher.start(-1);
                System.out.println("FAIL: Expected IndexOutOfBoundsException not thrown for group -1");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("PASS: Caught expected IndexOutOfBoundsException for group -1");
            } catch (Exception e) {
                System.out.println("FAIL: Unexpected exception for group -1: " + e);
            }
            try {
                matcher.start(3); // groupCount() == 2
                System.out.println("FAIL: Expected IndexOutOfBoundsException not thrown for group 3");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("PASS: Caught expected IndexOutOfBoundsException for group 3");
            } catch (Exception e) {
                System.out.println("FAIL: Unexpected exception for group 3: " + e);
            }
        } else {
            System.out.println("FAIL: Matcher did not find a match for input 'ab'");
        }
    }

    private static void testStartWithValidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.find()) {
            try {
                int startIndex = matcher.start(1); // group 1
                int startIndex2 = matcher.start(2); // group 2
                System.out.println("PASS: start(1) returned: " + startIndex);
                System.out.println("PASS: start(2) returned: " + startIndex2);
            } catch (Exception e) {
                System.out.println("FAIL: Unexpected exception: " + e);
            }
        } else {
            System.out.println("FAIL: Matcher did not find a match for input 'ab'");
        }
    }
}
