/* @test    @summary Test UnicodeSet.clear() method for proper reset behavior    @run main UnicodeSetWitupTest */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testClearResetsSet();
        testClearOnEmptySet();
        testClearMultipleTimes();
    }

    private static void testClearResetsSet() {
        UnicodeSet set = new UnicodeSet(0x0041, 0x005A); // A-Z
        set.clear();
        boolean containsA = set.contains(0x0041); // 'A'
        boolean containsZ = set.contains(0x005A); // 'Z'
        if (containsA || containsZ) {
            throw new AssertionError("After clear(), set should not contain any characters.");
        }
    }

    private static void testClearOnEmptySet() {
        UnicodeSet emptySet = new UnicodeSet();
        emptySet.clear();
        boolean containsAny = emptySet.contains(0x0000);
        if (containsAny) {
            throw new AssertionError("Clear on empty set should not add characters.");
        }
    }

    private static void testClearMultipleTimes() {
        UnicodeSet set = new UnicodeSet(0x0061, 0x007A); // a-z
        set.clear();
        set.clear();
        set.clear();
        boolean containsA = set.contains(0x0061); // 'a'
        if (containsA) {
            throw new AssertionError("Multiple clears should not re-add characters.");
        }
    }
}
