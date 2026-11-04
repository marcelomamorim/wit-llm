/*
 * @test
 * @summary Verify Random.ints() returns an unbounded IntStream whose elements follow the nextInt() sequence
 * @run main RandomWitupTest
 */
import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        long seed = 42L;

        Random r = new Random(seed);
        IntStream s = r.ints();
        if (s == null) {
            throw new AssertionError("ints() returned null");
        }

        Random ref = new Random(seed);
        int[] actual = s.limit(16).toArray();
        for (int i = 0; i < actual.length; i++) {
            int expected = ref.nextInt();
            if (actual[i] != expected) {
                throw new AssertionError("index " + i + ": expected " + expected + " but was " + actual[i]);
            }
        }

        long count = new Random(seed).ints().limit(0).count();
        if (count != 0L) {
            throw new AssertionError("limit(0) count expected 0 but was " + count);
        }

        Random r2 = new Random(seed);
        Random ref2 = new Random(seed);
        int[] first = r2.ints().limit(3).toArray();
        int[] second = r2.ints().limit(3).toArray();
        for (int i = 0; i < 3; i++) {
            if (first[i] != ref2.nextInt()) {
                throw new AssertionError("first segment mismatch at " + i);
            }
        }
        for (int i = 0; i < 3; i++) {
            if (second[i] != ref2.nextInt()) {
                throw new AssertionError("second segment mismatch at " + i);
            }
        }
    }
}
