/*
 * @test
 * @summary Test java.util.regex.Matcher.group() method
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testGroupReturnsFullMatch();
        testGroupThrowsIllegalStateExceptionWhenNoMatch();
    }

    static void testGroupReturnsFullMatch() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("123abc");
        boolean found = m.find();
        if (!found) {
            throw new RuntimeException("Expected to find a match but did not");
        }
        String group0 = m.group();
        if (!"123".equals(group0)) {
            throw new RuntimeException("Expected group() to return '123' but got: " + group0);
        }
    }

    static void testGroupThrowsIllegalStateExceptionWhenNoMatch() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("def");
        try {
            m.group();
            throw new RuntimeException("Expected IllegalStateException when calling group() before match");
        } catch (IllegalStateException e) {
            // Expected
        }
    }
}
