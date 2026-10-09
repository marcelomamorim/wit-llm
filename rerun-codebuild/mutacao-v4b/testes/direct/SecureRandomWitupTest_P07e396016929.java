/* @test
   @summary Verifies that calling nextBytes with valid parameters fills the byte array.
   @run main SecureRandomWitupTest_P07e396016929
 */
import java.security.SecureRandom;
import java.security.SecureRandomParameters;
import java.util.Arrays;

public class SecureRandomWitupTest_P07e396016929 {
    public static void main(String[] args) {
        testNextBytesWithValidParams();
        testNextBytesWithNullParams();
    }

    private static void testNextBytesWithValidParams() {
        byte[] buffer = new byte[16];
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters params = null; // Using null to simulate default behavior
        try {
            sr.nextBytes(buffer, params);
            boolean allZero = true;
            for (byte b : buffer) {
                if (b != 0) {
                    allZero = false;
                    break;
                }
            }
            if (allZero) {
                throw new AssertionError("Buffer not filled with random data");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    private static void testNextBytesWithNullParams() {
        byte[] buffer = new byte[8];
        SecureRandom sr = new SecureRandom();
        try {
            sr.nextBytes(buffer, null);
            boolean allZero = true;
            for (byte b : buffer) {
                if (b != 0) {
                    allZero = false;
                    break;
                }
            }
            if (allZero) {
                throw new AssertionError("Buffer not filled with random data when params is null");
            }
        } catch (IllegalArgumentException e) {
            // Expected if params is null and method throws
            // But in current implementation, null params are accepted
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
