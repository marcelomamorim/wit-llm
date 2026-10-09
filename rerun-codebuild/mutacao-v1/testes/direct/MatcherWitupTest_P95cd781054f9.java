/* @test    @summary Test for Matcher.start(String name) method in java.util.regex.Matcher    @run main MatcherWitupTest_P95cd781054f9 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P95cd781054f9 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(?<group1>abc)(?<group2>def)");
        Matcher matcher = pattern.matcher("abcdef");
        if (!matcher.find()) {
            throw new AssertionError("Matcher did not find a match.");
        }
        int startGroup1 = matcher.start("group1");
        int startGroup2 = matcher.start("group2");
        if (startGroup1 != 0) {
            throw new AssertionError("Expected start of group1 to be 0, but was " + startGroup1);
        }
        if (startGroup2 != 3) {
            throw new AssertionError("Expected start of group2 to be 3, but was " + startGroup2);
        }
        System.out.println("Test passed: start(String name) returns correct indices.");
    }
}
