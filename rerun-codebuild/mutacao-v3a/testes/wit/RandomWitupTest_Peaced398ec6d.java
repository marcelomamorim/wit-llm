/* @test    @summary Tests the doubles() method of java.util.Random for observable behavior.    @run main RandomWitupTest_Peaced398ec6d */

import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest_Peaced398ec6d {

    public static void main(String[] args) {
        testDoublesReturnsStream();
        testDoublesMultipleCalls();
    }

    public static void testDoublesReturnsStream() {
        Random rnd = new Random(12345L);
        DoubleStream ds = rnd.doubles();
        boolean hasElements = ds.iterator().hasNext();
        if (!hasElements) {
            throw new AssertionError("doubles() stream should have elements");
        }
        double firstValue = ds.iterator().nextDouble();
        if (Double.isNaN(firstValue) || Double.isInfinite(firstValue)) {
            throw new AssertionError("doubles() stream element should be a finite double");
        }
    }

    public static void testDoublesMultipleCalls() {
        Random rnd = new Random(54321L);
        DoubleStream ds1 = rnd.doubles();
        DoubleStream ds2 = rnd.doubles();
        double val1 = ds1.iterator().nextDouble();
        double val2 = ds2.iterator().nextDouble();
        if (val1 == val2) {
            throw new AssertionError("Consecutive calls to doubles() should produce different values");
        }
    }
}
