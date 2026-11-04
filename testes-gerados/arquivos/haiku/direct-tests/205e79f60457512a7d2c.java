/*
 * @test
 * @summary Verify UCharacterIterator.getInstance(StringBuffer) returns an iterator over the buffer's UTF-16 code units
 * @modules java.base/sun.text.normalizer
 * @run main UCharacterIteratorWitupTest
 */

import sun.text.normalizer.UCharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testBasicIteration();
        testEmptyBuffer();
        testIndependentInstances();
    }

    static void testBasicIteration() {
        StringBuffer sb = new StringBuffer("abc");
        UCharacterIterator it = UCharacterIterator.getInstance(sb);
        check(it != null, "getInstance returned null");
        check(it.getLength() == 3, "length expected 3 but was " + it.getLength());
        check(it.getIndex() == 0, "initial index expected 0");
        check(it.current() == 'a', "current at start expected 'a'");
        check(it.next() == 'b', "next expected 'b'");
        check(it.getIndex() == 1, "index after next expected 1");
        check(it.next() == 'c', "next expected 'c'");
        check(it.next() == UCharacterIterator.DONE, "next past end expected DONE");
    }

    static void testEmptyBuffer() {
        UCharacterIterator it = UCharacterIterator.getInstance(new StringBuffer());
        check(it.getLength() == 0, "empty length expected 0");
        check(it.getIndex() == 0, "empty initial index expected 0");
        check(it.current() == UCharacterIterator.DONE, "empty current expected DONE");
        check(it.next() == UCharacterIterator.DONE, "empty next expected DONE");
    }

    static void testIndependentInstances() {
        UCharacterIterator a = UCharacterIterator.getInstance(new StringBuffer("x"));
        UCharacterIterator b = UCharacterIterator.getInstance(new StringBuffer("yz"));
        check(a.getLength() == 1, "first instance length expected 1");
        check(b.getLength() == 2, "second instance length expected 2");
        check(b.next() == 'z', "second instance next expected 'z'");
        check(a.getIndex() == 0, "first instance index should be unaffected");
        check(a.current() == 'x', "first instance current should be unaffected");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
