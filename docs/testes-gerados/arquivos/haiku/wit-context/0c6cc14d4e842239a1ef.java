/*
 * @test
 * @summary Verify Timestamp constructor null checks and defensive copying of the date
 * @run main TimestampWitupTest
 */

import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TimestampWitupTest {

    public static void main(String[] args) throws Exception {
        CertPath path = new EmptyCertPath();

        expectNpe(null, path);
        expectNpe(new Date(1000L), null);
        expectNpe(null, null);

        Date d = new Date(1000L);
        Timestamp ts = new Timestamp(d, path);

        d.setTime(2000L);
        if (ts.getTimestamp().getTime() != 1000L) {
            throw new RuntimeException("Timestamp was affected by mutation of input Date");
        }

        Date copy = ts.getTimestamp();
        copy.setTime(3000L);
        if (ts.getTimestamp().getTime() != 1000L) {
            throw new RuntimeException("Timestamp was affected by mutation of returned Date");
        }

        if (ts.getSignerCertPath() != path) {
            throw new RuntimeException("Unexpected signer certificate path");
        }
    }

    static void expectNpe(Date date, CertPath certPath) {
        try {
            new Timestamp(date, certPath);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException");
    }

    static class EmptyCertPath extends CertPath {
        EmptyCertPath() {
            super("X.509");
        }

        public List<? extends Certificate> getCertificates() {
            return Collections.emptyList();
        }

        public byte[] getEncoded() {
            return new byte[0];
        }

        public Iterator<String> getEncodings() {
            return Collections.emptyIterator();
        }
    }
}
