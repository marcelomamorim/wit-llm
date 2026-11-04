/*
 * @test
 * @summary Verifies public behavior of StringTokenIterator.setText(String): it returns the same instance, resets iteration to the start of the new text, and clears the done state.
 * @modules java.base/sun.util.locale
 * @run main StringTokenIteratorWitupTest
 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        testReturnsSameInstanceAndResetsToStart();
        testResetsAfterExhaustion();
        testEmptyText();
        testMultiCharDelimiterSet();
        testLeadingDelimiterInNewText();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReturnsSameInstanceAndResetsToStart() {
        StringTokenIterator it = new StringTokenIterator("ab-cd", "-");
        it.next();
        StringTokenIterator r = it.setText("x-y");
        check(r == it, "setText must return the same instance");
        check("x".equals(it.current()), "current token should be first token of new text");
        check(it.currentStart() == 0, "currentStart should be 0 after setText");
        check(it.currentEnd() == 1, "currentEnd should be 1 after setText");
        check(!it.isDone(), "isDone should be false after setText");
        check(it.hasNext(), "hasNext should be true for multi-token text");
        check("y".equals(it.next()), "next token should be y");
    }

    static void testResetsAfterExhaustion() {
        StringTokenIterator it = new StringTokenIterator("a,b", ",");
        while (it.next() != null) {
            // exhaust iterator
        }
        check(it.isDone(), "iterator should be done after exhaustion");
        it.setText("c,d");
        check(!it.isDone(), "setText must clear done state");
        check("c".equals(it.current()), "current should be c after setText");
        check("d".equals(it.next()), "next should be d after setText");
    }

    static void testEmptyText() {
        StringTokenIterator it = new StringTokenIterator("a,b", ",");
        it.setText("");
        check("".equals(it.current()), "current should be empty string for empty text");
        check(it.currentStart() == 0, "currentStart should be 0 for empty text");
        check(it.currentEnd() == 0, "currentEnd should be 0 for empty text");
        check(!it.hasNext(), "hasNext should be false for empty text");
        check(it.next() == null, "next should return null for empty text");
        check(it.isDone(), "iterator should be done after next on empty text");
    }

    static void testMultiCharDelimiterSet() {
        StringTokenIterator it = new StringTokenIterator("x", "-_");
        it.setText("a_b-c");
        check("a".equals(it.current()), "first token should be a");
        check("b".equals(it.next()), "second token should be b");
        check("c".equals(it.next()), "third token should be c");
        check(it.next() == null, "no more tokens expected");
    }

    static void testLeadingDelimiterInNewText() {
        StringTokenIterator it = new StringTokenIterator("a-b", "-");
        it.setText("-a");
        check("".equals(it.current()), "leading delimiter should yield empty first token");
        check(it.currentStart() == 0, "currentStart should be 0");
        check(it.currentEnd() == 0, "currentEnd should be 0 at leading delimiter");
        check("a".equals(it.next()), "second token should be a");
    }
}
