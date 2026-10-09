/* @test    @summary Test setApplicationProtocols with valid input    @run main SSLParametersWitupTest_P7144b0a3310b */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P7144b0a3310b {

    public static void main(String[] args) {
        testSetApplicationProtocolsValid();
        testSetApplicationProtocolsNull();
        testSetApplicationProtocolsEmptyString();
    }

    static void testSetApplicationProtocolsValid() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"h2", "http/1.1"};
        params.setApplicationProtocols(protocols);
        // No exception expected, verify clone behavior by setting again
        String[] newProtocols = {"spdy"};
        params.setApplicationProtocols(newProtocols);
        System.out.println("testSetApplicationProtocolsValid passed");
    }

    static void testSetApplicationProtocolsNull() {
        SSLParameters params = new SSLParameters();
        boolean threw = false;
        try {
            params.setApplicationProtocols(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for null input");
        }
        System.out.println("testSetApplicationProtocolsNull passed");
    }

    static void testSetApplicationProtocolsEmptyString() {
        SSLParameters params = new SSLParameters();
        boolean threw = false;
        try {
            String[] protocols = {""};
            params.setApplicationProtocols(protocols);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for empty string element");
        }
        System.out.println("testSetApplicationProtocolsEmptyString passed");
    }
}
