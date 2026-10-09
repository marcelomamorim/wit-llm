/* @test    @summary Test for Random.longs() method handling of edge cases and exceptions    @run main RandomWitupTest_P19509d630e8e */

import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P19509d630e8e {

    public static void main(String[] args) {
        testLongsDefault();
        testLongsWithInvalidStreamSize();
        testLongsWithValidStreamSize();
    }

    static void testLongsDefault() {
        Random rand = new Random(12345L);
        try {
            LongStream stream = rand.longs();
            long value1 = stream.limit(1).findFirst().orElse(-1L);
            long value2 = rand.longs().limit(1).findFirst().orElse(-1L);
            if (value1 == value2) {
                System.out.println("PASS: longs() produces consistent output with same seed.");
            } else {
                System.out.println("FAIL: longs() output differs with same seed.");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Exception in testLongsDefault: " + t);
        }
    }

    static void testLongsWithInvalidStreamSize() {
        Random rand = new Random(54321L);
        try {
            // Using a negative stream size should throw IllegalArgumentException
            rand.longs(-1L);
            System.out.println("FAIL: Expected IllegalArgumentException for negative stream size.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative stream size.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }

    static void testLongsWithValidStreamSize() {
        Random rand = new Random(67890L);
        long streamSize = 10L;
        try {
            LongStream stream = rand.longs(streamSize); 
            long count = stream.count();
            if (count == streamSize) {
                System.out.println("PASS: longs(streamSize) produces correct number of elements.");
            } else {
                System.out.println("FAIL: longs(streamSize) produced wrong number of elements: " + count);
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Exception in testLongsWithValidStreamSize: " + t);
        }
        try {
            // Edge case: streamSize = 0
            LongStream streamZero = rand.longs(0L);
            if (streamZero.count() == 0) {
                System.out.println("PASS: longs(0L) produces zero elements.");
            } else {
                System.out.println("FAIL: longs(0L) did not produce zero elements.");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Exception in testLongsWithValidStreamSize (zero): " + t);
        }
    }
}
