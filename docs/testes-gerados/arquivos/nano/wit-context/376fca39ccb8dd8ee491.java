/* @test    @summary Testa o método adapt com Runnable não nulo e nulo    @run main ForkJoinTaskWitupTest */

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWitupTest {

    public static void main(String[] args) {
        testAdaptWithValidRunnable();
        testAdaptWithNullRunnable();
    }

    private static void testAdaptWithValidRunnable() {
        Runnable r = new Runnable() {
            public void run() {
                // implementação vazia
            }
        };
        ForkJoinTask<?> task = ForkJoinTask.adapt(r);
        if (task == null) {
            throw new AssertionError("adapt() retornou null para Runnable válido");
        }
    }

    private static void testAdaptWithNullRunnable() {
        try {
            ForkJoinTask<?> task = ForkJoinTask.adapt(null);
            if (task == null) {
                throw new AssertionError("adapt() retornou null para null");
            }
        } catch (NullPointerException e) {
            // esperado, pois o método lança NPE se runnable for null
        }
    }
}
