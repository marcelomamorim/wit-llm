/*
 * @test
 * @summary Tests observable behavior of SplittableRandom.nextDouble(double bound)
 * @run main SplittableRandomWitupTest
 */
public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testInvalidBoundsThrow();
        testResultWithinRange();
        testDeterminismWithSameSeed();
        System.out.println("PASSED");
    }

    static void testInvalidBoundsThrow() {
        double[] badBounds = {0.0, -0.0, -1.0, -Double.MIN_VALUE, Double.NaN, Double.NEGATIVE_INFINITY};
        for (double bound : badBounds) {
            SplittableRandom r = new SplittableRandom(42L);
            boolean thrown = false;
            try {
                r.nextDouble(bound);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new RuntimeException("Expected IllegalArgumentException for bound " + bound);
            }
        }
    }

    static void testResultWithinRange() {
        double[] bounds = {1.0, 5.5, Double.MIN_VALUE, 1e300};
        for (double bound : bounds) {
            SplittableRandom r = new SplittableRandom(7L);
            for (int i = 0; i < 10000; i++) {
                double v = r.nextDouble(bound);
                if (!(v >= 0.0 && v < bound)) {
                    throw new RuntimeException("Value " + v + " out of [0, " + bound + ")");
                }
            }
        }
    }

    static void testDeterminismWithSameSeed() {
        SplittableRandom a = new SplittableRandom(12345L);
        SplittableRandom b = new SplittableRandom(12345L);
        for (int i = 0; i < 1000; i++) {
            double va = a.nextDouble(7.0);
            double vb = b.nextDouble(7.0);
            if (va != vb) {
                throw new RuntimeException("Sequences diverge at index " + i + ": " + va + " vs " + vb);
            }
        }
    }
}
