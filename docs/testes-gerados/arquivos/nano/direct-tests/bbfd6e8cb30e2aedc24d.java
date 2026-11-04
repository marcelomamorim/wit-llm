/* @test    @summary Test Executors.newCachedThreadPool with custom ThreadFactory    @run main ExecutorsWitupTest */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testNewCachedThreadPoolWithCustomFactory();
    }

    public static void testNewCachedThreadPoolWithCustomFactory() {
        ThreadFactory factory = new ThreadFactory() {
            private int count = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("TestThread-" + count++);
                return t;
            }
        };
        ExecutorService executor = Executors.newCachedThreadPool(factory);
        try {
            String threadName = executor.submit(() -> Thread.currentThread().getName()).get();
            if (!threadName.startsWith("TestThread-")) {
                throw new AssertionError("Thread name does not start with 'TestThread-': " + threadName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }
    }
}
