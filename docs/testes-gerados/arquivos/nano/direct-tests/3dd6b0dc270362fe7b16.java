/*
 * @test
 * @summary Test the longs() method of java.util.SplittableRandom
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.LongStream;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testLongsReturnsStreamOfCorrectTypeAndSize();
        testLongsDeterminism();
    }

    static void testLongsReturnsStreamOfCorrectTypeAndSize() {
        SplittableRandom sr = new SplittableRandom(12345L);
        LongStream stream = sr.longs();
        long count = stream.limit(10).count();
        if (count != 10) {
            throw new AssertionError("Expected stream of size 10, but got " + count);
        }
    }

    static void testLongsDeterminism() {
        long seed = 54321L;
        SplittableRandom sr1 = new SplittableRandom(seed);
        SplittableRandom sr2 = new SplittableRandom(seed);
        LongStream stream1 = sr1.longs().limit(5);
        LongStream stream2 = sr2.longs().limit(5);
        long[] arr1 = stream1.toArray();
        long[] arr2 = stream2.toArray();
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                throw new AssertionError("Streams with same seed should produce same values at index " + i);
            }
        }
    }
}
