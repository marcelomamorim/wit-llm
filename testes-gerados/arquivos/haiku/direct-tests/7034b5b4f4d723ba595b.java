/*
 * @test
 * @summary Verifies IntPipeline.sum() through the public IntStream API
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        check(IntStream.empty().sum() == 0, "empty stream sum must be 0");
        check(IntStream.of(5).sum() == 5, "single element sum");
        check(IntStream.rangeClosed(1, 100).sum() == 5050, "range sum");
        check(IntStream.of(-3, 1, 2).sum() == 0, "negative values sum");
        check(IntStream.of(Integer.MAX_VALUE, 1).sum() == Integer.MIN_VALUE, "int overflow wraps");
        check(IntStream.rangeClosed(1, 1000).parallel().sum() == 500500, "parallel sum");
        check(IntStream.of(1, 2, 3).filter(i -> i > 1).sum() == 5, "sum after filter");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
