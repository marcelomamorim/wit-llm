/*
 * @test
 * @summary Verifies IntStream.iterate spliterator tryAdvance sequencing, laziness of the operator, and null action handling
 * @run main IntStreamWitupTest
 */

import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testSequence();
        testOperatorAppliedLazily();
        testNullActionRejectedWithoutStateChange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequence() {
        int[] buf = new int[3];
        int[] n = {0};
        IntConsumer collector = v -> buf[n[0]++] = v;
        Spliterator.OfInt sp = IntStream.iterate(1, x -> x * 2).spliterator();
        check(sp.tryAdvance(collector), "tryAdvance must return true");
        check(sp.tryAdvance(collector), "tryAdvance must return true");
        check(sp.tryAdvance(collector), "tryAdvance must return true");
        check(n[0] == 3, "expected 3 elements, got " + n[0]);
        check(buf[0] == 1, "first element must be seed, got " + buf[0]);
        check(buf[1] == 2, "second element must be 2, got " + buf[1]);
        check(buf[2] == 4, "third element must be 4, got " + buf[2]);
    }

    static void testOperatorAppliedLazily() {
        int[] calls = {0};
        int[] last = {-1};
        IntUnaryOperator f = x -> {
            calls[0]++;
            return x + 1;
        };
        Spliterator.OfInt sp = IntStream.iterate(10, f).spliterator();
        sp.tryAdvance(v -> last[0] = v);
        check(calls[0] == 0, "operator must not be applied to seed, calls=" + calls[0]);
        check(last[0] == 10, "seed must be emitted first, got " + last[0]);
        sp.tryAdvance(v -> last[0] = v);
        check(calls[0] == 1, "operator must be applied once, calls=" + calls[0]);
        check(last[0] == 11, "expected 11, got " + last[0]);
    }

    static void testNullActionRejectedWithoutStateChange() {
        int[] last = {-1};
        Spliterator.OfInt sp = IntStream.iterate(7, x -> x + 1).spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null action must throw NullPointerException");
        sp.tryAdvance(v -> last[0] = v);
        check(last[0] == 7, "seed must still be emitted after rejected null action, got " + last[0]);
    }
}
