/*
 * @test
 * @summary Tests the limit method of IntPipeline for various inputs.
 * @run main IntPipelineWitupTest
 */
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        testLimitWithPositiveNumber();
        testLimitWithZero();
        testLimitWithNegativeNumber();
        testLimitWithLargeNumber();
    }

    static void testLimitWithPositiveNumber() {
        IntStream stream = IntStream.range(0, 10);
        IntStream limitedStream = stream.limit(5);
        int[] result = limitedStream.toArray();
        assert result.length == 5 : "Expected length 5, but got " + result.length;
        for (int i = 0; i < 5; i++) {
            assert result[i] == i : "Expected element " + i + " but got " + result[i];
        }
    }

    static void testLimitWithZero() {
        IntStream stream = IntStream.range(0, 10);
        IntStream limitedStream = stream.limit(0);
        int[] result = limitedStream.toArray();
        assert result.length == 0 : "Expected length 0, but got " + result.length;
    }

    static void testLimitWithNegativeNumber() {
        IntStream stream = IntStream.range(0, 10);
        try {
            stream.limit(-1);
            assert false : "Expected IllegalArgumentException for negative maxSize";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("-1") : "Unexpected exception message: " + e.getMessage();
        }
    }

    static void testLimitWithLargeNumber() {
        IntStream stream = IntStream.range(0, 10);
        IntStream limitedStream = stream.limit(100);
        int[] result = limitedStream.toArray();
        assert result.length == 10 : "Expected length 10, but got " + result.length;
        for (int i = 0; i < 10; i++) {
            assert result[i] == i : "Expected element " + i + " but got " + result[i];
        }
    }
}
