/* @test    @summary Test the behavior of SecureRandom.nextBytes with null parameters    @run main SecureRandomWitupTest_P583bac68f9fb */

import java.security.SecureRandom;

public class SecureRandomWitupTest_P583bac68f9fb {

    public static void main(String[] args) {
        testNextBytesWithNullParams();
    }

    public static void testNextBytesWithNullParams() {
        byte[] bytes = new byte[10];
        SecureRandom sr = new SecureRandom();
        boolean threwException = false;
        try {
            sr.nextBytes(bytes, null);
        } catch (IllegalArgumentException e) {
            threwException = true;
        }
        if (!threwException) {
            throw new AssertionError("Expected IllegalArgumentException when passing null params");
        }
    }
}
