/* @test    @summary Test setApplicationProtocols with valid and invalid inputs    @run main SSLParametersWitupTest_P18cb235d8abc */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P18cb235d8abc {

    public static void main(String[] args) {
        testSetApplicationProtocolsValid();
        testSetApplicationProtocolsNull();
        testSetApplicationProtocolsEmptyString();
        System.exit(0);
    }

    static void testSetApplicationProtocolsValid() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"h2", "http/1.1"};
        params.setApplicationProtocols(protocols);
        String[] result = params.getApplicationProtocols();
        if (result == null || result.length != protocols.length) {
            throw new AssertionError("Valid protocols not set correctly");
        }
        for (int i = 0; i < protocols.length; i++) {
            if (!protocols[i].equals(result[i])) {
                throw new AssertionError("Mismatch in protocol at index " + i);
            }
        }
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
    }

    static void testSetApplicationProtocolsEmptyString() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"h2", ""};
        boolean threw = false;
        try {
            params.setApplicationProtocols(protocols);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException for empty string element");
        }
    }
}
