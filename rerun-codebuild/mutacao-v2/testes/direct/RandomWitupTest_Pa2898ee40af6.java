/*
 * @test
 * @summary Test java.util.Random.longs() produces a stream of longs
 * @run main RandomWitupTest_Pa2898ee40af6
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest_Pa2898ee40af6 {
    public static void main(String[] args) {
        Random rng = new Random(12345L);
        LongStream stream = rng.longs();
        // Take first 5 longs and check they are deterministic and within full long range
        long[] values = stream.limit(5).toArray();

        // Check length
        if (values.length != 5) {
            throw new AssertionError("Expected 5 longs, got " + values.length);
        }

        // Check values are deterministic for seed 12345L
        long[] expected = {
            -4962768465676381890L,
            4962768465676381890L,
            -4962768465676381890L,
            4962768465676381890L,
            -4962768465676381890L
        };

        for (int i = 0; i < values.length; i++) {
            if (values[i] != expected[i]) {
                throw new AssertionError("Value at index " + i + " differs. Expected: " + expected[i] + ", got: " + values[i]);
            }
        }
    }
}
