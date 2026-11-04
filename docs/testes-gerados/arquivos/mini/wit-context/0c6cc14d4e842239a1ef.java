/*
 * @test
 * @summary Test constructor of java.security.Timestamp for null checks and cloning behavior
 * @run main TimestampWitupTest
 */

import java.security.Timestamp;
import java.security.cert.CertPath;
import java.util.Date;
import java.util.Collections;
import java.util.List;
import java.security.cert.Certificate;

public class TimestampWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorNullTimestamp();
        testConstructorNullCertPath();
        testConstructorClonesDate();
        testGetTimestampReturnsClone();
    }

    private static CertPath createDummyCertPath() {
        // Minimal dummy CertPath implementation for testing
        return new CertPath("X.509") {
            private static final long serialVersionUID = 1L;
            public List<? extends Certificate> getCertificates() {
                return Collections.emptyList();
            }
        };
    }

    private static void testConstructorNullTimestamp() {
        CertPath cp = createDummyCertPath();
        try {
            new Timestamp(null, cp);
            throw new RuntimeException("Expected NullPointerException for null timestamp");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorNullCertPath() {
        Date now = new Date();
        try {
            new Timestamp(now, null);
            throw new RuntimeException("Expected NullPointerException for null signerCertPath");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorClonesDate() {
        Date now = new Date();
        CertPath cp = createDummyCertPath();
        Timestamp ts = new Timestamp(now, cp);
        // Modify original date after construction
        now.setTime(0L);
        Date tsDate = ts.getTimestamp();
        if (tsDate.getTime() == 0L) {
            throw new RuntimeException("Timestamp constructor did not clone Date parameter");
        }
    }

    private static void testGetTimestampReturnsClone() {
        Date now = new Date();
        CertPath cp = createDummyCertPath();
        Timestamp ts = new Timestamp(now, cp);
        Date d1 = ts.getTimestamp();
        d1.setTime(0L);
        Date d2 = ts.getTimestamp();
        if (d2.getTime() == 0L) {
            throw new RuntimeException("getTimestamp() did not return a clone");
        }
    }
}
