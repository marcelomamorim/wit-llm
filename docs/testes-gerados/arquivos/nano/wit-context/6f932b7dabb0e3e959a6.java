/* @test    @summary Test for SynchronousQueue.put method, focusing on null input handling and interruption behavior    @run main SynchronousQueueWitupTest */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueWitupTest {

    public static void main(String[] args) throws Exception {
        testPutWithNull();
        testPutInterruption();
    }

    private static void testPutWithNull() {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        try {
            queue.put(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testPutInterruption() throws Exception {
        SynchronousQueue<Object> queue = new SynchronousQueue<>();
        Thread t = new Thread(() -> {
            try {
                queue.put(new Object());
                System.out.println("FAIL: Expected InterruptedException not thrown");
            } catch (InterruptedException expected) {
                System.out.println("PASS: InterruptedException thrown as expected");
            } catch (Throwable t) {
                System.out.println("FAIL: Unexpected exception: " + t);
            }
        });
        t.start();
        Thread.sleep(50); // ensure thread is waiting on put
        t.interrupt();
        t.join();
    }
}
