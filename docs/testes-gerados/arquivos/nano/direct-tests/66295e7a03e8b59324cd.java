/* @test    @summary Test Object.wait(long, int) method for valid and invalid inputs    @run main ObjectWitupTest */

import java.lang.Object;

public class ObjectWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testWaitValid();
        testWaitNegativeTimeout();
        testWaitInvalidNanos();
    }

    static void testWaitValid() throws InterruptedException {
        Object obj = new Object();
        long timeout = 100;
        int nanos = 500000;
        obj.wait(timeout, nanos); // should not throw
        // Test with nanos > 0, timeout should be incremented
        long startTime = System.currentTimeMillis();
        obj.wait(50, 1); // should wait approximately 50ms
        long elapsed = System.currentTimeMillis() - startTime;
        assert elapsed >= 50 : "wait did not wait long enough";
    }

    static void testWaitNegativeTimeout() {
        Object obj = new Object();
        try {
            obj.wait(-1, 0);
            assert false : "Expected IllegalArgumentException for negative timeout";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("timeoutMillis") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testWaitInvalidNanos() {
        Object obj = new Object();
        try {
            obj.wait(10, -1);
            assert false : "Expected IllegalArgumentException for nanos < 0";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("nanosecond") : "Unexpected message: " + e.getMessage();
        }
        try {
            obj.wait(10, 1000000);
            assert false : "Expected IllegalArgumentException for nanos > 999999";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("nanosecond") : "Unexpected message: " + e.getMessage();
        }
    }
}
