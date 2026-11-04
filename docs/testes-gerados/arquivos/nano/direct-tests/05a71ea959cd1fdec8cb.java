/* @test    @summary Test Random.ints() method for deterministic behavior    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest {

    public static void main(String[] args) {
        testIntsMethod();
    }

    public static void testIntsMethod() {
        Random random = new Random(12345L);
        IntStream stream = random.ints();
        int[] expectedValues = {1804289383, 846930886, 1681692777, 1714636915, 1957747793};
        int index = 0;
        stream.limit(5).forEach(value -> {
            int expected = expectedValues[index];
            if (value != expected) {
                throw new AssertionError("Expected " + expected + " but got " + value);
            }
            index++;
        });
        System.out.println("ints() method produces expected sequence.");
    }
}
