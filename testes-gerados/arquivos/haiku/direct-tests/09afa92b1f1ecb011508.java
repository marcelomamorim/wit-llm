/*
 * @test
 * @summary Behavioral tests for Matcher iteration (next) via results() iterator
 * @run main MatcherWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testIteratesAllMatches();
        testNoSuchElementAfterExhaustion();
        testConcurrentModificationAfterFind();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testIteratesAllMatches() {
        Matcher m = Pattern.compile("a([0-9])").matcher("a1 a2 a3");
        Iterator<MatchResult> it = m.results().iterator();
        List<String> groups = new ArrayList<>();
        while (it.hasNext()) {
            groups.add(it.next().group(1));
        }
        check(groups.size() == 3, "expected 3 matches but got " + groups.size());
        check(groups.get(0).equals("1"), "unexpected group 0: " + groups.get(0));
        check(groups.get(1).equals("2"), "unexpected group 1: " + groups.get(1));
        check(groups.get(2).equals("3"), "unexpected group 2: " + groups.get(2));
    }

    static void testNoSuchElementAfterExhaustion() {
        Iterator<MatchResult> it = Pattern.compile("x").matcher("x").results().iterator();
        it.next();
        boolean thrown = false;
        try {
            it.next();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        check(thrown, "expected NoSuchElementException after exhaustion");
    }

    static void testConcurrentModificationAfterFind() {
        Matcher m = Pattern.compile("a").matcher("aaa");
        Iterator<MatchResult> it = m.results().iterator();
        it.next();
        m.find();
        boolean thrown = false;
        try {
            it.next();
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException after find()");
    }
}
