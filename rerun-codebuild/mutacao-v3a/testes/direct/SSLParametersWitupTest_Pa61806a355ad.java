/* @test    @summary Test setMaximumPacketSize method of SSLParameters for valid and invalid inputs    @run main SSLParametersWitupTest_Pa61806a355ad */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_Pa61806a355ad {

    public static void main(String[] args) {
        testSetMaximumPacketSizeValid();
        testSetMaximumPacketSizeNegative();
    }

    private static void testSetMaximumPacketSizeValid() {
        SSLParameters params = new SSLParameters();
        int size = 1024;
        params.setMaximumPacketSize(size);
        System.out.println("Valid size set: " + size);
    }

    private static void testSetMaximumPacketSizeNegative() {
        SSLParameters params = new SSLParameters();
        int negativeSize = -1;
        boolean exceptionThrown = false;
        try {
            params.setMaximumPacketSize(negativeSize);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            System.out.println("Caught expected IllegalArgumentException for size: " + negativeSize);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative size") ;
        }
    }
}
