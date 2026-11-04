/*
 * @test
 * @summary Test for StreamSupport.stream method handling null spliterator
 * @run main StreamSupportWitupTest
 */

import java.util.stream.StreamSupport;
import java.util.Spliterator;

public class StreamSupportWitupTest {

    public static void main(String[] args) {
        testNullSpliteratorThrowsNullPointerException();
        testValidSpliteratorReturnsStream();
    }

    private static void testNullSpliteratorThrowsNullPointerException() {
        boolean exceptionThrown = false;
        try {
            StreamSupport.stream(null, false);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when spliterator is null");
        }
    }

    private static void testValidSpliteratorReturnsStream() {
        Spliterator<Integer> spliterator = new Spliterator<Integer>() {
            private int current = 0;
            private final int max = 3;

            @Override
            public boolean tryAdvance(java.util.function.Consumer<? super Integer> action) {
                if (current < max) {
                    action.accept(current);
                    current++;
                    return true;
                }
                return false;
            }

            @Override
            public java.util.Spliterator<Integer> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return max - current;
            }

            @Override
            public int characteristics() {
                return 0;
            }
        };
        try {
            java.util.stream.Stream<Integer> stream = StreamSupport.stream(spliterator, false);
            long count = stream.count();
            if (count != 3) {
                throw new AssertionError("Stream did not produce expected number of elements");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
