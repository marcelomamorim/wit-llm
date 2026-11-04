/*
 * @test
 * @summary Verify UnicodeSet.retainAll intersection semantics, return value and frozen-set behavior
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */
import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {
    public static void main(String[] args) {
        testIntersectionWithPattern();
        testEmptyIntersection();
        testRetainAllWithItself();
        testFrozenSetThrows();
    }

    static void testIntersectionWithPattern() {
        UnicodeSet a = new UnicodeSet(0x41, 0x43);
        UnicodeSet b = new UnicodeSet("[B-D]");
        UnicodeSet r = a.retainAll(b);
        check(r == a, "retainAll must return this");
        check(!a.contains('A'), "A must be removed");
        check(a.contains('B'), "B must be retained");
        check(a.contains('C'), "C must be retained");
        check(!a.contains('D'), "D must not be added");
    }

    static void testEmptyIntersection() {
        UnicodeSet c = new UnicodeSet(0x41, 0x43);
        c.retainAll(new UnicodeSet(0x50, 0x55));
        check(!c.contains('A'), "A must be removed on empty intersection");
        check(!c.contains('B'), "B must be removed on empty intersection");
        check(!c.contains('C'), "C must be removed on empty intersection");
    }

    static void testRetainAllWithItself() {
        UnicodeSet d = new UnicodeSet(0x61, 0x63);
        d.retainAll(d);
        check(d.contains('a'), "a must remain after self-intersection");
        check(d.contains('c'), "c must remain after self-intersection");
        check(!d.contains('d'), "d must not appear after self-intersection");
    }

    static void testFrozenSetThrows() {
        UnicodeSet f = new UnicodeSet(0x41, 0x43);
        f.freeze();
        try {
            f.retainAll(new UnicodeSet(0x41, 0x41));
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("retainAll on frozen set must throw UnsupportedOperationException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
