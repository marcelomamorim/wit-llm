/*
 * @test
 * @summary Tests for SSLParameters.setMaximumPacketSize(int)
 * @run main SSLParametersWitupTest_Pa61806a355ad
 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_Pa61806a355ad {
    public static void main(String[] args) {
        testSetMaximumPacketSizeValid();
        testSetMaximumPacketSizeNegative();
    }

    static void testSetMaximumPacketSizeValid() {
        SSLParameters params = new SSLParameters();
        params.setMaximumPacketSize(0);
        params.setMaximumPacketSize(1024);
        System.out.println("testSetMaximumPacketSizeValid passed");
    }

    static void testSetMaximumPacketSizeNegative() {
        SSLParameters params = new SSLParameters();
        try {
            params.setMaximumPacketSize(-1);
            System.out.println("testSetMaximumPacketSizeNegative failed: no exception");
        } catch (IllegalArgumentException e) {
            System.out.println("testSetMaximumPacketSizeNegative passed");
        }
    }
}
