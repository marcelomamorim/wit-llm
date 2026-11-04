/*
 * @test
 * @summary Test sun.security.x509.X500Name constructor with String and format
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testNullDnameThrowsNPE();
        testUnsupportedFormatThrowsIOException();
        testValidFormatsDoNotThrow();
    }

    private static void testNullDnameThrowsNPE() {
        try {
            new X500Name(null, "RFC2253");
            throw new RuntimeException("Expected NullPointerException for null dname");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException for null dname", e);
        }
    }

    private static void testUnsupportedFormatThrowsIOException() {
        try {
            new X500Name("CN=Test", "UNSUPPORTED_FORMAT");
            throw new RuntimeException("Expected IOException for unsupported format");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testValidFormatsDoNotThrow() throws IOException {
        // RFC2253 format
        X500Name name1 = new X500Name("CN=Test", "RFC2253");
        if (name1 == null) {
            throw new RuntimeException("X500Name constructor returned null for RFC2253 format");
        }

        // DEFAULT format
        X500Name name2 = new X500Name("CN=Test", "DEFAULT");
        if (name2 == null) {
            throw new RuntimeException("X500Name constructor returned null for DEFAULT format");
        }
    }
}
