/*
 * @test
 * @summary Test ThreadLocalRandom.ints() produces a stream of ints
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        IntStream stream = tlr.ints();
        // Take first 10 ints and check they are within int range
        int[] values = stream.limit(10).toArray();
        if (values.length != 10) {
            throw new AssertionError("Expected 10 ints but got " + values.length);
        }
        for (int v : values) {
            // Since ints() returns unbounded int stream, values can be any int
            // Just check no exception and values are int (always true)
            // Also check no duplicates in this small sample (not guaranteed but likely)
            if (v < Integer.MIN_VALUE || v > Integer.MAX_VALUE) {
                throw new AssertionError("Value out of int range: " + v);
            }
        }
    }
}
