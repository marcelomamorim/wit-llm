/*
 * @test
 * @summary Witup-derived tests for UnicodeSet.complement(int, int) exception and range behavior
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */
import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testComplementReturnsThis();
        testComplementInvertsRange();
        testEmptyRangeIsNoop();
        testInvalidStartThrows();
        testInvalidEndThrows();
        testFrozenThrows();
    }

    static void testComplementReturnsThis() {
        final UnicodeSet s = new UnicodeSet(0x41, 0x5A);
        UnicodeSet r = s.complement(0, 0x10FFFF);
        if (r != s) {
            throw new RuntimeException("complement should return this");
        }
    }

    static void testComplementInvertsRange() {
        final UnicodeSet s = new UnicodeSet(0x41, 0x5A);
        s.complement(0, 0x10FFFF);
        if (s.contains(0x41)) {
            throw new RuntimeException("U+0041 should be removed by complement");
        }
        if (!s.contains(0x61)) {
            throw new RuntimeException("U+0061 should be added by complement");
        }
    }

    static void testEmptyRangeIsNoop() {
        final UnicodeSet s = new UnicodeSet(0x41, 0x5A);
        s.complement(10, 5);
        if (!s.contains(0x41)) {
            throw new RuntimeException("start > end should not change the set");
        }
        if (s.contains(0x61)) {
            throw new RuntimeException("start > end should not add code points");
        }
    }

    static void testInvalidStartThrows() {
        final UnicodeSet s = new UnicodeSet(0x41, 0x5A);
        expectThrows(IllegalArgumentException.class, () -> s.complement(-1, 5));
        expectThrows(IllegalArgumentException.class, () -> s.complement(0x110000, 0x110001));
    }

    static void testInvalidEndThrows() {
        final UnicodeSet s = new UnicodeSet(0x41, 0x5A);
        expectThrows(IllegalArgumentException.class, () -> s.complement(0, 0x110000));
        expectThrows(IllegalArgumentException.class, () -> s.complement(0, -1));
    }

    static void testFrozenThrows() {
        final UnicodeSet s = new UnicodeSet(0x41, 0x5A);
        s.freeze();
        expectThrows(UnsupportedOperationException.class, () -> s.complement(0, 10));
    }

    static void expectThrows(Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException("unexpected exception: " + t, t);
        }
        throw new RuntimeException("expected exception " + type.getName());
    }
}
