/* @test    @summary Test default constructor of NetscapeCertTypeExtension for JDK 11+28    @run main NetscapeCertTypeExtensionWitupTest */

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
        // Additional observable checks can be added if public getters are available
        System.out.println("testDefaultConstructor passed");
    }
}
