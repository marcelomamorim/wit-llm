/*
 * @test
 * @summary Test java.util.regex.Matcher.start(int) method for expected exceptions and valid returns
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testStartWithInvalidGroup();
        testStartWithValidGroup();
    }

    private static void testStartWithInvalidGroup() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        // find a match to initialize groups
        if (!m.find()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }

        // group < 0 should throw IndexOutOfBoundsException
        try {
            m.start(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group -1");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }

        // group > groupCount should throw IndexOutOfBoundsException
        int invalidGroup = m.groupCount() + 1;
        try {
            m.start(invalidGroup);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group " + invalidGroup);
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testStartWithValidGroup() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Setup failed: pattern did not match input");
        }

        // test start(0) returns start of entire match
        int start0 = m.start(0);
        if (start0 != 0) {
            throw new RuntimeException("start(0) expected 0 but got " + start0);
        }

        // test start(1) returns start of first group
        int start1 = m.start(1);
        if (start1 != 0) {
            throw new RuntimeException("start(1) expected 0 but got " + start1);
        }

        // test start(2) returns start of second group
        int start2 = m.start(2);
        if (start2 != 1) {
            throw new RuntimeException("start(2) expected 1 but got " + start2);
        }
    }
}
