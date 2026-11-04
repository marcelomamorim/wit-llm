/*
 * @test
 * @summary Test basic construction and behavior of CyclicBarrier(int parties) constructor
 * @run main CyclicBarrierWitupTest
 */

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierWitupTest {
    public static void main(String[] args) throws Exception {
        // Test construction with valid parties
        CyclicBarrier barrier = new CyclicBarrier(3);
        if (barrier == null) throw new RuntimeException("CyclicBarrier instance is null");

        // The parties count should be 3, but no direct getter, so test await behavior
        // Start 3 threads that await on the barrier and verify they all pass
        final CyclicBarrier cb = barrier;
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    cb.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    cb.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            public void run() {
                try {
                    cb.await();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        // Test construction with parties = 1, await should return immediately
        CyclicBarrier cb1 = new CyclicBarrier(1);
        try {
            cb1.await();
        } catch (Exception e) {
            throw new RuntimeException("await on barrier with 1 party should not throw", e);
        }

        // Test construction with parties = 0 should throw IllegalArgumentException
        boolean threw = false;
        try {
            new CyclicBarrier(0);
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) throw new RuntimeException("Expected IllegalArgumentException for parties=0");
    }
}
