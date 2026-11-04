/*
 * @test
 * @summary Verify ThreadLocalRandom.nextDouble(origin, bound) rejects invalid ranges and returns values within [origin, bound)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        checkInvalidRange(1.0, 1.0);
        checkInvalidRange(2.0, 1.0);
        checkInvalidRange(-1.0, -1.0);
        checkInvalidRange(Double.NaN, 1.0);
        checkInvalidRange(0.0, Double.NaN);

        checkValidRange(-3.5, 7.25);
        checkValidRange(0.0, 1.0);
        checkValidRange(1.0, 2.0);
        checkValidRange(-1000.0, -999.5);
    }

    static void checkInvalidRange(double origin, double bound) {
        try {
            ThreadLocalRandom.current().nextDouble(origin, bound);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException('nextDouble did not reject invalid range origin=' + origin + ' bound=' + bound);
    }

    static void checkValidRange(double origin, double bound) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 0; i < 1000; i++) {
            double v = rnd.nextDouble(origin, bound);
            if (v < origin || v >= bound) {
                throw new RuntimeException('value ' + v + ' outside [' + origin + ', ' + bound + ')');
            }
        }
    }
}
