/*
 * @test
 * @summary Witup-derived checks for the MatchResult iterator behind Matcher.results(): next() after exhaustion and after a reset during iteration
 * @run main MatcherWitupTest
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNextReturnsMatchesInOrder() {
        Matcher m = Pattern.compile("a").matcher("aba");
        Iterator<MatchResult> it = m.results().iterator();
        check(it.hasNext(), "expected first match");
        MatchResult first = it.next();
        check(first.start() == 0 && first.end() == 1, "first match wrong");
        check(it.hasNext(), "expected second match");
        MatchResult second = it.next();
        check(second.start() == 2 && second.end() == 3, "second match wrong");
        check(!it.hasNext(), "no more matches expected");
    }

    static void testNextAfterExhaustionThrows() {
        Matcher m = Pattern.compile("a").matcher("a");
        Iterator<MatchResult> it = m.results().iterator();
        it.next();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "next() after exhaustion must throw NoSuchElementException");
    }

    static void testNextAfterResetThrowsConcurrentModification() {
        Matcher m = Pattern.compile("a").matcher("aa");
        Iterator<MatchResult> it = m.results().iterator();
        it.next();
        m.reset();
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "next() after reset must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testNextReturnsMatchesInOrder();
        testNextAfterExhaustionThrows();
        testNextAfterResetThrowsConcurrentModification();
    }
}
