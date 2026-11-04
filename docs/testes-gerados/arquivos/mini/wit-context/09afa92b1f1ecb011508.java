/*
 * @test
 * @summary Test java.util.regex.Matcher.next() for expected exceptions and normal behavior
 * @run main MatcherWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testNextThrowsConcurrentModificationException();
        testNextThrowsNoSuchElementException();
        testNextReturnsMatchResult();
    }

    private static void testNextThrowsConcurrentModificationException() {
        // Setup a Matcher and forcibly set expectedCount != modCount by using the iterator
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("a");
        java.util.Iterator<MatchResult> it = m.results().iterator();

        // The iterator's next() calls Matcher.next() internally
        // We simulate concurrent modification by calling next() twice on iterator
        // The first next() should succeed
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next");
        }
        MatchResult first = it.next();
        if (first == null) {
            throw new AssertionError("First match result should not be null");
        }

        // The second call to next() on the iterator should throw ConcurrentModificationException
        // because expectedCount != modCount after first next()
        boolean cmeThrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException cme) {
            cmeThrown = true;
        }
        if (!cmeThrown) {
            throw new AssertionError("Expected ConcurrentModificationException was not thrown");
        }
    }

    private static void testNextThrowsNoSuchElementException() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher(""); // empty input, no matches
        java.util.Iterator<MatchResult> it = m.results().iterator();

        // Iterator has no next element
        if (it.hasNext()) {
            throw new AssertionError("Iterator should not have next");
        }

        boolean nseeThrown = false;
        try {
            it.next();
        } catch (NoSuchElementException nsee) {
            nseeThrown = true;
        }
        if (!nseeThrown) {
            throw new AssertionError("Expected NoSuchElementException was not thrown");
        }
    }

    private static void testNextReturnsMatchResult() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("a");

        // Call next() via results iterator and verify returned MatchResult
        java.util.Iterator<MatchResult> it = m.results().iterator();
        if (!it.hasNext()) {
            throw new AssertionError("Iterator should have next");
        }
        MatchResult mr = it.next();
        if (mr == null) {
            throw new AssertionError("MatchResult should not be null");
        }
        if (mr.start() != 0 || mr.end() != 1) {
            throw new AssertionError("MatchResult start/end indices incorrect");
        }
    }
}
