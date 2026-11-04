/*
 * @test
 * @summary Witup-derived checks for InhibitAnyPolicyExtension.set exception paths
 * @modules java.base/sun.security.x509
 * @run main InhibitAnyPolicyExtensionWitupTest
 */

import java.io.IOException;
import sun.security.x509.InhibitAnyPolicyExtension;

public class InhibitAnyPolicyExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        InhibitAnyPolicyExtension ext = new InhibitAnyPolicyExtension(0);

        checkSetThrows(ext, "unknown_name", Integer.valueOf(1));
        checkSetThrows(ext, "", Integer.valueOf(1));
        checkSetThrows(ext, InhibitAnyPolicyExtension.NAME, Integer.valueOf(1));
        checkSetThrows(ext, InhibitAnyPolicyExtension.SKIP_CERTS, "1");
        checkSetThrows(ext, InhibitAnyPolicyExtension.SKIP_CERTS, Long.valueOf(1L));
        checkSetThrows(ext, InhibitAnyPolicyExtension.SKIP_CERTS, null);
        checkSetThrows(ext, InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(-2));
        checkSetThrows(ext, "SKIP_CERTS", Integer.valueOf(-2));

        checkSetOk(ext, InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(-1));
        checkSetOk(ext, InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(0));
        checkSetOk(ext, InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(3));
        checkSetOk(ext, "Skip_Certs", Integer.valueOf(3));
        checkSetOk(ext, "SKIP_CERTS", Integer.valueOf(-1));

        checkSetThrows(ext, InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(-5));
        checkSetOk(ext, InhibitAnyPolicyExtension.SKIP_CERTS, Integer.valueOf(2));
    }

    static void checkSetThrows(InhibitAnyPolicyExtension ext, String name, Object value) {
        try {
            ext.set(name, value);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for name=" + name + " value=" + value);
    }

    static void checkSetOk(InhibitAnyPolicyExtension ext, String name, Object value) throws IOException {
        ext.set(name, value);
    }
}
