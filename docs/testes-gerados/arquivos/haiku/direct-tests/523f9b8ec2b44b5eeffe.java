/*
 * @test
 * @summary Behavioral tests for StringTokenIterator.setStart(int)
 * @modules java.base/sun.util.locale
 * @run main StringTokenIteratorWitupTest
 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        singleCharDelimiter();
        multiCharDelimiters();
        offsetPastEndThrows();
        setStartResetsDone();
        emptyText();
    }

    static void singleCharDelimiter() {
        StringTokenIterator it = new StringTokenIterator("ab-cd", "-");
        check(it.setStart(0) == it, "setStart must return this");
        check("ab".equals(it.current()), "token at offset 0 should be ab");
        check(it.currentStart() == 0, "start should be 0");
        check(it.currentEnd() == 2, "end should be 2");
        check(!it.isDone(), "done should be false after setStart");

        it.setStart(3);
        check("cd".equals(it.current()), "token at offset 3 should be cd");
        check(it.currentStart() == 3, "start should be 3");
        check(it.currentEnd() == 5, "end should be 5");

        it.setStart(5);
        check("".equals(it.current()), "token at end of text should be empty");
        check(it.currentStart() == 5, "start should be 5");
        check(!it.hasNext(), "no next token at end of text");
    }

    static void multiCharDelimiters() {
        StringTokenIterator it = new StringTokenIterator("a,b;c", ",;");
        it.setStart(2);
        check("b".equals(it.current()), "token at offset 2 should be b");
        check(it.currentEnd() == 3, "end should stop at ';' index 3");

        it.setStart(0);
        check("a".equals(it.current()), "token at offset 0 should be a");
        check(it.currentEnd() == 1, "end should stop at ',' index 1");
    }

    static void offsetPastEndThrows() {
        StringTokenIterator it = new StringTokenIterator("abc", "-");
        boolean thrown = false;
        try {
            it.setStart(4);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "offset greater than text length must throw IndexOutOfBoundsException");
    }

    static void setStartResetsDone() {
        StringTokenIterator it = new StringTokenIterator("ab", "-");
        it.setStart(2);
        it.next();
        check(it.isDone(), "iterator should be done after advancing past last token");
        it.setStart(0);
        check(!it.isDone(), "setStart must reset done to false");
        check("ab".equals(it.current()), "token after reset should be ab");
    }

    static void emptyText() {
        StringTokenIterator it = new StringTokenIterator("", "-");
        it.setStart(0);
        check("".equals(it.current()), "empty text should yield empty token at offset 0");
        check(it.currentStart() == 0 && it.currentEnd() == 0, "start and end should be 0");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
