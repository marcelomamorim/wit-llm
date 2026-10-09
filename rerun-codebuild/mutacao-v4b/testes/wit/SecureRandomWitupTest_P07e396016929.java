/* @test
   @summary Testa o método nextBytes com parâmetro null para SecureRandomParameters.
   @run main SecureRandomWitupTest_P07e396016929
 */
import java.security.SecureRandom;
public class SecureRandomWitupTest_P07e396016929 {
    public static void main(String[] args) {
        byte[] bytes = new byte[10];
        SecureRandom sr = new SecureRandom();
        try {
            sr.nextBytes(bytes, null);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
}
