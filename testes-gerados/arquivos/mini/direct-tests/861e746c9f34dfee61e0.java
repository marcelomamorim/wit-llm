/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.clear() method behavior
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        // Test clear() on a newly created UnicodeSet with a range
        UnicodeSet set = new UnicodeSet(0x41, 0x5A); // A-Z
        set.clear();
        // After clear, the set should be empty (len == 1 and list[0] == HIGH)
        // We cannot access list or len directly, so test observable behavior
        // The set should not contain any character, e.g. 'A'
        if (set.contains('A')) {
            throw new RuntimeException("clear() failed: set still contains 'A'");
        }
        // The set should not contain any string
        if (set.contains("A")) {
            throw new RuntimeException("clear() failed: set still contains string 'A'");
        }

        // Test clear() on a UnicodeSet created from pattern with strings
        UnicodeSet set2 = new UnicodeSet("[a-z]{abc}");
        set2.clear();
        if (set2.contains('a')) {
            throw new RuntimeException("clear() failed: set2 still contains 'a'");
        }
        if (set2.contains("abc")) {
            throw new RuntimeException("clear() failed: set2 still contains string 'abc'");
        }

        // Test that clear() returns the same instance
        UnicodeSet set3 = new UnicodeSet(0, 10);
        UnicodeSet returned = set3.clear();
        if (returned != set3) {
            throw new RuntimeException("clear() did not return this");
        }

        // Test that clear() on frozen set throws UnsupportedOperationException
        UnicodeSet set4 = new UnicodeSet(0, 5);
        set4.freeze();
        boolean threw = false;
        try {
            set4.clear();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("clear() did not throw on frozen set");
        }

        System.out.println("All tests passed.");
    }
}
