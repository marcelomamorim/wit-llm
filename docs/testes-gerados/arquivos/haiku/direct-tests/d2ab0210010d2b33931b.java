/*
 * @test
 * @summary Verify IntStream.skip for negative, zero, partial, exhausting and oversized counts
 * @run main IntPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        check(Arrays.toString(IntStream.of(1, 2, 3, 4, 5).skip(2).toArray()), "[3, 4, 5]");
        check(Arrays.toString(IntStream.range(0, 5).skip(0).toArray()), "[0, 1, 2, 3, 4]");
        check(Arrays.toString(IntStream.of(1, 2, 3).skip(3).toArray()), "[]");
        check(Arrays.toString(IntStream.of(1, 2, 3).skip(10).toArray()), "[]");
        check(Arrays.toString(IntStream.of(1, 2, 3).skip(Long.MAX_VALUE).toArray()), "[]");

        checkIllegalArgument(-1L, "-1");
        checkIllegalArgument(Long.MIN_VALUE, Long.toString(Long.MIN_VALUE));
    }

    private static void checkIllegalArgument(long n, String expectedMessage) {
        try {
            IntStream.of(1, 2, 3).skip(n);
        } catch (IllegalArgumentException e) {
            check(e.getMessage(), expectedMessage);
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for skip(" + n + ")");
    }

    private static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
