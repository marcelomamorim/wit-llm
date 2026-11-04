/*
 * @test    @summary Test for Matcher.start(int group) method.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartWithValidGroup();
        testStartWithInvalidGroup();
    }

    static void testStartWithValidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.matches()) {
            int startGroup0 = matcher.start(0);
            int startGroup1 = matcher.start(1);
            int startGroup2 = matcher.start(2);
            assert startGroup0 == 0 : "Expected start of group 0 to be 0";
            assert startGroup1 == 0 : "Expected start of group 1 to be 0";
            assert startGroup2 == 1 : "Expected start of group 2 to be 1";
        } else {
            throw new AssertionError("Matcher did not match input") ;
        }
    }

    static void testStartWithInvalidGroup() {
        Pattern pattern = Pattern.compile("(a)(b)");
        Matcher matcher = pattern.matcher("ab");
        if (matcher.matches()) {
            try {
                matcher.start(3);
                throw new AssertionError("Expected IndexOutOfBoundsException for invalid group") ;
            } catch (IndexOutOfBoundsException e) {
                // Expected exception
            }
        } else {
            throw new AssertionError("Matcher did not match input") ;
        }
    }
}
