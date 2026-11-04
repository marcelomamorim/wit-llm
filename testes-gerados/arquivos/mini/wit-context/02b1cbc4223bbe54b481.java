/*
 * @test
 * @summary Test java.util.regex.Matcher.end(int) method for expected exceptions and correct behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndWithInvalidGroupNegative();
        testEndWithInvalidGroupTooLarge();
        testEndWithValidGroup();
    }

    private static void testEndWithInvalidGroupNegative() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        m.find();
        try {
            m.end(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative group index");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testEndWithInvalidGroupTooLarge() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        m.find();
        int groupCount = m.groupCount();
        try {
            m.end(groupCount + 1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for group index > groupCount");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    private static void testEndWithValidGroup() {
        Pattern p = Pattern.compile("(a)(b)");
        Matcher m = p.matcher("ab");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find a match");
        }
        int end0 = m.end(0); // entire match
        int end1 = m.end(1); // group 1
        int end2 = m.end(2); // group 2
        if (end0 != 2) {
            throw new RuntimeException("Expected end(0) to be 2 but was " + end0);
        }
        if (end1 != 1) {
            throw new RuntimeException("Expected end(1) to be 1 but was " + end1);
        }
        if (end2 != 2) {
            throw new RuntimeException("Expected end(2) to be 2 but was " + end2);
        }
    }
}
