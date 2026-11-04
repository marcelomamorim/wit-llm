/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.cloneAsThawed() method
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testCloneAsThawedReturnsDistinctMutableCopy();
    }

    private static void testCloneAsThawedReturnsDistinctMutableCopy() {
        // Create a UnicodeSet with a range
        UnicodeSet original = new UnicodeSet(0x41, 0x5A); // A-Z
        // Freeze the original to simulate a frozen set
        original.freeze();

        // Clone as thawed should return a new UnicodeSet that is not frozen
        UnicodeSet clone = original.cloneAsThawed();

        if (clone == original) {
            throw new AssertionError("cloneAsThawed() returned the same instance");
        }

        if (clone.isFrozen()) {
            throw new AssertionError("cloneAsThawed() returned a frozen instance");
        }

        // The clone should be equal to the original
        if (!clone.equals(original)) {
            throw new AssertionError("cloneAsThawed() returned a set not equal to original");
        }

        // Modifying the clone should not affect the original
        clone.add(0x61); // add 'a'
        if (original.contains(0x61)) {
            throw new AssertionError("Modifying clone affected the original");
        }

        // The clone should contain the newly added character
        if (!clone.contains(0x61)) {
            throw new AssertionError("Clone does not contain added character");
        }
    }
}
