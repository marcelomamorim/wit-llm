/*
 * @test
 * @summary Verifies that the common pool parallelism honors the java.util.concurrent.ForkJoinPool.common.parallelism system property, which is consumed by the private ForkJoinPool(byte) constructor
 * @run main/othervm -Djava.util.concurrent.ForkJoinPool.common.parallelism=3 ForkJoinPoolWitupTest
 */

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) throws Exception {
        String pp = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
        if (!"3".equals(pp)) {
            throw new RuntimeException("Property not set as expected: " + pp);
        }

        ForkJoinPool common = ForkJoinPool.commonPool();
        if (common.getParallelism() != 3) {
            throw new RuntimeException("Expected common pool parallelism 3, got "
                    + common.getParallelism());
        }

        int result = common.submit(() -> 21 * 2).get();
        if (result != 42) {
            throw new RuntimeException("Unexpected task result: " + result);
        }
    }
}
