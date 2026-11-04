/* @test    @summary Test for sun.security.x509.CertificateVersion constructors and behavior    @run main CertificateVersionWitupTest */

import sun.security.util.DerInputStream;
import sun.security.x509.CertificateVersion;
import java.io.IOException;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultConstructor();
        testConstructorWithValidVersions();
        testConstructorWithInvalidVersion();
        testConstructorWithDerInputStream();
    }

    static void testDefaultConstructor() {
        try {
            CertificateVersion cv = new CertificateVersion();
            if (cv != null && cv.getVersion() == 0) {
                System.out.println("Default constructor test passed.");
            } else {
                System.out.println("Default constructor test failed.");
            }
        } catch (Exception e) {
            System.out.println("Default constructor threw exception: " + e);
        }
    }

    static void testConstructorWithValidVersions() {
        try {
            for (int v = 0; v <= 2; v++) {
                CertificateVersion cv = new CertificateVersion(v);
                if (cv != null && cv.getVersion() == v) {
                    System.out.println("Constructor with version " + v + " passed.");
                } else {
                    System.out.println("Constructor with version " + v + " failed.");
                }
            }
        } catch (Exception e) {
            System.out.println("Constructor with valid versions threw exception: " + e);
        }
    }

    static void testConstructorWithInvalidVersion() {
        try {
            new CertificateVersion(5);
            System.out.println("Constructor with invalid version did not throw exception.");
        } catch (IOException e) {
            System.out.println("Constructor with invalid version threw expected exception.");
        } catch (Exception e) {
            System.out.println("Constructor with invalid version threw unexpected exception: " + e);
        }
    }

    static void testConstructorWithDerInputStream() {
        try {
            // Prepare a DerInputStream with a valid DerValue for version 1
            byte[] derBytes = {0x02, 0x01, 0x01}; // INTEGER 1
            DerInputStream dis = new DerInputStream(derBytes);
            CertificateVersion cv = new CertificateVersion(dis);
            if (cv != null && cv.getVersion() == 1) {
                System.out.println("Constructor with DerInputStream passed.");
            } else {
                System.out.println("Constructor with DerInputStream failed.");
            }
        } catch (IOException e) {
            System.out.println("Constructor with DerInputStream threw exception: " + e);
        }
    }
}
