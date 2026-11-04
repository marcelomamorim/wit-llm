/*
 * @test
 * @summary Checks determinism, range and laziness of Random.longs()
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        long[] a = new Random(42L).longs().limit(100).toArray();
        long[] b = new Random(42L).longs().limit(100).toArray();
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("same seed produced different longs()");
        }

        for (long v : a) {
            if (v < 0L) {
                throw new RuntimeException("longs() produced negative value: " + v);
            }
        }

        long[] prefix = new Random(42L).longs().limit(5).toArray();
        for (int i = 0; i < prefix.length; i++) {
            if (prefix[i] != a[i]) {
                throw new RuntimeException("prefix mismatch at index " + i);
            }
        }

        long[] other = new Random(43L).longs().limit(100).toArray();
        if (Arrays.equals(a, other)) {
            throw new RuntimeException("different seeds produced identical sequences");
        }

        long count = new Random(7L).longs().limit(1000L).count();
        if (count != 1000L) {
            throw new RuntimeException("expected 1000 elements, got " + count);
        }

        long empty = new Random(1L).longs().limit(0L).count();
        if (empty != 0L) {
            throw new RuntimeException("expected 0 elements, got " + empty);
        }
    }
}
