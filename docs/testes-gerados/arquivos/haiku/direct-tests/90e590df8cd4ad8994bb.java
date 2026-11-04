/*
 * @test
 * @summary Verifies the public no-arg constructor of NetscapeCertTypeExtension sets the Netscape cert type OID and critical flag
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 * @run main NetscapeCertTypeExtensionWitupTest
 */

import java.util.Objects;

import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    private static final String EXPECTED_OID = "2.16.840.1.113730.1.1";

    public static void main(String[] args) throws Exception {
        testCriticalIsTrue();
        testExtensionIdMatchesStaticOid();
        testExtensionIdStringValue();
        testTwoInstancesAreEquivalent();
    }

    private static void testCriticalIsTrue() {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        if (!ext.isCritical()) {
            throw new RuntimeException("Expected critical flag to be true");
        }
    }

    private static void testExtensionIdMatchesStaticOid() {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        Object id = ext.getExtensionId();
        Object expected = NetscapeCertTypeExtension.NetscapeCertType_Id;
        if (!Objects.equals(id, expected)) {
            throw new RuntimeException("Extension id does not match NetscapeCertType_Id");
        }
    }

    private static void testExtensionIdStringValue() {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        Object id = ext.getExtensionId();
        String s = String.valueOf(id);
        if (!EXPECTED_OID.equals(s)) {
            throw new RuntimeException("Unexpected OID string: " + s);
        }
    }

    private static void testTwoInstancesAreEquivalent() {
        NetscapeCertTypeExtension a = new NetscapeCertTypeExtension();
        NetscapeCertTypeExtension b = new NetscapeCertTypeExtension();
        if (!Objects.equals(a.getExtensionId(), b.getExtensionId())) {
            throw new RuntimeException("Extension ids differ between instances");
        }
        if (a.isCritical() != b.isCritical()) {
            throw new RuntimeException("Critical flag differs between instances");
        }
    }
}
