/*
 * @test
 * @summary Verify CertificateVersion.delete resets the version for "number" (case-insensitive) and rejects unknown attribute names
 * @modules java.base/sun.security.x509
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    static byte[] enc(CertificateVersion cv) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream out = bos;
        cv.encode(out);
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] defaultEnc = enc(new CertificateVersion());

        // delete("number") resets a V3 version back to V1 (default encoding)
        CertificateVersion v3 = new CertificateVersion(CertificateVersion.V3);
        check(!java.util.Arrays.equals(defaultEnc, enc(v3)),
              "V3 encoding should differ from default V1 encoding");
        v3.delete("number");
        check(java.util.Arrays.equals(defaultEnc, enc(v3)),
              "delete(\"number\") should reset version to V1");

        // delete is case-insensitive via equalsIgnoreCase
        CertificateVersion v2 = new CertificateVersion(CertificateVersion.V2);
        v2.delete("NUMBER");
        check(java.util.Arrays.equals(defaultEnc, enc(v2)),
              "delete(\"NUMBER\") should reset version to V1");

        // Unknown attribute name must throw IOException and leave state unchanged
        CertificateVersion v2b = new CertificateVersion(CertificateVersion.V2);
        byte[] v2Enc = enc(v2b);
        boolean thrown = false;
        try {
            v2b.delete("bogus");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete(\"bogus\") should throw IOException");
        check(java.util.Arrays.equals(v2Enc, enc(v2b)),
              "failed delete must not change the version");

        // Deleting on a default (V1) object keeps default encoding
        CertificateVersion def = new CertificateVersion();
        def.delete("number");
        check(java.util.Arrays.equals(defaultEnc, enc(def)),
              "delete on V1 should keep V1 encoding");
    }
}
