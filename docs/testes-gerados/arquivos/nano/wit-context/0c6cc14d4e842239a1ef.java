/* @test    @summary Test for Timestamp constructor null arguments to verify NullPointerException is thrown.    @run main TimestampWitupTest */

import java.security.Timestamp;
import java.security.cert.CertPath;
import java.util.Date;

public class TimestampWitupTest {

    public static void main(String[] args) {
        testNullTimestamp();
        testNullSignerCertPath();
    }

    private static void testNullTimestamp() {
        CertPath dummyCertPath = null; // placeholder, actual CertPath object not needed for exception test
        try {
            new Timestamp(null, dummyCertPath);
            System.err.println("Failed: Expected NullPointerException for null timestamp");
        } catch (NullPointerException expected) {
            System.out.println("Passed: NullPointerException thrown for null timestamp");
        }
    }

    private static void testNullSignerCertPath() {
        Date now = new Date();
        try {
            new Timestamp(now, null);
            System.err.println("Failed: Expected NullPointerException for null signerCertPath");
        } catch (NullPointerException expected) {
            System.out.println("Passed: NullPointerException thrown for null signerCertPath");
        }
    }
}
