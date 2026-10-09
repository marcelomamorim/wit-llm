/* @test
   @summary Verifies that the longs() method returns a LongStream that produces values within the expected range.
   @run main ThreadLocalRandomWitupTest_Pdc360aaec7bc
 */
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;
import java.util.Objects;

public class ThreadLocalRandomWitupTest_Pdc360aaec7bc {
    public static void main(String[] args) {
        testLongsRange();
    }

    public static void testLongsRange() {
        LongStream stream = ThreadLocalRandom.current().longs();
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        long count = 0;
        long limit = 1000;
        for (long value : stream.limit(limit).toArray()) {
            if (value < 0 || value > Long.MAX_VALUE) {
                throw new AssertionError("Value out of range: " + value);
            }
            if (value < min) min = value;
            if (value > max) max = value;
            count++;
        }
        if (count != limit) {
            throw new AssertionError("Stream produced wrong number of elements: " + count);
        }
        // Since longs() is supposed to produce values in [0, Long.MAX_VALUE],
        // check if min and max are within range.
        if (min < 0 || max > Long.MAX_VALUE) {
            throw new AssertionError("Values out of expected range: min=" + min + ", max=" + max);
        }
    }
}
