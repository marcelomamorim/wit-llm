/*
 * @test
 * @summary Tests that Spliterators.OfInt wrapper forEachRemaining rejects a null action and delegates non-null actions
 * @run main SpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.IntStream;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNonNullActionConsumesAllElements();
    }

    private static void testNullActionThrowsNPE() {
        PrimitiveIterator.OfInt it = IntStream.of(1, 2, 3).iterator();
        Spliterator.OfInt spliterator = Spliterators.spliterator(it, 3L, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            spliterator.forEachRemaining((java.util.function.IntConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
    }

    private static void testNonNullActionConsumesAllElements() {
        PrimitiveIterator.OfInt it = IntStream.of(10, 20, 30).iterator();
        Spliterator.OfInt spliterator = Spliterators.spliterator(it, 3L, Spliterator.ORDERED);
        List<Integer> collected = new ArrayList<>();
        spliterator.forEachRemaining(v -> collected.add(v));
        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(20);
        expected.add(30);
        if (!expected.equals(collected)) {
            throw new RuntimeException("Unexpected elements: " + collected);
        }
    }
}
