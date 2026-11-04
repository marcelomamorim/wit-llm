/*
 * @test
 * @summary Test sun.security.x509.X500Name constructor with String and format
 * @run main sun.security.x509.X500NameWitupTest
 */

import sun.security.x509.X500Name;
import java.io.IOException;

public class X500NameWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrows();
        testUnsupportedFormatThrows();
        testFormatRFC2253Parses();
        testFormatDefaultParses();
    }

    private static void testNullNameThrows() {
        try {
            new X500Name(null, "RFC2253");
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testUnsupportedFormatThrows() {
        try {
            new X500Name("CN=Test", "UNSUPPORTED");
            throw new RuntimeException("Expected IOException for unsupported format");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testFormatRFC2253Parses() throws IOException {
        // RFC2253 format example
        X500Name name = new X500Name("CN=Test,OU=Dev,O=Example,C=US", "RFC2253");
        if (name == null) {
            throw new RuntimeException("X500Name constructor returned null for RFC2253 format");
        }
    }

    private static void testFormatDefaultParses() throws IOException {
        // DEFAULT format example
        X500Name name = new X500Name("CN=Test,OU=Dev,O=Example,C=US", "DEFAULT");
        if (name == null) {
            throw new RuntimeException("X500Name constructor returned null for DEFAULT format");
        }
    }
}
