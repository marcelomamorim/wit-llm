/*
 * @test
 * @summary Test default constructor of NetscapeCertTypeExtension
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        if (ext.extensionId == null) {
            throw new RuntimeException("extensionId must not be null");
        }
        if (!ext.critical) {
            throw new RuntimeException("critical must be true by default");
        }
        if (ext.bitString == null) {
            throw new RuntimeException("bitString must not be null");
        }
        if (ext.bitString.length != 0) {
            throw new RuntimeException("bitString length must be 0");
        }
    }
}
