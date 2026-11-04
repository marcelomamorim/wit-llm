/*
 * @test
 * @summary ForkJoinWorkerThread.setContextClassLoader always throws SecurityException
 * @run main ForkJoinWorkerThreadWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.Future;

public class ForkJoinWorkerThreadWitupTest {

    static String attempt(final ClassLoader cl) throws Exception {
        ForkJoinPool pool = new ForkJoinPool(1);
        try {
            Future<String> f = pool.submit(new Callable<String>() {
                public String call() {
                    Thread t = Thread.currentThread();
                    if (!(t instanceof ForkJoinWorkerThread)) {
                        return "not-worker";
                    }
                    try {
                        ((ForkJoinWorkerThread) t).setContextClassLoader(cl);
                        return "no-exception";
                    } catch (SecurityException e) {
                        return "SecurityException";
                    }
                }
            });
            return f.get();
        } finally {
            pool.shutdownNow();
        }
    }

    public static void main(String[] args) throws Exception {
        String r1 = attempt(null);
        if (!"SecurityException".equals(r1)) {
            throw new RuntimeException("null loader: expected SecurityException, got " + r1);
        }
        String r2 = attempt(ClassLoader.getSystemClassLoader());
        if (!"SecurityException".equals(r2)) {
            throw new RuntimeException("system loader: expected SecurityException, got " + r2);
        }
    }
}
