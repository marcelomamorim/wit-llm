/*
 * @test
 * @summary Verifies tryAdvance(LongConsumer) of spliterators built by Spliterators.spliterator(OfLong, long, int): null action guard and advancing behavior
 * @run main SpliteratorsWitupTest
 */
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNpe();
        testNullActionOnEmptyIteratorThrowsNpe();
        testAdvancesAndReturnsTrue();
        testReturnsFalseWhenExhausted();
    }

    private static Spliterator.OfLong create(long... values) {
        PrimitiveIterator.OfLong it = LongStream.of(values).iterator();
        return Spliterators.spliterator(it, values.length, 0);
    }

    private static void testNullActionThrowsNpe() {
        Spliterator.OfLong s = create(1L, 2L);
        boolean thrown = false;
        try {
            s.tryAdvance((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    private static void testNullActionOnEmptyIteratorThrowsNpe() {
        Spliterator.OfLong s = create();
        boolean thrown = false;
        try {
            s.tryAdvance((LongConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action on empty iterator");
        }
    }

    private static void testAdvancesAndReturnsTrue() {
        Spliterator.OfLong s = create(7L, 8L);
        long[] seen = new long[1];
        int[] calls = new int[1];
        LongConsumer c = v -> {
            seen[0] = v;
            calls[0]++;
        };

        if (!s.tryAdvance(c)) {
            throw new RuntimeException("Expected true on first tryAdvance");
        }
        if (seen[0] != 7L || calls[0] != 1) {
            throw new RuntimeException("Unexpected first element: " + seen[0] + " calls=" + calls[0]);
        }

        if (!s.tryAdvance(c)) {
            throw new RuntimeException("Expected true on second tryAdvance");
        }
        if (seen[0] != 8L || calls[0] != 2) {
            throw new RuntimeException("Unexpected second element: " + seen[0] + " calls=" + calls[0]);
        }
    }

    private static void testReturnsFalseWhenExhausted() {
        Spliterator.OfLong s = create(5L);
        int[] calls = new int[1];
        LongConsumer c = v -> calls[0]++;

        if (!s.tryAdvance(c)) {
            throw new RuntimeException("Expected true for the single element");
        }
        if (s.tryAdvance(c)) {
            throw new RuntimeException("Expected false when iterator is exhausted");
        }
        if (calls[0] != 1) {
            throw new RuntimeException("Action must not be invoked when exhausted; calls=" + calls[0]);
        }
    }
}
