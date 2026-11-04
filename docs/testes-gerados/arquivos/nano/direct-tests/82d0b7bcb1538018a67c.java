/* @test    @summary Test BasicConstraintsExtension.get(String) method for known attribute names    @run main BasicConstraintsExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testGetIsCa();
        testGetPathLen();
        testGetUnknownAttribute();
    }

    private static void testGetIsCa() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        Object result = ext.get("is_ca");
        if (!(result instanceof Boolean)) {
            throw new AssertionError("Expected Boolean for is_ca, got: " + result.getClass());
        }
        if (!((Boolean) result).booleanValue()) {
            throw new AssertionError("Expected true for is_ca, got false");
        }
    }

    private static void testGetPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 5);
        Object result = ext.get("path_len");
        if (!(result instanceof Integer)) {
            throw new AssertionError("Expected Integer for path_len, got: " + result.getClass());
        }
        if (((Integer) result).intValue() != 5) {
            throw new AssertionError("Expected 5 for path_len, got: " + result);
        }
    }

    private static void testGetUnknownAttribute() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 0);
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
