/* @test    @summary Test SecureRandom.nextBytes with valid parameters    @run main SecureRandomWitupTest_P53fb115f31fe */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_P53fb115f31fe {

    public static void main(String[] args) {
        testNextBytesWithValidParams();
        testNextBytesWithNullParams();
    }

    private static void testNextBytesWithValidParams() {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] buffer = new byte[16];
            SecureRandomParameters params = null; // Using null to simulate default behavior
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
            throw new RuntimeException("Unexpected exception in testNextBytesWithValidParams", e);
        }
    }

    private static void testNextBytesWithNullParams() {
        try {
            SecureRandom sr = new SecureRandom();
            byte[] buffer = new byte[8];
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
            throw new AssertionError("Expected no exception when params is null", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in testNextBytesWithNullParams", e);
        }
    }
}
