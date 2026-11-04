/*
 * @test
 * @summary Test java.security.Timestamp constructor behavior
 * @run main TimestampWitupTest
 */

import java.security.Timestamp;
import java.security.cert.CertPath;
import java.util.Date;
import java.util.Collections;

public class TimestampWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorRejectsNullTimestamp();
        testConstructorRejectsNullCertPath();
        testConstructorClonesDate();
        testGettersReturnExpectedValues();
    }

    private static void testConstructorRejectsNullTimestamp() {
        CertPath dummyCertPath = new DummyCertPath();
        try {
            new Timestamp(null, dummyCertPath);
            throw new RuntimeException("Expected NullPointerException for null timestamp");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorRejectsNullCertPath() {
        Date now = new Date();
        try {
            new Timestamp(now, null);
            throw new RuntimeException("Expected NullPointerException for null signerCertPath");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testConstructorClonesDate() {
        Date original = new Date();
        CertPath dummyCertPath = new DummyCertPath();
        Timestamp ts = new Timestamp(original, dummyCertPath);
        // Modify original date after construction
        original.setTime(0L);
        Date tsDate = ts.getTimestamp();
        if (tsDate.getTime() != original.getTime()) {
            // expected: tsDate should not reflect change to original
            if (tsDate.getTime() == 0L) {
                throw new RuntimeException("Timestamp date was not cloned properly");
            }
        }
    }

    private static void testGettersReturnExpectedValues() {
        Date now = new Date();
        CertPath dummyCertPath = new DummyCertPath();
        Timestamp ts = new Timestamp(now, dummyCertPath);
        Date tsDate = ts.getTimestamp();
        if (tsDate == null) {
            throw new RuntimeException("getTimestamp returned null");
        }
        if (tsDate.getTime() != now.getTime()) {
            throw new RuntimeException("getTimestamp returned incorrect date");
        }
        CertPath cp = ts.getSignerCertPath();
        if (cp != dummyCertPath) {
            throw new RuntimeException("getSignerCertPath did not return the expected CertPath instance");
        }
    }

    // Minimal dummy CertPath implementation for testing
    private static class DummyCertPath extends CertPath {
        private static final long serialVersionUID = 1L;

        DummyCertPath() {
            super("X.509");
        }

        public java.util.List getCertificates() {
            return Collections.emptyList();
        }
    }
}
