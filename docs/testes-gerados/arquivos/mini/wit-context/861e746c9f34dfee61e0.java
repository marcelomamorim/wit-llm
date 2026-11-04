/*
 * @test
 * @summary Test sun.text.normalizer.UnicodeSet.clear() method behavior and exception on frozen state
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testClearOnUnfrozenSet();
        testClearOnFrozenSetThrows();
    }

    private static void testClearOnUnfrozenSet() {
        // Create a UnicodeSet with a range
        UnicodeSet set = new UnicodeSet(0x41, 0x5A); // A-Z
        // Clear should reset the set to empty (list[0] = HIGH, len=1, strings cleared)
        UnicodeSet result = set.clear();
        if (result != set) {
            throw new AssertionError("clear() should return this");
        }
        // After clear, the set should be empty, so contains any char should be false
        if (set.contains(0x41)) {
            throw new AssertionError("Set should be empty after clear()");
        }
    }

    private static void testClearOnFrozenSetThrows() {
        UnicodeSet set = new UnicodeSet(0x30, 0x39); // 0-9
        set.freeze();
        boolean threw = false;
        try {
            set.clear();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("clear() on frozen set should throw UnsupportedOperationException");
        }
    }
}
