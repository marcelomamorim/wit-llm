/*
 * @test
 * @summary Verify the no-argument NetscapeCertTypeExtension constructor sets the extension id and critical flag
 * @modules java.base/sun.security.x509
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        check(ext != null, "constructor returned null");
        check(NetscapeCertTypeExtension.NetscapeCertType_Id.equals(ext.getExtensionId()),
              "extension id does not match NetscapeCertType_Id");
        check(ext.isCritical(), "extension should be critical by default");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
