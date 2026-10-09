/* @test    @summary Test for javax.net.ssl.SSLParameters.setApplicationProtocols(String[] protocols)    @run main SSLParametersWitupTest_P7144b0a3310b */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest_P7144b0a3310b {

    public static void main(String[] args) {
        testSetApplicationProtocolsWithNull();
        testSetApplicationProtocolsWithEmptyString();
        testSetApplicationProtocolsWithValidArray();
    }

    public static void testSetApplicationProtocolsWithNull() {
        SSLParameters params = new SSLParameters();
        try {
            params.setApplicationProtocols(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null input\n");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null input\n");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception type: " + t + "\n");
        }
    }

    public static void testSetApplicationProtocolsWithEmptyString() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {""};
        try {
            params.setApplicationProtocols(protocols);
            System.out.println("FAIL: Expected IllegalArgumentException for empty string element\n");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for empty string element\n");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception type: " + t + "\n");
        }
    }

    public static void testSetApplicationProtocolsWithValidArray() {
        SSLParameters params = new SSLParameters();
        String[] protocols = {"TLSv1.2", "TLSv1.3"};
        try {
            params.setApplicationProtocols(protocols);
            if (java.util.Arrays.equals(protocols, params.getClass().getDeclaredField("applicationProtocols").get(params))) {
                System.out.println("PASS: applicationProtocols set correctly\n");
            } else {
                System.out.println("FAIL: applicationProtocols not set correctly\n");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Exception during valid array test: " + e + "\n");
        }
    }
}
