/* @test
   @summary Verifies the behavior of ThreadLocalRandom.doubles().
   @run main ThreadLocalRandomWitupTest_P308097fc73a2
 */
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;
public class ThreadLocalRandomWitupTest_P308097fc73a2 {
    public static void main(String[] args) {
        DoubleStream ds = java.util.concurrent.ThreadLocalRandom.current().doubles();
        double[] values = ds.limit(10).toArray();
        if (values.length != 10) {
            throw new AssertionError("Expected 10 elements, got " + values.length);
        }
        for (double v : values) {
            if (Double.isNaN(v) || Double.isInfinite(v)) {
                throw new AssertionError("Generated value is NaN or Infinite: " + v);
            }
        }
        System.out.println("Test passed: doubles() generates 10 finite double values.");
    }
}
