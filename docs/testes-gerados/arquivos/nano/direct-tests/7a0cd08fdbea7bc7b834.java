/* @test    @summary Test Executors.newFixedThreadPool with custom ThreadFactory for JDK 11+28    @run main ExecutorsWitupTest */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testNewFixedThreadPoolWithCustomFactory();
    }

    public static void testNewFixedThreadPoolWithCustomFactory() {
        ThreadFactory factory = new ThreadFactory() {
            private int count = 0;
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("TestThread-" + count++);
                return t;
            }
        };
        ExecutorService executor = Executors.newFixedThreadPool(2, factory);
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
