/* @test    @summary Test for CertificateSubjectName.get(String) method    @run main CertificateSubjectNameWitupTest */

import java.io.IOException;
import java.io.ByteArrayInputStream;
import sun.security.x509.CertificateSubjectName;
import sun.security.util.X500Name;

public class CertificateSubjectNameWitupTest {

    public static void main(String[] args) throws Exception {
        testGetWithDname();
        testGetWithX500Principal();
        testGetWithInvalidName();
    }

    static void testGetWithDname() throws Exception {
        X500Name name = new X500Name("CN=Test, OU=Unit, O=Organization, C=US");
        CertificateSubjectName certName = new CertificateSubjectName(name);
        Object result = certName.get("dname");
        if (!(result instanceof X500Name)) {
            throw new AssertionError("Expected X500Name for 'dname', got: " + result.getClass());
        }
        if (!result.equals(name)) {
            throw new AssertionError("Returned X500Name does not match original");
        }
    }

    static void testGetWithX500Principal() throws Exception {
        X500Name name = new X500Name("CN=Principal, O=Org");
        CertificateSubjectName certName = new CertificateSubjectName(name);
        Object result = certName.get("x500principal");
        if (!(result instanceof java.security.cert.X500Principal)) {
            throw new AssertionError("Expected X500Principal for 'x500principal', got: " + result.getClass());
        }
        java.security.cert.X500Principal principal = (java.security.cert.X500Principal) result;
        if (!principal.getName().equals(name.toString())) {
            throw new AssertionError("X500Principal name does not match X500Name string");
        }
    }

    static void testGetWithInvalidName() throws Exception {
        X500Name name = new X500Name("CN=Invalid");
        CertificateSubjectName certName = new CertificateSubjectName(name);
        boolean exceptionThrown = false;
        try {
            certName.get("unknown");
        } catch (IOException e) {
            exceptionThrown = true;
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        }
    }
}
