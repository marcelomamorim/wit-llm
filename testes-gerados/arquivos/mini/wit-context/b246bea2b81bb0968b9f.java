/*
 * @test
 * @summary Test sun.security.x509.CertificateSubjectName.get(String) method
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;

public class CertificateSubjectNameWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a simple X500Name for testing
        X500Name name = new X500Name("CN=Test Subject");
        CertificateSubjectName csn = new CertificateSubjectName(name);

        // Test get with DN_NAME ("dname") returns the X500Name instance
        Object result1 = csn.get("dname");
        if (!(result1 instanceof X500Name)) {
            throw new RuntimeException("Expected X500Name instance for 'dname'");
        }
        if (!result1.equals(name)) {
            throw new RuntimeException("Returned X500Name does not match the original");
        }

        // Test get with DN_PRINCIPAL ("x500principal") returns X500Principal
        Object result2 = csn.get("x500principal");
        if (result2 == null) {
            throw new RuntimeException("Expected non-null X500Principal for 'x500principal'");
        }
        // The returned object should be an instance of javax.security.auth.x500.X500Principal
        if (!"javax.security.auth.x500.X500Principal".equals(result2.getClass().getName())) {
            throw new RuntimeException("Expected X500Principal instance for 'x500principal'");
        }

        // Test get with unrecognized name throws IOException
        boolean threw = false;
        try {
            csn.get("invalidName");
        } catch (IOException e) {
            threw = true;
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
        if (!threw) {
            throw new RuntimeException("Expected IOException for unrecognized attribute name");
        }
    }
}
