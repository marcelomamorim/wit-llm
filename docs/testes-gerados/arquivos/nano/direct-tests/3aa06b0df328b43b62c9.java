/* @test    @summary Test constructor X500Name(String, String) with valid and invalid inputs    @run main X500NameWitupTest */

import sun.security.x509.X500Name;
import java.io.IOException;

public class X500NameWitupTest {

    public static void main(String[] args) {
        testValidRFC2253();
        testValidDEFAULT();
        testInvalidFormat();
        testNullDname();
    }

    static void testValidRFC2253() {
        try {
            X500Name name = new X500Name("CN=Test, O=Example", "RFC2253");
            System.out.println("testValidRFC2253 passed");
        } catch (IOException e) {
            throw new RuntimeException("testValidRFC2253 failed", e);
        }
    }

    static void testValidDEFAULT() {
        try {
            X500Name name = new X500Name("CN=Test, O=Example", "DEFAULT");
            System.out.println("testValidDEFAULT passed");
        } catch (IOException e) {
            throw new RuntimeException("testValidDEFAULT failed", e);
        }
    }

    static void testInvalidFormat() {
        try {
            new X500Name("CN=Test, O=Example", "UNKNOWN_FORMAT");
            throw new RuntimeException("testInvalidFormat failed: expected IOException");
        } catch (IOException e) {
            System.out.println("testInvalidFormat passed");
        }
    }

    static void testNullDname() {
        try {
            new X500Name(null, "RFC2253");
            throw new RuntimeException("testNullDname failed: expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullDname passed");
        } catch (IOException e) {
            throw new RuntimeException("testNullDname failed: unexpected IOException", e);
        }
    }
}
