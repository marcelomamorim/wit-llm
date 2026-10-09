/* @test    @summary Teste do método get de BasicConstraintsExtension para diferentes nomes de atributo    @run main BasicConstraintsExtensionWitupTest_Pa66d6459528c */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest_Pa66d6459528c {

    public static void main(String[] args) throws Exception {
        testGetIsCa();
        testGetPathLen();
        testGetInvalidAttribute();
    }

    private static void testGetIsCa() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        Object result = ext.get("is_ca");
        if (!(result instanceof Boolean && ((Boolean) result).booleanValue() == true)) {
            throw new AssertionError("Expected true for 'is_ca', got: " + result);
        }
    }

    private static void testGetPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        Object result = ext.get("path_len");
        if (!(result instanceof Integer && ((Integer) result).intValue() == 3)) {
            throw new AssertionError("Expected 3 for 'path_len', got: " + result);
        }
    }

    private static void testGetInvalidAttribute() {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, -1);
        try {
            ext.get("unknown_attribute");
            throw new AssertionError("Expected IOException for unknown attribute");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
