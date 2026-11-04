/*
 * @test
 * @summary Test javax.net.ssl.SSLParameters.setApplicationProtocols(String[]) for valid and invalid inputs
 * @run main javax.net.ssl.SSLParametersWitupTest
 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest {
    public static void main(String[] args) {
        testSetApplicationProtocolsNull();
        testSetApplicationProtocolsEmptyElement();
        testSetApplicationProtocolsValid();
    }

    private static void testSetApplicationProtocolsNull() {
        SSLParameters params = new SSLParameters();
        try {
            params.setApplicationProtocols(null);
            throw new RuntimeException("Expected IllegalArgumentException for null protocols");
        } catch (IllegalArgumentException e) {
            if (!"protocols was null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSetApplicationProtocolsEmptyElement() {
        SSLParameters params = new SSLParameters();
        String[] invalidProtocols1 = new String[] {"http/1.1", null};
        try {
            params.setApplicationProtocols(invalidProtocols1);
            throw new RuntimeException("Expected IllegalArgumentException for null element in protocols");
        } catch (IllegalArgumentException e) {
            if (!"An element of protocols was null/empty".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        String[] invalidProtocols2 = new String[] {"http/1.1", ""};
        try {
            params.setApplicationProtocols(invalidProtocols2);
            throw new RuntimeException("Expected IllegalArgumentException for empty element in protocols");
        } catch (IllegalArgumentException e) {
            if (!"An element of protocols was null/empty".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }

    private static void testSetApplicationProtocolsValid() {
        SSLParameters params = new SSLParameters();
        String[] validProtocols = new String[] {"h2", "http/1.1"};
        params.setApplicationProtocols(validProtocols);
        String[] result = params.getApplicationProtocols();
        if (result == null || result.length != validProtocols.length) {
            throw new RuntimeException("Returned protocols array is invalid");
        }
        for (int i = 0; i < validProtocols.length; i++) {
            if (!validProtocols[i].equals(result[i])) {
                throw new RuntimeException("Returned protocols differ from set protocols");
            }
        }
        // Verify defensive copy: modifying original array does not affect stored array
        validProtocols[0] = "modified";
        String[] afterModification = params.getApplicationProtocols();
        if ("modified".equals(afterModification[0])) {
            throw new RuntimeException("Defensive copy not made in setApplicationProtocols");
        }
    }
}