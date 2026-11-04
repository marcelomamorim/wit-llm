/*
 * @test
 * @summary WITUP-derived checks for the ForkJoinPool(int parallelism) constructor: invalid parallelism is rejected and valid bounds produce a working pool
 * @run main ForkJoinPoolWitupTest
 */
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) throws Exception {
        checkIllegal(0);
        checkIllegal(-1);
        checkIllegal(Integer.MIN_VALUE);
        checkIllegal(0x8000);
        checkIllegal(Integer.MAX_VALUE);

        ForkJoinPool p1 = new ForkJoinPool(1);
        try {
            if (p1.getParallelism() != 1) {
                throw new RuntimeException("parallelism 1 expected, got " + p1.getParallelism());
            }
            int v = p1.submit(() -> 42).get();
            if (v != 42) {
                throw new RuntimeException("unexpected task result " + v);
            }
        } finally {
            p1.shutdown();
            if (!p1.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("pool did not terminate");
            }
        }

        ForkJoinPool pMax = new ForkJoinPool(0x7fff);
        try {
            if (pMax.getParallelism() != 0x7fff) {
                throw new RuntimeException("parallelism 0x7fff expected, got " + pMax.getParallelism());
            }
        } finally {
            pMax.shutdown();
            if (!pMax.awaitTermination(10, TimeUnit.SECONDS)) {
                throw new RuntimeException("max pool did not terminate");
            }
        }
    }

    static void checkIllegal(int parallelism) {
        try {
            new ForkJoinPool(parallelism);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for parallelism " + parallelism);
    }
}
