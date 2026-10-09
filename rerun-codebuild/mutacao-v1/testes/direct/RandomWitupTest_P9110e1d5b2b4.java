/* @test    @summary Test Random.longs() method for correct stream generation    @run main RandomWitupTest_P9110e1d5b2b4 */

import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P9110e1d5b2b4 {
    public static void main(String[] args) {
        testLongsReturnsStream();
        testLongsStreamHasExpectedElements();
    }

    private static void testLongsReturnsStream() {
        Random rand = new Random(12345L);
        LongStream stream = rand.longs();
        if (stream == null) {
            throw new AssertionError("FAIL: longs() returned null stream");
        }
        stream.close();
    }

    private static void testLongsStreamHasExpectedElements() {
        Random rand = new Random(12345L);
        LongStream stream = rand.longs();
        long[] values = stream.limit(5).toArray();
        long[] expected = {rand.nextLong(), rand.nextLong(), rand.nextLong(), rand.nextLong(), rand.nextLong()};
        // Since Random is deterministic with seed, the sequence should match
        // But because calling nextLong() advances the seed, we need to re-initialize
        // So, re-initialize and generate expected sequence
        rand = new Random(12345L);
        long[] expectedSeq = new long[5];
        for (int i = 0; i < 5; i++) {
            expectedSeq[i] = rand.nextLong();
        }
        if (!java.util.Arrays.equals(values, expectedSeq)) {
            throw new AssertionError("FAIL: Stream elements do not match expected sequence\nExpected: " + java.util.Arrays.toString(expectedSeq) + "\nActual: " + java.util.Arrays.toString(values));
        }
        stream.close();
    }
}
