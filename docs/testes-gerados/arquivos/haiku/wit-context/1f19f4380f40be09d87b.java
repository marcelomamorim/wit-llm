/*
 * @test
 * @summary Verifies LongStream.Builder.accept(long) state transitions, including IllegalStateException after build()
 * @run main StreamsWitupTest
 */

import java.util.stream.LongStream;

public class StreamsWitupTest {

    public static void main(String[] args) {
        testMultipleElementsThenBuild();
        testSingleElementThenBuild();
    }

    private static void testMultipleElementsThenBuild() {
        LongStream.Builder b = LongStream.builder();
        b.accept(1L);
        b.accept(2L);
        b.accept(3L);
        long sum = b.build().sum();
        if (sum != 6L) {
            throw new RuntimeException("unexpected sum " + sum);
        }
        boolean thrown = false;
        try {
            b.accept(4L);
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException after build()");
        }
    }

    private static void testSingleElementThenBuild() {
        LongStream.Builder b = LongStream.builder();
        b.accept(5L);
        long sum = b.build().sum();
        if (sum != 5L) {
            throw new RuntimeException("unexpected sum " + sum);
        }
        boolean thrown = false;
        try {
            b.accept(7L);
        } catch (IllegalStateException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException after build() with single element");
        }
    }
}
