/* @test
   @summary Verifies that Random.doubles() produces a DoubleStream that can generate doubles.
   @run main RandomWitupTest_P39b90d194a7b
 */
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P39b90d194a7b {
    public static void main(String[] args) {
        Random random = new Random(12345L);
        DoubleStream ds = random.doubles();
        double[] values = ds.limit(5).toArray();
        // Check that the stream produces 5 doubles
        if (values.length != 5) {
            throw new AssertionError("Expected 5 doubles, got " + values.length);
        }
        // Check that doubles are within the expected range [0.0, 1.0)
        for (double d : values) {
            if (d < 0.0 || d >= 1.0) {
                throw new AssertionError("Double out of range: " + d);
            }
        }
        // Generate another stream and verify that it produces different values (not equal to previous)
        DoubleStream ds2 = random.doubles();
        double[] values2 = ds2.limit(5).toArray();
        boolean allEqual = true;
        for (int i = 0; i < 5; i++) {
            if (values[i] != values2[i]) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) {
            throw new AssertionError("Two consecutive calls produced identical sequences, which is unlikely.");
        }
    }
}
