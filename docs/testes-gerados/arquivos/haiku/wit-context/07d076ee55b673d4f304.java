/*
 * @test
 * @summary Behavioral checks for Matcher.results() forEach iteration, including ConcurrentModificationException when the matcher is modified during iteration
 * @run main MatcherWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testForEachRemainingCollectsAllMatches();
        testForEachRemainingNoMatch();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingCollectsAllMatches() {
        Matcher m = Pattern.compile("a+").matcher("baaca");
        List<String> found = new ArrayList<>();
        m.results().forEach(r -> found.add(r.group()));
        check(found.size() == 2, "expected 2 matches, got " + found.size());
        check("aa".equals(found.get(0)), "first match should be aa");
        check("a".equals(found.get(1)), "second match should be a");
    }

    static void testForEachRemainingNoMatch() {
        Matcher m = Pattern.compile("z").matcher("abc");
        List<String> found = new ArrayList<>();
        m.results().forEach(r -> found.add(r.group()));
        check(found.isEmpty(), "no action expected when there is no match");
    }

    static void testForEachRemainingConcurrentModification() {
        Matcher m = Pattern.compile("a").matcher("aaa");
        boolean thrown = false;
        try {
            m.results().forEach(r -> m.find());
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "expected ConcurrentModificationException when matcher is modified during forEach");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
