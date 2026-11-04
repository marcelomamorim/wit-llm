/*
 * @test
 * @summary Test UnicodeSet.add(CharSequence) method behavior
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testAddSingleCodePoint();
        testAddString();
        testAddEmptyString();
    }

    private static void testAddSingleCodePoint() {
        UnicodeSet set = new UnicodeSet();
        set.add("a"); // single char string
        if (!set.contains('a')) {
            throw new RuntimeException("Failed to add single code point 'a'");
        }
    }

    private static void testAddString() {
        UnicodeSet set = new UnicodeSet();
        String multiChar = "ab";
        set.add(multiChar);
        if (set.contains('a') || set.contains('b')) {
            throw new RuntimeException("Adding multi-char string should not add individual chars");
        }
        // The strings field is not accessible, so we test by adding the same string again and checking no exception
        set.add(multiChar);
    }

    private static void testAddEmptyString() {
        UnicodeSet set = new UnicodeSet();
        String empty = "";
        set.add(empty);
        // Adding empty string should add it to strings collection, no exception expected
        set.add(empty);
    }
}
