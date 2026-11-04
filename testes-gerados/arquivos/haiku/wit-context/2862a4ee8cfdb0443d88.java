/*
 * @test
 * @summary Verifies DoubleStream.generate spliterator tryAdvance delivers supplied values and rejects a null action
 * @run main StreamSpliteratorsWitupTest
 */
import java.util.Spliterator;
import java.util.function.DoubleConsumer;
import java.util.stream.DoubleStream;

public class StreamSpliteratorsWitupTest {
    public static void main(String[] args) {
        Spliterator.OfDouble sp = DoubleStream.generate(() -> 2.5).spliterator();
        double[] box = new double[1];
        DoubleConsumer consumer = d -> box[0] = d;
        boolean advanced = sp.tryAdvance(consumer);
        if (!advanced) {
            throw new RuntimeException("tryAdvance should return true for an infinite supplier");
        }
        if (box[0] != 2.5) {
            throw new RuntimeException("unexpected value: " + box[0]);
        }

        DoubleConsumer nullAction = null;
        boolean npe = false;
        try {
            sp.tryAdvance(nullAction);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("tryAdvance(null) should throw NullPointerException");
        }
    }
}
