/* @test    @summary Test retainAll method of UnicodeSet for correct behavior and regressions    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testRetainAllWithOverlap();
        testRetainAllWithNoOverlap();
        testRetainAllWithEmptySet();
        testRetainAllWithSelf();
    }

    private static void testRetainAllWithOverlap() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet set2 = new UnicodeSet(0x0050, 0x0060); // P-`
        set1.retainAll(set2); // Should retain P to Z
        boolean containsP = set1.contains(0x0050); // P
        boolean containsZ = set1.contains(0x005A); // Z
        boolean containsA = set1.contains(0x0041); // A
        if (!containsP || !containsZ || containsA) {
            throw new AssertionError("Failed retainAll with overlap: " + set1);
        }
    }

    private static void testRetainAllWithNoOverlap() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet set2 = new UnicodeSet(0x0061, 0x007A); // a-z
        set1.retainAll(set2); // Should be empty
        if (set1.contains(0x0041) || set1.contains(0x0061)) {
            throw new AssertionError("Failed retainAll with no overlap: " + set1);
        }
    }

    private static void testRetainAllWithEmptySet() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet emptySet = new UnicodeSet(); // Empty set
        set1.retainAll(emptySet); // Should be empty
        if (set1.contains(0x0041)) {
            throw new AssertionError("Failed retainAll with empty set: " + set1);
        }
    }

    private static void testRetainAllWithSelf() {
        UnicodeSet set1 = new UnicodeSet(0x0041, 0x005A); // A-Z
        UnicodeSet copy = new UnicodeSet(set1);
        set1.retainAll(copy); // Should be unchanged
        if (!set1.equals(copy)) {
            throw new AssertionError("Failed retainAll with self: " + set1);
        }
    }
}
