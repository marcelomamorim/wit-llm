/*
 * @test
 * @summary Test javax.net.ssl.SSLParameters.setApplicationProtocols behavior
 * @run main SSLParametersWitupTest_P82bde19ce1b0
 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P82bde19ce1b0 {
    public static void main(String[] args) {
        testSetApplicationProtocolsValid();
        testSetApplicationProtocolsNullArray();
        testSetApplicationProtocolsNullElement();
        testSetApplicationProtocolsEmptyElement();
    }

    private static void testSetApplicationProtocolsValid() {
        SSLParameters params = new SSLParameters();
        String[] protocols = new String[] {"http/1.1", "h2"};
        params.setApplicationProtocols(protocols);
        String[] setProtocols = params.getApplicationProtocols();
        if (setProtocols.length != 2 || !setProtocols[0].equals("http/1.1") || !setProtocols[1].equals("h2")) {
            throw new RuntimeException("Failed to set valid application protocols correctly");
        }
        // Verify defensive copy: modifying original array does not affect stored array
        protocols[0] = "modified";
        String[] afterModify = params.getApplicationProtocols();
        if (afterModify[0].equals("modified")) {
            throw new RuntimeException("setApplicationProtocols did not clone input array");
        }
    }

    private static void testSetApplicationProtocolsNullArray() {
        SSLParameters params = new SSLParameters();
        try {
            params.setApplicationProtocols(null);
            throw new RuntimeException("Expected IllegalArgumentException for null protocols array");
        } catch (IllegalArgumentException e) {
            // expected
            if (!"protocols was null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for null protocols array: " + e.getMessage());
            }
        }
    }

    private static void testSetApplicationProtocolsNullElement() {
        SSLParameters params = new SSLParameters();
        String[] protocols = new String[] {"http/1.1", null};
        try {
            params.setApplicationProtocols(protocols);
            throw new RuntimeException("Expected IllegalArgumentException for null element in protocols array");
        } catch (IllegalArgumentException e) {
            // expected
            if (!"An element of protocols was null/empty".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for null element: " + e.getMessage());
            }
        }
    }

    private static void testSetApplicationProtocolsEmptyElement() {
        SSLParameters params = new SSLParameters();
        String[] protocols = new String[] {"http/1.1", ""};
        try {
            params.setApplicationProtocols(protocols);
            throw new RuntimeException("Expected IllegalArgumentException for empty element in protocols array");
        } catch (IllegalArgumentException e) {
            // expected
            if (!"An element of protocols was null/empty".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message for empty element: " + e.getMessage());
            }
        }
    }
}
