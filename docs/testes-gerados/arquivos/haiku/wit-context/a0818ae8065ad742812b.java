/*
 * @test
 * @summary Verify PrivateKeyUsageExtension.get(String) returns copies of the validity dates, matches names case-insensitively, and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import java.security.cert.CertificateException;
import java.util.Date;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        Date nb = new Date(1000000000000L);
        Date na = new Date(1100000000000L);
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(nb, na);

        if (ext.get(PrivateKeyUsageExtension.NOT_BEFORE).getTime() != nb.getTime()) {
            throw new RuntimeException("not_before mismatch");
        }
        if (ext.get(PrivateKeyUsageExtension.NOT_AFTER).getTime() != na.getTime()) {
            throw new RuntimeException("not_after mismatch");
        }
        if (ext.get("NOT_BEFORE").getTime() != nb.getTime()) {
            throw new RuntimeException("case-insensitive not_before mismatch");
        }
        if (ext.get("Not_After").getTime() != na.getTime()) {
            throw new RuntimeException("case-insensitive not_after mismatch");
        }

        Date returned = ext.get(PrivateKeyUsageExtension.NOT_BEFORE);
        returned.setTime(0L);
        if (ext.get(PrivateKeyUsageExtension.NOT_BEFORE).getTime() != nb.getTime()) {
            throw new RuntimeException("returned date is not a defensive copy");
        }

        boolean thrown = false;
        try {
            ext.get("unknown_attribute");
        } catch (CertificateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected CertificateException for unrecognized name");
        }
    }
}
