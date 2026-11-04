/*
 * @test
 * @summary Verifies DoubleStream.toArray over a SIZED spliterator whose elements exceed the reported size, which reaches the fixed-size double node builder accept path.
 * @run main NodesWitupTest
 */
import java.util.Arrays;
import java.util.Spliterator;
import java.util.function.DoubleConsumer;
import java.util.stream.StreamSupport;

public class NodesWitupTest {

    public static void main(String[] args) {
        testExactSizeSucceeds();
        testOverflowThrowsIllegalStateException();
    }

    static void testExactSizeSucceeds() {
        double[] expected = {1.0, 2.0};
        double[] actual = StreamSupport.doubleStream(
                new LyingSpliterator(new double[] {1.0, 2.0}, 2), false).toArray();
        if (!Arrays.equals(expected, actual)) {
            throw new RuntimeException("Unexpected array: " + Arrays.toString(actual));
        }
    }

    static void testOverflowThrowsIllegalStateException() {
        try {
            double[] actual = StreamSupport.doubleStream(
                    new LyingSpliterator(new double[] {1.0, 2.0, 3.0}, 2), false).toArray();
            throw new RuntimeException("Expected IllegalStateException, got: " + Arrays.toString(actual));
        } catch (IllegalStateException expected) {
            if (!expected.getMessage().contains("exceeded")) {
                throw new RuntimeException("Unexpected message: " + expected.getMessage());
            }
        }
    }

    static final class LyingSpliterator implements Spliterator.OfDouble {
        private final double[] values;
        private final long reported;
        private int index;

        LyingSpliterator(double[] values, long reported) {
            this.values = values;
            this.reported = reported;
        }

        @Override
        public boolean tryAdvance(DoubleConsumer action) {
            if (index < values.length) {
                action.accept(values[index++]);
                return true;
            }
            return false;
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return reported;
        }

        @Override
        public int characteristics() {
            return SIZED | SUBSIZED | ORDERED;
        }
    }
}
