/*
 * @test
 * @summary Tests the map method of LongPipeline for JDK 11+28
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongStream;
import java.util.stream.LongPipeline;
import java.util.function.LongUnaryOperator;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMapWithIdentity();
        testMapWithIncrement();
        testMapNullMapper();
    }

    static void testMapWithIdentity() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        LongStream result = stream.map(LongUnaryOperator.identity());
        long[] expected = {1L, 2L, 3L};
        long[] actual = result.toArray();
        assertArrayEquals(expected, actual);
    }

    static void testMapWithIncrement() {
        LongStream stream = LongStream.of(1L, 2L, 3L);
        LongStream result = stream.map(t -> t + 10);
        long[] expected = {11L, 12L, 13L};
        long[] actual = result.toArray();
        assertArrayEquals(expected, actual);
    }

    static void testMapNullMapper() {
        try {
            LongStream stream = LongStream.of(1L, 2L);
            stream.map(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void assertArrayEquals(long[] expected, long[] actual) {
        if (expected.length != actual.length) {
            throw new AssertionError("Array lengths differ. Expected: " + expected.length + ", Actual: " + actual.length);
        }
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] != actual[i]) {
                throw new AssertionError("Arrays differ at index " + i + ". Expected: " + expected[i] + ", Actual: " + actual[i]);
            }
        }
    }
}
