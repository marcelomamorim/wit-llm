/*
 * @test
 * @summary Test java.util.regex.Matcher.forEachRemaining method behavior
 * @run main MatcherWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Consumer;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingNoMatches();
        testForEachRemainingConcurrentModification();
    }

    private static void testForEachRemainingNormal() {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher("123 abc 456 7890");

        final StringBuilder sb = new StringBuilder();
        Consumer<MatchResult> consumer = new Consumer<MatchResult>() {
            public void accept(MatchResult mr) {
                sb.append(mr.group()).append(",");
            }
        };

        // find() not called before, state < 0, so forEachRemaining should find all matches
        m.forEachRemaining(consumer);

        String result = sb.toString();
        if (!result.equals("123,456,7890,")) {
            throw new AssertionError("Expected matches '123,456,7890,' but got: " + result);
        }
    }

    private static void testForEachRemainingNoMatches() {
        Pattern p = Pattern.compile("xyz");
        Matcher m = p.matcher("abc def ghi");

        final boolean[] called = {false};
        Consumer<MatchResult> consumer = new Consumer<MatchResult>() {
            public void accept(MatchResult mr) {
                called[0] = true;
            }
        };

        // No matches, forEachRemaining should not call consumer
        m.forEachRemaining(consumer);

        if (called[0]) {
            throw new AssertionError("Consumer should not be called when no matches found");
        }
    }

    private static void testForEachRemainingConcurrentModification() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("aaa");

        final int[] count = {0};
        Consumer<MatchResult> consumer = new Consumer<MatchResult>() {
            public void accept(MatchResult mr) {
                count[0]++;
                // Simulate concurrent modification by changing modCount indirectly
                // Since modCount is private and no public setter, we simulate by calling reset()
                if (count[0] == 1) {
                    m.reset(); // This will change modCount
                }
            }
        };

        boolean threw = false;
        try {
            m.forEachRemaining(consumer);
        } catch (ConcurrentModificationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected ConcurrentModificationException due to concurrent modification");
        }
    }
}
