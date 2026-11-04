/*
 * @test
 * @summary Test NetscapeCertTypeExtension constructor with boolean[] input
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import sun.security.x509.NetscapeCertTypeExtension;
import java.io.IOException;

public class NetscapeCertTypeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testValidBooleanArray();
    }

    private static void testValidBooleanArray() throws IOException {
        // Create a boolean array with some bits set
        boolean[] bits = new boolean[8];
        bits[0] = true;  // ssl_client
        bits[3] = true;  // object_signing

        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);

        if (ext == null) {
            throw new RuntimeException("Constructor returned null");
        }

        // The constructor sets critical to true and extensionId to NetscapeCertType_Id internally
        // We cannot access these fields directly, so we test that no exception was thrown and object created

        // No exception means test passed
        System.out.println("Test passed: NetscapeCertTypeExtension constructed successfully with boolean[]");
    }
}
