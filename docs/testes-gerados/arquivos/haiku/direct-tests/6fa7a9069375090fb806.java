/*
 * @test
 * @summary Verifies CertificateSubjectName.delete accepts the dname attribute case-insensitively and rejects unrecognized names with IOException
 * @modules java.base/sun.security.x509
 * @run main CertificateSubjectNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.X500Name;

public class CertificateSubjectNameWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteDnNameClearsName();
        testDeleteDnNameIgnoresCase();
        testDeleteUnknownNameThrows("x509.info.subject");
        testDeleteUnknownNameThrows("subject");
        testDeleteUnknownNameThrows("");
    }

    private static void testDeleteDnNameClearsName() throws IOException {
        CertificateSubjectName n = new CertificateSubjectName((X500Name) null);
        n.delete("dname");
        if (!"".equals(n.toString())) {
            throw new RuntimeException("Expected empty string after delete, got: " + n.toString());
        }
    }

    private static void testDeleteDnNameIgnoresCase() throws IOException {
        CertificateSubjectName n = new CertificateSubjectName((X500Name) null);
        n.delete("DNAME");
        n.delete("DName");
        if (!"".equals(n.toString())) {
            throw new RuntimeException("Expected empty string after case-insensitive delete");
        }
    }

    private static void testDeleteUnknownNameThrows(String name) {
        CertificateSubjectName n = new CertificateSubjectName((X500Name) null);
        try {
            n.delete(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: " + name);
    }
}
