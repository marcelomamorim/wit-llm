/*
 * @test
 * @summary Tests Spliterator.OfLong.forEachRemaining obtained from Spliterators.spliterator(long[], int)
 * @run main SpliteratorsWitupTest
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingVisitsAllElements();
        testForEachRemainingAfterPartialAdvance();
        testForEachRemainingOnEmptyArray();
        testForEachRemainingExhaustsSpliterator();
        testForEachRemainingNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static LongConsumer collector(final List<Long> out) {
        return v -> out.add(v);
    }

    static void testForEachRemainingVisitsAllElements() {
        long[] data = {1L, 2L, 3L};
        Spliterator.OfLong s = Spliterators.spliterator(data, 0);
        List<Long> out = new ArrayList<>();
        LongConsumer c = collector(out);
        s.forEachRemaining(c);
        check(out.size() == 3, "expected 3 elements, got " + out.size());
        check(out.get(0) == 1L && out.get(1) == 2L && out.get(2) == 3L,
              "unexpected elements: " + out);
    }

    static void testForEachRemainingAfterPartialAdvance() {
        long[] data = {10L, 20L, 30L};
        Spliterator.OfLong s = Spliterators.spliterator(data, 0);
        List<Long> out = new ArrayList<>();
        LongConsumer c = collector(out);
        boolean advanced = s.tryAdvance(c);
        check(advanced, "tryAdvance should return true on non-empty spliterator");
        check(out.size() == 1 && out.get(0) == 10L, "unexpected first element: " + out);
        s.forEachRemaining(c);
        check(out.size() == 3, "expected 3 total elements, got " + out.size());
        check(out.get(1) == 20L && out.get(2) == 30L, "unexpected remaining elements: " + out);
    }

    static void testForEachRemainingOnEmptyArray() {
        long[] data = new long[0];
        Spliterator.OfLong s = Spliterators.spliterator(data, 0);
        List<Long> out = new ArrayList<>();
        LongConsumer c = collector(out);
        s.forEachRemaining(c);
        check(out.isEmpty(), "empty array should produce no elements");
    }

    static void testForEachRemainingExhaustsSpliterator() {
        long[] data = {5L, 6L};
        Spliterator.OfLong s = Spliterators.spliterator(data, 0);
        List<Long> out = new ArrayList<>();
        LongConsumer c = collector(out);
        s.forEachRemaining(c);
        boolean more = s.tryAdvance(c);
        check(!more, "tryAdvance should return false after forEachRemaining");
        check(out.size() == 2, "second forEachRemaining must not revisit elements: " + out);
    }

    static void testForEachRemainingNullActionThrowsNPE() {
        long[] data = {1L};
        Spliterator.OfLong s = Spliterators.spliterator(data, 0);
        boolean thrown = false;
        try {
            s.forEachRemaining((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
    }
}
