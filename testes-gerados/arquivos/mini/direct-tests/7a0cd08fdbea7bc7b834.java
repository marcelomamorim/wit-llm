/*
 * @test
 * @summary Test java.util.concurrent.Executors.newFixedThreadPool(int, ThreadFactory)
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {
    public static void main(String[] args) throws Exception {
        testNewFixedThreadPoolBasic();
        testNewFixedThreadPoolThreadFactoryUsed();
    }

    static void testNewFixedThreadPoolBasic() throws Exception {
        int nThreads = 2;
        ExecutorService es = Executors.newFixedThreadPool(nThreads, Executors.defaultThreadFactory());
        if (!(es instanceof ThreadPoolExecutor)) {
            throw new RuntimeException("Returned ExecutorService is not a ThreadPoolExecutor");
        }
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;
        if (tpe.getCorePoolSize() != nThreads) {
            throw new RuntimeException("Core pool size mismatch: expected " + nThreads + " but got " + tpe.getCorePoolSize());
        }
        if (tpe.getMaximumPoolSize() != nThreads) {
            throw new RuntimeException("Maximum pool size mismatch: expected " + nThreads + " but got " + tpe.getMaximumPoolSize());
        }
        if (tpe.getKeepAliveTime(TimeUnit.MILLISECONDS) != 0L) {
            throw new RuntimeException("Keep alive time mismatch: expected 0 but got " + tpe.getKeepAliveTime(TimeUnit.MILLISECONDS));
        }
        es.shutdown();
        if (!es.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("ExecutorService did not terminate in time");
        }
    }

    static void testNewFixedThreadPoolThreadFactoryUsed() throws Exception {
        final boolean[] threadCreated = {false};
        ThreadFactory tf = new ThreadFactory() {
            public Thread newThread(Runnable r) {
                threadCreated[0] = true;
                Thread t = new Thread(r);
                t.setName("TestThread");
                return t;
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(1, tf);
        es.submit(new Runnable() {
            public void run() {
                // no-op
            }
        }).get();
        es.shutdown();
        if (!es.awaitTermination(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("ExecutorService did not terminate in time");
        }
        if (!threadCreated[0]) {
            throw new RuntimeException("ThreadFactory was not used to create thread");
        }
    }
}
