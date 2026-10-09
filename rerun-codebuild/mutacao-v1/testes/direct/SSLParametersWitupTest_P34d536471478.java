/* @test    @summary Test for SSLParameters.setMaximumPacketSize(int) method    @run main SSLParametersWitupTest_P34d536471478 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P34d536471478 {

    public static void main(String[] args) {
        testSetMaximumPacketSizeValid();
        testSetMaximumPacketSizeNegative();
    }

    private static void testSetMaximumPacketSizeValid() {
        SSLParameters params = new SSLParameters();
        int validSize = 1024;
        params.setMaximumPacketSize(validSize);
        if (params.maximumPacketSize != validSize) {
            throw new AssertionError("Expected maximumPacketSize to be " + validSize + ", but was " + params.maximumPacketSize);
        }
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
}
