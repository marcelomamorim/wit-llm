/*
 * @test
 * @summary Test for System.registerShutdownHook method.
 * @run main SystemWitupTest
 */

import java.lang.System;

public class SystemWitupTest {

    public static void main(String[] args) {
        testRegisterShutdownHookValid();
        testRegisterShutdownHookInvalidSlot();
    }

    private static void testRegisterShutdownHookValid() {
        Runnable hook = new Runnable() {
            public void run() {
                System.out.println("Shutdown hook executed");
            }
        };
        try {
            System.registerShutdownHook(0, false, hook);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input", e);
        }
    }

    private static void testRegisterShutdownHookInvalidSlot() {
        Runnable hook = new Runnable() {
            public void run() {
                System.out.println("Shutdown hook executed");
            }
        };
        try {
            System.registerShutdownHook(11, false, hook);
            throw new AssertionError("Expected IllegalArgumentException for slot > 10");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
        try {
            System.registerShutdownHook(-1, false, hook);
            throw new AssertionError("Expected IllegalArgumentException for slot < 0");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
