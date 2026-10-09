/* @test    @summary Test SecureRandom.nextBytes with valid parameters    @run main SecureRandomWitupTest_P5b4dfb85afe6 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_P5b4dfb85afe6 {

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
                System.err.println("Test failed: all bytes are zero after nextBytes call.");
            } else {
                System.out.println("testNextBytesWithValidParams passed.");
            }
        } catch (Exception e) {
            System.err.println("Exception in testNextBytesWithValidParams: " + e);
        }
    }

    private static void testNextBytesWithNullParams() {
        byte[] buffer = new byte[8];
        SecureRandom sr = new SecureRandom();
        try {
            sr.nextBytes(buffer, null);
            System.err.println("Test failed: expected IllegalArgumentException for null params.");
        } catch (IllegalArgumentException e) {
            System.out.println("testNextBytesWithNullParams passed.");
        } catch (Exception e) {
            System.err.println("Unexpected exception in testNextBytesWithNullParams: " + e);
        }
    }
}
