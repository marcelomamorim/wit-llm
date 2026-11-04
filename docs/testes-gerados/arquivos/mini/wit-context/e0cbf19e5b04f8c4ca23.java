/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.set(UnicodeSet other) method behavior including exception on frozen state
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;
import java.util.TreeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testSetCopiesContent();
        testSetThrowsIfFrozen();
    }

    private static void testSetCopiesContent() {
        UnicodeSet set1 = new UnicodeSet(0x41, 0x5A); // A-Z
        UnicodeSet set2 = new UnicodeSet(0x61, 0x7A); // a-z
        set1.set(set2);
        // After set, set1 should contain a-z range
        for (int c = 0x61; c <= 0x7A; c++) {
            if (!set1.contains(c)) {
                throw new AssertionError("set1 should contain character: " + (char)c);
            }
        }
        // set1 should not contain A-Z anymore
        for (int c = 0x41; c <= 0x5A; c++) {
            if (set1.contains(c)) {
                throw new AssertionError("set1 should not contain character: " + (char)c);
            }
        }
    }

    private static void testSetThrowsIfFrozen() {
        UnicodeSet set1 = new UnicodeSet(0x30, 0x39); // 0-9
        UnicodeSet set2 = new UnicodeSet(0x41, 0x46); // A-F
        set1.freeze();
        try {
            set1.set(set2);
            throw new AssertionError("Expected UnsupportedOperationException when calling set() on frozen UnicodeSet");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
    }
}
