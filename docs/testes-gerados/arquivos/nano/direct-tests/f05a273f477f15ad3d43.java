/* @test    @summary Test java.util.regex.Matcher.group(int) method for various scenarios    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupWithMatch();
        testGroupWithoutMatch();
        testGroupInvalidIndex();
        testGroupNegativeIndex();
    }

    static void testGroupWithMatch() {
        Pattern pattern = Pattern.compile("(\d+)-(\w+)");
        Matcher matcher = pattern.matcher("123-abc");
        boolean found = matcher.find();
        if (!found) {
            throw new AssertionError("Expected to find a match");
        }
        String group0 = matcher.group(0);
        String group1 = matcher.group(1);
        String group2 = matcher.group(2);
        if (!"123-abc".equals(group0)) {
            throw new AssertionError("group(0) mismatch: " + group0);
        }
        if (!"123".equals(group1)) {
            throw new AssertionError("group(1) mismatch: " + group1);
        }
        if (!"abc".equals(group2)) {
            throw new AssertionError("group(2) mismatch: " + group2);
        }
    }

    static void testGroupWithoutMatch() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("no digits here");
        // No match found, calling group should throw IllegalStateException
        try {
            matcher.group(1);
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testGroupInvalidIndex() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("123");
        matcher.find();
        try {
            matcher.group(3); // invalid group index
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testGroupNegativeIndex() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("123");
        matcher.find();
        try {
            matcher.group(-1); // negative group index
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
