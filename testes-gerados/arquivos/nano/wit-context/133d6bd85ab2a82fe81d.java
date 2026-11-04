/*
 * @test
 * @summary Testa o método Executors.newFixedThreadPool(int) para comportamentos observáveis.
 * @run main ExecutorsWitupTest
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsWitupTest {

    public static void main(String[] args) {
        testValidNThreads();
        testZeroNThreads();
        testNegativeNThreads();
    }

    static void testValidNThreads() {
        int nThreads = 3;
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        if (!(executor instanceof ThreadPoolExecutor)) {
            throw new AssertionError("Executor não é uma instância de ThreadPoolExecutor");
        }
        ThreadPoolExecutor tpe = (ThreadPoolExecutor) executor;
        assert tpe.getCorePoolSize() == nThreads : "Core pool size diferente de nThreads";
        assert tpe.getMaximumPoolSize() == nThreads : "Maximum pool size diferente de nThreads";
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void testZeroNThreads() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(0);
            throw new AssertionError("Esperado IllegalArgumentException para nThreads=0") ;
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    static void testNegativeNThreads() {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(-1);
            throw new AssertionError("Esperado IllegalArgumentException para nThreads negativos") ;
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
}
