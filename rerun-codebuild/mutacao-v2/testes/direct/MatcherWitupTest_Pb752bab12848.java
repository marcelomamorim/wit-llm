/*
 * @test
 * @summary Test java.util.regex.Matcher.group(String) method behavior
 * @run main MatcherWitupTest_Pb752bab12848
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest_Pb752bab12848 {
    public static void main(String[] args) {
        testGroupWithNamedCapturingGroup();
        testGroupWithUnmatchedGroupReturnsNull();
    }

    private static void testGroupWithNamedCapturingGroup() {
        Pattern p = Pattern.compile("(?<name>\\w+)");
        Matcher m = p.matcher("hello");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        String group = m.group("name");
        if (!"hello".equals(group)) {
            throw new RuntimeException("Expected group 'name' to be 'hello' but was '" + group + "'");
        }
    }

    private static void testGroupWithUnmatchedGroupReturnsNull() {
        Pattern p = Pattern.compile("(?<name>\\w+)?");
        Matcher m = p.matcher("");
        if (!m.find()) {
            throw new RuntimeException("Expected to find a match");
        }
        String group = m.group("name");
        if (group != null) {
            throw new RuntimeException("Expected group 'name' to be null for unmatched optional group but was '" + group + "'");
        }
    }
}
