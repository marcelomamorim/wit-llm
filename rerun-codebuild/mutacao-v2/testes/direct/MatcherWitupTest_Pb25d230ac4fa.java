/*
 * @test
 * @summary Test java.util.regex.Matcher.start(String) method behavior
 * @run main MatcherWitupTest_Pb25d230ac4fa
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_Pb25d230ac4fa {
    public static void main(String[] args) {
        testStartWithNamedGroup();
        testStartWithNonexistentGroup();
    }

    private static void testStartWithNamedGroup() {
        Pattern p = Pattern.compile("(?<word>\\w+)");
        Matcher m = p.matcher("hello");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        int start = m.start("word");
        if (start != 0) {
            throw new RuntimeException("Expected start index 0 for group 'word', got: " + start);
        }
    }

    private static void testStartWithNonexistentGroup() {
        Pattern p = Pattern.compile("(abc)");
        Matcher m = p.matcher("abc");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        try {
            m.start("nonexistent");
            throw new RuntimeException("Expected IllegalArgumentException for nonexistent group name");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
