/*
 * @test
 * @summary Verifies that a double stream whose spliterator reports SIZED with a size smaller than the elements it produces fails with IllegalStateException, and that a correctly sized spliterator succeeds.
 * @run main NodesWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;
import java.util.stream.StreamSupport;

public class NodesWitupTest {

    static final class FixedSizeSpliterator extends Spliterators.AbstractDoubleSpliterator {
        private final double[] values;
        private int index;

        FixedSizeSpliterator(double[] values, long declaredSize) {
            super(declaredSize, Spliterator.SIZED | Spliterator.SUBSIZED);
            this.values = values;
        }

        @Override
        public boolean tryAdvance(DoubleConsumer action) {
            if (index >= values.length) {
                return false;
            }
            action.accept(values[index++]);
            return true;
        }
    }

    static void checkExactSizeSucceeds() {
        double[] result = StreamSupport.doubleStream(
                new FixedSizeSpliterator(new double[] {1.0, 2.0}, 2), false).toArray();
        if (result.length != 2 || result[0] != 1.0 || result[1] != 2.0) {
            throw new AssertionError("unexpected result for exact size");
        }
    }

    static void checkOverflowThrows() {
        try {
            StreamSupport.doubleStream(
                    new FixedSizeSpliterator(new double[] {1.0, 2.0, 3.0}, 2), false).toArray();
        } catch (IllegalStateException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Accept exceeded fixed size")) {
                throw new AssertionError("unexpected message: " + msg, e);
            }
            return;
        }
        throw new AssertionError("expected IllegalStateException when exceeding fixed size");
    }

    public static void main(String[] args) {
        checkExactSizeSucceeds();
        checkOverflowThrows();
    }
}
