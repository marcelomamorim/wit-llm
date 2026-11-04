/*
 * @test
 * @summary Witup-generated behavioral tests for StringTokenIterator.setStart(int)
 * @modules java.base/sun.util.locale
 * @run main StringTokenIteratorWitupTest
 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        testOffsetGreaterThanLengthThrows();
        testOffsetEqualToLengthIsAccepted();
        testSetStartZeroSingleCharDelimiter();
        testSetStartMidTextSingleCharDelimiter();
        testSetStartMultiCharDelimiters();
        testSetStartReturnsSameInstance();
    }

    static void testOffsetGreaterThanLengthThrows() {
        StringTokenIterator it = new StringTokenIterator("abc", ";");
        boolean thrown = false;
        try {
            it.setStart(4);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "setStart(length+1) must throw IndexOutOfBoundsException");
    }

    static void testOffsetEqualToLengthIsAccepted() {
        StringTokenIterator it = new StringTokenIterator("abc", ";");
        it.setStart(3);
        check(it.currentStart() == 3, "currentStart should be 3");
        check(it.currentEnd() == 3, "currentEnd should be 3");
        check("".equals(it.current()), "token at end should be empty");
        check(!it.hasNext(), "no next token expected at end of text");
        check(!it.isDone(), "done should be false after setStart");
    }

    static void testSetStartZeroSingleCharDelimiter() {
        StringTokenIterator it = new StringTokenIterator("a;b", ";");
        it.setStart(0);
        check("a".equals(it.current()), "first token should be a");
        check(it.currentStart() == 0, "currentStart should be 0");
        check(it.currentEnd() == 1, "currentEnd should be 1");
        check(!it.isDone(), "done should be false");
    }

    static void testSetStartMidTextSingleCharDelimiter() {
        StringTokenIterator it = new StringTokenIterator("ab;cd;ef", ";");
        it.setStart(3);
        check("cd".equals(it.current()), "token from offset 3 should be cd");
        check(it.currentStart() == 3, "currentStart should be 3");
        check(it.currentEnd() == 5, "currentEnd should be 5");
    }

    static void testSetStartMultiCharDelimiters() {
        StringTokenIterator it = new StringTokenIterator("a,b;c", ";,");
        it.setStart(2);
        check("b".equals(it.current()), "token from offset 2 should be b");
        check(it.currentStart() == 2, "currentStart should be 2");
        check(it.currentEnd() == 3, "currentEnd should be 3");
    }

    static void testSetStartReturnsSameInstance() {
        StringTokenIterator it = new StringTokenIterator("a b", " ");
        StringTokenIterator ret = it.setStart(0);
        check(ret == it, "setStart must return this");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
