/*
 * @test
 * @summary Test cloneAsThawed method of UnicodeSet
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        // Create a UnicodeSet with a range
        UnicodeSet original = new UnicodeSet(0x41, 0x5A); // A-Z

        // Clone as thawed
        UnicodeSet clone = original.cloneAsThawed();

        // Check that clone is not frozen
        if (clone.isFrozen()) {
            throw new AssertionError("cloneAsThawed() returned a frozen UnicodeSet");
        }

        // Check that clone equals original
        if (!clone.equals(original)) {
            throw new AssertionError("cloneAsThawed() clone is not equal to original");
        }

        // Modify clone and check original is unchanged
        clone.add(0x61, 0x7A); // add a-z
        if (original.contains(0x61)) {
            throw new AssertionError("Modifying clone affected original");
        }

        // Check that original still contains only A-Z
        for (int c = 0x41; c <= 0x5A; c++) {
            if (!original.contains(c)) {
                throw new AssertionError("Original missing character after clone modification: " + (char)c);
            }
        }

        // Check that clone contains both A-Z and a-z
        for (int c = 0x41; c <= 0x5A; c++) {
            if (!clone.contains(c)) {
                throw new AssertionError("Clone missing uppercase character: " + (char)c);
            }
        }
        for (int c = 0x61; c <= 0x7A; c++) {
            if (!clone.contains(c)) {
                throw new AssertionError("Clone missing lowercase character: " + (char)c);
            }
        }
    }
}
