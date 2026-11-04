/*
 * @test
 * @summary Verify DoubleStream.generate produces supplied values, is sequential, and rejects a null supplier
 * @run main DoubleStreamWitupTest
 */
import java.util.stream.DoubleStream;

public class DoubleStreamWitupTest {
    public static void main(String[] args) {
        double[] counter = {0.0};
        double[] values = DoubleStream.generate(() -> counter[0]++).limit(3).toArray();
        check(values.length == 3, "expected 3 elements but got " + values.length);
        check(values[0] == 0.0, "values[0] expected 0.0 but was " + values[0]);
        check(values[1] == 1.0, "values[1] expected 1.0 but was " + values[1]);
        check(values[2] == 2.0, "values[2] expected 2.0 but was " + values[2]);

        double sum = DoubleStream.generate(() -> 2.5).limit(4).sum();
        check(sum == 10.0, "expected sum 10.0 but was " + sum);

        check(!DoubleStream.generate(() -> 1.0).isParallel(), "generated stream must be sequential");

        boolean npe = false;
        try {
            DoubleStream.generate(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "generate(null) must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
