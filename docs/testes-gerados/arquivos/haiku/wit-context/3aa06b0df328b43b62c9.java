/*
 * @test
 * @summary X500Name(String, String) constructor: null DN, unsupported format, and valid RFC2253/DEFAULT parsing
 * @modules java.base/sun.security.x509
 * @run main X500NameWitupTest
 */

import java.io.IOException;
import sun.security.x509.X500Name;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        testNullDnWithRfc2253();
        testNullDnWithDefault();
        testUnsupportedFormat();
        testValidRfc2253();
        testValidDefaultCaseInsensitive();
    }

    static void testNullDnWithRfc2253() {
        try {
            new X500Name(null, "RFC2253");
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        throw new RuntimeException("expected NullPointerException for null dname");
    }

    static void testNullDnWithDefault() {
        try {
            new X500Name(null, "DEFAULT");
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        throw new RuntimeException("expected NullPointerException for null dname");
    }

    static void testUnsupportedFormat() throws Exception {
        try {
            new X500Name("CN=Duke", "UNKNOWN");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Unsupported format")) {
                throw new RuntimeException("unexpected message: " + msg);
            }
            return;
        }
        throw new RuntimeException("expected IOException for unsupported format");
    }

    static void testValidRfc2253() throws IOException {
        X500Name name = new X500Name("CN=Duke,OU=JavaSoft,O=Sun,C=US", "RFC2253");
        String rfc = name.getRFC2253Name();
        if (rfc == null || !rfc.contains("CN=Duke")) {
            throw new RuntimeException("unexpected RFC2253 name: " + rfc);
        }
    }

    static void testValidDefaultCaseInsensitive() throws IOException {
        X500Name name = new X500Name("CN=Duke, OU=JavaSoft, O=Sun, C=US", "default");
        String str = name.toString();
        if (str == null || !str.contains("CN=Duke")) {
            throw new RuntimeException("unexpected name: " + str);
        }
    }
}
