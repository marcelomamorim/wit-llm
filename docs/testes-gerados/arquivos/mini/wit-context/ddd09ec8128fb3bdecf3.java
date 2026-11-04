/*
 * @test
 * @summary Test java.util.stream.Streams.forEachRemaining(Consumer<? super T>) for NPE and normal behavior
 * @run main StreamsWitupTest
 */

import java.util.Objects;
import java.util.function.Consumer;

public class StreamsWitupTest {

    static class DummyStreamBuilder<T> {
        T first;
        int count = -2; // simulate initial state as in source

        DummyStreamBuilder(T first) {
            this.first = first;
        }

        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);

            if (count == -2) {
                action.accept(first);
                count = -1;
            }
        }
    }

    public static void main(String[] args) {
        testForEachRemainingNullAction();
        testForEachRemainingNormal();
    }

    private static void testForEachRemainingNullAction() {
        DummyStreamBuilder<String> builder = new DummyStreamBuilder<>("test");
        try {
            builder.forEachRemaining(null);
            throw new Error("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testForEachRemainingNormal() {
        final StringBuilder sb = new StringBuilder();
        DummyStreamBuilder<String> builder = new DummyStreamBuilder<>("hello");
        builder.forEachRemaining(new Consumer<String>() {
            public void accept(String s) {
                sb.append(s);
            }
        });
        if (!"hello".equals(sb.toString())) {
            throw new Error("forEachRemaining did not accept the first element correctly");
        }
    }
}
