/*
 * @test
 * @summary Testa o construtor FutureTask(Runnable, V) para comportamento observável.
 * @run main FutureTaskWitupTest
 */

import java.util.concurrent.FutureTask;
import java.util.concurrent.ExecutionException;

public class FutureTaskWitupTest {

    public static void main(String[] args) {
        testNullRunnableThrows();
        testValidRunnableCreatesFuture();
    }

    private static void testNullRunnableThrows() {
        try {
            new FutureTask<Object>(null, "result");
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Test passed
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e, e);
        }
    }

    private static void testValidRunnableCreatesFuture() {
        Runnable r = () -> {};
        String resultValue = "done";
        FutureTask<String> ft = new FutureTask<>(r, resultValue);
        if (ft.isDone()) {
            throw new AssertionError("Future should not be done immediately after creation");
        }
        ft.run();
        try {
            String result = ft.get();
            if (!result.equals(resultValue)) {
                throw new AssertionError("Result mismatch: expected '" + resultValue + "', got '" + result + "'");
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new AssertionError("Unexpected exception during get(): " + e, e);
        }
    }
}
