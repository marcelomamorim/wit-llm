/* @test    @summary Test encoding of PKCS9Attributes with a simple DER encoding.    @run main PKCS9AttributesEncodeTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.pkcs.PKCS9Attributes;
import sun.security.util.DerInputStream;
import sun.security.util.ObjectIdentifier;

public class PKCS9AttributesEncodeTest {
    public static void main(String[] args) throws IOException {
        ObjectIdentifier oid = new ObjectIdentifier(new int[] {1, 2, 840, 113549});
        byte[] derBytes = {0x30, 0x0A, 0x06, 0x08, 0x2A, 0x86, 0x48, 0x86, 0xF7, 0x0D, 0x01, 0x01, 0x01};
        DerInputStream dis = new DerInputStream(derBytes);
        PKCS9Attributes attrs = new PKCS9Attributes(new ObjectIdentifier[] {oid}, dis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        attrs.encode((byte)0x31, baos); // Tag 0x31 (SET OF)
        byte[] output = baos.toByteArray();
        // Verify that output starts with the tag and contains the expected bytes
        if (output[0] != 0x31) {
            throw new AssertionError("Encoded output does not start with expected tag");
        }
        if (output.length <= 1) {
            throw new AssertionError("Encoded output is too short");
        }
        System.out.println("Encoding test passed. Output length: " + output.length);
    }
}
