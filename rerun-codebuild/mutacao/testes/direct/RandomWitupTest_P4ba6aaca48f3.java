/* @test    @summary Test Random.longs() method for deterministic behavior and correct stream generation    @run main RandomWitupTest_P4ba6aaca48f3 */

import java.util.Random;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest_P4ba6aaca48f3 {

    public static void main(String[] args) {
        testLongsMethod();
    }

    public static void testLongsMethod() {
        Random random = new Random(12345L);
        LongStream stream = random.longs();
        long[] expectedValues = { -1155484573019324130L,  1022541740951749274L,  1573774456449279270L,  1573774456449279270L,  1573774456449279270L };
        long[] actualValues = stream.limit(5).toArray();
        for (int i = 0; i < expectedValues.length; i++) {
            if (actualValues[i] != expectedValues[i]) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expectedValues[i] + " but got " + actualValues[i]);
            }
        }
        System.out.println("Random.longs() deterministic test passed.");
    }
}
