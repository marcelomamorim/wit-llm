/*
 * @test
 * @summary Checks IntPipeline.sum() through the public IntStream API, including reuse of a consumed stream
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        check(IntStream.of(1, 2, 3).sum() == 6, "sum of 1,2,3 should be 6");
        check(IntStream.range(0, 5).sum() == 10, "sum of range(0,5) should be 10");
        check(IntStream.empty().sum() == 0, "sum of empty stream should be 0");
        check(IntStream.of(Integer.MAX_VALUE, 1).sum() == Integer.MIN_VALUE, "int sum should wrap on overflow");
        check(IntStream.range(0, 1000).parallel().sum() == 499500, "parallel sum should be 499500");

        IntStream consumed = IntStream.of(1, 2, 3);
        check(consumed.sum() == 6, "first sum on a fresh stream should be 6");
        boolean thrown = false;
        try {
            consumed.sum();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "reusing a consumed stream should throw IllegalStateException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
