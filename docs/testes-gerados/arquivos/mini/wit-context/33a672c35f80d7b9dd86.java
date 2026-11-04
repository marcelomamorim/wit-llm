/*
 * @test
 * @summary Deterministic tests for ThreadLocalRandom.ints()
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {
    public static void main(String[] args) {
        testIntsStreamBasic();
    }

    private static void testIntsStreamBasic() {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        IntStream stream = tlr.ints();
        if (stream == null) {
            throw new AssertionError("ints() returned null IntStream");
        }
        // Check that the stream can produce some ints without error
        int count = 0;
        for (int i : (int[]) stream.limit(10).toArray()) {
            // Just consume to ensure no exceptions
            count++;
        }
        if (count != 10) {
            throw new AssertionError("ints() stream did not produce 10 ints");
        }
    }
}
