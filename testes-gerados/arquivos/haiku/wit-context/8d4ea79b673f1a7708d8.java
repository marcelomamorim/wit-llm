/*
 * @test
 * @summary Witup-generated tests for UnicodeSet.add(CharSequence)
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */
import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    public static void main(String[] args) {
        testAddSingleCodePointReturnsThisAndContains();
        testAddMultiCharStringDoesNotAddCodePoints();
        testAddOnFrozenSetThrowsUnsupportedOperation();
    }

    static void testAddSingleCodePointReturnsThisAndContains() {
        UnicodeSet set = new UnicodeSet(0x50, 0x50);
        UnicodeSet result = set.add("A");
        if (result != set) {
            throw new AssertionError("add(CharSequence) must return this");
        }
        if (!set.contains(0x41)) {
            throw new AssertionError("single code point 'A' not contained after add");
        }
        if (!set.contains(0x50)) {
            throw new AssertionError("pre-existing code point 'P' lost after add");
        }
        if (set.contains(0x42)) {
            throw new AssertionError("unexpected 'B' contained");
        }
    }

    static void testAddMultiCharStringDoesNotAddCodePoints() {
        UnicodeSet set = new UnicodeSet(0x41, 0x41);
        set.add("XY");
        if (set.contains('X')) {
            throw new AssertionError("multi-char string must not add code point 'X'");
        }
        if (set.contains('Y')) {
            throw new AssertionError("multi-char string must not add code point 'Y'");
        }
        if (!set.contains(0x41)) {
            throw new AssertionError("existing 'A' lost after multi-char add");
        }
    }

    static void testAddOnFrozenSetThrowsUnsupportedOperation() {
        UnicodeSet set = new UnicodeSet(0x41, 0x41);
        set.freeze();
        boolean thrown = false;
        try {
            set.add("B");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("add on frozen set must throw UnsupportedOperationException");
        }
        if (set.contains(0x42)) {
            throw new AssertionError("frozen set was modified despite exception");
        }
    }
}
