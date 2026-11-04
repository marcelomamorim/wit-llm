/* @test    @summary Test NetscapeCertTypeExtension default constructor behavior    @run main NetscapeCertTypeExtensionWitupTest */

import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        if (ext == null) {
            throw new AssertionError("Constructor returned null");
        }
        // Verify that extensionId is set to NetscapeCertType_Id
        if (ext.extensionId == null) {
            throw new AssertionError("extensionId is null");
        }
        // Verify that critical is true
        if (ext.critical != true) {
            throw new AssertionError("critical is not true");
        }
        // Verify that bitString is initialized to an empty array
        if (ext.bitString == null || ext.bitString.length != 0) {
            throw new AssertionError("bitString is not initialized to empty array");
        }
    }
}
