/* @test    @summary Test for java.util.regex.Matcher.group() method to verify exception behavior.    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupThrowsIllegalStateExceptionWhenNoMatch();
        testGroupThrowsIndexOutOfBoundsExceptionWhenInvalidGroup();
    }

    private static void testGroupThrowsIllegalStateExceptionWhenNoMatch() {
        Pattern pattern = Pattern.compile("a");
        Matcher matcher = pattern.matcher("b");
        try {
            matcher.group();
            System.out.println("FAIL: Expected IllegalStateException was not thrown.");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testGroupThrowsIndexOutOfBoundsExceptionWhenInvalidGroup() {
        Pattern pattern = Pattern.compile("(a)");
        Matcher matcher = pattern.matcher("a");
        if (matcher.find()) {
            try {
                matcher.group(1); // valid group
                matcher.group(2); // invalid group, should throw
                System.out.println("FAIL: Expected IndexOutOfBoundsException was not thrown.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("PASS: Caught expected IndexOutOfBoundsException.");
            } catch (Exception e) {
                System.out.println("FAIL: Caught unexpected exception: " + e);
            }
        } else {
            System.out.println("FAIL: Pattern did not find a match.");
        }
    }
}
