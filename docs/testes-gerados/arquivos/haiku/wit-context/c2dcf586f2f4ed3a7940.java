/*
 * @test
 * @summary WITUP-generated behavioral tests for StringUTF16.CodePointsSpliterator.tryAdvance via public String.codePoints() API
 * @run main StringUTF16WitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testAdvancesThroughCodePointsThenStops();
        testSurrogatePairIsSingleCodePoint();
        testEmptyStringReturnsFalseWithoutCallingAction();
        testNullActionThrowsNullPointerException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAdvancesThroughCodePointsThenStops() {
        Spliterator.OfInt sp = "a\u20AC".codePoints().spliterator();
        List<Integer> seen = new ArrayList<>();
        IntConsumer consumer = v -> seen.add(v);
        check(sp.tryAdvance(consumer), "first tryAdvance should return true");
        check(sp.tryAdvance(consumer), "second tryAdvance should return true");
        check(!sp.tryAdvance(consumer), "tryAdvance past end should return false");
        check(seen.size() == 2, "expected 2 code points, got " + seen.size());
        check(seen.get(0) == 0x61, "expected 'a' first, got " + seen.get(0));
        check(seen.get(1) == 0x20AC, "expected euro sign second, got " + seen.get(1));
    }

    static void testSurrogatePairIsSingleCodePoint() {
        Spliterator.OfInt sp = "\uD83D\uDE00".codePoints().spliterator();
        List<Integer> seen = new ArrayList<>();
        IntConsumer consumer = v -> seen.add(v);
        check(sp.tryAdvance(consumer), "surrogate pair should be advanced");
        check(seen.size() == 1, "surrogate pair should yield one code point, got " + seen.size());
        check(seen.get(0) == 0x1F600, "expected U+1F600, got " + seen.get(0));
        check(!sp.tryAdvance(consumer), "no code points should remain after surrogate pair");
        check(seen.size() == 1, "action must not be called after exhaustion");
    }

    static void testEmptyStringReturnsFalseWithoutCallingAction() {
        Spliterator.OfInt sp = "".codePoints().spliterator();
        List<Integer> seen = new ArrayList<>();
        IntConsumer consumer = v -> seen.add(v);
        check(!sp.tryAdvance(consumer), "empty string tryAdvance should return false");
        check(seen.isEmpty(), "action must not be called for empty string");
    }

    static void testNullActionThrowsNullPointerException() {
        Spliterator.OfInt sp = "\u20AC".codePoints().spliterator();
        try {
            sp.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
