/* @test    @summary Test for SplittableRandom forEachRemaining method    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.function.LongConsumer;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testForEachRemaining();
    }

    public static void testForEachRemaining() {
        SplittableRandom sr = new SplittableRandom(12345L);
        long[] result = new long[5];
        LongConsumer consumer = new LongConsumer() {
            int count = 0;
            public void accept(long value) {
                if (count < result.length) {
                    result[count] = value;
                    count++;
                }
            }
        };
        sr.forEachRemaining(consumer);
        boolean allNonZero = true;
        for (long v : result) {
            if (v == 0) {
                allNonZero = false;
                break;
            }
        }
        if (allNonZero) {
            System.out.println("Test passed: all values are non-zero.");
        } else {
            System.out.println("Test failed: some values are zero.");
        }
    }
}
