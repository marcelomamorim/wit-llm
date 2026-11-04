/*
 * @test
 * @summary Checks CodeSigner constructor behavior: null certificate path rejection, accessor results and hashCode with null timestamp
 * @run main CodeSignerWitupTest
 */

import java.security.CodeSigner;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertPath;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CodeSignerWitupTest {

    static final class StubCertPath extends CertPath {
        StubCertPath() {
            super("X.509");
        }

        @Override
        public List<? extends Certificate> getCertificates() {
            return Collections.emptyList();
        }

        @Override
        public byte[] getEncoded() throws CertificateEncodingException {
            return new byte[0];
        }

        @Override
        public Iterator<String> getEncodings() {
            return Collections.<String>emptyList().iterator();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        StubCertPath path = new StubCertPath();

        CodeSigner signer = new CodeSigner(path, null);
        check(signer.getSignerCertPath() == path,
              "getSignerCertPath must return the same CertPath instance");
        check(signer.getTimestamp() == null,
              "getTimestamp must return null when no timestamp is given");
        check(signer.hashCode() == path.hashCode(),
              "hashCode must equal certPath hashCode when timestamp is null");

        boolean npeThrown = false;
        try {
            new CodeSigner(null, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "null signerCertPath must raise NullPointerException");
    }
}
