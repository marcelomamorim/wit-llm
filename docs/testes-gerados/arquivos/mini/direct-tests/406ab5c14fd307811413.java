/*
 * @test
 * @summary Test UnicodeSet(String) constructor with various patterns
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testEmptyPattern();
        testSingleCharPattern();
        testRangePattern();
        testComplexPattern();
    }

    private static void testEmptyPattern() {
        UnicodeSet set = new UnicodeSet("");
        // An empty pattern should create an empty set
        if (set.size() != 0) {
            throw new RuntimeException("Expected empty UnicodeSet for empty pattern");
        }
    }

    private static void testSingleCharPattern() {
        UnicodeSet set = new UnicodeSet("a");
        // The set should contain 'a'
        if (!set.contains('a')) {
            throw new RuntimeException("Expected UnicodeSet to contain 'a'");
        }
        // The set should not contain 'b'
        if (set.contains('b')) {
            throw new RuntimeException("Expected UnicodeSet not to contain 'b'");
        }
    }

    private static void testRangePattern() {
        UnicodeSet set = new UnicodeSet("[a-z]");
        // The set should contain 'a' and 'z'
        if (!set.contains('a') || !set.contains('z')) {
            throw new RuntimeException("Expected UnicodeSet to contain 'a' and 'z'");
        }
        // The set should not contain 'A'
        if (set.contains('A')) {
            throw new RuntimeException("Expected UnicodeSet not to contain 'A'");
        }
    }

    private static void testComplexPattern() {
        UnicodeSet set = new UnicodeSet("[a-cx-z]");
        // The set should contain 'a', 'b', 'c', 'x', 'y', 'z'
        char[] included = {'a', 'b', 'c', 'x', 'y', 'z'};
        for (char c : included) {
            if (!set.contains(c)) {
                throw new RuntimeException("Expected UnicodeSet to contain '" + c + "'");
            }
        }
        // The set should not contain 'd'
        if (set.contains('d')) {
            throw new RuntimeException("Expected UnicodeSet not to contain 'd'");
        }
    }
}