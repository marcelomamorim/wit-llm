/*
 * @test
 * @summary Tests that StringTokenIterator.first() resets the iterator to the first token
 * @modules java.base/sun.util.locale
 * @run main StringTokenIteratorWitupTest
 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkEquals(Object expected, Object actual, String message) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new RuntimeException(message + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }

    public static void main(String[] args) {
        testFirstAfterIterationReturnsFirstToken();
        testFirstOnFreshIteratorReturnsFirstToken();
        testFirstWithEmptyLeadingToken();
        testFirstWithMultiCharDelimiters();
        testFirstResetsDoneState();
        testFirstWithEmptyText();
    }

    static void testFirstAfterIterationReturnsFirstToken() {
        StringTokenIterator it = new StringTokenIterator("ab,cd", ",");
        checkEquals("cd", advanceToSecond(it), "second token");
        checkEquals("ab", it.first(), "first() after iteration");
        checkEquals("ab", it.current(), "current() after first()");
        checkEquals(Integer.valueOf(0), Integer.valueOf(it.currentStart()), "currentStart after first()");
        checkEquals(Integer.valueOf(2), Integer.valueOf(it.currentEnd()), "currentEnd after first()");
    }

    static String advanceToSecond(StringTokenIterator it) {
        it.next();
        return it.next();
    }

    static void testFirstOnFreshIteratorReturnsFirstToken() {
        StringTokenIterator it = new StringTokenIterator("xy;zw", ";");
        checkEquals("xy", it.first(), "first() on fresh iterator");
        check(!it.isDone(), "iterator must not be done after first()");
    }

    static void testFirstWithEmptyLeadingToken() {
        StringTokenIterator it = new StringTokenIterator(",ab", ",");
        checkEquals("", it.first(), "first() with leading delimiter");
        checkEquals("ab", it.next(), "next() after leading empty token");
    }

    static void testFirstWithMultiCharDelimiters() {
        StringTokenIterator it = new StringTokenIterator("a;b", ";,");
        it.next();
        checkEquals("a", it.first(), "first() with multi-char delimiters");
    }

    static void testFirstResetsDoneState() {
        StringTokenIterator it = new StringTokenIterator("q", ",");
        it.next();
        check(it.isDone(), "iterator should be done after exhausting tokens");
        checkEquals("q", it.first(), "first() after done");
        check(!it.isDone(), "first() must clear done state");
    }

    static void testFirstWithEmptyText() {
        StringTokenIterator it = new StringTokenIterator("", ",");
        checkEquals("", it.first(), "first() on empty text");
        check(!it.hasNext(), "hasNext() on empty text must be false");
    }
}
