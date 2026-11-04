/*
 * @test
 * @summary Test for java.util.regex.Matcher forEachRemaining method
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Consumer;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testForEachRemaining_noExpectedCount();
        testForEachRemaining_withExpectedCount_noException();
        testForEachRemaining_withExpectedCount_withException();
    }

    private static void testForEachRemaining_noExpectedCount() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("123 456");
        try {
            matcher.forEachRemaining(new Consumer<>() {
                int count = 0;
                public void accept(java.util.regex.MatchResult mr) {
                    count++;
                }
            });
            // Expect no exception, matcher should process matches
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in noExpectedCount test", e);
        }
    }

    private static void testForEachRemaining_withExpectedCount_noException() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("123 456");
        // Set expectedCount to a value >= 0 and equal to modCount to avoid exception
        // Since expectedCount is package-private, simulate by setting to 0 (assuming initial value)
        // Note: In actual code, expectedCount is package-private; here, we assume default value 0
        // For test purposes, we simulate the condition
        try {
            // No modification to modCount, so expectedCount=0 is valid
            matcher.forEachRemaining(new Consumer<>() {
                int count = 0;
                public void accept(java.util.regex.MatchResult mr) {
                    count++;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in withExpectedCount_noException", e);
        }
    }

    private static void testForEachRemaining_withExpectedCount_withException() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("123 456");
        // Simulate expectedCount != modCount to trigger exception
        // Since expectedCount is package-private, we cannot set it directly; instead, we simulate by modifying modCount
        // But modCount is package-private; for test, assume we can access or simulate the condition
        // As we cannot modify internal state directly, we simulate by calling find() to change modCount
        matcher.find(); // changes modCount
        try {
            // Now, expectedCount is still 0, but modCount has changed, so exception should be thrown
            matcher.forEachRemaining(new Consumer<>() {
                int count = 0;
                public void accept(java.util.regex.MatchResult mr) {
                    count++;
                }
            });
            throw new RuntimeException("Expected ConcurrentModificationException was not thrown");
        } catch (ConcurrentModificationException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in withExpectedCount_withException", e);
        }
    }
}
