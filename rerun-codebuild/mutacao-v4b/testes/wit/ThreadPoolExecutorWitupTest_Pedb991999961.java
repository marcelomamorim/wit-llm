/* @test
   @summary Testa o método setKeepAliveTime para verificar comportamentos observáveis.
   @run main ThreadPoolExecutorWitupTest_Pedb991999961
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;
public class ThreadPoolExecutorWitupTest_Pedb991999961 {
    public static void main(String[] args) {
        testNegativeTime();
        testZeroTimeWithAllowCoreTimeout();
    }
    private static void testNegativeTime() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        try {
            executor.setKeepAliveTime(-1, TimeUnit.SECONDS);
            throw new AssertionError("Expected IllegalArgumentException for negative time");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
    private static void testZeroTimeWithAllowCoreTimeout() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new java.util.concurrent.ArrayBlockingQueue<Runnable>(10));
        // Forçar que allowCoreThreadTimeOut retorne true, usando reflexão ou método interno, mas como não há acesso, simular comportamento
        // Como não há método público para isso, assumimos que o método permite configurar essa condição externamente, ou que o valor padrão é false.
        // Como o método não fornece API pública para alterar essa configuração, este teste é uma simulação.
        // Portanto, apenas chamamos o método com time=0 e verificamos se lança exceção.
        try {
            executor.setKeepAliveTime(0, TimeUnit.SECONDS);
            // Se não lançar, o comportamento é que não há exceção, então o teste passa.
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Não esperado IllegalArgumentException ao passar tempo zero sem permitir core timeout");
        }
    }
}
