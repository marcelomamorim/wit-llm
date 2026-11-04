/*
 * @test
 * @summary Verify CertificateSubjectName.delete accepts the dname attribute (case-insensitively) and rejects other names with IOException
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
        testDeleteUnknownNameThrowsIOException();
        testDeleteUnknownNameLeavesStateUnchanged();
        testDeleteDnNameTwiceDoesNotFail();
    }

    private static void testDeleteDnNameClearsName() throws Exception {
        X500Name name = new X500Name("CN=Test");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        if (!csn.toString().equals(name.toString())) {
            throw new RuntimeException("Unexpected initial toString: " + csn.toString());
        }
        csn.delete("dname");
        if (!csn.toString().isEmpty()) {
            throw new RuntimeException("Name not cleared after delete(\"dname\"): " + csn.toString());
        }
    }

    private static void testDeleteDnNameIgnoresCase() throws Exception {
        X500Name name = new X500Name("CN=Test");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        csn.delete("DName");
        if (!csn.toString().isEmpty()) {
            throw new RuntimeException("Name not cleared after case-insensitive delete: " + csn.toString());
        }
    }

    private static void testDeleteUnknownNameThrowsIOException() throws Exception {
        X500Name name = new X500Name("CN=Test");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        expectIOException(csn, "subject");
        expectIOException(csn, "cn");
        expectIOException(csn, "");
    }

    private static void testDeleteUnknownNameLeavesStateUnchanged() throws Exception {
        X500Name name = new X500Name("CN=Test");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        expectIOException(csn, "subject");
        if (!csn.toString().equals(name.toString())) {
            throw new RuntimeException("State changed after rejected delete: " + csn.toString());
        }
    }

    private static void testDeleteDnNameTwiceDoesNotFail() throws Exception {
        X500Name name = new X500Name("CN=Test");
        CertificateSubjectName csn = new CertificateSubjectName(name);
        csn.delete("dname");
        csn.delete("dname");
        if (!csn.toString().isEmpty()) {
            throw new RuntimeException("Name not empty after repeated delete: " + csn.toString());
        }
    }

    private static void expectIOException(CertificateSubjectName csn, String attr) {
        try {
            csn.delete(attr);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute: \"" + attr + "\"");
    }
}
