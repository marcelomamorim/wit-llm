/* @test    @summary Test setting application protocols with valid input    @run main SSLParametersWitupTest_P18cb235d8abc */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P18cb235d8abc {

    public static void main(String[] args) {
        testSetApplicationProtocolsValid();
        testSetApplicationProtocolsNull();
        testSetApplicationProtocolsWithNullElement();
        testSetApplicationProtocolsWithEmptyString();
    }

    private static void testSetApplicationProtocolsValid() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"TLSv1.2", "TLSv1.3"};
        params.setApplicationProtocols(protocols);
        String[] result = params.getApplicationProtocols();
        if (result == null || result.length != protocols.length) {
            throw new AssertionError("Failed: valid protocols not set correctly");
        }
        for (int i = 0; i < protocols.length; i++) {
            if (!protocols[i].equals(result[i])) {
                throw new AssertionError("Failed: protocol mismatch at index " + i);
            }
        }
    }

    private static void testSetApplicationProtocolsNull() {
        SSLParameters params = new SSLParameters();
        boolean threw = false;
        try {
            params.setApplicationProtocols(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Failed: null protocols did not throw exception");
        }
    }

    private static void testSetApplicationProtocolsWithNullElement() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"TLSv1.2", null};
        boolean threw = false;
        try {
            params.setApplicationProtocols(protocols);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Failed: null element in protocols did not throw exception");
        }
    }

    private static void testSetApplicationProtocolsWithEmptyString() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"TLSv1.2", ""};
        boolean threw = false;
        try {
            params.setApplicationProtocols(protocols);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Failed: empty string in protocols did not throw exception");
        }
    }
}
