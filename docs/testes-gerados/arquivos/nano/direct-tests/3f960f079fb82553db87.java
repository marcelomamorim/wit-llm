/*
 * @test
 * @summary Test constructor FutureTask(Callable) for correct behavior and null handling
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;

public class FutureTaskWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidCallable();
        testConstructorWithNullCallable();
    }

    static void testConstructorWithValidCallable() {
        Callable<String> callable = new Callable<String>() {
            public String call() {
                return "result";
            }
        };
        FutureTask<String> ft = new FutureTask<>(callable);
        assert ft != null : "FutureTask instance should not be null";
    }

    static void testConstructorWithNullCallable() {
        try {
            new FutureTask<String>(null);
            assert false : "Constructor should throw NullPointerException when callable is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
