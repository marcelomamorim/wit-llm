/* @test    @summary Test the longs() method of java.util.Random for correct stream generation    @run main RandomWitupTest_P19509d630e8e */

import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P19509d630e8e {
    public static void main(String[] args) {
        testLongsReturnsStreamOfExpectedSize();
        testLongsDeterministicSequence();
    }

    static void testLongsReturnsStreamOfExpectedSize() {
        Random r = new Random(12345L);
        LongStream stream = r.longs();
        long count = stream.limit(100).count();
        if (count != 100) {
            throw new AssertionError("Expected stream size 100, but got " + count);
        }
    }

    static void testLongsDeterministicSequence() {
        long seed = 54321L;
        Random r1 = new Random(seed);
        Random r2 = new Random(seed);
        LongStream stream1 = r1.longs().limit(50);
        LongStream stream2 = r2.longs().limit(50);
        long[] arr1 = stream1.toArray();
        long[] arr2 = stream2.toArray();
        for (int i = 0; i < 50; i++) {
            if (arr1[i] != arr2[i]) {
                throw new AssertionError("Sequences differ at index " + i);
            }
        }
    }
}
