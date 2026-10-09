/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.longs()
 * @run main ThreadLocalRandomWitupTest_P2200df593783
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest_P2200df593783 {
    public static void main(String[] args) {
        testLongsStreamNonNull();
        testLongsStreamDistinctValues();
    }

    static void testLongsStreamNonNull() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        LongStream stream = tlr.longs();
        if (stream == null) {
            throw new AssertionError("ThreadLocalRandom.longs() returned null");
        }
    }

    static void testLongsStreamDistinctValues() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        LongStream stream = tlr.longs();
        // Take first 100 longs and check they are within Long range and not all equal
        long[] values = stream.limit(100).toArray();
        if (values.length != 100) {
            throw new AssertionError("Expected 100 values from longs() stream");
        }
        boolean allEqual = true;
        long first = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] != first) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            throw new AssertionError("All values from longs() stream are equal, expected variability");
        }
    }
}
