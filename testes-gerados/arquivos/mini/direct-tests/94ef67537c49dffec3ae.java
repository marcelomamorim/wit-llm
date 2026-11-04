/*
 * @test
 * @summary Test java.util.stream.Stream.tryAdvance method behavior
 * @run main StreamWitupTest
 */

import java.util.function.Consumer;
import java.util.Objects;

public class StreamWitupTest {

    static class TestStream<T> {
        private final java.util.function.Function<T, T> f;
        private T prev;
        private boolean started = false;
        private final T seed;

        TestStream(T seed, java.util.function.Function<T, T> f) {
            this.seed = seed;
            this.f = f;
        }

        public boolean tryAdvance(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            T t;
            if (started)
                t = f.apply(prev);
            else {
                t = seed;
                started = true;
            }
            action.accept(prev = t);
            return true;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceBasic();
        testTryAdvanceMultipleCalls();
        testTryAdvanceNullAction();
    }

    private static void testTryAdvanceBasic() {
        final Integer seed = 1;
        TestStream<Integer> stream = new TestStream<>(seed, new java.util.function.Function<Integer, Integer>() {
            public Integer apply(Integer x) {
                return x + 1;
            }
        });

        final int[] captured = new int[1];
        boolean result = stream.tryAdvance(new Consumer<Integer>() {
            public void accept(Integer t) {
                captured[0] = t;
            }
        });

        if (!result) {
            throw new RuntimeException("tryAdvance should return true on first call");
        }
        if (captured[0] != seed) {
            throw new RuntimeException("tryAdvance did not pass seed value on first call");
        }
    }

    private static void testTryAdvanceMultipleCalls() {
        final Integer seed = 10;
        TestStream<Integer> stream = new TestStream<>(seed, new java.util.function.Function<Integer, Integer>() {
            public Integer apply(Integer x) {
                return x * 2;
            }
        });

        final int[] captured = new int[3];
        for (int i = 0; i < 3; i++) {
            final int index = i;
            boolean result = stream.tryAdvance(new Consumer<Integer>() {
                public void accept(Integer t) {
                    captured[index] = t;
                }
            });
            if (!result) {
                throw new RuntimeException("tryAdvance should always return true");
            }
        }

        // Expected sequence: 10 (seed), 20 (10*2), 40 (20*2)
        if (captured[0] != 10 || captured[1] != 20 || captured[2] != 40) {
            throw new RuntimeException("tryAdvance sequence incorrect: " + captured[0] + ", " + captured[1] + ", " + captured[2]);
        }
    }

    private static void testTryAdvanceNullAction() {
        final Integer seed = 5;
        TestStream<Integer> stream = new TestStream<>(seed, new java.util.function.Function<Integer, Integer>() {
            public Integer apply(Integer x) {
                return x + 1;
            }
        });

        try {
            stream.tryAdvance(null);
            throw new RuntimeException("tryAdvance should throw NullPointerException when action is null");
        } catch (NullPointerException npe) {
            // Expected
        }
    }
}
