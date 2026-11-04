/* @test    @summary Test cloneAsThawed method of UnicodeSet for correct cloning and thawing behavior    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testCloneAsThawedCreatesMutableCopy();
        testCloneAsThawedDoesNotAlterOriginal();
    }

    private static void testCloneAsThawedCreatesMutableCopy() {
        UnicodeSet original = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet clone = original.cloneAsThawed();
        if (clone == null) {
            throw new AssertionError("cloneAsThawed returned null");
        }
        if (clone.isFrozen()) {
            throw new AssertionError("Cloned set should not be frozen");
        }
        // Verify that the clone contains the same elements
        for (int c = 0x0041; c <= 0x005A; c++) {
            if (!clone.contains(c)) {
                throw new AssertionError("Clone does not contain character: " + c);
            }
        }
        System.out.println("testCloneAsThawedCreatesMutableCopy passed");
    }

    private static void testCloneAsThawedDoesNotAlterOriginal() {
        UnicodeSet original = new UnicodeSet(0x0061, 0x007A); // a-z
        original.freeze();
        UnicodeSet clone = original.cloneAsThawed();
        if (clone.isFrozen()) {
            throw new AssertionError("Clone should not be frozen");
        }
        // Modify clone and verify original remains unchanged
        clone.add(0x00E9); // é
        if (original.contains(0x00E9)) {
            throw new AssertionError("Original set should not contain added character to clone");
        }
        System.out.println("testCloneAsThawedDoesNotAlterOriginal passed");
    }
}
