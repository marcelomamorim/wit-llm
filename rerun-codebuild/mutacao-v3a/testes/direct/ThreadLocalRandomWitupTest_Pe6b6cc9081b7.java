/* @test    @summary Test the longs() method of ThreadLocalRandom for correct stream generation    @run main ThreadLocalRandomWitupTest_Pe6b6cc9081b7 */

import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest_Pe6b6cc9081b7 {

    public static void main(String[] args) {
        testLongsMethod();
    }

    public static void testLongsMethod() {
        // Call the longs method
        LongStream stream = java.util.concurrent.ThreadLocalRandom.current().longs();
        // Obtain an iterator from the stream
        long[] values = new long[10];
        try {
            // Consume first 10 elements
            stream.limit(10).forEachOrdered(l -> {
                for (int i = 0; i < values.length; i++) {
                    if (values[i] == 0) {
                        values[i] = l;
                        break;
                    }
                }
            });
            boolean allZero = true;
            for (long v : values) {
                if (v != 0) {
                    allZero = false;
                    break;
                }
            }
            // Verify that we got 10 values and they are within expected range
            if (values.length == 10 && !allZero) {
                for (long v : values) {
                    if (v < 0 || v > Long.MAX_VALUE) {
                        throw new AssertionError("Value out of range: " + v);
                    }
                }
                System.out.println("longs() method test passed.");
            } else {
                throw new AssertionError("Failed to generate expected long values.");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during longs() stream test", e);
        }
    }
}
