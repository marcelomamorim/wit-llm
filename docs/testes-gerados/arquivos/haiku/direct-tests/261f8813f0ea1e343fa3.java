/*
 * @test
 * @summary Verify BasicConstraintsExtension.delete accepts known attribute names case-insensitively and rejects unknown names
 * @modules java.base/sun.security.x509
 * @run main BasicConstraintsExtensionWitupTest
 */

import java.io.IOException;

import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        ext.delete(BasicConstraintsExtension.IS_CA);
        ext.delete(BasicConstraintsExtension.PATH_LEN);
        ext.delete("IS_CA");
        ext.delete("path_len");

        BasicConstraintsExtension nonCa = new BasicConstraintsExtension(Boolean.FALSE, false, -1);
        nonCa.delete("is_ca");
        nonCa.delete("path_len");

        expectIOException(ext, "unknown");
        expectIOException(ext, "");
        expectIOException(nonCa, "basic");
    }

    private static void expectIOException(BasicConstraintsExtension ext, String name) throws Exception {
        try {
            ext.delete(name);
        } catch (IOException expected) {
            return;
        }
        throw new RuntimeException("Expected IOException for attribute name: " + name);
    }
}
