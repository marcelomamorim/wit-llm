/* @test    @summary Test for UnicodeSet.cloneAsThawed() method to verify it returns a non-frozen copy.    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testCloneAsThawedReturnsMutableCopy();
    }

    public static void testCloneAsThawedReturnsMutableCopy() {
        UnicodeSet original = new UnicodeSet();
        UnicodeSet clone = original.cloneAsThawed();
        if (clone == null) {
            throw new AssertionError("cloneAsThawed() returned null");
        }
        if (clone.isFrozen()) {
            throw new AssertionError("cloneAsThawed() returned a frozen set");
        }
        // Verify that the clone is a different object
        if (clone == original) {
            throw new AssertionError("cloneAsThawed() returned the same object as original");
        }
        // Verify that modifications to clone do not affect original
        try {
            clone.add(0x0041); // Add 'A'
        } catch (UnsupportedOperationException e) {
            throw new AssertionError("clone should be mutable, but add() threw exception", e);
        }
        if (original.contains(0x0041)) {
            throw new AssertionError("Original set was modified when changing clone");
        }
        System.out.println("testCloneAsThawedReturnsMutableCopy passed.");
    }
}
