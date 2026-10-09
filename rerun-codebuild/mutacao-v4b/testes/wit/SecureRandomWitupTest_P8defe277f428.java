/* @test
   @summary Testa o método reseed de java.security.SecureRandom para comportamentos observáveis.
   @run main SecureRandomWitupTest_P8defe277f428
 */
import java.security.SecureRandom;
import java.security.SecureRandomParameters;
public class SecureRandomWitupTest_P8defe277f428 {
    public static void main(String[] args) {
        testReseedWithNullParams();
        testReseedWithValidParams();
    }
    private static void testReseedWithNullParams() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.reseed(null);
            throw new AssertionError("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
    private static void testReseedWithValidParams() {
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters params = new SecureRandomParameters();
        try {
            sr.reseed(params);
            // comportamento esperado: não lança exceção
        } catch (UnsupportedOperationException e) {
            // pode ocorrer se engineReseed não for suportado, mas não deve lançar por causa do código atual
            throw new AssertionError("Unexpected UnsupportedOperationException", e);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception", e);
        }
    }
}
