/*
 * @test
 * @summary Verifies that the boolean[] constructor of NetscapeCertTypeExtension yields a critical extension with the Netscape-Cert-Type identifier
 * @modules java.base/sun.security.x509
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        if (NetscapeCertTypeExtension.NetscapeCertType_Id == null) {
            throw new RuntimeException("NetscapeCertType_Id must not be null");
        }

        boolean[] bits = {true, false, true, false, false, false, true, false};
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension(bits);
        if (!ext.isCritical()) {
            throw new RuntimeException("extension must be critical");
        }
        if (!ext.getExtensionId().equals(NetscapeCertTypeExtension.NetscapeCertType_Id)) {
            throw new RuntimeException("unexpected extension id");
        }

        NetscapeCertTypeExtension empty = new NetscapeCertTypeExtension(new boolean[8]);
        if (!empty.isCritical()) {
            throw new RuntimeException("all-false extension must be critical");
        }
        if (!empty.getExtensionId().equals(NetscapeCertTypeExtension.NetscapeCertType_Id)) {
            throw new RuntimeException("unexpected extension id for all-false bits");
        }
    }
}
