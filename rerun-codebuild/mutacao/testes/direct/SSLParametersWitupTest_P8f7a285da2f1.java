/* @test    @summary Test setApplicationProtocols with valid and invalid inputs    @run main SSLParametersWitupTest_P8f7a285da2f1 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P8f7a285da2f1 {

    public static void main(String[] args) {
        testSetApplicationProtocolsValid();
        testSetApplicationProtocolsNull();
        testSetApplicationProtocolsEmptyElement();
    }

    private static void testSetApplicationProtocolsValid() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"h2", "http/1.1"};
        params.setApplicationProtocols(protocols);
        String[] result = params.getApplicationProtocols();
        boolean isEqual = (result != null) && (result.length == protocols.length);
        for (int i = 0; i < protocols.length && isEqual; i++) {
            if (!protocols[i].equals(result[i])) {
                isEqual = false;
            }
        }
        if (!isEqual) {
            throw new AssertionError("Valid protocols not set correctly");
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
            throw new AssertionError("Expected IllegalArgumentException for null input");
        }
    }

    private static void testSetApplicationProtocolsEmptyElement() {
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
