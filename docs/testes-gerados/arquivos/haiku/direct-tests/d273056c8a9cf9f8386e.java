/*
 * @test
 * @summary Checks the public encode(OutputStream) behavior of PrivateKeyUsageExtension
 * @modules java.base/sun.security.x509
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static byte[] encode(PrivateKeyUsageExtension ext) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ext.encode(bos);
        return bos.toByteArray();
    }

    static void testBothDatesProducesSequence() throws Exception {
        PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension(
                new Date(0L), new Date(1000000000000L));
        byte[] enc = encode(ext);
        check(enc.length > 0, "encoding must not be empty");
        check(enc[0] == 0x30, "encoding must start with SEQUENCE tag 0x30");
    }

    static void testEncodingIsDeterministic() throws Exception {
        PrivateKeyUsageExtension a = new PrivateKeyUsageExtension(
                new Date(0L), new Date(1000000000000L));
        PrivateKeyUsageExtension b = new PrivateKeyUsageExtension(
                new Date(0L), new Date(1000000000000L));
        byte[] ea = encode(a);
        byte[] eb = encode(b);
        check(java.util.Arrays.equals(ea, eb),
                "same dates must produce identical encodings");
        byte[] ea2 = encode(a);
        check(java.util.Arrays.equals(ea, ea2),
                "repeated encode calls must be stable");
    }

    static void testDifferentDatesProduceDifferentEncodings() throws Exception {
        PrivateKeyUsageExtension a = new PrivateKeyUsageExtension(
                new Date(0L), new Date(1000000000000L));
        PrivateKeyUsageExtension b = new PrivateKeyUsageExtension(
                new Date(0L), new Date(2000000000000L));
        check(!java.util.Arrays.equals(encode(a), encode(b)),
                "different notAfter must change encoding");
    }

    static void testNotBeforeOnlyDiffersFromBoth() throws Exception {
        PrivateKeyUsageExtension both = new PrivateKeyUsageExtension(
                new Date(0L), new Date(1000000000000L));
        PrivateKeyUsageExtension onlyBefore = new PrivateKeyUsageExtension(
                new Date(0L), null);
        byte[] eb = encode(both);
        byte[] eo = encode(onlyBefore);
        check(eo.length > 0, "encoding with only notBefore must not be empty");
        check(!java.util.Arrays.equals(eb, eo),
                "omitting notAfter must change encoding");
    }

    public static void main(String[] args) throws Exception {
        testBothDatesProducesSequence();
        testEncodingIsDeterministic();
        testDifferentDatesProduceDifferentEncodings();
        testNotBeforeOnlyDiffersFromBoth();
    }
}
