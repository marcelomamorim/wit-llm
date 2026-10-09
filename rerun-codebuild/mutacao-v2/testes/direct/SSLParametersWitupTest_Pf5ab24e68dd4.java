/*
 * @test
 * @summary Test javax.net.ssl.SSLParameters.setMaximumPacketSize behavior
 * @run main SSLParametersWitupTest_Pf5ab24e68dd4
 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_Pf5ab24e68dd4 {
    public static void main(String[] args) {
        testSetMaximumPacketSizePositive();
        testSetMaximumPacketSizeZero();
        testSetMaximumPacketSizeNegative();
    }

    private static void testSetMaximumPacketSizePositive() {
        SSLParameters params = new SSLParameters();
        params.setMaximumPacketSize(1024);
        int size = params.getMaximumPacketSize();
        if (size != 1024) {
            throw new RuntimeException("Expected maximumPacketSize to be 1024 but was " + size);
        }
    }

    private static void testSetMaximumPacketSizeZero() {
        SSLParameters params = new SSLParameters();
        params.setMaximumPacketSize(0);
        int size = params.getMaximumPacketSize();
        if (size != 0) {
            throw new RuntimeException("Expected maximumPacketSize to be 0 but was " + size);
        }
    }

    private static void testSetMaximumPacketSizeNegative() {
        SSLParameters params = new SSLParameters();
        try {
            params.setMaximumPacketSize(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative maximumPacketSize");
        } catch (IllegalArgumentException iae) {
            // Expected exception
        }
    }
}
