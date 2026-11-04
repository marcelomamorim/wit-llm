/*
 * @test
 * @summary Witup-derived checks for IntPipeline.limit(long) negative argument handling and prefix semantics
 * @run main IntPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNegativeLimitThrows();
        testNegativeMinValueLimitThrows();
        testZeroLimitIsEmpty();
        testLimitTakesPrefix();
        testLimitLargerThanSizeKeepsAll();
    }

    static void testNegativeLimitThrows() {
        boolean thrown = false;
        try {
            IntStream.of(1, 2, 3).limit(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
            if (!"-1".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("limit(-1) did not throw IllegalArgumentException");
        }
    }

    static void testNegativeMinValueLimitThrows() {
        boolean thrown = false;
        try {
            IntStream.of(1, 2, 3).limit(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
            if (!Long.toString(Long.MIN_VALUE).equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("limit(Long.MIN_VALUE) did not throw IllegalArgumentException");
        }
    }

    static void testZeroLimitIsEmpty() {
        long count = IntStream.of(1, 2, 3).limit(0).count();
        if (count != 0) {
            throw new RuntimeException("limit(0) count was " + count);
        }
    }

    static void testLimitTakesPrefix() {
        String actual = Arrays.toString(IntStream.of(1, 2, 3, 4).limit(2).toArray());
        if (!"[1, 2]".equals(actual)) {
            throw new RuntimeException("unexpected prefix: " + actual);
        }
    }

    static void testLimitLargerThanSizeKeepsAll() {
        int length = IntStream.of(1, 2, 3).limit(Long.MAX_VALUE).toArray().length;
        if (length != 3) {
            throw new RuntimeException("unexpected length: " + length);
        }
    }
}
