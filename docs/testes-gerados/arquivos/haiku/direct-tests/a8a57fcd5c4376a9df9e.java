/*
 * @test
 * @summary LongPipeline.mapToDouble applies the mapper to each element and rejects a null mapper
 * @run main LongPipelineWitupTest
 */
import java.util.function.LongToDoubleFunction;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        LongToDoubleFunction half = v -> v * 0.5;
        double[] r = LongStream.of(1L, 2L, 3L).mapToDouble(half).toArray();
        check(r.length == 3 && r[0] == 0.5 && r[1] == 1.0 && r[2] == 1.5, "mapped values");

        double[] e = LongStream.empty().mapToDouble(half).toArray();
        check(e.length == 0, "empty stream");

        LongToDoubleFunction toDouble = v -> (double) v;
        double[] neg = LongStream.of(-4L).mapToDouble(toDouble).toArray();
        check(neg.length == 1 && neg[0] == -4.0, "negative value");

        boolean npe = false;
        try {
            LongStream.of(1L).mapToDouble(null);
        } catch (NullPointerException ex) {
            npe = true;
        }
        check(npe, "null mapper must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
