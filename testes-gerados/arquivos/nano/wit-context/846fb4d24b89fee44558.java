/* @test    @summary Testa o método setContextClassLoader de ForkJoinWorkerThread para comportamento de exceção */
/* @run main ForkJoinWorkerThreadWitupTest */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class ForkJoinWorkerThreadWitupTest {

    public static void main(String[] args) {
        testSetContextClassLoaderThrows();
    }

    public static void testSetContextClassLoaderThrows() {
        ForkJoinPool pool = new ForkJoinPool(1);
        ForkJoinWorkerThread worker = null;
        try {
            worker = pool.getWorkerThreadFactory().newThread(pool);
        } catch (Exception e) {
            // Caso o método de fábrica não esteja acessível, cria uma instância de forma segura
            worker = new ForkJoinWorkerThread(pool) {};
        }
        ClassLoader cl = null; // Pode ser null para testar comportamento padrão
        try {
            worker.setContextClassLoader(cl);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected SecurityException was not thrown.");
        } catch (SecurityException se) {
            // Esperado, o teste passa
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
