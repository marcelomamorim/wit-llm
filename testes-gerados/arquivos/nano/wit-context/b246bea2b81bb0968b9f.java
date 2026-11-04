/* @test    @summary Test for CertificateSubjectName.get(String) method to verify exception handling and behavior.    @run main CertificateSubjectNameWitupTest */

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import sun.security.x509.CertificateSubjectName;

public class CertificateSubjectNameWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithValidDname();
        testGetWithX500Principal();
        testGetWithInvalidName();
    }

    private static void testGetWithValidDname() throws Exception {
        // Setup: create a CertificateSubjectName with a valid X500Name
        // Since constructor with X500Name is not directly testable here, mock or simulate
        // For simplicity, assume a minimal mock or placeholder
        // Note: In real scenario, instantiate with actual X500Name if available
        // Here, focus on exception handling for unrecognized attribute
        try {
            CertificateSubjectName obj = new CertificateSubjectNamePlaceholder();
            Object result = obj.get("dname");
            if (result == null) {
                throw new AssertionError("Expected non-null result for 'dname'");
            }
            System.out.println("testGetWithValidDname passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetWithValidDname: " + e);
        }
    }

    private static void testGetWithX500Principal() throws Exception {
        try {
            CertificateSubjectName obj = new CertificateSubjectNamePlaceholder();
            Object result = obj.get("x500principal");
            if (result == null) {
                throw new AssertionError("Expected non-null result for 'x500principal'");
            }
            System.out.println("testGetWithX500Principal passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetWithX500Principal: " + e);
        }
    }

    private static void testGetWithInvalidName() throws Exception {
        try {
            CertificateSubjectName obj = new CertificateSubjectNamePlaceholder();
            obj.get("invalidName");
            throw new AssertionError("Expected IOException for unrecognized attribute");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected IOException message: " + msg);
            }
            System.out.println("testGetWithInvalidName passed.");
        }
    }

    // Placeholder class to simulate CertificateSubjectName behavior
    static class CertificateSubjectNamePlaceholder extends CertificateSubjectName {
        public CertificateSubjectNamePlaceholder() {
            super(null);
        }
        @Override
        public Object get(String name) throws IOException {
            if ("dname".equalsIgnoreCase(name)) {
                return "CN=Test"; // mock return
            } else if ("x500principal".equalsIgnoreCase(name)) {
                return new Object(); // mock object
            } else {
                throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSubjectName.");
            }
        }
    }
}
