/* @test    @summary Test delete method of BasicConstraintsExtension for attribute removal and exception handling    @run main BasicConstraintsExtensionWitupTest */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteIsCaAttribute();
        testDeletePathLenAttribute();
        testDeleteUnrecognizedAttribute();
    }

    private static void testDeleteIsCaAttribute() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        ext.delete("is_ca");
        assert ext.ca == false : "Expected ca to be false after delete("is_ca")";
        System.out.println("testDeleteIsCaAttribute passed.");
    }

    private static void testDeletePathLenAttribute() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        ext.delete("path_len");
        assert ext.pathLen == -1 : "Expected pathLen to be -1 after delete("path_len")";
        System.out.println("testDeletePathLenAttribute passed.");
    }

    private static void testDeleteUnrecognizedAttribute() {
        BasicConstraintsExtension ext;
        try {
            ext = new BasicConstraintsExtension(true, 3);
            ext.delete("unknown_attribute");
            assert false : "Expected IOException for unrecognized attribute";
        } catch (IOException e) {
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message";
            System.out.println("testDeleteUnrecognizedAttribute passed.");
        }
    }
}
