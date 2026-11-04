/* @test    @summary Testa o método setThreadFactory para verificar comportamento com entrada nula.    @run main ThreadPoolExecutorWitupTest */

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class ThreadPoolExecutorWitupTest {

    public static void main(String[] args) {
        testSetThreadFactoryNull();
    }

    public static void testSetThreadFactoryNull() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setThreadFactory(null);
            // Espera-se que uma NullPointerException seja lançada
            System.out.println("Falha: NullPointerException não lançada ao passar null.");
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException lançada como esperado.");
        } catch (Exception e) {
            System.out.println("Falha: Exceção inesperada: " + e);
        }
    }
}
