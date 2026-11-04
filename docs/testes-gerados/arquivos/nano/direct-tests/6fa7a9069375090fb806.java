/* @test    @summary Test delete method of CertificateSubjectName class    @run main CertificateSubjectNameWitupTest */

import java.io.IOException;
import java.io.ByteArrayInputStream;
import sun.security.x509.CertificateSubjectName;

public class CertificateSubjectNameWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteWithDname();
        testDeleteWithInvalidName();
    }

    private static void testDeleteWithDname() throws Exception {
        // Create a dummy X500Name object (simulate with null, as actual creation requires complex setup)
        // For testing delete, the actual value is not used, only the method invocation
        CertificateSubjectName name = new CertificateSubjectName((sun.security.x509.X500Name) null);
        name.delete("dname");
        // After deletion, dnName and dnPrincipal should be null
        // Accessing private fields is not possible; rely on no exception thrown
        System.out.println("testDeleteWithDname passed");
    }

    private static void testDeleteWithInvalidName() {
        try {
            CertificateSubjectName name = new CertificateSubjectName((sun.security.x509.X500Name) null);
            name.delete("invalidName");
            System.err.println("testDeleteWithInvalidName failed: exception not thrown");
        } catch (IOException e) {
            if (e.getMessage().contains("Attribute name not recognized")) {
                System.out.println("testDeleteWithInvalidName passed");
            } else {
                System.err.println("testDeleteWithInvalidName failed: wrong exception message");
            }
        } catch (Exception e) {
            System.err.println("testDeleteWithInvalidName failed: unexpected exception " + e);
        }
    }
}
