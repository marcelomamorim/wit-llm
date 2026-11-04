/*
 * @test
 * @summary Checks UnicodeSet.retainAll(UnicodeSet) intersection semantics, return value, and frozen-set rejection
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testRetainAllKeepsIntersectionAndReturnsThis();
        testRetainAllOnFrozenSetThrows();
    }

    static void testRetainAllKeepsIntersectionAndReturnsThis() {
        UnicodeSet a = new UnicodeSet(0x61, 0x63);
        UnicodeSet b = new UnicodeSet(0x62, 0x64);
        UnicodeSet result = a.retainAll(b);
        if (result != a) {
            throw new RuntimeException("retainAll must return this");
        }
        if (!a.contains(0x62) || !a.contains(0x63)) {
            throw new RuntimeException("intersection must contain b and c");
        }
        if (a.contains(0x61) || a.contains(0x64)) {
            throw new RuntimeException("intersection must not contain a or d");
        }
    }

    static void testRetainAllOnFrozenSetThrows() {
        UnicodeSet a = new UnicodeSet("[a-c]");
        a.freeze();
        UnicodeSet b = new UnicodeSet(0x62, 0x64);
        try {
            a.retainAll(b);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException on frozen set");
    }
}
