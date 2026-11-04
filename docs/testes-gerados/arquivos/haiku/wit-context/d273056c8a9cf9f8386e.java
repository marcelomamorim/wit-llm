/*
 * @test
 * @summary Checks public encode(OutputStream) output for PrivateKeyUsageExtension built with notBefore/notAfter dates
 * @modules java.base/sun.security.x509
 * @run main PrivateKeyUsageExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Date;

import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        Date notBefore = new Date(1000000000000L);
        Date notAfter = new Date(1100000000000L);

        PrivateKeyUsageExtension both = new PrivateKeyUsageExtension(notBefore, notAfter);
        ByteArrayOutputStream out1 = new ByteArrayOutputStream();
        both.encode(out1);
        byte[] enc1 = out1.toByteArray();

        if (enc1.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (enc1[0] != 0x30) {
            throw new RuntimeException("encoding does not start with SEQUENCE tag: " + enc1[0]);
        }

        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        both.encode(out2);
        if (!Arrays.equals(enc1, out2.toByteArray())) {
            throw new RuntimeException("repeated encode produced different bytes");
        }

        PrivateKeyUsageExtension onlyBefore = new PrivateKeyUsageExtension(notBefore, null);
        ByteArrayOutputStream out3 = new ByteArrayOutputStream();
        onlyBefore.encode(out3);
        byte[] enc3 = out3.toByteArray();
        if (enc3.length == 0 || enc3[0] != 0x30) {
            throw new RuntimeException("notBefore-only encoding is not a valid SEQUENCE");
        }
        if (Arrays.equals(enc1, enc3)) {
            throw new RuntimeException("notBefore-only encoding equals notBefore+notAfter encoding");
        }
    }
}
