/*
 * @test
 * @summary Tests X500Name(String, String) constructor for DEFAULT, RFC2253, unsupported and null inputs
 * @modules java.base/sun.security.x509
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import java.io.IOException;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultFormat();
        testDefaultFormatCaseInsensitive();
        testRfc2253Format();
        testRfc2253FormatCaseInsensitive();
        testUnsupportedFormat();
        testNullName();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testDefaultFormat() throws IOException {
        X500Name name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun, C=US", "DEFAULT");
        check(name.toString().contains("CN=Duke"), "DEFAULT format should keep CN value");
        check(name.toString().contains("O=Sun"), "DEFAULT format should keep O value");
    }

    static void testDefaultFormatCaseInsensitive() throws IOException {
        X500Name upper = new X500Name("CN=Duke, O=Sun, C=US", "DEFAULT");
        X500Name lower = new X500Name("CN=Duke, O=Sun, C=US", "default");
        check(upper.equals(lower), "'default' and 'DEFAULT' should yield equal names");
    }

    static void testRfc2253Format() throws IOException {
        X500Name name = new X500Name("CN=Duke,OU=JavaSoft,O=Sun,C=US", "RFC2253");
        check(name.toString().contains("Duke"), "RFC2253 format should keep CN value");
        check(name.toString().contains("Sun"), "RFC2253 format should keep O value");
    }

    static void testRfc2253FormatCaseInsensitive() throws IOException {
        X500Name upper = new X500Name("CN=Duke,O=Sun,C=US", "RFC2253");
        X500Name lower = new X500Name("CN=Duke,O=Sun,C=US", "rfc2253");
        check(upper.equals(lower), "'rfc2253' and 'RFC2253' should yield equal names");
    }

    static void testUnsupportedFormat() {
        boolean thrown = false;
        try {
            new X500Name("CN=Duke", "XYZ");
        } catch (IOException e) {
            thrown = true;
            check(e.getMessage() != null && e.getMessage().contains("XYZ"),
                  "IOException message should mention unsupported format");
        }
        check(thrown, "Unsupported format should throw IOException");
    }

    static void testNullName() {
        boolean thrown = false;
        try {
            new X500Name(null, "DEFAULT");
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Expected NullPointerException, got IOException", e);
        }
        check(thrown, "Null dname should throw NullPointerException");
    }
}
