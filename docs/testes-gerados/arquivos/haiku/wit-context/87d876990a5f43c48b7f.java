/*
 * @test
 * @summary WITUP-generated checks for ExtendedKeyUsageExtension.encode(OutputStream)
 * @modules java.base/sun.security.x509
 *          java.base/sun.security.util
 * @run main ExtendedKeyUsageExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.ExtendedKeyUsageExtension;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        Vector<ObjectIdentifier> usages = new Vector<ObjectIdentifier>();
        ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension(Boolean.FALSE, usages);

        ByteArrayOutputStream first = new ByteArrayOutputStream();
        ext.encode(first);
        byte[] encoded1 = first.toByteArray();

        if (encoded1.length == 0) {
            throw new RuntimeException("encode produced no bytes");
        }
        if (encoded1[0] != 0x30) {
            throw new RuntimeException("outer DER tag is not SEQUENCE: " + encoded1[0]);
        }

        ByteArrayOutputStream second = new ByteArrayOutputStream();
        ext.encode(second);
        byte[] encoded2 = second.toByteArray();

        if (!Arrays.equals(encoded1, encoded2)) {
            throw new RuntimeException("repeated encode produced different bytes");
        }

        ByteArrayOutputStream appended = new ByteArrayOutputStream();
        appended.write(new byte[] {1, 2, 3});
        ext.encode(appended);
        byte[] withPrefix = appended.toByteArray();
        if (withPrefix.length != encoded1.length + 3) {
            throw new RuntimeException("encode did not append to existing stream");
        }
        for (int i = 0; i < encoded1.length; i++) {
            if (withPrefix[i + 3] != encoded1[i]) {
                throw new RuntimeException("appended encoding differs at index " + i);
            }
        }
    }
}
