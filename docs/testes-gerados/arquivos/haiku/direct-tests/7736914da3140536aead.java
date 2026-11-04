/*
 * @test
 * @summary Basic observable behavior of UCharacterIterator.getInstance(String)
 * @modules java.base/sun.text.normalizer
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) throws Exception {
        testNonNullForRegularString();
        testNonNullForEmptyString();
        testReturnsNewInstanceEachCall();
        testInstanceOfUCharacterIterator();
        testIsCloneable();
        testDoneConstant();
    }

    static void testNonNullForRegularString() {
        UCharacterIterator it = UCharacterIterator.getInstance("abc");
        if (it == null) {
            throw new RuntimeException("getInstance(\"abc\") returned null");
        }
    }

    static void testNonNullForEmptyString() {
        UCharacterIterator it = UCharacterIterator.getInstance("");
        if (it == null) {
            throw new RuntimeException("getInstance(\"\") returned null");
        }
    }

    static void testReturnsNewInstanceEachCall() {
        UCharacterIterator a = UCharacterIterator.getInstance("xyz");
        UCharacterIterator b = UCharacterIterator.getInstance("xyz");
        if (a == b) {
            throw new RuntimeException("getInstance returned the same object for two calls");
        }
    }

    static void testInstanceOfUCharacterIterator() {
        Object it = UCharacterIterator.getInstance("abc");
        if (!(it instanceof UCharacterIterator)) {
            throw new RuntimeException("result is not a UCharacterIterator");
        }
    }

    static void testIsCloneable() {
        Object it = UCharacterIterator.getInstance("abc");
        if (!(it instanceof Cloneable)) {
            throw new RuntimeException("UCharacterIterator should implement Cloneable");
        }
    }

    static void testDoneConstant() {
        if (UCharacterIterator.DONE != -1) {
            throw new RuntimeException("DONE expected -1 but was " + UCharacterIterator.DONE);
        }
    }
}
