/*
 * @test
 * @summary Test for Matcher.end() method, focusing on IllegalStateException when match state is invalid.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndMethodWithInvalidState();
        testEndMethodWithValidState();
    }

    private static void testEndMethodWithInvalidState() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("xyz");
        try {
            // Intentionally do not call find() or matches(), so match state is invalid
            int result = matcher.end();
            System.out.println("FAIL: Expected IllegalStateException, but got result: " + result);
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException when match state is invalid.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testEndMethodWithValidState() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abcxyz");
        if (matcher.find()) {
            try {
                int endIndex = matcher.end();
                System.out.println("PASS: Matcher.end() returned: " + endIndex);
            } catch (Exception e) {
                System.out.println("FAIL: Unexpected exception when match is valid: " + e);
            }
        } else {
            System.out.println("FAIL: Matcher.find() did not find a match as expected.");
        }
    }
}
