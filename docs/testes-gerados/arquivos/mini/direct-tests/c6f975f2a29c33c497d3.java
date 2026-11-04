/*
 * @test
 * @summary Test java.util.regex.Matcher.start(int) method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testStartWithValidGroup();
        testStartWithInvalidNegativeGroup();
        testStartWithInvalidGroupGreaterThanGroupCount();
    }

    private static void testStartWithValidGroup() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        int start0 = m.start(0); // entire match
        int start1 = m.start(1); // group 1
        int start2 = m.start(2); // group 2

        if (start0 != 0) {
            throw new RuntimeException("Expected start(0) to be 0 but was " + start0);
        }
        if (start1 != 0) {
            throw new RuntimeException("Expected start(1) to be 0 but was " + start1);
        }
        if (start2 != 1) {
            throw new RuntimeException("Expected start(2) to be 1 but was " + start2);
        }
    }

    private static void testStartWithInvalidNegativeGroup() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        try {
            m.start(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testStartWithInvalidGroupGreaterThanGroupCount() {
        Pattern p = Pattern.compile("(a)");
        Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        int invalidGroup = m.groupCount() + 1;
        try {
            m.start(invalidGroup);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group " + invalidGroup);
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
