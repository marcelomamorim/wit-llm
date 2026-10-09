/* @test
   @summary Verifies that the ints() method of Random returns a stream of integers.
   @run main RandomWitupTest_Pbb929772ec5b
 */
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_Pbb929772ec5b {
    public static void main(String[] args) {
        Random random = new Random(12345L);
        IntStream stream = random.ints();
        int[] values = stream.limit(5).toArray();
        int[] expected = {random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt(), random.nextInt()};
        for (int i = 0; i < 5; i++) {
            if (values[i] != expected[i]) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expected[i] + " but got " + values[i]);
            }
        }
    }
}
