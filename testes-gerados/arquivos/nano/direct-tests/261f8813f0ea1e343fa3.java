/* @test    @summary Test delete method of BasicConstraintsExtension class    @run main BasicConstraintsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testDeleteCaAttribute();
        testDeletePathLenAttribute();
        testDeleteUnrecognizedAttribute();
    }

    static void testDeleteCaAttribute() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        ext.delete("is_ca");
        assert !ext.ca : "Expected ca to be false after delete";
        assert ext.pathLen == 3 : "Expected pathLen to remain 3";
    }

    static void testDeletePathLenAttribute() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 5);
        ext.delete("path_len");
        assert ext.ca : "Expected ca to remain true";
        assert ext.pathLen == -1 : "Expected pathLen to be -1 after delete";
    }

    static void testDeleteUnrecognizedAttribute() {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        boolean exceptionThrown = false;
        try {
            ext.delete("unknown_attribute");
        } catch (IOException e) {
            exceptionThrown = true;
            assert e.getMessage().contains("Attribute name not recognized") : "Unexpected exception message";
        }
        assert exceptionThrown : "Expected IOException to be thrown for unrecognized attribute";
    }
}
