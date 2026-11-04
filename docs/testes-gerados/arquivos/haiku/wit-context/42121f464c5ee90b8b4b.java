/*
 * @test
 * @summary Checks public behavior of SplittableRandom.ints(): unbounded stream, limit semantics, and seed determinism
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        int[] first = new SplittableRandom(42L).ints().limit(64).toArray();
        int[] second = new SplittableRandom(42L).ints().limit(64).toArray();
        check(first.length == 64, "expected 64 elements, got " + first.length);
        check(Arrays.equals(first, second), "same seed must produce same sequence");

        int[] other = new SplittableRandom(43L).ints().limit(64).toArray();
        check(!Arrays.equals(first, other), "different seeds should produce different sequences");

        long count = new SplittableRandom(7L).ints().limit(1000L).count();
        check(count == 1000L, "expected count 1000, got " + count);

        int[] empty = new SplittableRandom(7L).ints().limit(0L).toArray();
        check(empty.length == 0, "limit(0) must be empty");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
