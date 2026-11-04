/* @test    @summary Test default constructor of ForkJoinPool    @run main ForkJoinPoolWitupTest */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        ForkJoinPool pool = new ForkJoinPool();
        int parallelism = pool.getParallelism();
        int expectedParallelism = Math.min(ForkJoinPool.MAX_CAP, Runtime.getRuntime().availableProcessors());
        assert parallelism == expectedParallelism : "Expected parallelism " + expectedParallelism + " but got " + parallelism;
        long keepAliveTime = pool.getKeepAliveTime(TimeUnit.MILLISECONDS);
        assert keepAliveTime == ForkJoinPool.DEFAULT_KEEPALIVE : "Expected default keepAliveTime " + ForkJoinPool.DEFAULT_KEEPALIVE + " but got " + keepAliveTime;
        boolean asyncMode = pool.getAsyncMode();
        assert asyncMode == false : "Expected asyncMode to be false";
    }
}
