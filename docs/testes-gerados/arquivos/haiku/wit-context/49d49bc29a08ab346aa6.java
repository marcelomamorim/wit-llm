/*
 * @test
 * @summary Witup-generated checks for CertificateVersion.encode
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main CertificateVersionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerInputStream;
import sun.security.x509.CertificateVersion;

public class CertificateVersionWitupTest {

    public static void main(String[] args) throws Exception {
        checkConstants();
        checkDefaultEncodesNothing();
        checkV2Encoding();
        checkV3Encoding();
        checkRoundTrip();
        checkInvalidVersions();
    }

    static void checkConstants() {
        check(CertificateVersion.V1 == 0, "V1 must be 0");
        check(CertificateVersion.V2 == 1, "V2 must be 1");
        check(CertificateVersion.V3 == 2, "V3 must be 2");
    }

    static void checkDefaultEncodesNothing() throws Exception {
        byte[] out = encode(new CertificateVersion());
        check(out.length == 0, "default version must encode nothing");
    }

    static void checkV2Encoding() throws Exception {
        byte[] expected = {(byte)0xA0, 3, 2, 1, 1};
        byte[] out = encode(new CertificateVersion(CertificateVersion.V2));
        check(Arrays.equals(expected, out), "V2 encoding mismatch");
    }

    static void checkV3Encoding() throws Exception {
        byte[] expected = {(byte)0xA0, 3, 2, 1, 2};
        byte[] out = encode(new CertificateVersion(CertificateVersion.V3));
        check(Arrays.equals(expected, out), "V3 encoding mismatch");
    }

    static void checkRoundTrip() throws Exception {
        byte[] data = {(byte)0xA0, 3, 2, 1, 2};
        CertificateVersion cv = new CertificateVersion(new DerInputStream(data));
        check(Arrays.equals(data, encode(cv)), "round trip mismatch");
    }

    static void checkInvalidVersions() {
        int[] bad = {-1, 3, 4};
        for (int v : bad) {
            boolean thrown = false;
            try {
                new CertificateVersion(v);
            } catch (IOException e) {
                thrown = true;
            }
            check(thrown, "expected IOException for version " + v);
        }
    }

    static byte[] encode(CertificateVersion cv) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        cv.encode(bos);
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
