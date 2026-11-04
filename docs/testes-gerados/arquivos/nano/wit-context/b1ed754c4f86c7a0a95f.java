/*
 * @test
 * @summary Test for SSLParameters.setApplicationProtocols(String[] protocols)
 * @run main SSLParametersWitupTest
 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest {

    public static void main(String[] args) {
        testSetApplicationProtocolsNull();
        testSetApplicationProtocolsEmptyString();
        testSetApplicationProtocolsNullElement();
        testSetApplicationProtocolsValid();
    }

    public static void testSetApplicationProtocolsNull() {
        SSLParameters params = new SSLParameters();
        boolean threw = false;
        try {
            params.setApplicationProtocols(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for null protocols");
        }
    }

    public static void testSetApplicationProtocolsEmptyString() {
        SSLParameters params = new SSLParameters();
        boolean threw = false;
        try {
            params.setApplicationProtocols(new String[] {""});
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for empty string element");
        }
    }

    public static void testSetApplicationProtocolsNullElement() {
        SSLParameters params = new SSLParameters();
        boolean threw = false;
        try {
            params.setApplicationProtocols(new String[] {null});
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for null element");
        }
    }

    public static void testSetApplicationProtocolsValid() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"h2", "http/1.1"};
        params.setApplicationProtocols(protocols);
        // Verify that the internal state matches the input
        String[] storedProtocols = getApplicationProtocols(params);
        if (storedProtocols.length != protocols.length) {
            throw new AssertionError("Stored protocols length mismatch");
        }
        for (int i = 0; i < protocols.length; i++) {
            if (!protocols[i].equals(storedProtocols[i])) {
                throw new AssertionError("Mismatch at index " + i);
            }
        }
    }

    // Reflection helper to access private field for verification
    private static String[] getApplicationProtocols(SSLParameters params) {
        try {
            java.lang.reflect.Field field = SSLParameters.class.getDeclaredField("applicationProtocols");
            field.setAccessible(true);
            return (String[]) field.get(params);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Reflection failed", e);
        }
    }
}
