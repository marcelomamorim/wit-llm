/*
 * @test
 * @summary Verifies IntStream.mapToLong null-mapper rejection and observable mapping results
 * @run main IntPipelineWitupTest
 */
import java.util.function.IntToLongFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testMappingSum();
        testWideningResult();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullMapperThrowsNPE() {
        IntToLongFunction nullMapper = null;
        try {
            IntStream.of(1, 2, 3).mapToLong(nullMapper);
            throw new RuntimeException("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testMappingSum() {
        long sum = IntStream.range(0, 3).mapToLong(i -> i * 10L).sum();
        check(sum == 30L, "Expected sum 30 but was " + sum);
    }

    static void testWideningResult() {
        long sum = IntStream.of(Integer.MAX_VALUE).mapToLong(i -> (long) i + 1L).sum();
        check(sum == 2147483648L, "Expected 2147483648 but was " + sum);
    }
}
