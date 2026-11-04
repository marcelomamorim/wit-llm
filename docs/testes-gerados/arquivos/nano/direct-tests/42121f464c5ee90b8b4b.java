/* @test    @summary Test the ints() method of SplittableRandom for correct stream generation    @run main SplittableRandomWitupTest */

import java.util.SplittableRandom;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testIntsMethod();
    }

    public static void testIntsMethod() {
        SplittableRandom sr = new SplittableRandom(12345L);
        IntStream stream = sr.ints();
        int[] values = stream.limit(10).toArray();
        int[] expected = {1804289383, 846930886, 1681692777, 1714636915, 1957747793, 424238335, 719885386, 1649760492, 596516649, 1189641421};
        for (int i = 0; i < values.length; i++) {
            if (values[i] != expected[i]) {
                throw new AssertionError("ints() method produced unexpected value at index " + i + ": " + values[i]);
            }
        }
        System.out.println("ints() method test passed.");
    }
}
