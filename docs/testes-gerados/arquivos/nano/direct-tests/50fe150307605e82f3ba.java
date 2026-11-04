/*
 * @test
 * @summary Test LongStream.concat method for correctness and null handling
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public class LongStreamWitupTest {

    public static void main(String[] args) {
        testConcatWithValidStreams();
        testConcatWithNullA();
        testConcatWithNullB();
    }

    static void testConcatWithValidStreams() {
        LongStream stream1 = LongStream.of(1L, 2L, 3L);
        LongStream stream2 = LongStream.of(4L, 5L);
        LongStream result = LongStream.concat(stream1, stream2);
        long[] expected = {1L, 2L, 3L, 4L, 5L};
        long[] actual = result.toArray();
        assert java.util.Arrays.equals(expected, actual) : "Concatenation of valid streams failed";
        result.close();
    }

    static void testConcatWithNullA() {
        try {
            LongStream.concat(null, LongStream.of(1L));
            assert false : "Expected NullPointerException for null first argument";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConcatWithNullB() {
        try {
            LongStream.concat(LongStream.of(1L), null);
            assert false : "Expected NullPointerException for null second argument";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
