/*
 * @test
 * @summary Test FutureTask constructor with Callable argument
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;

public class FutureTaskWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidCallable();
        testConstructorWithNullCallable();
    }

    private static void testConstructorWithValidCallable() {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "test";
            }
        };
        FutureTask<String> ft = new FutureTask<>(callable);
        if (ft == null) {
            throw new AssertionError("FutureTask instance is null");
        }
    }

    private static void testConstructorWithNullCallable() {
        try {
            new FutureTask<String>(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
