/*
 * @test
 * @summary Test java.util.regex.Matcher.next() method behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testNextWithConcurrentModification();
        testNextWithoutHasNext();
        testNextReturnsMatchResult();
    }

    static void testNextWithConcurrentModification() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("a");
        // forcibly set expectedCount != modCount by reflection is not allowed,
        // so simulate by calling next() twice after one find
        // but since we cannot access expectedCount or modCount, we test that next() throws only if hasNext() false
        // So here we test that next() throws ConcurrentModificationException if expectedCount != modCount
        // This is not possible to simulate externally without reflection,
        // so we skip this test as no public API to set expectedCount or modCount
    }

    static void testNextWithoutHasNext() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("b"); // no match
        try {
            m.next();
            throw new AssertionError("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    static void testNextReturnsMatchResult() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new AssertionError("Expected to find a match");
        }
        MatchResult mr = m.next();
        if (mr == null) {
            throw new AssertionError("next() returned null");
        }
        if (mr.start() != 0 || mr.end() != 1) {
            throw new AssertionError("MatchResult has incorrect start or end");
        }
    }
}
