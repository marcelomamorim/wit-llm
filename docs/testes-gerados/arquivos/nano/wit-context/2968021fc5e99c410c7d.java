/*
 * @test
 * @summary Tests for java.util.Random.longs(long, long)
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testLongsWithValidRange();
        testLongsWithInvalidRange();
    }

    static void testLongsWithValidRange() {
        Random rand = new Random(12345L);
        long origin = 10L;
        long bound = 20L;
        LongStream stream = rand.longs(origin, bound);
        boolean valid = stream.allMatch(n -> n >= origin && n < bound);
        if (!valid) {
            throw new AssertionError("longs() produced value outside range") ;
        }
    }

    static void testLongsWithInvalidRange() {
        Random rand = new Random(54321L);
        long origin = 30L;
        long bound = 20L;
        try {
            rand.longs(origin, bound);
            throw new AssertionError("Expected IllegalArgumentException not thrown") ;
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
