/*
 * @test
 * @summary Constructor validates nulls and defensively copies the timestamp Date
 * @run main TimestampWitupTest
 */

import java.security.cert.Certificate;
import java.security.cert.CertPath;
import java.security.cert.CertificateEncodingException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TimestampWitupTest {

    static class StubPath extends CertPath {
        StubPath() {
            super("X.509");
        }

        public List<? extends Certificate> getCertificates() {
            return Collections.emptyList();
        }

        public byte[] getEncoded() throws CertificateEncodingException {
            return new byte[0];
        }

        public byte[] getEncoded(String encoding) throws CertificateEncodingException {
            return new byte[0];
        }

        public Iterator<String> getEncodings() {
            return Collections.<String>emptyIterator();
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Date date = new Date(1000L);
        CertPath path = new StubPath();
        Timestamp ts = new Timestamp(date, path);

        check(ts.getTimestamp().getTime() == 1000L, "timestamp time mismatch");
        check(ts.getSignerCertPath() == path, "signer cert path should be the same reference");

        date.setTime(2000L);
        check(ts.getTimestamp().getTime() == 1000L,
              "constructor must defensively copy the Date");

        check(ts.getTimestamp() != ts.getTimestamp(),
              "getTimestamp should return a fresh clone");

        try {
            new Timestamp(null, path);
            throw new RuntimeException("expected NullPointerException for null timestamp");
        } catch (NullPointerException expected) {
        }

        try {
            new Timestamp(new Date(0L), null);
            throw new RuntimeException("expected NullPointerException for null signerCertPath");
        } catch (NullPointerException expected) {
        }
    }
}
