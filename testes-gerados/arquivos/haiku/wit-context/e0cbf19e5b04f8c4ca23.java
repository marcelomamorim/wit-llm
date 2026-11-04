/*
 * @test
 * @summary Witup-generated checks for UnicodeSet.set(UnicodeSet)
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testSetCopiesRangeAndReturnsThis();
        testFrozenTargetThrowsAndIsUnchanged();
        testFrozenSourceIsCopiedToUnfrozenTarget();
    }

    static void testSetCopiesRangeAndReturnsThis() {
        UnicodeSet src = new UnicodeSet(65, 67);
        UnicodeSet dst = new UnicodeSet(100, 100);
        UnicodeSet result = dst.set(src);
        if (result != dst) {
            throw new RuntimeException("set(UnicodeSet) must return this");
        }
        if (!dst.contains(65) || !dst.contains(66) || !dst.contains(67)) {
            throw new RuntimeException("copied range A-C not present");
        }
        if (dst.contains(100)) {
            throw new RuntimeException("previous content not replaced");
        }
    }

    static void testFrozenTargetThrowsAndIsUnchanged() {
        UnicodeSet dst = new UnicodeSet(100, 100);
        dst.freeze();
        UnicodeSet src = new UnicodeSet(65, 65);
        try {
            dst.set(src);
            throw new RuntimeException("expected UnsupportedOperationException for frozen target");
        } catch (UnsupportedOperationException expected) {
            // expected
        }
        if (!dst.contains(100) || dst.contains(65)) {
            throw new RuntimeException("frozen target modified despite exception");
        }
    }

    static void testFrozenSourceIsCopiedToUnfrozenTarget() {
        UnicodeSet src = new UnicodeSet(70, 71);
        src.freeze();
        UnicodeSet dst = new UnicodeSet(1, 1);
        dst.set(src);
        if (!dst.contains(70) || !dst.contains(71) || dst.contains(1)) {
            throw new RuntimeException("frozen source contents not copied");
        }
    }
}
