/*
 * @test
 * @summary Test for Matcher.group(int) method to ensure correct substring extraction and exception handling.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testValidGroup();
        testInvalidGroupNegative();
        testInvalidGroupExceeds();
        testGroupReturnsNull();
    }

    static void testValidGroup() {
        Pattern pattern = Pattern.compile("(foo)(bar)");
        Matcher matcher = pattern.matcher("foobar");
        if (matcher.find()) {
            String group0 = matcher.group(0);
            String group1 = matcher.group(1);
            String group2 = matcher.group(2);
            if (!"foobar".equals(group0)) {
                throw new AssertionError("Expected group 0 to be 'foobar', but was: " + group0);
            }
            if (!"foo".equals(group1)) {
                throw new AssertionError("Expected group 1 to be 'foo', but was: " + group1);
            }
            if (!"bar".equals(group2)) {
                throw new AssertionError("Expected group 2 to be 'bar', but was: " + group2);
            }
        } else {
            throw new AssertionError("Pattern did not match when expected.");
        }
    }

    static void testInvalidGroupNegative() {
        Pattern pattern = Pattern.compile("(abc)");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            try {
                matcher.group(-1);
                throw new AssertionError("Expected IndexOutOfBoundsException for negative group index.");
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Pattern did not match when expected.");
        }
    }

    static void testInvalidGroupExceeds() {
        Pattern pattern = Pattern.compile("(abc)");
        Matcher matcher = pattern.matcher("abc");
        if (matcher.find()) {
            int groupCount = matcher.groupCount();
            try {
                matcher.group(groupCount + 1);
                throw new AssertionError("Expected IndexOutOfBoundsException for group index exceeding groupCount.");
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Pattern did not match when expected.");
        }
    }

    static void testGroupReturnsNull() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.find()) {
            // Group 2 is present, so test with a pattern that has an optional group that may not match
            Pattern patternOptional = Pattern.compile("(a)?(b)");
            Matcher matcherOptional = patternOptional.matcher("b");
            if (matcherOptional.find()) {
                String result = matcherOptional.group(1);
                if (result != null) {
                    throw new AssertionError("Expected group 1 to be null, but was: " + result);
                }
            } else {
                throw new AssertionError("Optional pattern did not match as expected.");
            }
        } else {
            throw new AssertionError("Pattern did not match when expected.");
        }
    }
}
