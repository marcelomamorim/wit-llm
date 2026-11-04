/*
 * @test
 * @summary Verifies delete() on BasicConstraintsExtension: rejects unknown attribute names and clears is_ca / path_len observably
 * @modules java.base/sun.security.x509
 * @run main BasicConstraintsExtensionWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testUnknownNameThrows();
        testDeleteIsCaCaseInsensitive();
        testDeletePathLen();
    }

    static void testUnknownNameThrows() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.TRUE, true, 3);
        boolean thrown = false;
        try {
            ext.delete("unknown");
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "delete with unrecognized name must throw IOException");
    }

    static void testDeleteIsCaCaseInsensitive() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.TRUE, true, 3);
        ext.delete("IS_CA");
        BasicConstraintsExtension expected = new BasicConstraintsExtension(Boolean.TRUE, false, 0);
        check(java.util.Arrays.equals(enc(ext), enc(expected)),
              "delete(IS_CA) must reset ca to false and re-encode accordingly");
    }

    static void testDeletePathLen() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.TRUE, true, 3);
        ext.delete("path_len");
        BasicConstraintsExtension expected = new BasicConstraintsExtension(Boolean.TRUE, true, -1);
        check(java.util.Arrays.equals(enc(ext), enc(expected)),
              "delete(path_len) must drop pathLen from the encoding");
    }

    static byte[] enc(BasicConstraintsExtension e) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        e.encode(bos);
        return bos.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
