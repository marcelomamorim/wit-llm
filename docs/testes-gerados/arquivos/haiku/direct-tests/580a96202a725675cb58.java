/*
 * @test
 * @summary X500Name(byte[]) must decode DER produced by getEncoded() and reject malformed input with IOException
 * @modules java.base/sun.security.x509
 * @run main X500NameWitupTest
 */

import sun.security.x509.X500Name;
import java.io.IOException;
import java.util.Arrays;

public class X500NameWitupTest {

    public static void main(String[] args) throws Exception {
        X500Name original = new X500Name("CN=Duke, OU=JDK, O=Oracle, C=US");
        byte[] der = original.getEncoded();

        X500Name decoded = new X500Name(der);

        if (!original.toString().equals(decoded.toString())) {
            throw new RuntimeException("Decoded name differs: " + decoded);
        }
        if (!original.equals(decoded)) {
            throw new RuntimeException("Decoded name not equal to original");
        }
        if (!Arrays.equals(der, decoded.getEncoded())) {
            throw new RuntimeException("Re-encoded DER differs from input");
        }

        try {
            new X500Name(new byte[] {0x02, 0x01, 0x00});
            throw new RuntimeException("Expected IOException for non-SEQUENCE input");
        } catch (IOException expected) {
        }

        try {
            new X500Name(new byte[] {0x30, 0x05, 0x01});
            throw new RuntimeException("Expected IOException for truncated input");
        } catch (IOException expected) {
        }
    }
}
