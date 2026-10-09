/* @test    @summary Test CertificateVersion.get(String) method for expected behaviors and exception paths.    @run main CertificateVersionWitupTest_Pefcfa9729c3f */

import sun.security.x509.CertificateVersion;
import java.io.IOException;

public class CertificateVersionWitupTest_Pefcfa9729c3f {

    public static void main(String[] args) {
        testGetWithValidName();
        testGetWithInvalidName();
    }

    private static void testGetWithValidName() {
        try {
            CertificateVersion cv = new CertificateVersion();
            Integer result = cv.get("x509.info.version");
            if (result == null) {
                System.err.println("Failed: get("x509.info.version") returned null");
                System.exit(1);
            }
            if (result.intValue() != CertificateVersion.V1) {
                System.err.println("Failed: get("x509.info.version") returned " + result);
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Unexpected IOException in testGetWithValidName: " + e);
            System.exit(1);
        }
    }

    private static void testGetWithInvalidName() {
        try {
            CertificateVersion cv = new CertificateVersion();
            cv.get("invalidName");
            System.err.println("Failed: get("invalidName") did not throw IOException");
            System.exit(1);
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                System.err.println("Failed: IOException message unexpected: " + msg);
                System.exit(1);
            }
        }
    }
}
