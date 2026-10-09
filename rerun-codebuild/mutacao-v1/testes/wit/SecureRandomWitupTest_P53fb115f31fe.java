/* @test    @summary Teste do método nextBytes com parâmetro null para SecureRandomParameters    @run main SecureRandomWitupTest_P53fb115f31fe */

import java.security.SecureRandom;

public class SecureRandomWitupTest_P53fb115f31fe {

    public static void main(String[] args) {
        testNextBytesWithNullParams();
    }

    public static void testNextBytesWithNullParams() {
        byte[] bytes = new byte[10];
        SecureRandom sr = new SecureRandom();
        try {
            sr.nextBytes(bytes, null);
            throw new AssertionError("FAIL: Esperado IllegalArgumentException ao passar null para params");
        } catch (IllegalArgumentException e) {
            // Test passou, exceção esperada
        } catch (Throwable t) {
            throw new AssertionError("FAIL: Exceção inesperada: " + t);
        }
    }
}
