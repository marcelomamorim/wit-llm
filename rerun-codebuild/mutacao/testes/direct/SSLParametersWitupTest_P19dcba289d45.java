/* @test    @summary Test setMaximumPacketSize method of SSLParameters for valid and invalid inputs    @run main SSLParametersWitupTest_P19dcba289d45 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P19dcba289d45 {

    public static void main(String[] args) {
        testSetMaximumPacketSizeValid();
        testSetMaximumPacketSizeNegative();
    }

    private static void testSetMaximumPacketSizeValid() {
        SSLParameters params = new SSLParameters();
        int validSize = 1024;
        params.setMaximumPacketSize(validSize);
        if (params.getClass().getDeclaredFields() != null) {
            // Accessing the private field via reflection to verify the value
            try {
                java.lang.reflect.Field field = SSLParameters.class.getDeclaredField("maximumPacketSize");
                field.setAccessible(true);
                int value = (int) field.get(params);
                if (value != validSize) {
                    throw new AssertionError("Expected maximumPacketSize to be " + validSize + " but was " + value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
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
            throw new AssertionError("Expected IllegalArgumentException for negative input");
        }
    }
}
