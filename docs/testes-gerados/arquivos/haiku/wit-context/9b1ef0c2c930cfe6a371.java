/*
 * @test
 * @summary WITUP-generated behavioral tests for StringTokenIterator.setText
 * @modules java.base/sun.util.locale
 * @run main StringTokenIteratorWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest {

    public static void main(String[] args) {
        testSetTextResetsIteration();
        testSetTextEmptyText();
        testSetTextSingleCharDelimiter();
        testSetTextMultiCharDelimiters();
        testSetTextNullThrowsNPE();
        testSetTextReturnsSameInstance();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<String> collectTokens(StringTokenIterator it) {
        List<String> out = new ArrayList<>();
        String t = it.first();
        while (t != null) {
            out.add(t);
            t = it.next();
        }
        return out;
    }

    static void testSetTextResetsIteration() {
        StringTokenIterator it = new StringTokenIterator("ab,cd", ",");
        it.next();
        it.next();
        it.next();
        check(it.isDone(), "iterator should be done after exhausting tokens");

        it.setText("x,y");
        check(!it.isDone(), "setText should reset done flag");
        check("x".equals(it.current()), "setText should reset current token to first token");
        check(it.currentStart() == 0, "setText should reset start to 0");
        check(it.currentEnd() == 1, "setText should reset end to first delimiter");
        check("y".equals(it.next()), "second token after setText should be y");
    }

    static void testSetTextEmptyText() {
        StringTokenIterator it = new StringTokenIterator("abc", ",");
        it.setText("");
        check("".equals(it.current()), "empty text should yield empty current token");
        check(it.currentStart() == 0, "empty text start should be 0");
        check(it.currentEnd() == 0, "empty text end should be 0");
        check(!it.hasNext(), "empty text should have no next token");
        check(it.next() == null, "next on empty text should return null");
        check(it.isDone(), "iterator should be done after next on empty text");
    }

    static void testSetTextSingleCharDelimiter() {
        StringTokenIterator it = new StringTokenIterator("a", ",");
        it.setText("p,q,r");
        List<String> tokens = collectTokens(it);
        check(tokens.size() == 3, "expected 3 tokens but got " + tokens.size());
        check("p".equals(tokens.get(0)), "token 0 should be p");
        check("q".equals(tokens.get(1)), "token 1 should be q");
        check("r".equals(tokens.get(2)), "token 2 should be r");
    }

    static void testSetTextMultiCharDelimiters() {
        StringTokenIterator it = new StringTokenIterator("a", " ;");
        it.setText("a;b c");
        List<String> tokens = collectTokens(it);
        check(tokens.size() == 3, "expected 3 tokens but got " + tokens.size());
        check("a".equals(tokens.get(0)), "token 0 should be a");
        check("b".equals(tokens.get(1)), "token 1 should be b");
        check("c".equals(tokens.get(2)), "token 2 should be c");
    }

    static void testSetTextNullThrowsNPE() {
        StringTokenIterator it = new StringTokenIterator("a", ",");
        boolean thrown = false;
        try {
            it.setText(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "setText(null) should throw NullPointerException");
    }

    static void testSetTextReturnsSameInstance() {
        StringTokenIterator it = new StringTokenIterator("a", ",");
        StringTokenIterator returned = it.setText("b,c");
        check(returned == it, "setText should return the same iterator instance");
    }
}
