/* @test    @summary Test for SSLParameters.setMaximumPacketSize(int) handling of negative values    @run main SSLParametersWitupTest_P34d536471478 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P34d536471478 {

    public static void main(String[] args) {
        testSetMaximumPacketSizeNegative();
        testSetMaximumPacketSizeZero();
        testSetMaximumPacketSizePositive();
    }

    private static void testSetMaximumPacketSizeNegative() {
        SSLParameters params = new SSLParameters();
        boolean exceptionThrown = false;
        try {
            params.setMaximumPacketSize(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative maximumPacketSize");
        }
    }

    private static void testSetMaximumPacketSizeZero() {
        SSLParameters params = new SSLParameters();
        try {
            params.setMaximumPacketSize(0);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero maximumPacketSize: " + e);
        }
    }

    private static void testSetMaximumPacketSizePositive() {
        SSLParameters params = new SSLParameters();
        try {
            params.setMaximumPacketSize(1024);
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for positive maximumPacketSize: " + e);
        }
    }
}
