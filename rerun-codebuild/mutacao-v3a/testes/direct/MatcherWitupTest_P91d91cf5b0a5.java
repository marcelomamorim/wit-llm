/*
 * @test
 * @summary Tests the behavior of java.util.regex.Matcher.group(String) method.
 * @run main MatcherWitupTest_P91d91cf5b0a5
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P91d91cf5b0a5 {

    public static void main(String[] args) {
        testGroupWithExistingGroupName();
        testGroupWithNonExistingGroupName();
        testGroupWithNullGroupName();
    }

    private static void testGroupWithExistingGroupName() {
        Pattern pattern = Pattern.compile("(?<name>abc)");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            String result = matcher.group("name");
            if ("abc".equals(result)) {
                System.out.println("PASS: group("name") with existing group returns expected value");
            } else {
                System.out.println("FAIL: group("name") with existing group returned: " + result);
            }
        } else {
            System.out.println("FAIL: matcher.find() did not find a match for pattern with named group");
        }
    }

    private static void testGroupWithNonExistingGroupName() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            String result = matcher.group("nonexistent");
            if (result == null) {
                System.out.println("PASS: group("nonexistent") returns null for non-existent group");
            } else {
                System.out.println("FAIL: group("nonexistent") returned: " + result);
            }
        } else {
            System.out.println("FAIL: matcher.find() did not find a match for pattern 'abc'");
        }
    }

    private static void testGroupWithNullGroupName() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("abc");
        try {
            matcher.group(null);
            System.out.println("FAIL: group(null) did not throw NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("PASS: group(null) threw NullPointerException");
        } catch (Exception e) {
            System.out.println("FAIL: group(null) threw unexpected exception: " + e);
        }
    }
}
