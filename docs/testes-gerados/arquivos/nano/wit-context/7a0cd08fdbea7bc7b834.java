/*
 * @test
 * @summary Testa o método Executors.newFixedThreadPool com entradas inválidas.
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testNegativeNThreads();
        testZeroNThreads();
        testNThreadsLessThanZero();
    }

    private static void testNegativeNThreads() {
        try {
            ExecutorService es = Executors.newFixedThreadPool(-1, new DummyThreadFactory());
            // Espera-se que lance IllegalArgumentException
            System.out.println("Erro: esperado IllegalArgumentException para nThreads = -1, mas não ocorreu.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: IllegalArgumentException lançado para nThreads = -1.");
        } catch (Exception e) {
            System.out.println("Erro: exceção inesperada para nThreads = -1: " + e);
        }
    }

    private static void testZeroNThreads() {
        try {
            ExecutorService es = Executors.newFixedThreadPool(0, new DummyThreadFactory());
            System.out.println("Erro: esperado IllegalArgumentException para nThreads = 0, mas não ocorreu.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: IllegalArgumentException lançado para nThreads = 0.");
        } catch (Exception e) {
            System.out.println("Erro: exceção inesperada para nThreads = 0: " + e);
        }
    }

    private static void testNThreadsLessThanZero() {
        try {
            ExecutorService es = Executors.newFixedThreadPool(-5, new DummyThreadFactory());
            System.out.println("Erro: esperado IllegalArgumentException para nThreads = -5, mas não ocorreu.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: IllegalArgumentException lançado para nThreads = -5.");
        } catch (Exception e) {
            System.out.println("Erro: exceção inesperada para nThreads = -5: " + e);
        }
    }

    private static class DummyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }
}
