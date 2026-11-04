/*
 * @test
 * @summary Behavioral tests for Matcher.forEachRemaining using only public API
 * @run main MatcherWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReportsAllMatches();
        testReportsGroupValues();
        testNoMatchInvokesNoAction();
        testRemainingFromCurrentMatch();
        testSecondCallIsNoOp();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testReportsAllMatches() {
        Matcher m = Pattern.compile("a").matcher("banana");
        List<Integer> starts = new ArrayList<>();
        m.forEachRemaining(mr -> starts.add(mr.start()));
        check(starts.equals(List.of(1, 3, 5)) || starts.equals(java.util.Arrays.asList(1, 3, 5)),
              "expected starts [1, 3, 5] but was " + starts);
    }

    static void testReportsGroupValues() {
        Matcher m = Pattern.compile("(an)").matcher("banana");
        List<String> groups = new ArrayList<>();
        m.forEachRemaining(mr -> groups.add(mr.group(1)));
        check(groups.size() == 2, "expected 2 groups but was " + groups);
        check("an".equals(groups.get(0)), "unexpected first group " + groups);
        check("an".equals(groups.get(1)), "unexpected second group " + groups);
    }

    static void testNoMatchInvokesNoAction() {
        Matcher m = Pattern.compile("z").matcher("banana");
        int[] calls = {0};
        m.forEachRemaining(mr -> calls[0]++);
        check(calls[0] == 0, "action should not be invoked, calls=" + calls[0]);
    }

    static void testRemainingFromCurrentMatch() {
        Matcher m = Pattern.compile("a").matcher("banana");
        check(m.find(), "first find should succeed");
        List<Integer> starts = new ArrayList<>();
        m.forEachRemaining(mr -> starts.add(mr.start()));
        check(starts.equals(java.util.Arrays.asList(1, 3, 5)),
              "expected [1, 3, 5] from current match but was " + starts);
    }

    static void testSecondCallIsNoOp() {
        Matcher m = Pattern.compile("a").matcher("banana");
        int[] calls = {0};
        m.forEachRemaining(mr -> calls[0]++);
        check(calls[0] == 3, "first call expected 3 calls but was " + calls[0]);
        m.forEachRemaining(mr -> calls[0]++);
        check(calls[0] == 3, "second call should be a no-op but calls=" + calls[0]);
    }
}
