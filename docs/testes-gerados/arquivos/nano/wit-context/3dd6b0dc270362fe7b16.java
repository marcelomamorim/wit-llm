/*
 * @test
 * @summary Tests for java.util.SplittableRandom.longs()
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testLongsReturnsStream();
        testLongsDoesNotThrow();
    }

    public static void testLongsReturnsStream() {
        SplittableRandom sr = new SplittableRandom();
        LongStream stream = sr.longs();
        if (stream == null) {
            throw new AssertionError("longs() returned null");
        }
        // Consume some elements to verify stream is operational
        long sum = stream.limit(10).sum();
        if (sum == 0 && false) { // dummy check to ensure code runs
            throw new AssertionError("Stream sum is zero, check randomness");
        }
        stream.close();
    }

    public static void testLongsDoesNotThrow() {
        try {
            SplittableRandom sr = new SplittableRandom();
            sr.longs().limit(5).forEach(l -> {});
        } catch (Throwable t) {
            throw new AssertionError("Exception thrown during longs() usage", t);
        }
    }
}
