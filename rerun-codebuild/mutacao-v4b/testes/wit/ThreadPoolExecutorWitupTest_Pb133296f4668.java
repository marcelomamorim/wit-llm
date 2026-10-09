/* @test
   @summary Testa o método allowCoreThreadTimeOut para diferentes valores.
   @run main ThreadPoolExecutorWitupTest_Pb133296f4668
 */
import java.util.concurrent.ThreadPoolExecutor;
public class ThreadPoolExecutorWitupTest_Pb133296f4668 {
    public static void main(String[] args) {
        testAllowCoreThreadTimeOutZero();
        testAllowCoreThreadTimeOutPositive();
    }
    private static void testAllowCoreThreadTimeOutZero() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<>(10));
        try {
            executor.allowCoreThreadTimeOut(true);
            // Valor válido: keepAliveTime > 0, não lança exceção
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Não deveria lançar exceção para keepAliveTime > 0", e);
        }
        try {
            executor.allowCoreThreadTimeOut(false);
            // Valor padrão, não deve lançar exceção
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Não deveria lançar exceção ao passar false", e);
        }
    }
    private static void testAllowCoreThreadTimeOutPositive() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0L, java.util.concurrent.TimeUnit.MILLISECONDS, new java.util.concurrent.ArrayBlockingQueue<>(10));
        try {
            executor.allowCoreThreadTimeOut(true);
            // Deve lançar IllegalArgumentException devido keepAliveTime = 0
            throw new AssertionError("Esperava IllegalArgumentException para keepAliveTime=0 ao permitir core thread timeout")
        } catch (IllegalArgumentException e) {
            // esperado
        }
        try {
            executor.allowCoreThreadTimeOut(false);
            // Valor padrão, não lança exceção
        } catch (IllegalArgumentException e) {
            throw new AssertionError("Não deveria lançar exceção ao passar false", e);
        }
    }
}
