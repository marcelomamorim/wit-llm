/*
 * @test
 * @summary Witup-generated checks for AuthorityKeyIdentifierExtension.encode(OutputStream) with an empty extension value
 * @modules java.base/sun.security.x509
 * @run main AuthorityKeyIdentifierExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        AuthorityKeyIdentifierExtension ext =
                new AuthorityKeyIdentifierExtension(null, null, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ext.encode(out);
        } catch (IOException e) {
            return;
        }
        byte[] data = out.toByteArray();
        if (data.length > 0 && data[0] != 0x30) {
            throw new RuntimeException("Encoded output is not a DER SEQUENCE");
        }
    }
}
