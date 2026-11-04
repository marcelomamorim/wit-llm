/*
 * @test
 * @summary Test StreamSpliterators.InfiniteSupplyingSpliterator.OfRef.tryAdvance behavior
 * @run main StreamSpliteratorsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StreamSpliteratorsWitupTest {

    static class InfiniteSupplyingSpliteratorOfRef<T> {
        private final Supplier<T> s;

        InfiniteSupplyingSpliteratorOfRef(Supplier<T> s) {
            this.s = s;
        }

        public boolean tryAdvance(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            action.accept(s.get());
            return true;
        }
    }

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
    }

    private static void testTryAdvanceWithValidConsumer() {
        final StringBuilder sb = new StringBuilder();
        Supplier<String> supplier = new Supplier<String>() {
            private int count = 0;
            public String get() {
                return "val" + count++;
            }
        };
        InfiniteSupplyingSpliteratorOfRef<String> spliterator = new InfiniteSupplyingSpliteratorOfRef<>(supplier);

        Consumer<String> consumer = new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        };

        boolean result = spliterator.tryAdvance(consumer);
        if (!result) {
            throw new RuntimeException("tryAdvance should return true");
        }
        if (!sb.toString().startsWith("val")) {
            throw new RuntimeException("Consumer did not receive expected value");
        }
    }

    private static void testTryAdvanceWithNullConsumer() {
        Supplier<String> supplier = new Supplier<String>() {
            public String get() {
                return "test";
            }
        };
        InfiniteSupplyingSpliteratorOfRef<String> spliterator = new InfiniteSupplyingSpliteratorOfRef<>(supplier);

        try {
            spliterator.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
