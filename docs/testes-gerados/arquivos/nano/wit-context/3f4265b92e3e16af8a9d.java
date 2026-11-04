/*
 * @test
 * @summary Tests for java.util.Random.longs()
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testLongsDefault();
        testLongsWithSeed();
        testLongsNegativeStreamSize();
        testLongsZeroStreamSize();
    }

    static void testLongsDefault() {
        Random rand = new Random();
        LongStream stream = rand.longs();
        long[] values = stream.limit(5).toArray();
        boolean allEqual = true;
        for (int i = 1; i < values.length; i++) {
            if (values[i] != values[0]) {
                allEqual = false;
                break;
            }
        }
        if (values.length != 5) {
            throw new AssertionError("Expected 5 elements, got " + values.length);
        }
        System.out.println("testLongsDefault passed with values: " + java.util.Arrays.toString(values));
    }

    static void testLongsWithSeed() {
        long seed = 12345L;
        Random rand1 = new Random(seed);
        Random rand2 = new Random(seed);
        LongStream stream1 = rand1.longs();
        LongStream stream2 = rand2.longs();
        long[] vals1 = stream1.limit(3).toArray();
        long[] vals2 = stream2.limit(3).toArray();
        if (!java.util.Arrays.equals(vals1, vals2)) {
            throw new AssertionError("Sequences with same seed differ: " + java.util.Arrays.toString(vals1) + " vs " + java.util.Arrays.toString(vals2));
        }
        System.out.println("testLongsWithSeed passed with sequence: " + java.util.Arrays.toString(vals1));
    }

    static void testLongsNegativeStreamSize() {
        Random rand = new Random();
        try {
            LongStream stream = rand.longs();
            stream.limit(-1).toArray();
            throw new AssertionError("Expected IllegalArgumentException for negative limit");
        } catch (IllegalArgumentException e) {
            System.out.println("testLongsNegativeStreamSize passed with expected exception.");
        }
    }

    static void testLongsZeroStreamSize() {
        Random rand = new Random();
        LongStream stream = rand.longs();
        long[] result = stream.limit(0).toArray();
        if (result.length != 0) {
            throw new AssertionError("Expected empty array for zero limit, got: " + java.util.Arrays.toString(result));
        }
        System.out.println("testLongsZeroStreamSize passed.");
    }
}
