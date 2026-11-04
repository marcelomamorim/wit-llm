/*
 * @test
 * @summary Basic observable behavior of Random.doubles()
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;

public class RandomWitupTest {
    public static void main(String[] args) {
        Random r1 = new Random(42L);
        double[] a = r1.doubles().limit(100).toArray();
        if (a.length != 100) {
            fail("expected 100 elements but got " + a.length);
        }
        for (double d : a) {
            if (!(d >= 0.0 && d < 1.0)) {
                fail("value out of range [0,1): " + d);
            }
        }

        Random r2 = new Random(42L);
        double[] b = r2.doubles().limit(100).toArray();
        if (!Arrays.equals(a, b)) {
            fail("same seed must produce identical doubles() sequences");
        }

        double[] c = r1.doubles().limit(100).toArray();
        if (Arrays.equals(a, c)) {
            fail("state must advance between successive doubles() streams");
        }

        long count = new Random(7L).doubles().limit(1000).count();
        if (count != 1000L) {
            fail("expected count 1000 but got " + count);
        }

        double[] zero = new Random(0L).doubles().limit(0).toArray();
        if (zero.length != 0) {
            fail("limit(0) must yield an empty stream");
        }
    }

    private static void fail(String msg) {
        throw new RuntimeException(msg);
    }
}
