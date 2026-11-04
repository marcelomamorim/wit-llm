/*
 * @test
 * @summary Tests the limit method of LongPipeline for various inputs.
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testLimitWithPositiveMaxSize();
        testLimitWithZero();
        testLimitWithNegative();
        testLimitWithLargeMaxSize();
    }

    static void testLimitWithPositiveMaxSize() {
        LongStream stream = LongStream.of(1, 2, 3, 4, 5);
        LongStream limitedStream = stream.limit(3);
        long[] result = limitedStream.toArray();
        assert result.length == 3 : "Expected length 3, got " + result.length;
        assert result[0] == 1 : "Expected first element 1, got " + result[0];
        assert result[1] == 2 : "Expected second element 2, got " + result[1];
        assert result[2] == 3 : "Expected third element 3, got " + result[2];
    }

    static void testLimitWithZero() {
        LongStream stream = LongStream.of(10, 20, 30);
        LongStream limitedStream = stream.limit(0);
        long[] result = limitedStream.toArray();
        assert result.length == 0 : "Expected length 0, got " + result.length;
    }

    static void testLimitWithNegative() {
        LongStream stream = LongStream.of(7, 8, 9);
        boolean exceptionThrown = false;
        try {
            stream.limit(-1).toArray();
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative maxSize";
    }

    static void testLimitWithLargeMaxSize() {
        LongStream stream = LongStream.of(1, 2, 3);
        LongStream limitedStream = stream.limit(Long.MAX_VALUE);
        long[] result = limitedStream.toArray();
        assert result.length == 3 : "Expected length 3, got " + result.length;
        assert result[0] == 1 : "Expected first element 1, got " + result[0];
        assert result[1] == 2 : "Expected second element 2, got " + result[1];
        assert result[2] == 3 : "Expected third element 3, got " + result[2];
    }
}
