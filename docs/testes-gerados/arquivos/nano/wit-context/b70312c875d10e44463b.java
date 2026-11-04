/* @test    @summary Test for SSLParameters.setMaximumPacketSize(int) handling of negative values    @run main SSLParametersWitupTest */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest {

    public static void main(String[] args) {
        testSetMaximumPacketSizeNegative();
        testSetMaximumPacketSizeZero();
        testSetMaximumPacketSizePositive();
    }

    private static void testSetMaximumPacketSizeNegative() {
        SSLParameters params = new SSLParameters();
        try {
            params.setMaximumPacketSize(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative input");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testSetMaximumPacketSizeZero() {
        SSLParameters params = new SSLParameters();
        try {
            params.setMaximumPacketSize(0);
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for zero input: " + e);
            System.exit(1);
        }
    }

    private static void testSetMaximumPacketSizePositive() {
        SSLParameters params = new SSLParameters();
        try {
            params.setMaximumPacketSize(1024);
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for positive input: " + e);
            System.exit(1);
        }
    }
}
