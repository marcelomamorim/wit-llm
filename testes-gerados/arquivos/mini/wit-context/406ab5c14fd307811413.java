/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet(String) constructor behavior
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testValidPattern();
        testEmptyPattern();
        testFrozenSetThrows();
    }

    private static void testValidPattern() {
        // Test constructor with a valid pattern string
        UnicodeSet set = new UnicodeSet("[a-z]");
        if (!set.contains('a') || !set.contains('z')) {
            throw new RuntimeException("UnicodeSet does not contain expected characters for pattern [a-z]");
        }
    }

    private static void testEmptyPattern() {
        // Test constructor with empty pattern string
        UnicodeSet set = new UnicodeSet("");
        // The set should be empty, so it should not contain 'a'
        if (set.contains('a')) {
            throw new RuntimeException("UnicodeSet constructed with empty pattern should not contain 'a'");
        }
    }

    private static void testFrozenSetThrows() {
        // Create a UnicodeSet and freeze it, then try to construct a new UnicodeSet with pattern
        // that triggers checkFrozen() indirectly via applyPattern
        UnicodeSet frozenSet = new UnicodeSet("[a-z]");
        frozenSet.freeze();
        try {
            // The constructor calls applyPattern which calls checkFrozen internally
            // so constructing a new UnicodeSet with a pattern on a frozen set should not throw,
            // but if we try to modify the frozen set, it should throw UnsupportedOperationException
            frozenSet.add('b');
            throw new RuntimeException("Expected UnsupportedOperationException when modifying frozen UnicodeSet");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
