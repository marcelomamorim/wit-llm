/*
 * @test
 * @summary Tests for java.util.concurrent.FutureTask
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;

public class FutureTaskWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCallable();
        testConstructorWithValidCallable();
    }

    private static void testConstructorWithNullCallable() {
        try {
            new FutureTask<Object>(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testConstructorWithValidCallable() {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "test";
            }
        };
        FutureTask<String> ft = new FutureTask<>(callable);
        if (ft == null) {
            System.err.println("Failed: FutureTask instance is null");
        } else {
            System.out.println("Passed: FutureTask created with valid callable");
        }
    }
}
