/*
 * @test
 * @summary Test LongStream.concat for null arguments and normal concatenation
 * @run main LongStreamWitupTest
 */

import java.util.stream.LongStream;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public class LongStreamWitupTest {
    public static void main(String[] args) {
        testConcatNullA();
        testConcatNullB();
        testConcatNormal();
    }

    private static void testConcatNullA() {
        LongStream b = LongStream.of(1L, 2L);
        try {
            LongStream.concat(null, b);
            throw new RuntimeException("Expected NullPointerException for null a");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConcatNullB() {
        LongStream a = LongStream.of(1L, 2L);
        try {
            LongStream.concat(a, null);
            throw new RuntimeException("Expected NullPointerException for null b");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConcatNormal() {
        LongStream a = LongStream.of(1L, 2L);
        LongStream b = LongStream.of(3L, 4L);
        LongStream concatenated = LongStream.concat(a, b);

        long[] result = concatenated.toArray();
        if (result.length != 4) {
            throw new RuntimeException("Concatenated stream length incorrect: " + result.length);
        }
        if (result[0] != 1L || result[1] != 2L || result[2] != 3L || result[3] != 4L) {
            throw new RuntimeException("Concatenated stream elements incorrect");
        }
    }
}
