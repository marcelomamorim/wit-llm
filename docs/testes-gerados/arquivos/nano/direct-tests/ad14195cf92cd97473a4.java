/*
 * @test    @summary Test System.registerShutdownHook method for correct registration.
 * @run main SystemWitupTest
 */

import java.lang.System;

public class SystemWitupTest {

    public static void main(String[] args) {
        testRegisterShutdownHook()
        ;
    }

    public static void testRegisterShutdownHook() {
        final boolean[] hookCalled = {false};
        Runnable hook = new Runnable() {
            @Override
            public void run() {
                hookCalled[0] = true;
            }
        };
        try {
            System.registerShutdownHook(0, false, hook);
            // Since actual shutdown hooks are invoked during JVM shutdown,
            // we test that the method completes without exception.
            System.out.println("registerShutdownHook executed without exception.");
        } catch (Throwable t) {
            throw new AssertionError("registerShutdownHook threw an exception", t);
        }
        // Note: Cannot verify hook execution here as JVM shutdown is not triggered.
        // But we can verify that no exception was thrown during registration.
    }
}
