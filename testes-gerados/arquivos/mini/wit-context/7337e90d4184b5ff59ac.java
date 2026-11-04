/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.retainAll(UnicodeSet c) behavior including freeze check
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testRetainAllBasic();
        testRetainAllFrozenThrows();
    }

    private static void testRetainAllBasic() {
        // Create two UnicodeSets with overlapping ranges
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet set2 = new UnicodeSet(0x004D, 0x0060); // M-`

        // RetainAll should keep intersection: M-Z
        set1.retainAll(set2);

        // Check that set1 contains M and Z
        if (!set1.contains(0x004D) || !set1.contains(0x005A)) {
            throw new RuntimeException("retainAll failed to retain intersection characters");
        }

        // Check that set1 does not contain L (0x004C) which is before M
        if (set1.contains(0x004C)) {
            throw new RuntimeException("retainAll failed: contains character outside intersection");
        }
    }

    private static void testRetainAllFrozenThrows() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet set2 = new UnicodeSet(0x004D, 0x0060); // M-`

        // Freeze set1 to make it immutable
        set1.freeze();

        boolean threw = false;
        try {
            set1.retainAll(set2);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("retainAll did not throw UnsupportedOperationException on frozen set");
        }
    }
}
