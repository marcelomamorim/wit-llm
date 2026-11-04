/*
 * @test
 * @summary Test the end() method of java.util.regex.Matcher for correct return value after a match.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testEndMethodReturnsCorrectIndex();
    }

    public static void testEndMethodReturnsCorrectIndex() {
        Pattern pattern = Pattern.compile("abc");
        String input = "abcabc";
        Matcher matcher = pattern.matcher(input);
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match in input: " + input);
        }
        int endIndex = matcher.end();
        int expectedEndIndex = matcher.start() + matcher.group().length();
        if (endIndex != expectedEndIndex) {
            throw new AssertionError("end() returned " + endIndex + ", expected " + expectedEndIndex);
        }
        if (endIndex != 3) {
            throw new AssertionError("Expected end() to return 3, but got " + endIndex);
        }
    }
}
