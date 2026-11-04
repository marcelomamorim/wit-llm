/*
 * @test
 * @summary Witup-derived behavioral tests for Thread.join()
 * @run main ThreadWitupTest
 */

public class ThreadWitupTest {

    public static void main(String[] args) throws Exception {
        testJoinOnUnstartedThreadReturnsImmediately();
        testJoinOnTerminatedThreadReturnsImmediately();
        testJoinThrowsInterruptedExceptionWhenInterrupted();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testJoinOnUnstartedThreadReturnsImmediately() throws InterruptedException {
        Thread t = new Thread(() -> { });
        t.join();
        check(!t.isAlive(), "unstarted thread must not be alive after join()");
    }

    static void testJoinOnTerminatedThreadReturnsImmediately() throws InterruptedException {
        Thread t = new Thread(() -> { });
        t.start();
        t.join();
        t.join();
        check(!t.isAlive(), "terminated thread must not be alive after join()");
    }

    static void testJoinThrowsInterruptedExceptionWhenInterrupted() throws InterruptedException {
        Thread blocker = new Thread(() -> {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                // expected on cleanup
            }
        });
        blocker.setDaemon(true);
        blocker.start();

        Thread.currentThread().interrupt();
        boolean thrown = false;
        try {
            blocker.join();
        } catch (InterruptedException e) {
            thrown = true;
        } finally {
            Thread.interrupted();
            blocker.interrupt();
            blocker.join();
        }
        check(thrown, "join() on alive thread with interrupt status set must throw InterruptedException");
    }
}
