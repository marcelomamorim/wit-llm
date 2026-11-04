/*
 * @test
 * @summary Exercises IntPipeline.mapToDouble through its public API, which drives the anonymous Sink accept(int) path
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        double[] basic = IntStream.of(1, 2, 3).mapToDouble(i -> i * 0.5).toArray();
        check(basic.length == 3 && basic[0] == 0.5 && basic[1] == 1.0 && basic[2] == 1.5,
              "basic mapping must preserve order and apply mapper");

        double[] negZero = IntStream.of(-2, 0).mapToDouble(i -> i / 4.0).toArray();
        check(negZero.length == 2 && negZero[0] == -0.5 && negZero[1] == 0.0,
              "negative and zero inputs must be mapped");

        double[] empty = IntStream.empty().mapToDouble(i -> i * 2.0).toArray();
        check(empty.length == 0, "empty source must yield empty result");

        double sum = IntStream.rangeClosed(1, 4).mapToDouble(i -> i * 1.0).sum();
        check(sum == 10.0, "downstream terminal operation must see all mapped values");

        try {
            IntStream.of(1).mapToDouble(null);
            throw new RuntimeException("null mapper must throw NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
