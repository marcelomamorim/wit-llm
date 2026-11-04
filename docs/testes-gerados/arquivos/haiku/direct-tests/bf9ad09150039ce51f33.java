/*
 * @test
 * @summary Verifies Spliterator.OfDouble.tryAdvance from Spliterators array-backed spliterator: null action, sequential advance, and exhaustion
 * @run main SpliteratorsWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testSequentialAdvance();
        testNullActionThrowsAndDoesNotConsume();
        testExhaustedReturnsFalseWithoutInvokingAction();
    }

    static void testSequentialAdvance() {
        double[] data = {1.5, -2.0, 3.25};
        Spliterator.OfDouble s = Spliterators.spliterator(data, Spliterator.ORDERED);
        List<Double> seen = new ArrayList<>();
        if (!s.tryAdvance(seen::add)) throw new RuntimeException("first tryAdvance should be true");
        if (!s.tryAdvance(seen::add)) throw new RuntimeException("second tryAdvance should be true");
        if (!s.tryAdvance(seen::add)) throw new RuntimeException("third tryAdvance should be true");
        if (seen.size() != 3) throw new RuntimeException("expected 3 elements, got " + seen.size());
        if (seen.get(0) != 1.5 || seen.get(1) != -2.0 || seen.get(2) != 3.25)
            throw new RuntimeException("unexpected order/values: " + seen);
    }

    static void testNullActionThrowsAndDoesNotConsume() {
        double[] data = {7.0, 8.0};
        Spliterator.OfDouble s = Spliterators.spliterator(data, Spliterator.ORDERED);
        boolean thrown = false;
        try {
            s.tryAdvance((java.util.function.DoubleConsumer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("expected NullPointerException for null action");
        List<Double> seen = new ArrayList<>();
        if (!s.tryAdvance(seen::add)) throw new RuntimeException("element should still be available after NPE");
        if (seen.size() != 1 || seen.get(0) != 7.0)
            throw new RuntimeException("null action must not consume element: " + seen);
    }

    static void testExhaustedReturnsFalseWithoutInvokingAction() {
        double[] data = {4.0};
        Spliterator.OfDouble s = Spliterators.spliterator(data, Spliterator.ORDERED);
        List<Double> seen = new ArrayList<>();
        if (!s.tryAdvance(seen::add)) throw new RuntimeException("first tryAdvance should be true");
        if (s.tryAdvance(seen::add)) throw new RuntimeException("tryAdvance on exhausted spliterator should be false");
        if (seen.size() != 1) throw new RuntimeException("action invoked after exhaustion: " + seen);
    }
}
