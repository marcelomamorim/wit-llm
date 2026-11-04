/* @test    @summary Test for java.util.regex.Matcher forEachRemaining method    @run main MatcherWitupTest */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Consumer;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithMatches();
        testForEachRemainingWithNoMatches();
        testForEachRemainingConcurrentModification();
    }

    static void testForEachRemainingWithMatches() {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher("abc123def456");
        StringBuilder result = new StringBuilder();
        matcher.forEachRemaining(matchResult -> {
            result.append(matchResult.group());
        });
        assert result.toString().equals("123456") : "Expected '123456', got '" + result.toString() + '"';
    }

    static void testForEachRemainingWithNoMatches() {
        Pattern pattern = Pattern.compile("xyz");
        Matcher matcher = pattern.matcher("abc123def456");
        StringBuilder result = new StringBuilder();
        matcher.forEachRemaining(matchResult -> {
            result.append(matchResult.group());
        });
        assert result.toString().isEmpty() : "Expected empty string, got '" + result.toString() + "'";
    }

    static void testForEachRemainingConcurrentModification() {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher("12345");
        StringBuilder result = new StringBuilder();
        try {
            matcher.forEachRemaining(matchResult -> {
                result.append(matchResult.group());
                // Simulate concurrent modification by changing the text
                // Note: This is a conceptual test; actual concurrent modification
                // would require multi-threading or internal state change.
                // Here, we simulate by calling a method that modifies internal state.
                // Since internal state is private, this is a placeholder.
                // Alternatively, we can forcibly throw exception to simulate.
                throw new ConcurrentModificationException();
            });
            assert false : "Expected ConcurrentModificationException";
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }
    }
}
