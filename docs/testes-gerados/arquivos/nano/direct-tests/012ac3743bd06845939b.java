/*
 * @test
 * @summary Test ExecutorCompletionService constructor with valid and null executor
 * @run main ExecutorCompletionServiceWitupTest
 */

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorCompletionService;

public class ExecutorCompletionServiceWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidExecutor();
        testConstructorWithNullExecutor();
    }

    static void testConstructorWithValidExecutor() {
        Executor executor = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<?> ecs = new ExecutorCompletionService<>(executor);
        assert ecs != null : "ExecutorCompletionService should not be null with valid executor";
    }

    static void testConstructorWithNullExecutor() {
        try {
            new ExecutorCompletionService<>(null);
            assert false : "Constructor should throw NullPointerException when executor is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
