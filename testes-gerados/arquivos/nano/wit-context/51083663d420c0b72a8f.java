/*
 * @test
 * @summary Test for Executors.callable(Runnable, T) method.
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testCallableWithNullTask();
        testCallableWithValidRunnable();
    }

    private static void testCallableWithNullTask() {
        try {
            Executors.callable(null, "result");
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testCallableWithValidRunnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // do nothing
            }
        };
        String expectedResult = "result";
        try {
            Callable<String> callable = Executors.callable(runnable, expectedResult);
            String actualResult = callable.call();
            if (expectedResult.equals(actualResult)) {
                System.out.println("Passed: Callable returns expected result");
            } else {
                System.err.println("Failed: Callable did not return expected result");
            }
        } catch (Throwable t) {
            System.err.println("Failed: Exception during callable execution: " + t);
        }
    }
}
