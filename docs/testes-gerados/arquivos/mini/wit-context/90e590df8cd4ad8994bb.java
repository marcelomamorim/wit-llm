/*
 * @test
 * @summary Test default constructor of NetscapeCertTypeExtension
 * @run main NetscapeCertTypeExtensionWitupTest
 */

public class NetscapeCertTypeExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        // Test default constructor
        sun.security.x509.NetscapeCertTypeExtension ext = new sun.security.x509.NetscapeCertTypeExtension();

        // Check that extensionId is set to NetscapeCertType_Id
        if (ext.extensionId == null) {
            throw new AssertionError("extensionId should not be null");
        }

        // Check that critical is true
        if (!ext.critical) {
            throw new AssertionError("critical should be true");
        }

        // Check that bitString is initialized to empty array
        if (ext.bitString == null) {
            throw new AssertionError("bitString should not be null");
        }
        if (ext.bitString.length != 0) {
            throw new AssertionError("bitString length should be 0");
        }
    }
}
