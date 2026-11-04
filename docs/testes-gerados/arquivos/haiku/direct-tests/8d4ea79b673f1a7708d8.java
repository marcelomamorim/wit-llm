/*
 * @test
 * @summary Behavioral tests for UnicodeSet.add(CharSequence) through public API
 * @modules java.base/sun.text.normalizer
 * @run main UnicodeSetWitupTest
 */

import sun.text.normalizer.UnicodeSet;

public class UnicodeSetWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAddSingleCharString() {
        UnicodeSet set = new UnicodeSet("[]");
        set.add("a");
        check(set.contains('a'), "single-char string should add its code point");
        check(!set.contains('b'), "unrelated code point should not be contained");
    }

    static void testAddReturnsSameInstance() {
        UnicodeSet set = new UnicodeSet("[]");
        UnicodeSet result = set.add("x");
        check(result == set, "add(CharSequence) should return this");
    }

    static void testAddSupplementaryCodePoint() {
        UnicodeSet set = new UnicodeSet("[]");
        String s = new String(Character.toChars(0x1F600));
        set.add(s);
        check(set.contains(0x1F600), "surrogate pair string should add supplementary code point");
        check(!set.contains(0x1F601), "neighbouring supplementary code point should not be contained");
    }

    static void testAddMultiCharStringNotCodePoint() {
        UnicodeSet set = new UnicodeSet("[]");
        set.add("ab");
        check(!set.contains('a'), "multi-char string should not add its first char as code point");
        check(!set.contains('b'), "multi-char string should not add its second char as code point");
    }

    static void testAddToFrozenSetThrows() {
        UnicodeSet set = new UnicodeSet("[a]");
        set.freeze();
        boolean thrown = false;
        try {
            set.add("b");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "add on frozen set should throw UnsupportedOperationException");
        check(!set.contains('b'), "frozen set should not change after rejected add");
    }

    public static void main(String[] args) {
        testAddSingleCharString();
        testAddReturnsSameInstance();
        testAddSupplementaryCodePoint();
        testAddMultiCharStringNotCodePoint();
        testAddToFrozenSetThrows();
    }
}
