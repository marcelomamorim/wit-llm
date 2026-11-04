/*
 * @test
 * @summary Tests for java.lang.Thread.join()
 * @run main ThreadWitupTest
 */

import java.lang.Thread;

public class ThreadWitupTest {

    public static void main(String[] args) throws InterruptedException {
        testJoinNormal();
        testJoinWithNegativeMillis();
        testJoinWithExcessiveNanos();
    }

    public static void testJoinNormal() throws InterruptedException {
        Thread t = new Thread() {
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        };
        t.start();
        t.join();
        assert !t.isAlive() : "Thread should have finished after join";
    }

    public static void testJoinWithNegativeMillis() {
        Thread t = new Thread();
        try {
            // Since join() with no args calls join(0), simulate invalid argument scenario
            // but in current implementation, join() with no args does not accept parameters.
            // To test the exception, we need to call join(long millis) directly.
            // However, as per source, join() calls join(0), so no exception is thrown.
            // For the purpose of this test, we simulate calling join(long millis) with negative value.
            Thread.class.getDeclaredMethod("join", long.class).setAccessible(true);
            Thread.class.getDeclaredMethod("join", long.class).invoke(t, -1L);
            assert false : "Expected IllegalArgumentException for negative millis";
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            assert cause instanceof IllegalArgumentException : "Expected IllegalArgumentException";
        } catch (Exception e) {
            // Other reflection exceptions
            assert false : "Unexpected exception: " + e;
        }
    }

    public static void testJoinWithExcessiveNanos() {
        Thread t = new Thread();
        try {
            // Similarly, invoke join(long, int) with invalid nanos
            java.lang.reflect.Method joinMethod = Thread.class.getDeclaredMethod("join", long.class, int.class);
            joinMethod.setAccessible(true);
            joinMethod.invoke(t, 0L, 1000000); // nanos > 999999
            assert false : "Expected IllegalArgumentException for excessive nanos";
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            assert cause instanceof IllegalArgumentException : "Expected IllegalArgumentException";
        } catch (Exception e) {
            // Other reflection exceptions
            assert false : "Unexpected exception: " + e;
        }
    }
}
