/*
 * @test
 * @summary Test for Executors.callable(Runnable task) method.
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testCallableWithNullTask();
        testCallableWithValidTask();
    }

    private static void testCallableWithNullTask() {
        try {
            Executors.callable((Runnable) null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testCallableWithValidTask() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                // do nothing
            }
        };
        try {
            Callable<Object> callable = Executors.callable(task);
            if (callable != null) {
                Object result = callable.call();
                System.out.println("Callable executed, result: " + result);
            } else {
                System.err.println("Failed: Callable is null");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception during callable execution: " + e);
        }
    }
}
