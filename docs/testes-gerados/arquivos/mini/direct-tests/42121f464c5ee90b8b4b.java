/*
 * @test
 * @summary Test SplittableRandom.ints() produces a stream of ints with expected properties
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;
import java.util.stream.IntStream;

public class SplittableRandomWitupTest {
    public static void main(String[] args) {
        SplittableRandom sr = new SplittableRandom(12345L);
        IntStream stream = sr.ints();

        // Test that stream is not null
        if (stream == null) {
            throw new AssertionError("ints() returned null stream");
        }

        // Test that stream produces ints within full int range
        // Collect first 1000 ints and check range
        int count = 0;
        for (int val : (Iterable<Integer>) stream::iterator) {
            if (val < Integer.MIN_VALUE || val > Integer.MAX_VALUE) {
                throw new AssertionError("Value out of int range: " + val);
            }
            count++;
            if (count >= 1000) break;
        }

        if (count != 1000) {
            throw new AssertionError("Stream did not produce 1000 ints");
        }

        // Test that multiple calls produce different streams
        SplittableRandom sr2 = new SplittableRandom(12345L);
        IntStream s1 = sr.ints();
        IntStream s2 = sr2.ints();

        int first1 = s1.findFirst().getAsInt();
        int first2 = s2.findFirst().getAsInt();

        if (first1 != first2) {
            throw new AssertionError("Streams from same seed differ on first int");
        }

        // Test that split instances produce different streams
        SplittableRandom split = sr.split();
        IntStream s3 = split.ints();
        int first3 = s3.findFirst().getAsInt();

        if (first1 == first3) {
            throw new AssertionError("Split instance produced same first int as original");
        }
    }
}
