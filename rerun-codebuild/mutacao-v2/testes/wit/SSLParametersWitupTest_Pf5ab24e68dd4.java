/*
 * @test
 * @summary Test javax.net.ssl.SSLParameters.setMaximumPacketSize(int) for valid and invalid inputs
 * @run main SSLParametersWitupTest_Pf5ab24e68dd4
 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_Pf5ab24e68dd4 {
    public static void main(String[] args) {
        SSLParameters params = new SSLParameters();

        // Test valid positive value
        params.setMaximumPacketSize(1024);
        if (getMaximumPacketSize(params) != 1024) {
            throw new RuntimeException("Failed to set maximumPacketSize to 1024");
        }

        // Test zero value
        params.setMaximumPacketSize(0);
        if (getMaximumPacketSize(params) != 0) {
            throw new RuntimeException("Failed to set maximumPacketSize to 0");
        }

        // Test negative value triggers IllegalArgumentException
        boolean threw = false;
        try {
            params.setMaximumPacketSize(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException for negative maximumPacketSize");
        }
    }

    // Helper method to reflectively get the private field maximumPacketSize
    // Because there is no public getter, reflection is used here as a last resort
    private static int getMaximumPacketSize(SSLParameters params) {
        try {
            java.lang.reflect.Field f = SSLParameters.class.getDeclaredField("maximumPacketSize");
            f.setAccessible(true);
            return f.getInt(params);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get maximumPacketSize via reflection", e);
        }
    }
}
