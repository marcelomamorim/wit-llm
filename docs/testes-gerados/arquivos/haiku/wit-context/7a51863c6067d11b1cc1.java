/*
 * @test
 * @summary Basic observable behavior of NetscapeCertTypeExtension(boolean[]) constructor
 * @modules java.base/sun.security.x509
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructWithSslBits();
        testConstructWithAllFalseBits();
        testConstructWithAllBitsSet();
        testConstantsUnchanged();
    }

    static void testConstructWithSslBits() throws IOException {
        boolean[] bits = new boolean[] { true, true, false, false, false, false, false, false };
        Object ext = new NetscapeCertTypeExtension(bits);
        check(ext != null, "constructor returned null");
        check(ext instanceof NetscapeCertTypeExtension, "unexpected type");
    }

    static void testConstructWithAllFalseBits() throws IOException {
        boolean[] bits = new boolean[8];
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        check(ext != null, "constructor returned null for all-false bits");
    }

    static void testConstructWithAllBitsSet() throws IOException {
        boolean[] bits = new boolean[] { true, true, true, true, false, true, true, true };
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        check(ext != null, "constructor returned null for all-true bits");
    }

    static void testConstantsUnchanged() {
        check(NetscapeCertTypeExtension.NAME.equals("NetscapeCertType"), "NAME changed");
        check(NetscapeCertTypeExtension.SSL_CLIENT.equals("ssl_client"), "SSL_CLIENT changed");
        check(NetscapeCertTypeExtension.SSL_SERVER.equals("ssl_server"), "SSL_SERVER changed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
