/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet add(int c) method
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        // Test adding a single character to a new UnicodeSet
        UnicodeSet set = new UnicodeSet();
        set.add(65); // 'A'
        if (!set.contains(65)) {
            throw new RuntimeException("Failed to add character 65 (A) to UnicodeSet");
        }

        // Test adding multiple characters
        set.add(66); // 'B'
        set.add(67); // 'C'
        if (!set.contains(66) || !set.contains(67)) {
            throw new RuntimeException("Failed to add characters 66 (B) or 67 (C) to UnicodeSet");
        }

        // Test that add returns the same set instance (fluent API)
        UnicodeSet returned = set.add(68); // 'D'
        if (returned != set) {
            throw new RuntimeException("add(int) did not return the same UnicodeSet instance");
        }
        if (!set.contains(68)) {
            throw new RuntimeException("Failed to add character 68 (D) to UnicodeSet");
        }

        // Test that adding a character already in the set does not remove it
        set.add(65);
        if (!set.contains(65)) {
            throw new RuntimeException("Character 65 (A) missing after re-adding");
        }

        // Test that after freezing, add(int) throws UnsupportedOperationException
        set.freeze();
        boolean threw = false;
        try {
            set.add(69); // 'E'
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException when adding to frozen UnicodeSet");
        }
    }
}
