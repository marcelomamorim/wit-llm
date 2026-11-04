/* @test    @summary Test ForkJoinPool constructor with default parameters.    @run main ForkJoinPoolWitupTest */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ForkJoinPoolWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
        testConstructorWithInvalidProcessors();
        testConstructorWithNullFactory();
    }

    public static void testDefaultConstructor() {
        ForkJoinPool pool = new ForkJoinPool();
        assert pool != null : "Pool should be instantiated";
        // Verifica se o pool está ativo e pode aceitar tarefas
        try {
            pool.execute(() -> {});
        } catch (Exception e) {
            assert false : "Executor should accept tasks without exception";
        }
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // Ignorado
        }
    }

    public static void testConstructorWithInvalidProcessors() {
        int invalidProcessors = -1;
        try {
            new ForkJoinPool(invalidProcessors);
            assert false : "Expected IllegalArgumentException for negative processors";
        } catch (IllegalArgumentException e) {
            // Test passa se exceção for lançada
        }
    }

    public static void testConstructorWithNullFactory() {
        try {
            ForkJoinPool pool = new ForkJoinPool(4, null, null, false);
            assert pool != null : "Pool should be instantiated even with null factory";
            pool.shutdown();
        } catch (Exception e) {
            assert false : "Constructor with null factory should not throw";
        }
    }
}
