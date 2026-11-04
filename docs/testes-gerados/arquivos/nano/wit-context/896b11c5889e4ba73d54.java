/*
 * @test
 * @summary Tests for java.util.regex.Matcher.group() method.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupWithoutMatch();
        testGroupWithValidMatch();
        testGroupWithInvalidIndex();
    }

    private static void testGroupWithoutMatch() {
        Pattern pattern = Pattern.compile("a*");
        Matcher matcher = pattern.matcher("bbb");
        // No match performed yet, calling group() should throw IllegalStateException
        try {
            matcher.group();
            System.err.println("FAIL: Expected IllegalStateException not thrown in testGroupWithoutMatch");
        } catch (IllegalStateException e) {
            // Expected exception
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception in testGroupWithoutMatch: " + e);
        }
    }

    private static void testGroupWithValidMatch() {
        Pattern pattern = Pattern.compile("(a+)");
        Matcher matcher = pattern.matcher("aaabbb");
        if (matcher.find()) {
            String group0 = matcher.group();
            String group1 = matcher.group(1);
            if (!"aaa".equals(group0)) {
                System.err.println("FAIL: group() returned: " + group0);
            }
            if (!"aaa".equals(group1)) {
                System.err.println("FAIL: group(1) returned: " + group1);
            }
        } else {
            System.err.println("FAIL: No match found in testGroupWithValidMatch");
        }
    }

    private static void testGroupWithInvalidIndex() {
        Pattern pattern = Pattern.compile("(a+)");
        Matcher matcher = pattern.matcher("aaa");
        if (matcher.find()) {
            try {
                // Calling group() with no arguments is equivalent to group(0), valid
                String g0 = matcher.group();
                if (!"aaa".equals(g0)) {
                    System.err.println("FAIL: group() returned: " + g0);
                }
                // Calling group() with an invalid index should throw IndexOutOfBoundsException
                matcher.group(2);
                System.err.println("FAIL: Expected IndexOutOfBoundsException not thrown in testGroupWithInvalidIndex");
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            } catch (Exception e) {
                System.err.println("FAIL: Unexpected exception in testGroupWithInvalidIndex: " + e);
            }
        } else {
            System.err.println("FAIL: No match found in testGroupWithInvalidIndex");
        }
    }
}
