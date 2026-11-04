/*
 * @test
 * @summary Verifies flatMapToInt null-mapper rejection and basic observable behavior via public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNpe();
        testFlatMapToIntSum();
        testNullResultTreatedAsEmpty();
    }

    static void testNullMapperThrowsNpe() {
        boolean thrown = false;
        try {
            Stream.of(1, 2, 3).flatMapToInt(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null mapper");
        }
    }

    static void testFlatMapToIntSum() {
        int sum = Stream.of(1, 2, 3)
                .flatMapToInt(x -> IntStream.of(x, x * 10))
                .sum();
        if (sum != 66) {
            throw new AssertionError("Expected sum 66 but was " + sum);
        }
    }

    static void testNullResultTreatedAsEmpty() {
        long count = Stream.of(1, 2)
                .flatMapToInt(x -> (IntStream) null)
                .count();
        if (count != 0L) {
            throw new AssertionError("Expected 0 elements but was " + count);
        }
    }
}
