/* @test
   @summary Tests the reseed method of SecureRandom for null argument.
   @run main SecureRandomWitupTest_P8defe277f428
 */
import java.security.SecureRandom;
import java.security.SecureRandomParameters;
public class SecureRandomWitupTest_P8defe277f428 {
    public static void main(String[] args) {
        SecureRandom sr = new SecureRandom();
        try {
            sr.reseed(null);
            throw new AssertionError("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
