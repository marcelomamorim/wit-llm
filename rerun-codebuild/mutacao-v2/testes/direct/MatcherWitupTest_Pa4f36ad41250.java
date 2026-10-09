/*
 * @test
 * @summary Test java.util.regex.Matcher.end(String) method behavior
 * @run main MatcherWitupTest_Pa4f36ad41250
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_Pa4f36ad41250 {
    public static void main(String[] args) {
        testEndWithNamedGroup();
        testEndWithNonexistentGroup();
    }

    private static void testEndWithNamedGroup() {
        Pattern p = Pattern.compile("(?<word>\\w+)");
        Matcher m = p.matcher("hello world");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        int endIndex = m.end("word");
        if (endIndex != 5) {
            throw new RuntimeException("Expected end index 5 for group 'word', got: " + endIndex);
        }
    }

    private static void testEndWithNonexistentGroup() {
        Pattern p = Pattern.compile("(?<word>\\w+)");
        Matcher m = p.matcher("hello world");
        m.find();
        try {
            m.end("nonexistent");
            throw new RuntimeException("Expected IllegalArgumentException for nonexistent group name");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
