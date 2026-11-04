/*
 * @test
 * @summary Verify UnicodeSet.clear() returns the same instance and rejects changes on a frozen set
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testClearReturnsSameInstanceFromRange();
        testClearReturnsSameInstanceFromPattern();
        testClearRepeatedCallsReturnSameInstance();
        testClearOnFrozenSetThrows();
    }

    static void testClearReturnsSameInstanceFromRange() {
        UnicodeSet set = new UnicodeSet(0x41, 0x5A);
        UnicodeSet result = set.clear();
        if (result != set) {
            throw new RuntimeException("clear() must return this for range-constructed set");
        }
    }

    static void testClearReturnsSameInstanceFromPattern() {
        UnicodeSet set = new UnicodeSet("[a-z]");
        UnicodeSet result = set.clear();
        if (result != set) {
            throw new RuntimeException("clear() must return this for pattern-constructed set");
        }
    }

    static void testClearRepeatedCallsReturnSameInstance() {
        UnicodeSet set = new UnicodeSet(0x30, 0x39);
        UnicodeSet first = set.clear();
        UnicodeSet second = set.clear();
        if (first != set || second != set) {
            throw new RuntimeException("repeated clear() must keep returning this");
        }
    }

    static void testClearOnFrozenSetThrows() {
        UnicodeSet set = new UnicodeSet(0x30, 0x39);
        set.freeze();
        boolean thrown = false;
        try {
            set.clear();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("clear() on frozen set must throw UnsupportedOperationException");
        }
    }
}
