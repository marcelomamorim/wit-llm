/*
 * @test
 * @summary Tests for java.util.regex.Matcher.end(String name)
 * @run main MatcherWitupTest_P046580b2c935
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P046580b2c935 {

    public static void main(String[] args) {
        testEndWithNullName();
        testEndWithInvalidGroupName();
        testEndWithValidGroupName();
    }

    private static void testEndWithNullName() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        matcher.find();
        try {
            matcher.end(null);
            System.out.println("FAIL: Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null name");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for null name: " + e);
        }
    }

    private static void testEndWithInvalidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>a)(b)");
        Matcher matcher = pattern.matcher("ab");
        matcher.find();
        String invalidGroupName = "nonexistent";
        try {
            matcher.end(invalidGroupName);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid group name");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown as expected for invalid group name");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for invalid group name: " + e);
        }
    }

    private static void testEndWithValidGroupName() {
        Pattern pattern = Pattern.compile("(?<foo>a)(b)");
        Matcher matcher = pattern.matcher("ab");
        matcher.find();
        String groupName = "foo";
        try {
            int endIndex = matcher.end(groupName);
            System.out.println("PASS: end index for group '" + groupName + "' is " + endIndex);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception for valid group name: " + e);
        }
    }
}
