/*
 * @test
 * @summary Verifies that SSLParameters.setMaximumPacketSize rejects negative values and accepts non-negative values
 * @run main SSLParametersWitupTest
 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest {

    public static void main(String[] args) {
        SSLParameters params = new SSLParameters();

        checkNegativeRejected(params, -1);
        checkNegativeRejected(params, Integer.MIN_VALUE);

        params.setMaximumPacketSize(0);
        params.setMaximumPacketSize(1);
        params.setMaximumPacketSize(Integer.MAX_VALUE);
    }

    private static void checkNegativeRejected(SSLParameters params, int value) {
        boolean thrown = false;
        try {
            params.setMaximumPacketSize(value);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException for value " + value);
        }
    }
}
