/*
 * @test
 * @summary Test NetscapeCertTypeExtension constructor with boolean[] bitString
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import sun.security.x509.NetscapeCertTypeExtension;
import java.io.IOException;

public class NetscapeCertTypeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testConstructorWithValidBooleanArray();
        testConstructorWithEmptyBooleanArray();
        testConstructorWithNullBooleanArray();
    }

    private static void testConstructorWithValidBooleanArray() throws IOException {
        boolean[] bits = new boolean[8];
        bits[0] = true;  // ssl_client
        bits[3] = true;  // object_signing
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        if (ext == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }

    private static void testConstructorWithEmptyBooleanArray() throws IOException {
        boolean[] bits = new boolean[0];
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        if (ext == null) {
            throw new RuntimeException("Constructor returned null for empty array");
        }
    }

    private static void testConstructorWithNullBooleanArray() {
        try {
            new NetscapeCertTypeExtension(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            // IOException not expected here
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
